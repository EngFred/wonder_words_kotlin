package com.kotlin.wonderwords.features.profile.data.mapper

import com.kotlin.wonderwords.features.profile.data.model.AccountDetailsDTO
import com.kotlin.wonderwords.features.profile.data.model.UserProfileDetailsDTO
import com.kotlin.wonderwords.features.profile.domain.model.AccountDetails
import com.kotlin.wonderwords.features.profile.domain.model.UserProfileDetails

fun UserProfileDetailsDTO.toUserProfileDetails() : UserProfileDetails {
    return UserProfileDetails(
        name = name,
        picUrl = picUrl,
        followers = followers,
        following = following,
        pro = pro,
        accountDetails = accountDetailsDTO?.toAccountDetails()
    )
}

fun AccountDetailsDTO.toAccountDetails() : AccountDetails {
    return AccountDetails(
        email = email,
        proExpiration = proExpiration
    )
}