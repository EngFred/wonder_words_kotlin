package com.kotlin.wonderwords.features.create_quote.presentation.screens

sealed class CreateQuoteUiEvents {
    data class AuthorChanged(val author: String ) : CreateQuoteUiEvents()
    data class BodyChanged( val body: String ) : CreateQuoteUiEvents()
    data object CreateButtonClicked :  CreateQuoteUiEvents()
}