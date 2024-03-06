package com.likander.newsy.di

import com.likander.newsy.features.discover.data.remote.api.DiscoverApi
import com.likander.newsy.features.discover.data.remote.data_source.DiscoverRemoteDataSource
import com.likander.newsy.features.discover.data.remote.data_source.DiscoverRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoverRemoteModule {
    @Provides
    @Singleton
    fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi = retrofit
        .create(DiscoverApi::class.java)

    @Provides
    @Singleton
    fun provideDiscoverRemoteDataSource(discoverApi: DiscoverApi): DiscoverRemoteDataSource =
        DiscoverRemoteDataSourceImpl(discoverApi)
}