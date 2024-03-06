package com.likander.newsy.di

import com.likander.newsy.features.headline.data.remote.api.HeadlineApi
import com.likander.newsy.features.headline.data.remote.data_source.HeadlineRemoteDataSource
import com.likander.newsy.features.headline.data.remote.data_source.HeadlineRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeadlineRemoteModule {
    @Provides
    @Singleton
    fun provideHeadlineApi(retrofit: Retrofit): HeadlineApi = retrofit
        .create(HeadlineApi::class.java)

    @Provides
    @Singleton
    fun provideHeadlineRemoteDataSource(
        headlineApi: HeadlineApi
    ): HeadlineRemoteDataSource =
        HeadlineRemoteDataSourceImpl(headlineApi)
}