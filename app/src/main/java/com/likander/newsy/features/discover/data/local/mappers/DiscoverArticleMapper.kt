package com.likander.newsy.features.discover.data.local.mappers

import com.likander.newsy.core.common.data.mappers.Mapper
import com.likander.newsy.core.common.data.model.ArticleDto
import com.likander.newsy.features.discover.data.local.model.DiscoverArticleEntity
import com.likander.newsy.features.headline.domain.model.Article

class DiscoverArticleMapper : Mapper<DiscoverArticleEntity, Article> {
    override fun toModel(value: DiscoverArticleEntity): Article =
        Article(
            id = value.id,
            publishedAt = value.publishedAt,
            favourite = value.favourite,
            description = value.description,
            title = value.title,
            source = value.source,
            url = value.url,
            category = value.category,
            urlToImage = value.urlToImage,
            page = value.page,
            content = value.content,
            author = value.author
        )

    override fun fromModelToEntity(value: Article): DiscoverArticleEntity =
        DiscoverArticleEntity(
            id = value.id,
            author = value.author,
            content = value.content,
            page = value.page,
            urlToImage = value.urlToImage,
            url = value.url,
            category = value.category,
            source = value.source,
            title = value.title,
            publishedAt = value.publishedAt,
            description = value.description,
            favourite = value.favourite
        )


}

fun ArticleDto.toDiscoverArticleEntity(page: Int, category: String): DiscoverArticleEntity =
    DiscoverArticleEntity(
        author = author,
        content = content ?: "empty value",
        description = description ?: "empty value",
        publishedAt = publishedAt,
        title = title,
        source = sourceDto?.name ?: "empty value",
        category = category,
        url = url,
        urlToImage = urlToImage,
        page = page
    )