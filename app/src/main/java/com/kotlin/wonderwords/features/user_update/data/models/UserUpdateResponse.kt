package com.kotlin.wonderwords.features.user_update.data.models

import com.google.gson.annotations.SerializedName

data class UserUpdateResponse(
    @SerializedName("error_code")
    val errorCode: Int?,
    val message: String?
)
