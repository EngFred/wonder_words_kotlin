package com.kotlin.wonderwords.features.create_quote.data.repository

import android.util.Log
import com.kotlin.wonderwords.core.network.DataState
import com.kotlin.wonderwords.features.create_quote.data.api.AddQuoteApiService
import com.kotlin.wonderwords.features.create_quote.domain.models.AddQuoteRequest
import com.kotlin.wonderwords.features.create_quote.domain.repository.AddQuoteRepository
import javax.inject.Inject

class AddQuoteRepositoryImpl @Inject constructor(
    private val addQuoteApiService: AddQuoteApiService
) : AddQuoteRepository {

    companion object {
        const val TAG = "AddQuoteRepositoryImpl"
    }
    override suspend fun addQuote(requestBody: AddQuoteRequest): DataState<Int> {
        return try {
            Log.v(TAG, "Adding quote...")
            val quoteDetailsDTO = addQuoteApiService.addQuote(requestBody)
            Log.v(TAG, "Quote added successfully!!")
            return DataState.Success(quoteDetailsDTO.id)
        } catch (e: Exception) {
            Log.d(TAG, "Failed to add quote! $e")
            DataState.Error(e.message.toString())
        }
    }

}