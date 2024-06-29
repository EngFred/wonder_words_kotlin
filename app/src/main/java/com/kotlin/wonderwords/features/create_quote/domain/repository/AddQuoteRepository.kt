package com.kotlin.wonderwords.features.create_quote.domain.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.create_quote.domain.models.AddQuoteRequest

interface AddQuoteRepository {
    suspend fun addQuote( requestBody: AddQuoteRequest ) : DataState<Int>
}