package com.likander.newsy.features.discover.di

import com.likander.newsy.core.common.data.mappers.Mapper
import com.likander.newsy.features.discover.data.local.data_source.DiscoverLocalDataSource
import com.likander.newsy.features.discover.data.local.mappers.DiscoverArticleMapper
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import com.likander.newsy.features.discover.data.remote.data_source.DiscoverRemoteDataSource
import com.likander.newsy.features.discover.data.repo.DiscoverRepoImpl
import com.likander.newsy.features.discover.domain.repo.DiscoverRepo
import com.likander.newsy.features.headline.domain.model.Article
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DiscoverModule {
    @Provides
    fun provideDiscoverArticleMapper(): Mapper<DiscoverArticleEntity, Article> =
        DiscoverArticleMapper()

    @Provides
    fun provideDiscoverRepo(
        discoverRemoteDataSource: DiscoverRemoteDataSource,
        discoverLocalDataSource: DiscoverLocalDataSource,
        mapper: Mapper<DiscoverArticleEntity, Article>
    ): DiscoverRepo = DiscoverRepoImpl(
        discoverRemoteDataSource,
        discoverLocalDataSource,
        mapper
    )
}