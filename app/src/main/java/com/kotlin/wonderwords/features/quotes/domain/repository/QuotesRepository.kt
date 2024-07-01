package com.kotlin.wonderwords.features.quotes.domain.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.quotes.domain.models.DataSource
import com.kotlin.wonderwords.features.quotes.domain.models.Quote
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    suspend fun fetchQuotes( page: Int, category: QuoteCategory) : DataState<DataSource>
    suspend fun getQuoteOfTheDay() : DataState<Quote>
}