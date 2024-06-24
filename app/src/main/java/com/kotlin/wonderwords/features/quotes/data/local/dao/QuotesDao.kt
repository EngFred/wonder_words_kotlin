package com.kotlin.wonderwords.features.quotes.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity

@Dao
interface QuotesDao {
    @Query("SELECT * FROM QUOTES_TABLE")
    fun getAllQuotes() : PagingSource<Int, QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes( quotes: List<QuoteEntity> )

    @Query("DELETE FROM QUOTES_TABLE")
    suspend fun deleteQuotes()
}