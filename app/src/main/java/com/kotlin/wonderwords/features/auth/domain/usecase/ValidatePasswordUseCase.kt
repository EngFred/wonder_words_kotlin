package com.kotlin.wonderwords.features.auth.domain.usecase

import com.kotlin.wonderwords.features.auth.domain.models.ValidationResult

class ValidatePasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(false, "Password cannot be blank")
        }
        if (password.length < 6) {
            return ValidationResult(false, "Password must be at least 6 characters long")
        }
        return ValidationResult(true)
    }
}
