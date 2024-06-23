package com.kotlin.wonderwords.di

import com.kotlin.wonderwords.BuildConfig
import com.kotlin.wonderwords.features.auth.data.source.AuthApiService
import com.kotlin.wonderwords.features.auth.data.token_manager.TokenManager
import com.kotlin.wonderwords.features.quotes.data.source.api.QuotesApiService
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
}