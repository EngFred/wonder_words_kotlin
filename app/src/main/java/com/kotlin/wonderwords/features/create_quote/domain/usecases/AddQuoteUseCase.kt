package com.kotlin.wonderwords.features.create_quote.domain.usecases

import com.kotlin.wonderwords.features.create_quote.domain.models.AddQuoteRequest
import com.kotlin.wonderwords.features.create_quote.domain.repository.AddQuoteRepository
import javax.inject.Inject

class AddQuoteUseCase @Inject constructor(
    private val addQuoteRepository: AddQuoteRepository
) {
    suspend operator fun invoke( requestBody: AddQuoteRequest ) = addQuoteRepository.addQuote(requestBody)
}