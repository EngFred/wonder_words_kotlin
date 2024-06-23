package com.kotlin.wonderwords.features.auth.domain.usecase

import com.kotlin.wonderwords.features.auth.domain.entity.ValidationResult

class ValidateUsernameUseCase {
    operator fun invoke(username: String): ValidationResult {
        if (username.isBlank()) {
            return ValidationResult(false, "Username cannot be blank")
        }
        if (username.length < 3) {
            return ValidationResult(false, "Username must be at least 3 characters long")
        }
        return ValidationResult(true)
    }
}