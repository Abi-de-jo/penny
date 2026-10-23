package com.penny.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY occurredAt DESC")
    fun observeAll(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun byId(id: String): TransactionEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: TransactionEntity): Long

    @Query("UPDATE transactions SET title = :title, description = :description, updatedAt = :updatedAt WHERE id = :id")
    suspend fun annotate(id: String, title: String, description: String?, updatedAt: Long)

    @Query("UPDATE transactions SET kind = :kind, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateKind(id: String, kind: TransactionKind, updatedAt: Long)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: String)
    @Query("DELETE FROM transactions")
    suspend fun deleteAll()
}
