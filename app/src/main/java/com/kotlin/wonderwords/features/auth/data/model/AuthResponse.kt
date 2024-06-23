package com.kotlin.wonderwords.features.auth.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("User-Token")
    val userToken: String?,
    @SerializedName("login")
    val userName: String?,
    @SerializedName("error_code")
    val errorCode: Int?,
    val message: String?,
    val email: String?
)
