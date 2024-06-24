package com.kotlin.wonderwords.features.quotes.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlin.wonderwords.features.quotes.data.local.dao.QuotesDao
import com.kotlin.wonderwords.features.quotes.data.local.dao.RemoteKeysDao
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import com.kotlin.wonderwords.features.quotes.data.modals.RemoteKey

@Database(
    entities = [QuoteEntity::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDao() : QuotesDao
    abstract fun remoteKeysDao() : RemoteKeysDao

}