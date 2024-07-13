package com.likander.newsy.di

import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import com.likander.newsy.features.detail.data.DetailRepoImpl
import com.likander.newsy.features.detail.data.dao.DetailDao
import com.likander.newsy.features.detail.domain.repo.DetailRepo
import com.likander.newsy.features.detail.domain.usecase.DetailUseCases
import com.likander.newsy.features.detail.domain.usecase.GetDetailDiscoverArticleUseCase
import com.likander.newsy.features.detail.domain.usecase.GetDetailHeadlineArticleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {
    @Provides
    @Singleton
    fun provideDetailDao(newsArticleDatabase: NewsArticleDatabase): DetailDao =
        newsArticleDatabase.detailDao()

    @Provides
    @Singleton
    fun provideDetailRepo(detailDao: DetailDao): DetailRepo = DetailRepoImpl(detailDao)

    @Provides
    @Singleton
    fun provideDetailUseCases(detailRepo: DetailRepo): DetailUseCases = DetailUseCases(
        GetDetailHeadlineArticleUseCase(detailRepo),
        GetDetailDiscoverArticleUseCase(detailRepo)
    )
}