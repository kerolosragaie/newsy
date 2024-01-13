package com.likander.newsy.features.headline.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {

    @Query("SELECT * FROM headline_table")
    fun getAllHeadlineArticles(): PagingSource<Int, HeadlineEntity>

    @Query("SELECT * FROM headline_table WHERE id= :id")
    fun getHeadlineArticle(id: Int): Flow<HeadlineEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeadlineArticles(articles: List<HeadlineEntity>)

    @Query("DELETE FROM headline_table WHERE favourite=0")
    suspend fun removeAllHeadlineArticles()

    @Delete
    suspend fun removeFavouriteArticle(headlineEntity: HeadlineEntity)

    @Query("UPDATE headline_table SET favourite= :isFavourite WHERE id= :id")
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int)
}