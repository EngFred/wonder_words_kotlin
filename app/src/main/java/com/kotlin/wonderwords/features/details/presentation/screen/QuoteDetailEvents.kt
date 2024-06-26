package com.kotlin.wonderwords.features.details.presentation.screen

sealed class QuoteDetailEvents {

    data object FavClicked : QuoteDetailEvents()
    data object Upvoted: QuoteDetailEvents()
    data object Downvoted: QuoteDetailEvents()

}