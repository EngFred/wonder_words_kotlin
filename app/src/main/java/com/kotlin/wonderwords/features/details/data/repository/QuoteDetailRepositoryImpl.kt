package com.kotlin.wonderwords.features.details.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.details.data.api.QuoteDetailsApiService
import com.kotlin.wonderwords.features.details.data.mapper.toQuoteDetails
import com.kotlin.wonderwords.features.details.domain.models.QuoteDetails
import com.kotlin.wonderwords.features.details.domain.repository.QuoteDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import java.net.ConnectException
import javax.inject.Inject

class QuoteDetailRepositoryImpl @Inject constructor(
    private val quoteDetailService: QuoteDetailsApiService,
    private val tokenManager: TokenManager
) :  QuoteDetailRepository {

    companion object {
        const val TAG = "QuoteDetailRepositoryImpl"
    }
    override suspend fun fetchQuoteDetails(quoteId: Int): Flow<DataState<QuoteDetails>> {
        return flow {
            emit(DataState.Loading)
            val userToken = tokenManager.getToken()
            if (userToken != null) {
                val quoteDetailsDTO = quoteDetailService.fetchQuoteDetails(quoteId, userToken)
                val quoteDetails = quoteDetailsDTO.toQuoteDetails()
                emit(DataState.Success(quoteDetails))
            } else {
                emit(DataState.Error("User token is null"))
            }
        }.flowOn(Dispatchers.IO).catch {
            if (it is ConnectException || it is IOException || it.cause is IOException) {
                Log.e(TAG, "No internet connection!")
                DataState.Error("No internet connection!")
            } else{
                Log.e(TAG, it.message.toString())
                emit(DataState.Error(it.message.toString()))
            }
        }
    }

    override suspend fun favQuote(quoteId: Int): QuoteDetails? {
        return try {
            val userToken = tokenManager.getToken() ?: return null
            val quoteDetailsDTO = quoteDetailService.favQuote(quoteId, userToken)

            if ( quoteDetailsDTO.errorCode == null ) { //no error
                val quoteDetails = quoteDetailsDTO.toQuoteDetails()
                Log.i(TAG, "Quote added to favorites")
                quoteDetails
            } else {
                null
            }
        }catch (e: Exception) {
            Log.e(TAG, "Error adding quote to favorites: ${e.message}")
            null
        }
    }

    override suspend fun unfavQuote(quoteId: Int): QuoteDetails? {
        return try {
            val userToken = tokenManager.getToken() ?: return null
            val quoteDetailsDTO = quoteDetailService.unfavQuote(quoteId, userToken)
            if ( quoteDetailsDTO.errorCode == null ) {
                val quoteDetails = quoteDetailsDTO.toQuoteDetails()
                Log.i(TAG, "Quote removed from favorite")
                quoteDetails
            } else {
                null
            }
        }catch (e: Exception) {
            Log.e(TAG, "Error removing quote from favorites: ${e.message}")
            null
        }
    }

    override suspend fun upvoteQuote(quoteId: Int): QuoteDetails? {
        return try {
            val userToken = tokenManager.getToken() ?: return null
            val quoteDetailsDTO = quoteDetailService.upvoteQuote(quoteId, userToken)
            if ( quoteDetailsDTO.errorCode == null ) {
                val quoteDetails = quoteDetailsDTO.toQuoteDetails()
                Log.i(TAG, "Quote up voted")
                quoteDetails
            } else {
                null
            }
        }catch (e: Exception) {
            Log.e(TAG, "Error up voting quote: ${e.message}")
            null
        }
    }

    override suspend fun downvoteQuote(quoteId: Int): QuoteDetails? {
        return try {
            val userToken = tokenManager.getToken() ?: return null
            val quoteDetailsDTO = quoteDetailService.downvoteQuote(quoteId, userToken)

            if ( quoteDetailsDTO.errorCode == null ) {
                val quoteDetails = quoteDetailsDTO.toQuoteDetails()
                Log.i(TAG, "Quote down voted")
                quoteDetails
            } else {
                null
            }
        }catch (e: Exception) {
            Log.e(TAG, "Error down voting quote: ${e.message}")
            null
        }
    }

}