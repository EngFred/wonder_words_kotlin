package com.kotlin.wonderwords.features.profile.data.model

import com.google.gson.annotations.SerializedName

data class SignOutResponse(
    val message: String?,
    @SerializedName("error_code")
    val errorCode: Int?
)
