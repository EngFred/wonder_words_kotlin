package com.kotlin.wonderwords.features.quotes.data.source.api

import com.kotlin.wonderwords.features.quotes.data.modals.QuotesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApiService {

    @GET("/api/quotes")
    suspend fun getQuotes(
        @Query("page") page: Int
    ): QuotesResponse

}