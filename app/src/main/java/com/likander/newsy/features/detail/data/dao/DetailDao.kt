package com.likander.newsy.features.detail.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.likander.newsy.features.detail.data.model.DetailDto

@Dao
interface DetailDao {
    @Query("SELECT * FROM headline_articles_table WHERE id = :id")
    suspend fun getHeadlineArticle(id: Int): DetailDto

    @Query("SELECT * FROM discover_articles_table WHERE id = :id")
    suspend fun getDiscoverArticle(id: Int): DetailDto
}