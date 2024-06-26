package com.kotlin.wonderwords.features.details.domain.entity

data class QuoteDetails(
    val id: Int,
    val body: String?,
    val author: String?,
    val upvotesCount: Int?,
    val downvotesCount: Int?,
    val favoritesCount: Int?,
    val url: String?,
    val userDetails: UserDetails?
)

data class UserDetails(
    val favorited: Boolean?,
    val upvoted: Boolean?,
    val downvoted: Boolean?
)