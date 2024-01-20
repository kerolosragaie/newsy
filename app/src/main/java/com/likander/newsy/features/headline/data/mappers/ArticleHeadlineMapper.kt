package com.likander.newsy.features.headline.data.mappers

import com.likander.newsy.features.headline.data.remote.model.ArticleDto
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity

class ArticleHeadlineMapper(
    private val page: Int = 0,
    private val category: String = "",
) : Mapper<ArticleDto, HeadlineEntity> {
    override fun toModel(value: ArticleDto): HeadlineEntity = value.run {
        HeadlineEntity(
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
    }

    override fun fromModelToEntity(value: HeadlineEntity): ArticleDto = value.run {
        ArticleDto(
            author,
            content,
            description,
            publishedAt,
        )
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String =
        value ?: "Unknown $default"
}