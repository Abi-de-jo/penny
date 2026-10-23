package com.penny.app.notifications

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.penny.app.PennyApplication
import com.penny.app.data.Direction
import com.penny.app.data.TransactionSource
import com.penny.app.sms.TransactionSmsParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class PennyNotificationListener : NotificationListenerService() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val parser = TransactionSmsParser()

    companion object {
        // Whitelisted Indian payment & banking app package names
        val SUPPORTED_PAYMENT_PACKAGES = setOf(
            "com.google.android.apps.nbu.paisa.user", // Google Pay
            "com.phonepe.app",                       // PhonePe
            "net.one97.paytm",                       // Paytm
            "com.dreamplug.androidapp",              // CRED
            "in.org.npci.upiapp",                    // BHIM
            "com.axis.mobile",                       // Axis Bank
            "com.msf.kbank.mobile",                  // Kotak 811
            "com.snapwork.hdfc",                     // HDFC Bank
            "com.csam.icici.bank.imobile",           // ICICI iMobile
            "com.sbi.lotusintouch",                  // YONO SBI
            "com.sbi.SBIFreedomPlus",                // SBI Anywhere
            "com.canarabank.mobility",               // Canara Bank
            "com.pnb.pnbone",                        // PNB One
            "com.infrasoft.uboi",                    // Union Bank
            "com.bankofbaroda.mconnect"              // bob World
        )
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn == null) return
        val pkg = sbn.packageName ?: return

        // Google Play Store Guidelines & Privacy:
        // NEVER inspect personal communications (WhatsApp, Telegram, Signal, Contacts, etc.).
        // Only process whitelisted payment/banking packages or apps explicitly identifying as bank/upi.
        val isPaymentApp = SUPPORTED_PAYMENT_PACKAGES.contains(pkg) ||
                pkg.contains("bank", ignoreCase = true) ||
                pkg.contains("paisa", ignoreCase = true) ||
                pkg.contains("upi", ignoreCase = true)

        if (!isPaymentApp) return

        val extras = sbn.notification?.extras ?: return
        val title = extras.getCharSequence(Notification.EXTRA_TITLE)?.toString().orEmpty()
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)?.toString().orEmpty()
        val bigText = extras.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString().orEmpty()

        val fullText = "$title $text $bigText".trim()
        if (fullText.isBlank()) return

        val receivedAt = if (sbn.postTime > 0) sbn.postTime else System.currentTimeMillis()
        val transaction = parser.parse(pkg, fullText, receivedAt)?.copy(source = TransactionSource.SMS) ?: return

        val app = applicationContext as? PennyApplication ?: return
        scope.launch {
            if (app.repository.insert(transaction) && transaction.direction == Direction.DEBIT) {
                app.foregroundDebitEvents.emit(transaction.id)
                val settings = withTimeoutOrNull(1000) { app.preferences.settings.first() }
                if (settings?.notificationsEnabled != false) {
                    ExpenseNotifications.post(applicationContext, transaction, settings?.notificationSoundEnabled != false)
                }
            }
        }
    }
}
