package com.kotlin.wonderwords.features.auth.domain.usecase

import android.util.Patterns
import com.kotlin.wonderwords.features.auth.domain.entity.ValidationResult

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, "Email cannot be blank")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, "Invalid email format")
        }
        return ValidationResult(true)
    }
}