package com.kotlin.wonderwords.features.quotes.data.modals

import com.google.gson.annotations.SerializedName

data class QuoteDTO(
    val author: String?,
    @SerializedName("author_permalink")
    val authorPermalink: String?,
    val body: String?,
    val dialogue: Boolean?,
    @SerializedName("downvotes_count")
    val downvotesCount: Int?,
    val favorite: Boolean?,
    @SerializedName("favorites_count")
    val favoritesCount: Int?,
    val id: Int?,
    val tags: List<String>?,
    @SerializedName("upvotes_count")
    val upvotesCount: Int?,
    val url: String?
)
