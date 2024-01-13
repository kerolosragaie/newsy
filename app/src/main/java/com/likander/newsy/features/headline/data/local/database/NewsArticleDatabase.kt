package com.likander.newsy.features.headline.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.likander.newsy.features.headline.data.local.dao.HeadlineDao
import com.likander.newsy.features.headline.data.local.dao.HeadlineRemoteKeyDao
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import com.likander.newsy.features.headline.data.local.models.HeadlineRemoteKeyEntity

@Database(
    entities = [
        HeadlineEntity::class,
        HeadlineRemoteKeyEntity::class,
    ],
    exportSchema = false,
    version = 1
)
abstract class NewsArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineRemoteKeyDao(): HeadlineRemoteKeyDao
}