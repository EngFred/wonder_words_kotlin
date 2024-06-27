package com.kotlin.wonderwords.features.details.domain.usecase

import com.kotlin.wonderwords.features.details.domain.repository.QuoteDetailRepository
import javax.inject.Inject

class DownvoteQuoteUseCase @Inject constructor(
    private val quoteDetailRepository: QuoteDetailRepository
) {
    suspend operator fun invoke(id: Int) = quoteDetailRepository.downvoteQuote(id)
}