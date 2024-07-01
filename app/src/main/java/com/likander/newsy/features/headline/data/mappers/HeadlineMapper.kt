package com.likander.newsy.features.headline.data.mappers

import com.likander.newsy.core.common.data.mappers.Mapper
import com.likander.newsy.features.headline.data.local.models.HeadlineArticleEntity
import com.likander.newsy.features.headline.domain.model.Article

class HeadlineMapper : Mapper<HeadlineArticleEntity, Article> {
    override fun toModel(value: HeadlineArticleEntity): Article =
        value.run {
            Article(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = source,
                title = title,
                url = url,
                urlToImage = urlToImage,
                page = page,
            )
        }

    override fun fromModelToEntity(value: Article): HeadlineArticleEntity =
        value.run {
            HeadlineArticleEntity(
                id = id,
                author = author,
                content = content,
                description = description,
                publishedAt = publishedAt,
                source = source,
                title = title,
                url = url,
                urlToImage = urlToImage,
                page = page,
            )
        }

}