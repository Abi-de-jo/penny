package com.penny.app.sms

import com.penny.app.data.Direction
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId

class TransactionSmsParserTest {
    private val parser = TransactionSmsParser(allowSynthetic = true)
    private val receivedAt = LocalDateTime.of(2026, 10, 22, 13, 31).atZone(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli()

    @Test fun `debit extracts transaction amount not available balance`() {
        val parsed = parser.parse("PENNYTEST", "PENNYTEST: INR 250.00 debited from A/c XX4521 at CAFE on 22-Oct-2026 13:30. Ref 626001001. Avl Bal INR 10000.00.", receivedAt)!!
        assertEquals(25_000, parsed.amountMinor)
        assertEquals(Direction.DEBIT, parsed.direction)
        assertEquals("4521", parsed.accountLast4)
        assertEquals("626001001", parsed.reference)
    }

    @Test fun `credit parses commas and paise`() {
        val parsed = parser.parse("HDFCBK", "INR 5,000.25 credited to A/c XX4521 on 22-Oct-2026 14:00. Ref 626001002.", receivedAt)!!
        assertEquals(500_025, parsed.amountMinor)
        assertEquals(Direction.CREDIT, parsed.direction)
    }

    @Test fun `release parser rejects synthetic fixtures`() {
        assertNull(TransactionSmsParser(false).parse("9000000001", "PENNYTEST: INR 250 debited from A/c XX4521. Ref 626001001.", receivedAt))
    }

    @Test fun `rejects otp declined and promotion messages`() {
        assertNull(parser.parse("BANK", "OTP 123456 for INR 250 transaction", receivedAt))
        assertNull(parser.parse("BANK", "INR 250 transaction declined on your card", receivedAt))
        assertNull(parser.parse("BANK", "Offer: spend INR 250 and get rewards", receivedAt))
    }

    @Test fun `same reference produces same fingerprint`() {
        val first = parser.parse("BANK", "INR 250 debited from A/c XX4521. Ref ABC123456.", receivedAt)!!
        val redelivery = parser.parse("BANK", "INR 250.00 debited from account XX4521. Ref ABC123456. Avl Bal INR 10,000.", receivedAt + 1000)!!
        assertEquals(first.fingerprint, redelivery.fingerprint)
    }

    @Test fun `different references preserve equal amount purchases`() {
        val first = parser.parse("BANK", "INR 250 debited from A/c XX4521. Ref ABC123456.", receivedAt)!!
        val second = parser.parse("BANK", "INR 250 debited from A/c XX4521. Ref ABC123457.", receivedAt)!!
        assertNotEquals(first.fingerprint, second.fingerprint)
    }

    @Test fun `kotak sent message is parsed as debit`() {
        val parsed = parser.parse(
            "AD-KOTAKB",
            "Sent Rs.1.00 from XX4873 to Mr Jeevaradhakrishna on 23-Oct-26. UPI ref no. 626001003. Not you? Tap / -Kotak",
            receivedAt,
        )!!

        assertEquals(100, parsed.amountMinor)
        assertEquals(Direction.DEBIT, parsed.direction)
        assertEquals("4873", parsed.accountLast4)
        assertEquals("626001003", parsed.reference)
        assertEquals("Mr Jeevaradhakrishna", parsed.merchantOrSender)
    }
    @Test fun `sbi debit with transfer to parsed correctly`() {
        val parsed = parser.parse(
            "VM-SBIINB",
            "Dear SBI User, your A/c ending 5678 debited by Rs 2500.00 on 23Oct26 transfer to Amazon UPI: 629712345678 -SBI",
            receivedAt,
        )!!
        assertEquals(250_000, parsed.amountMinor)
        assertEquals(Direction.DEBIT, parsed.direction)
        assertEquals("5678", parsed.accountLast4)
        assertEquals("629712345678", parsed.reference)
        assertEquals("SBI", parsed.bank)
    }

    @Test fun `icici credit with vpa parsed correctly`() {
        val parsed = parser.parse(
            "VK-ICICIB",
            "Your A/c 9012 is credited with INR 15,000.00 on 23-Oct-26 by A/c linked to VPA boss@okicici (UPI Ref no 629789012345).",
            receivedAt,
        )!!
        assertEquals(1_500_000, parsed.amountMinor)
        assertEquals(Direction.CREDIT, parsed.direction)
        assertEquals("9012", parsed.accountLast4)
        assertEquals("629789012345", parsed.reference)
        assertEquals("ICICI", parsed.bank)
    }

    @Test fun `gpay notification parsed as debit`() {
        val parsed = parser.parse(
            "com.google.android.apps.nbu.paisa.user",
            "Paid ₹240 to Blue Tokai Coffee on Google Pay. UPI transaction ID: 629744556677",
            receivedAt,
        )!!
        assertEquals(24_000, parsed.amountMinor)
        assertEquals(Direction.DEBIT, parsed.direction)
        assertEquals("629744556677", parsed.reference)
        assertEquals("Blue Tokai Coffee", parsed.merchantOrSender)
        assertEquals("GPAY", parsed.bank)
    }

    @Test fun `phonepe notification parsed as debit`() {
        val parsed = parser.parse(
            "com.phonepe.app",
            "You have sent ₹500 to Ramesh Kumar on PhonePe. Transaction ID: T261023153012345678",
            receivedAt,
        )!!
        assertEquals(50_000, parsed.amountMinor)
        assertEquals(Direction.DEBIT, parsed.direction)
        assertEquals("Ramesh Kumar", parsed.merchantOrSender)
        assertEquals("PHONEPE", parsed.bank)
    }
}
