package com.kotlin.wonderwords.features.details.data.mapper

import com.kotlin.wonderwords.features.details.data.model.QuoteDetailsDTO
import com.kotlin.wonderwords.features.details.data.model.UserDetailsDTO
import com.kotlin.wonderwords.features.details.domain.entity.QuoteDetails
import com.kotlin.wonderwords.features.details.domain.entity.UserDetails

fun QuoteDetailsDTO.toQuoteDetails() : QuoteDetails {
    return QuoteDetails(
        id = id,
        body = body,
        author = author,
        upvotesCount = upvotesCount,
        downvotesCount = downvotesCount,
        favoritesCount = favoritesCount,
        url = url,
        userDetails = userDetails?.toUserDetails()
    )
}

fun UserDetailsDTO.toUserDetails() : UserDetails {
    return UserDetails(
        favorited = favorited,
        upvoted = upvoted,
        downvoted = downvoted
    )
}