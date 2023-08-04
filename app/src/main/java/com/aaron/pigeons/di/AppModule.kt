package com.aaron.pigeons.di

import com.aaron.pigeons.constants.Constants
import com.aaron.pigeons.domain.repository.PigeonRepository
import com.aaron.pigeons.domain.repository.PigeonRepositoryImpl
import com.aaron.pigeons.remote.PigeonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providePigeonApi(): PigeonApi {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PigeonApi::class.java)
    }

    @Provides
    @Singleton
    fun providePigeonRepository(pigeonApi: PigeonApi): PigeonRepository {
        return PigeonRepositoryImpl(pigeonApi)
    }
}