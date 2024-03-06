package com.likander.newsy.di

import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.headline.data.local.dao.HeadlineDao
import com.likander.newsy.features.headline.data.local.dao.HeadlineRemoteKeyDao
import com.likander.newsy.features.headline.data.local.data_source.HeadlineLocalDataSource
import com.likander.newsy.features.headline.data.local.data_source.HeadlineLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HeadlineLocalModule {
    @Provides
    @Singleton
    fun providesHeadlineDao(
        database: NewsArticleDatabase,
    ): HeadlineDao = database.headlineDao()

    @Provides
    @Singleton
    fun providesHeadlineRemoteKeyDao(
        database: NewsArticleDatabase,
    ): HeadlineRemoteKeyDao = database.headlineRemoteKeyDao()

    @Provides
    @Singleton
    fun provideHeadlineLocalDataSource(
        headlineDao: HeadlineDao,
        newsArticleDatabase: NewsArticleDatabase,
    ): HeadlineLocalDataSource =
        HeadlineLocalDataSourceImpl(
            headlineDao = headlineDao,
            newsArticleDatabase = newsArticleDatabase,
        )
}