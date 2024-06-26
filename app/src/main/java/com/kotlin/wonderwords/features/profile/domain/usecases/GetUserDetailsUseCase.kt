package com.kotlin.wonderwords.features.profile.domain.usecases

import com.kotlin.wonderwords.features.profile.domain.repository.UserProfileRepository
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {
    suspend operator fun invoke() = userProfileRepository.getUserDetails()
}