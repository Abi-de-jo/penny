package com.penny.app.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.penny.app.MainActivity
import com.penny.app.R
import com.penny.app.data.TransactionEntity
import java.text.NumberFormat
import java.util.Locale

object ExpenseNotifications {
    private const val LEGACY_CHANNEL_ID = "expense_reminders"
    private const val SOUND_CHANNEL_ID = "penny_expenses_sound_v3"
    private const val SILENT_CHANNEL_ID = "penny_expenses_silent_v3"
    private const val PREVIEW_NOTIFICATION_ID = 700_001
    const val EXTRA_TRANSACTION_ID = "transaction_id"

    fun createChannels(context: Context) {
        val manager = context.getSystemService(NotificationManager::class.java)
        val audio = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        val soundUri = Uri.parse("android.resource://${context.packageName}/raw/penny_chime")
        val sound = NotificationChannel(
            SOUND_CHANNEL_ID,
            "Penny expense reminders",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "Expense reminders with the Penny chime"
            setSound(soundUri, audio)
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 200, 100, 200)
            lightColor = Color.rgb(124, 77, 255)
            enableLights(true)
        }
        val silent = NotificationChannel(
            SILENT_CHANNEL_ID,
            "Silent expense reminders",
            NotificationManager.IMPORTANCE_HIGH,
        ).apply {
            description = "Expense reminders without sound"
            setSound(null, null)
            enableVibration(false)
        }
        manager.createNotificationChannels(listOf(sound, silent))
        manager.deleteNotificationChannel(LEGACY_CHANNEL_ID)
        manager.deleteNotificationChannel("penny_expenses_sound_v2")
        manager.deleteNotificationChannel("penny_expenses_silent_v2")
    }

    fun post(context: Context, transaction: TransactionEntity, soundEnabled: Boolean) {
        if (!canNotify(context)) return
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(EXTRA_TRANSACTION_ID, transaction.id)
        }
        val pending = PendingIntent.getActivity(
            context,
            transaction.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val amount = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")).apply {
            minimumFractionDigits = if (transaction.amountMinor % 100 == 0L) 0 else 2
            maximumFractionDigits = 2
        }.format(transaction.amountMinor / 100.0)
        val account = listOfNotNull(transaction.bank, transaction.accountLast4?.let { "••$it" })
            .joinToString(" ").ifBlank { "Bank transaction" }
        val message = "$account · Add a title while it is fresh."
        val notification = baseBuilder(context, soundEnabled)
            .setContentTitle("Penny noticed $amount")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setContentIntent(pending)
            .build()
        NotificationManagerCompat.from(context).notify(transaction.id.hashCode(), notification)
    }

    fun playChime(context: Context) {
        runCatching {
            android.media.MediaPlayer.create(context, R.raw.penny_chime)?.apply {
                setOnCompletionListener { it.release() }
                start()
            }
        }
    }

    fun postPreview(context: Context, soundEnabled: Boolean) {
        if (!canNotify(context)) return
        val manager = NotificationManagerCompat.from(context)
        manager.cancel(PREVIEW_NOTIFICATION_ID)
        val intent = Intent(context, MainActivity::class.java)
        val pending = PendingIntent.getActivity(
            context,
            PREVIEW_NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val notification = baseBuilder(context, soundEnabled)
            .setContentTitle("Penny notifications are ready")
            .setContentText(if (soundEnabled) "That was the Penny chime." else "This reminder arrived silently.")
            .setAutoCancel(true)
            .setContentIntent(pending)
            .build()
        manager.notify(PREVIEW_NOTIFICATION_ID, notification)
    }

    private fun baseBuilder(context: Context, soundEnabled: Boolean): NotificationCompat.Builder {
        val soundUri = Uri.parse("android.resource://${context.packageName}/raw/penny_chime")
        val builder = NotificationCompat.Builder(context, if (soundEnabled) SOUND_CHANNEL_ID else SILENT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setColor(Color.rgb(124, 77, 255))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)

        if (soundEnabled) {
            builder.setSound(soundUri, android.media.AudioManager.STREAM_NOTIFICATION)
            builder.setVibrate(longArrayOf(0, 200, 100, 200))
            builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS)
        } else {
            builder.setSilent(true)
        }
        return builder
    }
    private fun canNotify(context: Context) =
        Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    fun cancel(context: Context, id: String) = NotificationManagerCompat.from(context).cancel(id.hashCode())
    fun cancelAll(context: Context) = NotificationManagerCompat.from(context).cancelAll()
}
