package com.likander.newsy.core.common.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.likander.newsy.features.detail.data.dao.DetailDao
import com.likander.newsy.features.discover.data.local.dao.DiscoverDao
import com.likander.newsy.features.discover.data.local.dao.DiscoverRemoteKeyDao
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import com.likander.newsy.features.discover.data.local.model.DiscoverRemoteArticleKeyEntity
import com.likander.newsy.features.headline.data.local.dao.HeadlineDao
import com.likander.newsy.features.headline.data.local.dao.HeadlineRemoteKeyDao
import com.likander.newsy.features.headline.data.local.models.HeadlineArticleEntity
import com.likander.newsy.features.headline.data.local.models.HeadlineRemoteKeyEntity
import com.likander.newsy.features.search.data.local.dao.SearchArticleDao
import com.likander.newsy.features.search.data.local.dao.SearchRemoteKeyDao
import com.likander.newsy.features.search.data.local.model.SearchEntity
import com.likander.newsy.features.search.data.local.model.SearchRemoteKeyEntity

@Database(
    entities = [
        HeadlineArticleEntity::class,
        HeadlineRemoteKeyEntity::class,
        DiscoverArticleEntity::class,
        DiscoverRemoteArticleKeyEntity::class,
        SearchEntity::class,
        SearchRemoteKeyEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class NewsArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineRemoteKeyDao(): HeadlineRemoteKeyDao
    abstract fun discoverDao(): DiscoverDao
    abstract fun discoverRemoteKeyDao(): DiscoverRemoteKeyDao
    abstract fun detailDao(): DetailDao
    abstract fun searchArticleDao(): SearchArticleDao
    abstract fun searchRemoteKeyDao(): SearchRemoteKeyDao
}