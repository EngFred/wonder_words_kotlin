package com.kotlin.wonderwords.features.details.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.details.data.api.QuoteDetailsApiService
import com.kotlin.wonderwords.features.details.data.mapper.toQuoteDetails
import com.kotlin.wonderwords.features.details.domain.entity.QuoteDetails
import com.kotlin.wonderwords.features.details.domain.repository.QuoteDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class QuoteDetailRepositoryImpl @Inject constructor(
    private val quoteDetailService: QuoteDetailsApiService
) :  QuoteDetailRepository {

    companion object {
        const val TAG = "QuoteDetailRepositoryImpl"
    }
    override suspend fun fetchQuoteDetails(quoteId: Int): Flow<DataState<QuoteDetails>> {
        return flow {
            emit(DataState.Loading)
            val quoteDetailsDTO = quoteDetailService.fetchQuoteDetails(quoteId)
            val quoteDetails = quoteDetailsDTO.toQuoteDetails()
            emit(DataState.Success(quoteDetails))
        }.flowOn(Dispatchers.IO).catch {
            Log.e(TAG, it.message.toString())
            emit(DataState.Error("Something went wrong"))
        }
    }

    override suspend fun favQuote(quoteId: Int): QuoteDetails? {
        return try {
            val quoteDetailsDTO = quoteDetailService.favQuote(quoteId)
            val quoteDetails = quoteDetailsDTO.toQuoteDetails()
            Log.i(TAG, "Quote added to favorites")
            quoteDetails
        }catch (e: Exception) {
            Log.e(TAG, "Error adding quote to favorites: ${e.message}")
            return null
        }
    }

    override suspend fun unfavQuote(quoteId: Int): QuoteDetails? {
        return try {
            val quoteDetailsDTO = quoteDetailService.unfavQuote(quoteId)
            val quoteDetails = quoteDetailsDTO.toQuoteDetails()
            Log.i(TAG, "Quote removed from favorites")
            quoteDetails
        }catch (e: Exception) {
            Log.e(TAG, "Error removing quote from favorites: ${e.message}")
            return null
        }
    }

    override suspend fun upvoteQuote(quoteId: Int): QuoteDetails? {
        return try {
            val quoteDetailsDTO = quoteDetailService.upvoteQuote(quoteId)
            val quoteDetails = quoteDetailsDTO.toQuoteDetails()
            Log.i(TAG, "Quote up voted")
            quoteDetails
        }catch (e: Exception) {
            Log.e(TAG, "Error up voting quote: ${e.message}")
            return null
        }
    }

    override suspend fun downvoteQuote(quoteId: Int): QuoteDetails? {
        return try {
            val quoteDetailsDTO = quoteDetailService.downvoteQuote(quoteId)
            val quoteDetails = quoteDetailsDTO.toQuoteDetails()
            Log.i(TAG, "Quote down voted")
            quoteDetails
        }catch (e: Exception) {
            Log.e(TAG, "Error down voting quote: ${e.message}")
            return null
        }
    }

}