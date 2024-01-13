package com.likander.newsy.features.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    exportSchema = false,
    version = 1
)
abstract class NewsArticleDatabase : RoomDatabase() {
}