package com.likander.newsy.di

import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.search.data.local.data_source.SearchLocalDataSource
import com.likander.newsy.features.search.data.local.data_source.SearchLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchLocalModule {
    @Provides
    @Singleton
    fun provideSearchLocalDataSource(
        database: NewsArticleDatabase
    ): SearchLocalDataSource = SearchLocalDataSourceImpl(database)
}