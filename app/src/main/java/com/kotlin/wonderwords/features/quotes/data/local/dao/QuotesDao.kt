package com.kotlin.wonderwords.features.quotes.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<QuoteEntity> )

    @Query("SELECT * FROM QUOTES_TABLE WHERE category = :category")
    suspend fun getQuotes( category: String ) : List<QuoteEntity>

    @Query("DELETE FROM QUOTES_TABLE")
    suspend fun clearAllQuotes()

    @Query("DELETE FROM QUOTES_TABLE WHERE category = :category")
    suspend fun clearAllQuotesByCategory(category: String)

}