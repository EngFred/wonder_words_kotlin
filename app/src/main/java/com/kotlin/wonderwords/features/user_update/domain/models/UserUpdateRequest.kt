package com.kotlin.wonderwords.features.user_update.domain.models

data class UserUpdateRequest(
    val user: UserInfoToUpdate
)

data class UserInfoToUpdate(
    val login: String? = null,
    val email: String? = null,
    val password: String? = null,
    val twitter_username: String? = null,
    val facebook_username: String? = null,
    val pic: String? = null,
    val profanity_filter: Boolean? = null
)
