package com.kotlin.wonderwords.features.details.data.api

import com.kotlin.wonderwords.features.details.data.model.QuoteDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface QuoteDetailsApiService {

    @GET("quotes/{quote_id}")
    suspend fun fetchQuoteDetails(
        @Path("quote_id") quoteId: Int,
        @Header("User-Token") userToken: String
    ): QuoteDetailsDTO

    @PUT("quotes/{quote_id}/fav")
    suspend fun favQuote(
        @Path("quote_id") quoteId: Int,
        @Header("User-Token") userToken: String
    ): QuoteDetailsDTO

    @PUT("quotes/{quote_id}/unfav")
    suspend fun unfavQuote(
        @Path("quote_id") quoteId: Int,
        @Header("User-Token") userToken: String
    ): QuoteDetailsDTO

    @PUT("quotes/{quote_id}/upvote")
    suspend fun upvoteQuote(
        @Path("quote_id") quoteId: Int,
        @Header("User-Token") userToken: String
    ): QuoteDetailsDTO

    @PUT("quotes/{quote_id}/downvote")
    suspend fun downvoteQuote(
        @Path("quote_id") quoteId: Int,
        @Header("User-Token") userToken: String
    ): QuoteDetailsDTO
}