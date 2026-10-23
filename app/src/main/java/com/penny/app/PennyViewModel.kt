package com.penny.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.penny.app.data.Direction
import com.penny.app.data.PennySettings
import com.penny.app.data.PennyThemeMode
import com.penny.app.data.PennyLanguage
import com.penny.app.data.TransactionEntity
import com.penny.app.data.TransactionKind
import com.penny.app.data.TransactionSource
import com.penny.app.notifications.ExpenseNotifications
import com.penny.app.sms.TransactionSmsParser
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

data class PennyUiState(
    val transactions: List<TransactionEntity> = emptyList(),
    val settings: PennySettings = PennySettings(),
)

class PennyViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application as PennyApplication
    val state = combine(app.repository.transactions, app.preferences.settings, ::PennyUiState)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PennyUiState())
    val foregroundDebitEvents = app.foregroundDebitEvents

    fun completeOnboarding() = viewModelScope.launch { app.preferences.completeOnboarding() }
    fun notifications(enabled: Boolean) = viewModelScope.launch { app.preferences.setNotifications(enabled) }
    fun notificationSound(enabled: Boolean) = viewModelScope.launch { app.preferences.setNotificationSound(enabled) }
    fun themeMode(mode: PennyThemeMode) = viewModelScope.launch { app.preferences.setThemeMode(mode) }
    fun language(lang: PennyLanguage) = viewModelScope.launch { app.preferences.setLanguage(lang) }
    fun saveDraft(id: String, title: String, description: String) = viewModelScope.launch {
        app.preferences.saveDraft(id, title, description)
    }
    fun saveExpense(id: String, title: String, description: String, onSaved: () -> Unit) = viewModelScope.launch {
        if (title.trim().isEmpty() || title.trim().length > 60 || description.length > 300) return@launch
        app.repository.annotate(id, title, description)
        app.preferences.clearDraft()
        ExpenseNotifications.cancel(getApplication(), id)
        onSaved()
    }
    fun markTransfer(id: String, transfer: Boolean) = viewModelScope.launch { app.repository.markTransfer(id, transfer) }
    fun deleteTransaction(id: String, onDone: (() -> Unit)? = null) = viewModelScope.launch {
        app.repository.delete(id)
        ExpenseNotifications.cancel(getApplication(), id)
        onDone?.invoke()
    }
    fun deleteAll(onDone: () -> Unit) = viewModelScope.launch {
        app.repository.deleteAll(); app.preferences.clear(); ExpenseNotifications.cancelAll(getApplication()); onDone()
    }

    fun simulate(direction: Direction, customAmount: String? = null) = viewModelScope.launch {
        val now = LocalDateTime.now()
        val timestamp = now.format(DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm"))
        val ref = System.currentTimeMillis().toString()
        val verb = if (direction == Direction.DEBIT) "debited from" else "credited to"
        val amount = customAmount ?: if (direction == Direction.DEBIT) "45280.00" else "125000.00"
        val body = "PENNYTEST: INR $amount $verb A/c XX4521 at CAFE on $timestamp. Ref $ref. Avl Bal INR 10000.00."
        TransactionSmsParser(true).parse("PENNYTEST", body, System.currentTimeMillis())?.copy(source = TransactionSource.DEBUG_SIMULATION)?.let {
            if (app.repository.insert(it) && it.direction == Direction.DEBIT) app.foregroundDebitEvents.emit(it.id)
        }
    }
    fun seedRealisticStoreData(onDone: () -> Unit = {}) = viewModelScope.launch {
        app.repository.deleteAll()
        val now = System.currentTimeMillis()
        val hour = 3600_000L

        val txs = listOf(
            TransactionEntity(
                id = UUID.randomUUID().toString(),
                amountMinor = 340_00,
                direction = Direction.DEBIT,
                bank = "HDFC",
                accountKey = "HDFC:4521",
                accountLast4 = "4521",
                merchantOrSender = "Swiggy",
                occurredAt = now - 2 * hour,
                receivedAt = now - 2 * hour,
                timeSource = "PARSED_SMS",
                reference = "629731450011",
                title = "Lunch at office",
                description = "Healthy bowl & juice",
                kind = TransactionKind.PURCHASE,
                source = TransactionSource.DEBUG_SIMULATION,
                fingerprint = "seed_1",
                createdAt = now,
                updatedAt = now,
            ),
            TransactionEntity(
                id = UUID.randomUUID().toString(),
                amountMinor = 180_00,
                direction = Direction.DEBIT,
                bank = "KOTAK",
                accountKey = "KOTAK:4873",
                accountLast4 = "4873",
                merchantOrSender = "Uber India",
                occurredAt = now - 4 * hour,
                receivedAt = now - 4 * hour,
                timeSource = "PARSED_SMS",
                reference = "629731450012",
                title = "Uber ride",
                description = "Morning commute to office",
                kind = TransactionKind.PURCHASE,
                source = TransactionSource.DEBUG_SIMULATION,
                fingerprint = "seed_2",
                createdAt = now,
                updatedAt = now,
            ),
            TransactionEntity(
                id = UUID.randomUUID().toString(),
                amountMinor = 1250_00,
                direction = Direction.DEBIT,
                bank = "ICICI",
                accountKey = "ICICI:9012",
                accountLast4 = "9012",
                merchantOrSender = "D-Mart Supermarket",
                occurredAt = now - 6 * hour,
                receivedAt = now - 6 * hour,
                timeSource = "PARSED_SMS",
                reference = "629731450013",
                title = "Weekly groceries",
                description = "Monthly essentials",
                kind = TransactionKind.PURCHASE,
                source = TransactionSource.DEBUG_SIMULATION,
                fingerprint = "seed_3",
                createdAt = now,
                updatedAt = now,
            ),
            TransactionEntity(
                id = UUID.randomUUID().toString(),
                amountMinor = 65000_00,
                direction = Direction.CREDIT,
                bank = "HDFC",
                accountKey = "HDFC:4521",
                accountLast4 = "4521",
                merchantOrSender = "Salary / Infosys",
                occurredAt = now - 8 * hour,
                receivedAt = now - 8 * hour,
                timeSource = "PARSED_SMS",
                reference = "629731450014",
                title = "October Salary",
                description = "Monthly remuneration",
                kind = TransactionKind.CREDIT,
                source = TransactionSource.DEBUG_SIMULATION,
                fingerprint = "seed_4",
                createdAt = now,
                updatedAt = now,
            ),
            TransactionEntity(
                id = UUID.randomUUID().toString(),
                amountMinor = 85_00,
                direction = Direction.DEBIT,
                bank = "KOTAK",
                accountKey = "KOTAK:4873",
                accountLast4 = "4873",
                merchantOrSender = "Chai Point",
                occurredAt = now - 1 * hour,
                receivedAt = now - 1 * hour,
                timeSource = "PARSED_SMS",
                reference = "629731450015",
                title = "Evening tea",
                description = "Ginger tea with cookies",
                kind = TransactionKind.PURCHASE,
                source = TransactionSource.DEBUG_SIMULATION,
                fingerprint = "seed_5",
                createdAt = now,
                updatedAt = now,
            ),
            TransactionEntity(
                id = UUID.randomUUID().toString(),
                amountMinor = 15000_00,
                direction = Direction.DEBIT,
                bank = "HDFC",
                accountKey = "HDFC:4521",
                accountLast4 = "4521",
                merchantOrSender = "House Rent",
                occurredAt = now - 9 * hour,
                receivedAt = now - 9 * hour,
                timeSource = "PARSED_SMS",
                reference = "629731450016",
                title = "House rent transfer",
                description = "Flat rent payment",
                kind = TransactionKind.OWN_TRANSFER,
                source = TransactionSource.DEBUG_SIMULATION,
                fingerprint = "seed_6",
                createdAt = now,
                updatedAt = now,
            )
        )
        txs.forEach { app.repository.insert(it) }
        onDone()
}
    }

fun List<TransactionEntity>.includedTotals(): Pair<Long, Long> {
    val included = filter { it.kind != TransactionKind.OWN_TRANSFER }
    return included.filter { it.direction == Direction.CREDIT }.sumOf { it.amountMinor } to
        included.filter { it.direction == Direction.DEBIT }.sumOf { it.amountMinor }
}
