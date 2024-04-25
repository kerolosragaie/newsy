package com.likander.newsy.features.discover.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiscoverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(list: List<DiscoverArticleEntity>)

    @Query("SELECT * FROM discover_articles_table WHERE category=:category")
    fun getDiscoverArticles(category: String): PagingSource<Int, DiscoverArticleEntity>

    @Query("SELECT * FROM discover_articles_table WHERE id=:id")
    fun getDiscoverArticle(id: Int): Flow<DiscoverArticleEntity>

    @Query("DELETE FROM discover_articles_table WHERE favourite=0 AND category=:category")
    suspend fun removeAllDiscoverArticles(category: String)

    @Delete
    suspend fun removeFavouriteArticle(discoverArticleEntity: DiscoverArticleEntity)

    @Query("UPDATE discover_articles_table SET favourite=:isFavourite WHERE id=:id")
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int
}