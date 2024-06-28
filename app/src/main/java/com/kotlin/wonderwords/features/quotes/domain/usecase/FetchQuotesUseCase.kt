package com.kotlin.wonderwords.features.quotes.domain.usecase

import com.kotlin.wonderwords.features.quotes.domain.models.QuoteCategory
import com.kotlin.wonderwords.features.quotes.domain.repository.QuotesRepository
import javax.inject.Inject

class FetchQuotesUseCase @Inject constructor(
    private val repository: QuotesRepository
) {
    suspend operator fun invoke( page:Int, category: QuoteCategory ) = repository.fetchQuotes(page, category)
}