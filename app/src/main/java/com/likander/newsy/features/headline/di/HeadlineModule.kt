package com.likander.newsy.features.headline.di

import com.likander.newsy.core.common.data.mappers.Mapper
import com.likander.newsy.features.headline.data.local.data_source.HeadlineLocalDataSource
import com.likander.newsy.features.headline.data.local.models.HeadlineArticleEntity
import com.likander.newsy.features.headline.data.mappers.HeadlineMapper
import com.likander.newsy.features.headline.data.remote.data_source.HeadlineRemoteDataSource
import com.likander.newsy.features.headline.data.repo.HeadlineRepoImpl
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.domain.repo.HeadlineRepo
import com.likander.newsy.features.headline.domain.usecase.FetchHeadlineArticleUseCase
import com.likander.newsy.features.headline.domain.usecase.HeadlineUseCases
import com.likander.newsy.features.headline.domain.usecase.UpdateHeadlineFavouriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HeadlineModule {
    @Provides
    fun provideHeadlineMapper(): Mapper<HeadlineArticleEntity, Article> = HeadlineMapper()

    @Provides
    fun provideHeadlineRepo(
        headlineRemoteDataSource: HeadlineRemoteDataSource,
        headlineLocalDataSource: HeadlineLocalDataSource,
        mapper: Mapper<HeadlineArticleEntity, Article>,
    ): HeadlineRepo =
        HeadlineRepoImpl(
            headlineRemoteDataSource = headlineRemoteDataSource,
            headlineLocalDataSource = headlineLocalDataSource,
            mapper = mapper,
        )

    @Provides
    fun provideHeadlineUseCases(
        headlineRepo: HeadlineRepo,
    ): HeadlineUseCases = HeadlineUseCases(
        fetchHeadlineArticleUseCase = FetchHeadlineArticleUseCase(headlineRepo),
        updateHeadlineFavouriteUseCase = UpdateHeadlineFavouriteUseCase(headlineRepo)
    )
}