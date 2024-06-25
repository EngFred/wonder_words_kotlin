package com.kotlin.wonderwords.features.quotes.data.mapper

import com.kotlin.wonderwords.features.quotes.data.modals.QuoteDTO
import com.kotlin.wonderwords.features.quotes.data.modals.QuoteEntity
import com.kotlin.wonderwords.features.quotes.domain.domain.Quote
import com.kotlin.wonderwords.features.quotes.domain.domain.QuoteCategory

fun QuoteEntity.toQuote() : Quote {
    return Quote(
        id = id,
        author = author,
        body = body,
        favorite = favorite
    )
}

fun QuoteDTO.toQuoteEntity(category: String) : QuoteEntity {
    return  QuoteEntity(
        id = id!!,
        author = author,
        body = body,
        favorite = favorite,
        category = category,
        timeStamp = System.currentTimeMillis()
    )
}

fun QuoteDTO.toQuote() : Quote {
    return  Quote(
        id = id,
        author = author,
        body = body,
        favorite = favorite
    )
}