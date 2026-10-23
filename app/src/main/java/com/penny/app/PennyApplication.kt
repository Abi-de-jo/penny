package com.penny.app

import android.app.Application
import com.penny.app.data.PennyDatabase
import com.penny.app.data.PennyPreferences
import com.penny.app.data.TransactionRepository
import com.penny.app.notifications.ExpenseNotifications
import kotlinx.coroutines.flow.MutableSharedFlow

class PennyApplication : Application() {
    lateinit var repository: TransactionRepository
    lateinit var preferences: PennyPreferences
    val foregroundDebitEvents = MutableSharedFlow<String>(extraBufferCapacity = 16)

    override fun onCreate() {
        super.onCreate()
        repository = TransactionRepository(PennyDatabase.create(this).transactions())
        preferences = PennyPreferences(this)
        ExpenseNotifications.createChannels(this)
    }
}
