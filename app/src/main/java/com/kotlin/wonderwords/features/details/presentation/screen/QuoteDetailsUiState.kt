package com.kotlin.wonderwords.features.details.presentation.screen

import com.kotlin.wonderwords.features.details.domain.entity.QuoteDetails

data class QuoteDetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val quote: QuoteDetails? = null,
//    val favorited: Boolean? = null,
//    val upvoted: Boolean? = null,
//    val downVoted: Boolean? = null,
//    val favoritesCount: Int = 0,
//    val upvotesCount: Int = 0,
//    val downvotesCount: Int = 0
)
