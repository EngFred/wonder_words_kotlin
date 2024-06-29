package com.kotlin.wonderwords.features.quotes.data.modals

import com.google.gson.annotations.SerializedName

data class QuoteOfTheDayResponse(
    @SerializedName("qotd_date")
    val createdAt: String,
    val quote: QuoteDTO
)