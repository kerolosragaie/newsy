package com.likander.newsy.features.detail.domain.repo

import com.likander.newsy.core.utils.UiState
import com.likander.newsy.features.detail.domain.model.DetailArticle
import kotlinx.coroutines.flow.Flow

interface DetailRepo {
    suspend fun getHeadlineArticle(id: Int): Flow<UiState<DetailArticle>>
    suspend fun getDiscoverArticle(id: Int): Flow<UiState<DetailArticle>>
}