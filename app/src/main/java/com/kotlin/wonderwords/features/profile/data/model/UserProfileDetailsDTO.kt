package com.kotlin.wonderwords.features.profile.data.model

import com.google.gson.annotations.SerializedName

data class UserProfileDetailsDTO(
    @SerializedName("login")
    val name: String?,
    @SerializedName("pic_url")
    val picUrl: String?,
    val followers: Int?,
    val following: Int?,
    val pro: Boolean?,
    @SerializedName("public_favorites_count")
    val publicFavoritesCount: Int,
    @SerializedName("account_details")
    val accountDetailsDTO: AccountDetailsDTO?
)

data class AccountDetailsDTO(
    val email: String?,
    @SerializedName("private_favorites_count")
    val privateFavoritesCount: Int,
    @SerializedName("pro_expiration")
    val proExpiration: String? //date
)
