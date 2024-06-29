package com.kotlin.wonderwords.features.quotes.domain.models

data class DataSource(
    val source: Source,
    val isLastPage: Boolean,
    val quotes: List<Quote>
)
