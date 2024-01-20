package com.likander.newsy.features.headline.di

import com.likander.newsy.features.headline.data.local.data_source.HeadlineLocalDataSource
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import com.likander.newsy.features.headline.data.mappers.Mapper
import com.likander.newsy.features.headline.data.remote.data_source.HeadlineRemoteDataSource
import com.likander.newsy.features.headline.data.remote.model.ArticleDto
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
    fun provideHeadlineRepo(
        headlineRemoteDataSource: HeadlineRemoteDataSource,
        headlineLocalDataSource: HeadlineLocalDataSource,
        mapper: Mapper<HeadlineEntity, Article>,
        articleHeadlineMapper: Mapper<ArticleDto, HeadlineEntity>,
    ): HeadlineRepo =
        HeadlineRepoImpl(
            headlineRemoteDataSource = headlineRemoteDataSource,
            headlineLocalDataSource = headlineLocalDataSource,
            mapper = mapper,
            articleHeadlineMapper = articleHeadlineMapper,
        )

    @Provides
    fun provideHeadlineUseCases(
        headlineRepo: HeadlineRepo,
    ): HeadlineUseCases = HeadlineUseCases(
        fetchHeadlineArticleUseCase = FetchHeadlineArticleUseCase(
            headlineRepo
        ),
        updateHeadlineFavouriteUseCase = UpdateHeadlineFavouriteUseCase(
            headlineRepo
        ),
    )

}