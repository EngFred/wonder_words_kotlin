package com.kotlin.wonderwords.features.quotes.data.repository

import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuote
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuoteEntity
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import com.kotlin.wonderwords.features.quotes.domain.models.DataSource
import com.kotlin.wonderwords.features.quotes.domain.models.Quote
import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory
import com.kotlin.wonderwords.features.quotes.domain.models.Source
import com.kotlin.wonderwords.features.quotes.domain.repository.QuotesRepository
import okio.IOException
import java.net.ConnectException
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val quotesApiService: QuotesApiService,
    private val quotesDatabase: QuotesDatabase
): QuotesRepository {

    override suspend fun fetchQuotes(page: Int, category: QuoteCategory): DataState<DataSource> {

        if (category == QuoteCategory.All) {
            return try {
                val quotesDTO = quotesApiService.getQuotes(page).quotes
                if (page == 1) {
                    quotesDatabase.quotesDao().clearAllQuotesByCategory(category.name.lowercase()) // for refreshing
                }
                quotesDatabase.quotesDao()
                    .insertQuotes(quotesDTO.map { it.toQuoteEntity(category.name.lowercase()) })
                val quotes = quotesDTO.map { it.toQuote() }
                val dataFrom = DataSource(
                    source = Source.Remote,
                    data = quotes
                )
                DataState.Success(dataFrom)
            }catch (e: Exception){
                if (e is IOException || e is ConnectException) {
                    val quotes = quotesDatabase.quotesDao().getQuotes(category.name.lowercase())
                        .map { it.toQuote() }
                    val dataFrom = DataSource(
                        source = Source.Cache,
                        data = quotes
                    )
                    DataState.Success(dataFrom)
                } else {
                    DataState.Error(error = e.message.toString())
                }
            }
        } else {
            return try {
                val quotesDTO = quotesApiService.getQuotesByCategory(page, category.name.lowercase()).quotes
                if (page == 1) {
                    quotesDatabase.quotesDao().clearAllQuotesByCategory(category.name.lowercase())
                }
                quotesDatabase.quotesDao()
                    .insertQuotes(quotesDTO.map { it.toQuoteEntity(category.name.lowercase()) })
                val quotes = quotesDTO.map { it.toQuote() }
                val dataFrom = DataSource(
                    source = Source.Remote,
                    data = quotes
                )
                DataState.Success(dataFrom)
            } catch (e: Exception) {
                if (e is IOException || e is ConnectException) {
                    val quotes = quotesDatabase.quotesDao().getQuotes(category.name.lowercase())
                        .map { it.toQuote() }
                    val dataFrom = DataSource(
                        source = Source.Cache,
                        data = quotes
                    )
                    DataState.Success(dataFrom)
                } else {
                    DataState.Error(error = e.message.toString())
                }
            }
        }
    }

}