package com.kotlin.wonderwords.features.create_quote.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.create_quote.data.api.AddQuoteApiService
import com.kotlin.wonderwords.features.create_quote.domain.models.AddQuoteRequest
import com.kotlin.wonderwords.features.create_quote.domain.repository.AddQuoteRepository
import okio.IOException
import java.net.ConnectException
import javax.inject.Inject

class AddQuoteRepositoryImpl @Inject constructor(
    private val addQuoteApiService: AddQuoteApiService,
    private val tokenManager: TokenManager
) : AddQuoteRepository {

    companion object {
        const val TAG = "AddQuoteRepositoryImpl"
    }
    override suspend fun addQuote(requestBody: AddQuoteRequest): DataState<Int> {
        return try {
            Log.v(TAG, "Adding quote...")
            val userToken = tokenManager.getToken() ?: return DataState.Error("User not logged in")
            val quoteDetailsDTO = addQuoteApiService.addQuote(userToken ,requestBody)
            Log.v(TAG, "Quote added successfully!!")
            return DataState.Success(quoteDetailsDTO.id)
        } catch (e: Exception) {
            if (e is ConnectException || e is IOException || e.cause is IOException) {
                Log.e(TAG, "No internet connection!")
                DataState.Error("No internet connection!")
            } else{
                Log.d(TAG, "Failed to add quote! $e")
                DataState.Error(e.message.toString())
            }
        }
    }

}