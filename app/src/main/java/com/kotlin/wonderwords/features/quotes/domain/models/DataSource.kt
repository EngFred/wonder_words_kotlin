package com.kotlin.wonderwords.features.quotes.domain.models

import com.kotlin.wonderwords.features.quotes.data.repository.Source

data class DataSource(
    val source: Source,
    val data: List<Quote>
)
