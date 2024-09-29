package com.likander.newsy.features.search.domain.usecase

import androidx.paging.PagingData
import com.likander.newsy.features.search.domain.model.SearchArticle
import com.likander.newsy.features.search.domain.repo.SearchRepo
import kotlinx.coroutines.flow.Flow

class FetchSearchArticleUseCase(private val searchRepo: SearchRepo) {
    operator fun invoke(query: String): Flow<PagingData<SearchArticle>> =
        searchRepo.fetchSearchArticles(query)
}