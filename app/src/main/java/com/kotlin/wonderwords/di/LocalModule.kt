package com.kotlin.wonderwords.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidateEmailUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidatePasswordUseCase
import com.kotlin.wonderwords.features.auth.domain.usecase.ValidateUsernameUseCase
import com.kotlin.wonderwords.features.quotes.data.local.db.QuotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providesDatastore( @ApplicationContext context: Context ): DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("settings")
    }

    @Provides
    @Singleton
    fun providesValidateEmailUseCase() : ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    @Singleton
    fun providesValidatePasswordUseCase() : ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    @Singleton
    fun providesValidateUsernameUseCase() : ValidateUsernameUseCase{
        return ValidateUsernameUseCase()
    }

    @Provides
    @Singleton
    fun providesQuotesDatabase( @ApplicationContext context: Context ) : QuotesDatabase {
        return  Room.databaseBuilder(
            context,
            QuotesDatabase::class.java,
            "quoted.db",
        ).fallbackToDestructiveMigration().build()
    }
}