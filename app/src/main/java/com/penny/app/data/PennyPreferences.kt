package com.penny.app.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.pennyDataStore by preferencesDataStore("penny_preferences")

enum class PennyThemeMode { SYSTEM, LIGHT, DARK }
enum class PennyLanguage { ENGLISH, TAMIL, HINDI }

data class PennySettings(
    val onboardingComplete: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val notificationSoundEnabled: Boolean = true,
    val themeMode: PennyThemeMode = PennyThemeMode.SYSTEM,
    val language: PennyLanguage = PennyLanguage.ENGLISH,
    val draftTransactionId: String? = null,
    val draftTitle: String = "",
    val draftDescription: String = "",
)

class PennyPreferences(private val context: Context) {
    private object Keys {
        val onboarding = booleanPreferencesKey("onboarding_complete")
        val notifications = booleanPreferencesKey("notifications_enabled")
        val notificationSound = booleanPreferencesKey("notification_sound_enabled")
        val themeMode = stringPreferencesKey("theme_mode")
        val draftId = stringPreferencesKey("draft_id")
        val draftTitle = stringPreferencesKey("draft_title")
        val draftDescription = stringPreferencesKey("draft_description")
        val language = stringPreferencesKey("app_language")
    }

    val settings = context.pennyDataStore.data.map { values ->
        PennySettings(
            onboardingComplete = values[Keys.onboarding] ?: false,
            notificationsEnabled = values[Keys.notifications] ?: true,
            notificationSoundEnabled = values[Keys.notificationSound] ?: true,
            themeMode = values[Keys.themeMode]?.let { value ->
                PennyThemeMode.entries.firstOrNull { it.name == value }
            } ?: PennyThemeMode.SYSTEM,
            language = values[Keys.language]?.let { value ->
                PennyLanguage.entries.firstOrNull { it.name == value }
            } ?: PennyLanguage.ENGLISH,
            draftTransactionId = values[Keys.draftId],
            draftTitle = values[Keys.draftTitle] ?: "",
            draftDescription = values[Keys.draftDescription] ?: "",
        )
    }

    suspend fun completeOnboarding() = context.pennyDataStore.edit { it[Keys.onboarding] = true }
    suspend fun setNotifications(enabled: Boolean) = context.pennyDataStore.edit { it[Keys.notifications] = enabled }
    suspend fun setNotificationSound(enabled: Boolean) = context.pennyDataStore.edit { it[Keys.notificationSound] = enabled }
    suspend fun setThemeMode(mode: PennyThemeMode) = context.pennyDataStore.edit { it[Keys.themeMode] = mode.name }
    suspend fun setLanguage(lang: PennyLanguage) = context.pennyDataStore.edit { it[Keys.language] = lang.name }
    suspend fun saveDraft(id: String, title: String, description: String) = context.pennyDataStore.edit {
        it[Keys.draftId] = id; it[Keys.draftTitle] = title; it[Keys.draftDescription] = description
    }
    suspend fun clearDraft() = context.pennyDataStore.edit {
        it.remove(Keys.draftId); it.remove(Keys.draftTitle); it.remove(Keys.draftDescription)
    }
    suspend fun clear() = context.pennyDataStore.edit { it.clear() }
}
