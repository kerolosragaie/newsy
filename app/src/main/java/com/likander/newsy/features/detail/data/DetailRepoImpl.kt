package com.likander.newsy.features.detail.data

import com.likander.newsy.core.utils.UiState
import com.likander.newsy.features.detail.data.dao.DetailDao
import com.likander.newsy.features.detail.data.model.toDetailArticle
import com.likander.newsy.features.detail.domain.model.DetailArticle
import com.likander.newsy.features.detail.domain.repo.DetailRepo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DetailRepoImpl(private val detailDao: DetailDao) : DetailRepo {
    override suspend fun getHeadlineArticle(id: Int): Flow<UiState<DetailArticle>> =
        callbackFlow {
            trySend(UiState.Loading)
            try {
                val headline = detailDao.getHeadlineArticle(id).toDetailArticle()
                trySend(UiState.Success(headline))
            } catch (e: Exception) {
                trySend(UiState.Error(e))
            }
            awaitClose {}
        }

    override suspend fun getDiscoverArticle(id: Int): Flow<UiState<DetailArticle>> =
        callbackFlow {
            trySend(UiState.Loading)
            try {
                val discover = detailDao.getDiscoverArticle(id).toDetailArticle()
                trySend(UiState.Success(discover))
            } catch (e: Exception) {
                trySend(UiState.Error(e))
            }
            awaitClose {}
        }
}