package com.penny.app.data

import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val dao: TransactionDao) {
    val transactions: Flow<List<TransactionEntity>> = dao.observeAll()
    suspend fun insert(transaction: TransactionEntity): Boolean = dao.insert(transaction) != -1L
    suspend fun get(id: String) = dao.byId(id)
    suspend fun annotate(id: String, title: String, description: String?) =
        dao.annotate(id, title.trim(), description?.trim()?.ifBlank { null }, System.currentTimeMillis())
    suspend fun markTransfer(id: String, transfer: Boolean) = dao.updateKind(
        id, if (transfer) TransactionKind.OWN_TRANSFER else TransactionKind.UNKNOWN, System.currentTimeMillis()
    )
    suspend fun delete(id: String) = dao.deleteById(id)
    suspend fun deleteAll() = dao.deleteAll()
}
