package com.kotlin.wonderwords.features.quotes.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.quotes.data.api.QuotesApiService
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuote
import com.kotlin.wonderwords.features.quotes.data.mapper.toQuoteEntity
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

    companion object {
        const val TAG = "QuotesRepositoryImpl"
    }

    override suspend fun fetchQuotes(page: Int, category: QuoteCategory): DataState<DataSource> {

        if (category == QuoteCategory.All) {
            return try {
                val apiResponse =  quotesApiService.getQuotes(page)

                val quotesDTO = apiResponse.quotes.filter {
                    !it.body.isNullOrEmpty() && it.body.length > 16
                }
                if (page == 1) {
                    quotesDatabase.quotesDao().clearAllQuotesByCategory(category.name.lowercase()) // for refreshing
                }
                quotesDatabase.quotesDao()
                    .insertQuotes(quotesDTO.map { it.toQuoteEntity(category.name.lowercase()) })
                val quotes = quotesDTO.map { it.toQuote() }
                val dataFrom = DataSource(
                    source = Source.Remote,
                    isLastPage = apiResponse.lastPage,
                    quotes = quotes
                )
                DataState.Success(dataFrom)

            } catch (e: Exception){
                if (e is ConnectException || e is IOException) {
                    val quotes = quotesDatabase.quotesDao().getQuotes(category.name.lowercase())
                        .map { it.toQuote() }
                    val dataFrom = DataSource(
                        source = Source.Cache,
                        isLastPage = false,
                        quotes = quotes
                    )
                    DataState.Success(dataFrom)
                } else {
                    DataState.Error(error = e.message.toString())
                }
            }
        } else {
            return try {
                val apiResponse = quotesApiService.getQuotesByCategory(page, category.name.lowercase())

                val quotesDTO = apiResponse.quotes.filter {
                    !it.body.isNullOrEmpty() && it.body.length > 16
                }

                if (page == 1) {
                    quotesDatabase.quotesDao().clearAllQuotesByCategory(category.name.lowercase())
                }
                quotesDatabase.quotesDao()
                    .insertQuotes(quotesDTO.map { it.toQuoteEntity(category.name.lowercase()) })
                val quotes = quotesDTO.map { it.toQuote() }
                val dataFrom = DataSource(
                    source = Source.Remote,
                    isLastPage = apiResponse.lastPage,
                    quotes = quotes
                )
                DataState.Success(dataFrom)
            } catch (e: Exception) {
                if (e is ConnectException || e is IOException) {
                    val quotes = quotesDatabase.quotesDao().getQuotes(category.name.lowercase())
                        .map { it.toQuote() }
                    val dataFrom = DataSource(
                        source = Source.Cache,
                        isLastPage = false,
                        quotes = quotes
                    )
                    DataState.Success(dataFrom)
                } else {
                    DataState.Error(error = e.message.toString())
                }
            }
        }
    }

    override suspend fun getQuoteOfTheDay(): DataState<Quote> {
        return try {
            Log.d("$", "Fetching quote of the day...")
            val qotdRes = quotesApiService.getQuoteOfTheDay()
            val qotd = qotdRes.quote.toQuote()

            if ( qotd.body != null && qotd.body.length > 16  ) {
                Log.d("$", "Successfully fetched quote of the day")
                DataState.Success(qotd)
            } else {
                Log.d("$", "Received quote of the day is deformed! $qotd")
                DataState.Error("Received quote of the day is deformed!")
            }
        }catch (e: Exception) {
            Log.d("$", "Error getting quote of the day! $e")
            DataState.Error(e.message.toString())
        }
    }

}