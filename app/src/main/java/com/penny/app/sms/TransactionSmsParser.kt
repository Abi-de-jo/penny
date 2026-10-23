package com.penny.app.sms

import com.penny.app.data.Direction
import com.penny.app.data.TransactionEntity
import com.penny.app.data.TransactionKind
import com.penny.app.data.TransactionSource
import java.math.BigDecimal
import java.math.RoundingMode
import java.security.MessageDigest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Locale
import java.util.UUID

class TransactionSmsParser(private val allowSynthetic: Boolean = false) {
    private val noiseKeywords = listOf(
        "otp", "one time password", "verification code", "valid for",
        "declined", "failed", "unsuccessful", "insufficient funds",
        "payment request", "requested", "collect request", "mandate requested",
        "due date", "minimum due", "bill generated", "statement generated",
        "pre-approved", "apply now", "loan offer", "win reward", "scratch card"
    )

    private val debitKeywords = Regex(
        "(?i)\\b(debited|debited\\s+by|debited\\s+for|spent|paid|paid\\s+to|sent|sent\\s+to|purchase(?:d)?|withdrawn|withdrew|transferred|transfer\\s+to|deducted|charged)\\b"
    )

    private val creditKeywords = Regex(
        "(?i)\\b(credited|credited\\s+with|credited\\s+by|received|received\\s+from|deposited|refunded|refund|reversed|reversal|cashback|added\\s+to)\\b"
    )

    // Currency prefix or suffix: INR 500, Rs. 500, Rs 500/-, ₹500, 500.00 INR
    private val amountRegex = Regex(
        "(?i)(?:(?:INR|Rs\\.?|₹)\\s*([0-9][0-9,]*(?:\\.[0-9]{1,2})?)|([0-9][0-9,]*(?:\\.[0-9]{1,2})?)\\s*(?:INR|Rs\\.?|₹|/-))"
    )

    // A/c **1234, A/c ending 5678, A/c ending with 4321, from XX4873, Card ending in 9876, a/c ...7890
    private val accountRegex = Regex(
        "(?i)(?:(?:a/?c|account|card)\\s*(?:no\\.?\\s*|ending\\s*(?:with|in)?\\s*|linked\\s+to\\s*)?|from\\s+)(?:[xX]+|\\*+|\\.{2,})?([0-9]{4})\\b"
    )

    // 12-digit Indian UPI UTR / RRN (highest precedence)
    private val upiUtrRegex = Regex(
        "(?i)(?:utr|rrn|upi\\s*(?:ref|txn)?(?:\\s*no\\.?|\\s*id)?)\\s*[:#-]?\\s*([0-9]{12})"
    )

    // Alphanumeric reference (6-25 chars)
    private val referenceRegex = Regex(
        "(?i)(?:ref(?:erence)?|txn|utr|rrn)\\s*(?:no\\.?|id|#|:)?\\s*([A-Za-z0-9-]{6,25})"
    )

    private val generic12DigitRegex = Regex("\\b([0-9]{12})\\b")

    // Merchant / recipient extractors
    private val atMerchantRegex = Regex("(?i)\\bat\\s+([A-Za-z0-9 &._-]{2,30}?)(?:\\s+(?:on|via|using|ref|avl|available|\\.|\\$))")
    private val toRecipientRegex = Regex("(?i)\\b(?:paid|sent|transfer(?:red)?|transferred)?\\s*to\\s+([A-Za-z0-9 &._-]{2,30}?)(?:\\s+(?:on|via|using|upi|ref|avl|\\.|\\$))")
    private val towardsRegex = Regex("(?i)\\btowards\\s+([A-Za-z0-9 &._/@-]{2,30}?)(?:\\s+(?:on|via|ref|avl|\\.|\\$))")
    private val fromSenderRegex = Regex("(?i)\\bfrom\\s+([A-Za-z][A-Za-z0-9 &._-]{2,30}?)(?:\\s+(?:on|via|using|ref|\\.|\\$))")
    private val vpaRegex = Regex("(?i)\\b(?:vpa|upi(?:/|\\s+linked\\s+to\\s+vpa\\s+))([a-zA-Z0-9._-]+@[a-zA-Z0-9]+)")

    fun parse(sender: String?, body: String, receivedAt: Long): TransactionEntity? {
        val clean = body.replace(Regex("\\s+"), " ").trim()
        val lower = clean.lowercase(Locale.ROOT)
        if (clean.startsWith("PENNYTEST:") && !allowSynthetic) return null
        if (noiseKeywords.any(lower::contains)) return null

        val direction = when {
            debitKeywords.containsMatchIn(clean) -> Direction.DEBIT
            creditKeywords.containsMatchIn(clean) -> Direction.CREDIT
            else -> return null
        }

        val amountMatch = amountRegex.find(clean) ?: return null
        val rawNum = amountMatch.groupValues[1].ifEmpty { amountMatch.groupValues[2] }
        val rupees = runCatching { BigDecimal(rawNum.replace(",", "")) }.getOrNull() ?: return null
        val amountMinor = runCatching { rupees.movePointRight(2).setScale(0, RoundingMode.UNNECESSARY).longValueExact() }.getOrNull()
            ?.takeIf { it > 0 } ?: return null

        val account = accountRegex.find(clean)?.groupValues?.get(1)
        val reference = extractReference(clean)
        val bank = resolveBank(sender, clean)
        val occurred = parseDate(clean, receivedAt) ?: receivedAt
        val merchant = extractMerchant(clean, direction)

        val fingerprintBasis = reference?.let { "${direction.name}|${account.orEmpty()}|${it.uppercase()}" }
            ?: "${direction.name}|${account.orEmpty()}|$amountMinor|${occurred / 60000}"

        val now = System.currentTimeMillis()
        return TransactionEntity(
            id = UUID.randomUUID().toString(),
            amountMinor = amountMinor,
            direction = direction,
            bank = bank,
            accountKey = account?.let { "${bank.orEmpty()}:$it" },
            accountLast4 = account,
            merchantOrSender = merchant,
            occurredAt = occurred,
            receivedAt = receivedAt,
            timeSource = if (occurred == receivedAt) "RECEIVED" else "PARSED_SMS",
            reference = reference,
            kind = if (direction == Direction.DEBIT) TransactionKind.PURCHASE else TransactionKind.CREDIT,
            source = if (clean.startsWith("PENNYTEST:")) TransactionSource.DEBUG_SIMULATION else TransactionSource.SMS,
            fingerprint = sha256(fingerprintBasis),
            createdAt = now,
            updatedAt = now,
        )
    }

    private fun extractReference(clean: String): String? {
        upiUtrRegex.find(clean)?.groupValues?.get(1)?.let { return it }
        referenceRegex.find(clean)?.groupValues?.get(1)?.let { ref ->
            val lower = ref.lowercase(Locale.ROOT)
            if (lower !in setOf("transaction", "reference", "balance", "available")) {
                return ref
            }
        }
        generic12DigitRegex.find(clean)?.groupValues?.get(1)?.let { return it }
        return null
    }

    private fun extractMerchant(clean: String, direction: Direction): String? {
        atMerchantRegex.find(clean)?.groupValues?.get(1)?.trim()?.let { m ->
            val lower = m.lowercase(Locale.ROOT)
            if (lower !in setOf("pos", "a/c", "account", "the", "branch")) {
                return m.trimEnd('.', ',')
            }
        }
        toRecipientRegex.find(clean)?.groupValues?.get(1)?.trim()?.let { m ->
            val lower = m.lowercase(Locale.ROOT)
            if (lower !in setOf("a/c", "account", "vpa", "your", "the")) {
                return m.trimEnd('.', ',')
            }
        }
        towardsRegex.find(clean)?.groupValues?.get(1)?.trim()?.let { m ->
            return m.trimEnd('.', ',')
        }
        vpaRegex.find(clean)?.groupValues?.get(1)?.trim()?.let { vpa ->
            return vpa
        }
        if (direction == Direction.CREDIT) {
            fromSenderRegex.find(clean)?.groupValues?.get(1)?.trim()?.let { m ->
                val lower = m.lowercase(Locale.ROOT)
                if (!lower.startsWith("a/c") && !lower.startsWith("account") && !lower.startsWith("xx")) {
                    return m.trimEnd('.', ',')
                }
            }
        }
        return null
    }

    private fun resolveBank(sender: String?, clean: String): String? {
        val s = sender.orEmpty().uppercase(Locale.ROOT)
        return when {
            s.contains("KOTAK") -> "KOTAK"
            s.contains("HDFC") -> "HDFC"
            s.contains("SBI") -> "SBI"
            s.contains("ICICI") -> "ICICI"
            s.contains("AXIS") -> "AXIS"
            s.contains("INDBNK") || s.contains("INDUSB") -> "INDUSIND"
            s.contains("PNB") || s.contains("PUNJAB") -> "PNB"
            s.contains("CANARA") || s.contains("CANBNK") -> "CANARA"
            s.contains("BOB") || s.contains("BARODA") -> "BOB"
            s.contains("UNION") -> "UNION"
            s.contains("PAYTM") -> "PAYTM"
            s.contains("PHONEPE") -> "PHONEPE"
            s.contains("PAISA") || s.contains("GPAY") -> "GPAY"
            s.contains("CRED") -> "CRED"
            else -> {
                val cleanUpper = clean.uppercase(Locale.ROOT)
                when {
                    cleanUpper.contains("KOTAK") -> "KOTAK"
                    cleanUpper.contains("HDFC") -> "HDFC"
                    cleanUpper.contains("STATE BANK") || cleanUpper.contains("SBI") -> "SBI"
                    cleanUpper.contains("ICICI") -> "ICICI"
                    cleanUpper.contains("AXIS") -> "AXIS"
                    cleanUpper.contains("INDUSIND") -> "INDUSIND"
                    cleanUpper.contains("PUNJAB NATIONAL") || cleanUpper.contains("PNB") -> "PNB"
                    cleanUpper.contains("CANARA") -> "CANARA"
                    cleanUpper.contains("BANK OF BARODA") -> "BOB"
                    cleanUpper.contains("GOOGLE PAY") || cleanUpper.contains("GPAY") -> "GPAY"
                    cleanUpper.contains("PHONEPE") -> "PHONEPE"
                    cleanUpper.contains("PAYTM") -> "PAYTM"
                    else -> sender?.replace(Regex("(?i)^(?:[A-Z]{2}-)?"), "")?.takeIf { it.any(Char::isLetter) }
                        ?.substringBefore('-')?.take(12)?.uppercase(Locale.ROOT)
                }
            }
        }
    }

    private fun parseDate(body: String, receivedAt: Long): Long? {
        val now = LocalDate.now(ZoneId.systemDefault())
        // 1. "22-Oct-2026 13:30" or "23-Oct-26"
        val wordDateRegex = Regex("(?i)\\b(\\d{1,2})[-/ ]([A-Za-z]{3})[-/ ](\\d{2,4})(?:[ ,]+(\\d{1,2}:\\d{2}(?::\\d{2})?)(?:\\s*(AM|PM))?)?")
        wordDateRegex.find(body)?.let { m ->
            return runCatching {
                val day = m.groupValues[1].toInt()
                val month = parseMonth(m.groupValues[2]) ?: return@runCatching null
                var year = m.groupValues[3].toInt()
                if (year < 100) year += 2000
                val timeStr = m.groupValues[4]
                val ampm = m.groupValues[5]
                val time = parseTime(timeStr, ampm, receivedAt)
                LocalDateTime.of(year, month, day, time.hour, time.minute, time.second)
                    .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }.getOrNull()
        }

        // 2. "23Oct26" or "23Oct2026"
        val tightDateRegex = Regex("(?i)\\b(\\d{1,2})([A-Za-z]{3})(\\d{2,4})")
        tightDateRegex.find(body)?.let { m ->
            return runCatching {
                val day = m.groupValues[1].toInt()
                val month = parseMonth(m.groupValues[2]) ?: return@runCatching null
                var year = m.groupValues[3].toInt()
                if (year < 100) year += 2000
                val time = LocalTime.now(ZoneId.systemDefault())
                LocalDateTime.of(year, month, day, time.hour, time.minute, 0)
                    .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }.getOrNull()
        }

        // 3. "23-10-2026" or "23/10/26"
        val numDateRegex = Regex("\\b(\\d{1,2})[-/](\\d{1,2})[-/](\\d{2,4})(?:[ ,]+(\\d{1,2}:\\d{2}(?::\\d{2})?)(?:\\s*(AM|PM))?)?")
        numDateRegex.find(body)?.let { m ->
            return runCatching {
                val day = m.groupValues[1].toInt()
                val month = m.groupValues[2].toInt()
                var year = m.groupValues[3].toInt()
                if (year < 100) year += 2000
                val timeStr = m.groupValues[4]
                val ampm = m.groupValues[5]
                val time = parseTime(timeStr, ampm, receivedAt)
                LocalDateTime.of(year, month, day, time.hour, time.minute, time.second)
                    .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }.getOrNull()
        }

        return null
    }

    private fun parseMonth(monthStr: String): Int? {
        val months = listOf("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec")
        val idx = months.indexOf(monthStr.lowercase(Locale.ENGLISH).take(3))
        return if (idx >= 0) idx + 1 else null
    }

    private fun parseTime(timeStr: String, ampm: String, fallbackMillis: Long): LocalTime {
        if (timeStr.isBlank()) {
            return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(fallbackMillis), ZoneId.systemDefault()).toLocalTime()
        }
        return runCatching {
            val parts = timeStr.split(":")
            var hour = parts[0].toInt()
            val min = parts[1].toInt()
            val sec = if (parts.size > 2) parts[2].toInt() else 0
            if (ampm.equals("PM", ignoreCase = true) && hour < 12) hour += 12
            if (ampm.equals("AM", ignoreCase = true) && hour == 12) hour = 0
            LocalTime.of(hour, min, sec)
        }.getOrElse { LocalTime.MIDNIGHT }
    }

    private fun sha256(value: String) = MessageDigest.getInstance("SHA-256")
        .digest(value.toByteArray()).joinToString("") { "%02x".format(it) }
}
