package com.kotlin.wonderwords.features.quotes.data.mapper

import com.kotlin.wonderwords.features.quotes.data.modals.QuoteDTO
import com.kotlin.wonderwords.features.quotes.domain.entity.Quote

fun QuoteDTO.toQuote() : Quote {
    return Quote(
        id = id,
        author = author,
        body = body,
        favorite = favorite
    )
}