package com.kotlin.wonderwords.features.user_update.domain.usecases

import com.kotlin.wonderwords.features.user_update.domain.models.UserUpdateRequest
import com.kotlin.wonderwords.features.user_update.domain.repository.UpdateUserRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val updateUserInfoRepository: UpdateUserRepository
) {
    suspend operator fun invoke( username: String, userUpdateRequest: UserUpdateRequest ) = updateUserInfoRepository.updateUserInfo(username, userUpdateRequest)
}