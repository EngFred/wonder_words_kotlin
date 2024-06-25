package com.kotlin.wonderwords.features.quotes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes( quotes: List<QuoteEntity> )

    @Query("DELETE FROM QUOTES_TABLE")
    suspend fun deleteQuotes()

    @Query("SELECT COUNT(*) FROM QUOTES_TABLE")
    suspend fun getQuotesCount(): Int

    @Query("DELETE FROM QUOTES_TABLE WHERE category = :category")
    suspend fun deleteQuotesByCategory(category: String)

    @Query("SELECT * FROM QUOTES_TABLE  WHERE category = :category LIMIT :limit OFFSET :offset")
    fun getQuotesByCategory(category: String, limit: Int, offset: Int) : Flow<List<QuoteEntity>>

}