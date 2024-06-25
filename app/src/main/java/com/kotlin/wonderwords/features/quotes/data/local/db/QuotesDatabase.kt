package com.kotlin.wonderwords.features.quotes.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlin.wonderwords.features.quotes.data.local.dao.QuotesDao
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity

@Database(
    entities = [QuoteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDao() : QuotesDao

}