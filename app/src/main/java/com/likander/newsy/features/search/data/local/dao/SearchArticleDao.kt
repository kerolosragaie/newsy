package com.likander.newsy.features.search.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.likander.newsy.features.search.data.local.model.SearchEntity

@Dao
interface SearchArticleDao {
    @Query("SELECT * FROM search_table")
    fun getAllSearchArticles(): PagingSource<Int, SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSearchArticles(articles: List<SearchEntity>)

    @Query("UPDATE search_table SET favourite=:isFavourite WHERE id=:id")
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Int)

    @Query("DELETE FROM search_table WHERE favourite=0")
    suspend fun removeAllArticles()
}