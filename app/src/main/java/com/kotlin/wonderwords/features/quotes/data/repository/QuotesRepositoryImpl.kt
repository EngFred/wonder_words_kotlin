package com.kotlin.wonderwords.features.quotes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuote
import com.kotlin.wonderwords.features.quotes.data.source.api.QuotesApiService
import com.kotlin.wonderwords.features.quotes.data.source.pagination.QuotesPagingSource
import com.kotlin.wonderwords.features.quotes.domain.entity.Quote
import com.kotlin.wonderwords.features.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val quotesApiService: QuotesApiService
): QuotesRepository {

    override suspend fun fetchQuotes(): Flow<PagingData<Quote>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { QuotesPagingSource(quotesApiService) }
        ).flow.map {
            it.map { quote ->
                quote.toQuote()
            }
        }.flowOn(Dispatchers.IO)
    }

}