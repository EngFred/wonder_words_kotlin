package com.kotlin.wonderwords.features.quotes.data.modals

import com.google.gson.annotations.SerializedName

data class QuotesResponse(
    @SerializedName("last_page")
    val lastPage: Boolean,
    val page: Int,
    val quotes: List<QuoteDTO>
)