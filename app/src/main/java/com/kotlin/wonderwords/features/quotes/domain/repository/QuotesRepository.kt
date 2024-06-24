package com.kotlin.wonderwords.features.quotes.domain.repository

import androidx.paging.PagingData
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import com.kotlin.wonderwords.features.quotes.domain.domain.Quote
import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    suspend fun fetchQuotes( category: QuoteCategory) : Flow<PagingData<Quote>>
}