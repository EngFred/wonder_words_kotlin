package com.kotlin.wonderwords.features.auth.domain.models

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)