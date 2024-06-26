package com.kotlin.wonderwords.features.details.data.model

import com.google.gson.annotations.SerializedName

data class QuoteDetailsDTO(
    val id: Int,
    val body: String?,
    val author: String?,
    val tags: List<String>?,
    val dialog: Boolean?,
    @SerializedName("upvotes_count")
    val upvotesCount: Int?,
    @SerializedName("downvotes_count")
    val downvotesCount: Int?,
    @SerializedName("favorites_count")
    val favoritesCount: Int?,
    val private: Boolean?,
    val url: String?,
    @SerializedName("author_permalink")
    val authorPermalink: String?,
    @SerializedName("user_details")
    val userDetails: UserDetailsDTO?
)

data class UserDetailsDTO(
    @SerializedName("favorite")
    val favorited: Boolean?,
    @SerializedName("upvote")
    val upvoted: Boolean?,
    @SerializedName("downvote")
    val downvoted: Boolean?,
    val hidden: Boolean?
)
