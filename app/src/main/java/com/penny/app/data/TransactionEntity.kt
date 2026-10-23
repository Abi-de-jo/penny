package com.penny.app.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

enum class Direction { DEBIT, CREDIT }
enum class TransactionKind { PURCHASE, CREDIT, OWN_TRANSFER, REFUND, REVERSAL, UNKNOWN }
enum class TransactionSource { SMS, DEBUG_SIMULATION }

@Entity(
    tableName = "transactions",
    indices = [Index(value = ["fingerprint"], unique = true), Index("occurredAt")]
)
data class TransactionEntity(
    @PrimaryKey val id: String,
    val amountMinor: Long,
    val currency: String = "INR",
    val direction: Direction,
    val bank: String?,
    val accountKey: String?,
    val accountLast4: String?,
    val merchantOrSender: String?,
    val occurredAt: Long,
    val receivedAt: Long,
    val timeSource: String,
    val reference: String?,
    val title: String? = null,
    val description: String? = null,
    val kind: TransactionKind,
    val relatedTransactionId: String? = null,
    val source: TransactionSource,
    val fingerprint: String,
    val createdAt: Long,
    val updatedAt: Long,
)
