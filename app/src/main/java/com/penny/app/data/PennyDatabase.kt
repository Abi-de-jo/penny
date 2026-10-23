package com.penny.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

class PennyConverters {
    @TypeConverter fun direction(value: String) = Direction.valueOf(value)
    @TypeConverter fun direction(value: Direction) = value.name
    @TypeConverter fun kind(value: String) = TransactionKind.valueOf(value)
    @TypeConverter fun kind(value: TransactionKind) = value.name
    @TypeConverter fun source(value: String) = TransactionSource.valueOf(value)
    @TypeConverter fun source(value: TransactionSource) = value.name
}

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
@TypeConverters(PennyConverters::class)
abstract class PennyDatabase : RoomDatabase() {
    abstract fun transactions(): TransactionDao

    companion object {
        fun create(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            PennyDatabase::class.java,
            "penny.db"
        ).build()
    }
}
