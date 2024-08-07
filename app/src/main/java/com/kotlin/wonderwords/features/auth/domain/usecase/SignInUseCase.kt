package com.kotlin.wonderwords.features.auth.domain.usecase

import com.kotlin.wonderwords.features.auth.domain.models.AuthRequest
import com.kotlin.wonderwords.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(signInRequest: AuthRequest ) = authRepository.signIn(signInRequest)
}