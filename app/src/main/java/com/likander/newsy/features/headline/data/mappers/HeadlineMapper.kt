package com.likander.newsy.features.headline.data.mappers

import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.data.local.models.HeadlineEntity

class HeadlineMapper : Mapper<HeadlineEntity, Article> {
    override fun toModel(value: HeadlineEntity): Article = value.run {
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

    override fun fromModelToEntity(value: Article): HeadlineEntity =
        value.run {
            HeadlineEntity(
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