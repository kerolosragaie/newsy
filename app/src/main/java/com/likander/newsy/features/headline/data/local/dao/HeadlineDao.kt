package com.likander.newsy.features.headline.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.likander.newsy.features.headline.data.local.models.HeadlineArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {

    @Query("SELECT * FROM headline_articles_table")
    fun getAllHeadlineArticles(): PagingSource<Int, HeadlineArticleEntity>

    @Query("SELECT * FROM headline_articles_table WHERE id= :id")
    fun getHeadlineArticle(id: Int): Flow<HeadlineArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeadlineArticles(articles: List<HeadlineArticleEntity>)

    @Query("DELETE FROM headline_articles_table WHERE favourite=0")
    suspend fun removeAllHeadlineArticles()

    @Delete
    suspend fun removeFavouriteArticle(headlineArticleEntity: HeadlineArticleEntity)

    @Query("UPDATE headline_articles_table SET favourite= :isFavourite WHERE id=:id")
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int): Int
}