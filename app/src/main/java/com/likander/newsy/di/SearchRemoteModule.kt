package com.likander.newsy.di

import com.likander.newsy.features.search.data.remote.SearchApi
import com.likander.newsy.features.search.data.remote.data_source.SearchRemoteDataSource
import com.likander.newsy.features.search.data.remote.data_source.SearchRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchRemoteModule {
    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

    @Provides
    @Singleton
    fun provideSearchRemoteDataSource(
        searchApi: SearchApi
    ): SearchRemoteDataSource = SearchRemoteDataSourceImpl(searchApi)
}