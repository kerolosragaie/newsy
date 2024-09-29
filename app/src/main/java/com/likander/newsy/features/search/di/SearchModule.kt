package com.likander.newsy.features.search.di

import com.likander.newsy.features.search.data.local.data_source.SearchLocalDataSource
import com.likander.newsy.features.search.data.remote.data_source.SearchRemoteDataSource
import com.likander.newsy.features.search.data.repo.SearchRepoImpl
import com.likander.newsy.features.search.domain.repo.SearchRepo
import com.likander.newsy.features.search.domain.usecase.FetchSearchArticleUseCase
import com.likander.newsy.features.search.domain.usecase.SearchUseCases
import com.likander.newsy.features.search.domain.usecase.UpdateFavouriteSearchArticleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {
    @Provides
    fun provideSearchRepo(
        localDataSource: SearchLocalDataSource,
        remoteDataSource: SearchRemoteDataSource
    ): SearchRepo = SearchRepoImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource
    )

    @Provides
    fun provideSearchUseCases(
        searchRepo: SearchRepo
    ): SearchUseCases = SearchUseCases(
        fetchSearchArticle = FetchSearchArticleUseCase(searchRepo),
        updateSearchFavourite = UpdateFavouriteSearchArticleUseCase(searchRepo)
    )
}