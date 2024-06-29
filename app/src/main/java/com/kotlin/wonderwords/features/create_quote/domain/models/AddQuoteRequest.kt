package com.kotlin.wonderwords.features.create_quote.domain.models

data class AddQuoteRequest(
    val quote: QuoteRequest
)

data class QuoteRequest(
    val author: String,
    val body: String
)
