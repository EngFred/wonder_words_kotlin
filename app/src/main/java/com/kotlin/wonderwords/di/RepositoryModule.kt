package com.kotlin.wonderwords.di

import com.kotlin.wonderwords.features.auth.data.repository.AuthRepositoryImpl
import com.kotlin.wonderwords.features.auth.data.source.AuthApiService
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.auth.domain.repository.AuthRepository
import com.kotlin.wonderwords.features.details.data.api.QuoteDetailsApiService
import com.kotlin.wonderwords.features.details.data.repository.QuoteDetailRepositoryImpl
import com.kotlin.wonderwords.features.details.domain.repository.QuoteDetailRepository
import com.kotlin.wonderwords.features.profile.data.api.UserProfileApiService
import com.kotlin.wonderwords.features.profile.data.repository.UserProfileRepositoryImpl
import com.kotlin.wonderwords.features.profile.domain.repository.UserProfileRepository
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import com.kotlin.wonderwords.features.quotes.data.repository.QuotesRepositoryImpl
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import com.kotlin.wonderwords.features.quotes.domain.repository.QuotesRepository
import com.kotlin.wonderwords.features.user_update.data.api.UpdateUserApiService
import com.kotlin.wonderwords.features.user_update.data.repository.UpdateUserInfoRepositoryImpl
import com.kotlin.wonderwords.features.user_update.domain.repository.UpdateUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesAuthRepository(
        authApiService: AuthApiService,
        tokenManager: TokenManager
    ) : AuthRepository {
        return  AuthRepositoryImpl(
            authApiService = authApiService,
            tokenManager = tokenManager
        )
    }

    @Provides
    @Singleton
    fun providesQuotesRepository(
        quotesApiService: QuotesApiService,
        quotesDatabase: QuotesDatabase
    ) : QuotesRepository{
        return  QuotesRepositoryImpl(
            quotesApiService = quotesApiService,
            quotesDatabase = quotesDatabase
        )
    }

    @Provides
    @Singleton
    fun providesQuoteDetailsRepository(
        quoteDetailsApiService: QuoteDetailsApiService
    ) : QuoteDetailRepository {
        return  QuoteDetailRepositoryImpl(quoteDetailsApiService)
    }

    @Provides
    @Singleton
    fun providesUserProfileRepository(
        userProfileApiService: UserProfileApiService,
        tokenManager: TokenManager,
        quotesDatabase: QuotesDatabase
    ): UserProfileRepository {
        return UserProfileRepositoryImpl(userProfileApiService, tokenManager, quotesDatabase)
    }

    @Provides
    @Singleton
    fun providesUpdateUserRepository(
        updateUserApiService: UpdateUserApiService,
        tokenManager: TokenManager
    ): UpdateUserRepository {
        return UpdateUserInfoRepositoryImpl(updateUserApiService,tokenManager)
    }
}