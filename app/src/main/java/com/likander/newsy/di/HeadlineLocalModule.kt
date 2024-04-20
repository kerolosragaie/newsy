package com.likander.newsy.di

import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
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
    fun provideHeadlineLocalDataSource(newsArticleDatabase: NewsArticleDatabase): HeadlineLocalDataSource =
        HeadlineLocalDataSourceImpl(newsArticleDatabase)
}