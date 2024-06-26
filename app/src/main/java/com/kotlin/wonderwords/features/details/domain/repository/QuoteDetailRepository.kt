package com.kotlin.wonderwords.features.details.domain.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.details.domain.entity.QuoteDetails
import kotlinx.coroutines.flow.Flow

interface QuoteDetailRepository {

    suspend fun fetchQuoteDetails(
        quoteId: Int
    ): Flow<DataState<QuoteDetails>>

    suspend fun favQuote(
        quoteId: Int
    ): QuoteDetails?

    suspend fun unfavQuote(
        quoteId: Int
    ): QuoteDetails?

    suspend fun upvoteQuote(
        quoteId: Int
    ): QuoteDetails?

    suspend fun downvoteQuote(
        quoteId: Int
    ): QuoteDetails?
}