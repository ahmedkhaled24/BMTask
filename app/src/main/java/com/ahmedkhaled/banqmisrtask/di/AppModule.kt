package com.ahmedkhaled.banqmisrtask.di

import android.app.Application
import android.content.Context
import com.ahmedkhaled.banqmisrtask.data.remote.ApiInterface
import com.ahmedkhaled.banqmisrtask.data.repository.LatestCurrenciesApiRepoImpl
import com.ahmedkhaled.banqmisrtask.domain.repository.CurrenciesApIRepo
import com.ahmedkhaled.banqmisrtask.utils.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class DataPort {
    @Binds
    @Singleton
    abstract fun bindApiRepo(impl: LatestCurrenciesApiRepoImpl): CurrenciesApIRepo
}