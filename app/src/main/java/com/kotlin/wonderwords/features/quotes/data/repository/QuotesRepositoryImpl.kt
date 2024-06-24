package com.kotlin.wonderwords.features.quotes.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuote
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuoteEntity
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteDTO
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import com.kotlin.wonderwords.features.quotes.data.remote.pagination.AllQuotesPagingSource
import com.kotlin.wonderwords.features.quotes.data.remote.pagination.QuotesByCategoryPagingSource
import com.kotlin.wonderwords.features.quotes.data.remote.pagination.QuotesRemoteMediator
import com.kotlin.wonderwords.features.quotes.domain.domain.Quote
import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory
import com.kotlin.wonderwords.features.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val quotesApiService: QuotesApiService,
    private val quotesDatabase: QuotesDatabase
): QuotesRepository {

    override suspend fun fetchQuotes(category: QuoteCategory): Flow<PagingData<Quote>> {
        if (category == QuoteCategory.All) {
           return  Pager(
               config = PagingConfig(20),
               pagingSourceFactory = {
                  AllQuotesPagingSource(quotesApiService)
               }
           ).flow.map {
                it.map { quote ->
                    quote.toQuote()
                }
           }.flowOn(Dispatchers.IO).catch {
               Log.e("#", "Flow error: $it")
           }

        }
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { QuotesByCategoryPagingSource(quotesApiService, category.name.lowercase()) }
        ).flow.map {
            it.map { quote ->
                quote.toQuote()
            }
        }.flowOn(Dispatchers.IO).catch {
            Log.e("#", "Flow error: $it")
        }
    }

}