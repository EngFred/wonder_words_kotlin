package com.kotlin.wonderwords.features.quotes.domain.usecase

import com.kotlin.wonderwords.features.quotes.domain.repository.QuotesRepository
import javax.inject.Inject

class GetQuoteOfTheDayUseCase @Inject constructor(
    private val repository: QuotesRepository
) {
    suspend operator fun invoke() = repository.getQuoteOfTheDay()
}