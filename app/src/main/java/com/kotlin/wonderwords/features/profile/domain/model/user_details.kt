package com.kotlin.wonderwords.features.profile.domain.model

data class UserProfileDetails(
    val name: String?,
    val picUrl: String?,
    val followers: Int?,
    val following: Int?,
    val pro: Boolean?,
    val accountDetails: AccountDetails?,
)

data class AccountDetails(
    val email: String?,
    val proExpiration: String?
)
