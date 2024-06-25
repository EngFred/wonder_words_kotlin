package com.kotlin.wonderwords.di

import android.content.Context
import com.kotlin.wonderwords.features.auth.data.repository.AuthRepositoryImpl
import com.kotlin.wonderwords.features.auth.data.source.AuthApiService
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.auth.domain.repository.AuthRepository
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import com.kotlin.wonderwords.features.quotes.data.repository.QuotesRepositoryImpl
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import com.kotlin.wonderwords.features.quotes.domain.repository.QuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
}