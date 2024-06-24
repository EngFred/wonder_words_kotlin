package com.kotlin.wonderwords.features.quotes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {
//    @Query("SELECT * FROM QUOTES_TABLE")
//    fun getAllQuotes() : PagingSource<Int, QuoteEntity>

    @Query("SELECT * FROM QUOTES_TABLE LIMIT :limit OFFSET :offset")
    fun getQuotes(limit: Int, offset: Int) : Flow<List<QuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuotes( quotes: List<QuoteEntity> )

    @Query("DELETE FROM QUOTES_TABLE")
    suspend fun deleteQuotes()

    @Query("SELECT COUNT(*) FROM QUOTES_TABLE")
    suspend fun getQuotesCount(): Int
}