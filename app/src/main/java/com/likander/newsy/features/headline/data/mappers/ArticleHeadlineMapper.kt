package com.likander.newsy.features.headline.data.mappers

import com.likander.newsy.core.common.data.model.ArticleDto
import com.likander.newsy.features.headline.data.local.models.HeadlineArticleEntity

fun ArticleDto.toHeadlineEntity(page: Int, category: String): HeadlineArticleEntity =
    HeadlineArticleEntity(
        author = formatEmptyValue(author, "author"),
        content = formatEmptyValue(content, "content"),
        description = formatEmptyValue(description, "description"),
        publishedAt = publishedAt,
        source = sourceDto?.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
        page = page,
        category = category,
    )

private fun formatEmptyValue(value: String?, default: String = ""): String =
    value ?: "Unknown $default"