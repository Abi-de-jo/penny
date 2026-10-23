package com.penny.app.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.penny.app.BuildConfig
import com.penny.app.PennyApplication
import com.penny.app.data.Direction
import com.penny.app.notifications.ExpenseNotifications
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class TransactionSmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
        val pending = goAsync()
        val app = context.applicationContext as PennyApplication
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val body = messages.joinToString("") { it.messageBody.orEmpty() }
        val sender = messages.firstOrNull()?.originatingAddress
        val receivedAt = messages.firstOrNull()?.timestampMillis ?: System.currentTimeMillis()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                val transaction = TransactionSmsParser(BuildConfig.DEBUG).parse(sender, body, receivedAt) ?: return@launch
                if (!app.repository.insert(transaction) || transaction.direction != Direction.DEBIT) return@launch
                val foreground = ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
                if (foreground) app.foregroundDebitEvents.emit(transaction.id)
                else {
                    val settings = withTimeoutOrNull(1_000) { app.preferences.settings.first() }
                    if (settings?.notificationsEnabled != false) {
                        ExpenseNotifications.post(context, transaction, settings?.notificationSoundEnabled != false)
                    }
                }
            } finally { pending.finish() }
        }
    }
}
