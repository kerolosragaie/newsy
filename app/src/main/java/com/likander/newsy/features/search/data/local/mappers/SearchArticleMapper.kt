package com.likander.newsy.features.search.data.local.mappers

import com.likander.newsy.core.common.data.model.ArticleDto
import com.likander.newsy.features.search.data.local.model.SearchEntity
import com.likander.newsy.features.search.domain.model.SearchArticle

fun ArticleDto.toSearchEntity(page: Int, category: String): SearchEntity =
    SearchEntity(
        author = author.toString(),
        content = content.toString(),
        description = description.toString(),
        publishedAt = publishedAt.toString(),
        title = title.toString(),
        url = url.toString(),
        urlToImage = urlToImage.toString(),
        category = category,
        page = page,
        source = sourceDto?.name.toString()
    )

fun SearchEntity.toDomainModel(): SearchArticle =
    SearchArticle(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage,
        category = category,
        page = page,
        source = source
    )