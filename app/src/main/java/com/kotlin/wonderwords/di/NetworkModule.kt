package com.kotlin.wonderwords.di

import com.kotlin.wonderwords.BuildConfig
import com.kotlin.wonderwords.features.auth.data.source.AuthApiService
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.details.data.api.QuoteDetailsApiService
import com.kotlin.wonderwords.features.profile.data.api.UserProfileApiService
import com.kotlin.wonderwords.features.quotes.data.remote.api.QuotesApiService
import com.kotlin.wonderwords.features.user_update.data.api.UpdateUserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenManager: TokenManager
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalReq = chain.request()
                val reqBuilder = originalReq.newBuilder()
                    .addHeader("Authorization", "Token token=${BuildConfig.API_KEY}")

                // Fetch user token from TokenManager
                val userToken = runBlocking {
                    tokenManager.getToken()
                }

                if (userToken?.isNotEmpty() == true) {
                    reqBuilder.addHeader("User-Token", userToken)
                }

                chain.proceed(reqBuilder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://favqs.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAuthApiService( retrofit: Retrofit) : AuthApiService {
        return  retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesQuotesApiService( retrofit: Retrofit) : QuotesApiService {
        return  retrofit.create(QuotesApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesQuoteDetailsApiService( retrofit: Retrofit ) : QuoteDetailsApiService {
        return  retrofit.create(QuoteDetailsApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesUserProfileApiService( retrofit: Retrofit ) : UserProfileApiService {
        return retrofit.create(UserProfileApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesUpdateUserApiService( retrofit: Retrofit ) : UpdateUserApiService {
        return retrofit.create(UpdateUserApiService::class.java)
    }
}