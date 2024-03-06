package com.likander.newsy.di

import android.content.Context
import androidx.room.Room
import com.likander.newsy.core.common.data.local.database.NewsArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providesNewsDatabase(@ApplicationContext context: Context): NewsArticleDatabase =
        Room.databaseBuilder(
            context,
            NewsArticleDatabase::class.java,
            "newsy.db",
        ).build()
}