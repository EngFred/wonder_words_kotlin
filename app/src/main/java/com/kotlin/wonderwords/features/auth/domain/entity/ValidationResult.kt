package com.kotlin.wonderwords.features.auth.domain.entity

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)