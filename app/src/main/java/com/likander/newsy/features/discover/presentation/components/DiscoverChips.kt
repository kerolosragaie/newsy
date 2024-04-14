package com.likander.newsy.features.discover.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.likander.newsy.core.theme.ITEM_PADDING
import com.likander.newsy.core.utils.ArticleCategory

@Composable
fun DiscoverChips(
    selectedCategory: ArticleCategory,
    categories: List<ArticleCategory>,
    onCategoryChange: (ArticleCategory) -> Unit
) {
    LazyRow {
        items(
            categories.size,
            key = { categories[it].ordinal }
        ) { index ->
            DiscoverChip(
                selected = categories[index] == selectedCategory,
                onCLick = { onCategoryChange(categories[index]) },
                label = categories[index].name
            )
        }
    }
}

@Composable
private fun DiscoverChip(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onCLick: () -> Unit,
    label: String
) {
    InputChip(
        modifier=modifier.padding(horizontal = ITEM_PADDING),
        selected = selected,
        onClick = onCLick,
        label = { Text(text = label) },
    )
}