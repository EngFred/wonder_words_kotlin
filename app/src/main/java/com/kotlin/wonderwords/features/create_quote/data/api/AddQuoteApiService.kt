package com.kotlin.wonderwords.features.create_quote.data.api

import com.kotlin.wonderwords.features.create_quote.domain.models.AddQuoteRequest
import com.kotlin.wonderwords.features.details.data.model.QuoteDetailsDTO
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AddQuoteApiService {
    @POST("/api/quotes")
    suspend fun addQuote(
        @Header("User-Token") userToken: String,
        @Body requestBody: AddQuoteRequest
    ) : QuoteDetailsDTO
}