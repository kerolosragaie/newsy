package com.likander.newsy.di

import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.discover.data.local.dao.DiscoverDao
import com.likander.newsy.features.discover.data.local.dao.DiscoverRemoteKeyDao
import com.likander.newsy.features.discover.data.local.data_source.DiscoverLocalDataSource
import com.likander.newsy.features.discover.data.local.data_source.DiscoverLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoverLocalModule {
    @Provides
    @Singleton
    fun provideDiscoverDao(database: NewsArticleDatabase): DiscoverDao = database.discoverDao()

    @Provides
    @Singleton
    fun provideDiscoverRemoteKeyDao(database: NewsArticleDatabase): DiscoverRemoteKeyDao =
        database.discoverRemoteKeyDao()

    @Provides
    @Singleton
    fun provideDiscoverLocalDataSource(
        discoverDao: DiscoverDao,
        newsArticleDatabase: NewsArticleDatabase
    ): DiscoverLocalDataSource = DiscoverLocalDataSourceImpl(
        discoverDao,
        newsArticleDatabase
    )
}