package com.likander.newsy.features.detail.presentation.model

import com.likander.newsy.core.utils.UiState
import com.likander.newsy.features.detail.domain.model.DetailArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DetailUiState(
    val selectedDetailArticle: Flow<UiState<DetailArticle>> = emptyFlow(),
    val error: Boolean = false
)
