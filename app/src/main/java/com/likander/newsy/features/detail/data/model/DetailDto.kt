package com.likander.newsy.features.detail.data.model

import androidx.room.ColumnInfo
import com.likander.newsy.features.detail.domain.model.ArticleMetaData
import com.likander.newsy.features.detail.domain.model.DetailArticle

data class DetailDto(
    val id: Int = 0,
    val author: String,
    val content: String,
    val description: String,
    @ColumnInfo("published_at")
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    @ColumnInfo("url_to_image")
    val urlToImage: String?,
    val favourite: Boolean = false,
    val category: String,
    @ColumnInfo(name = "page")
    var page: Int,
) {
    val readingTime: Int
        get() {
            val wordsPerMinute = 200
            val totalWords = content.split(Regex("\\s+")).size
            return if (totalWords < wordsPerMinute)
                1
            else
                totalWords / wordsPerMinute
        }
}

fun DetailDto.toDetailArticle(): DetailArticle =
    DetailArticle(
        id = id,
        content = content,
        description = description,
        title = title,
        url = url,
        urlToImage = urlToImage,
        favourite = favourite,
        category = category,
        articleMetaData = ArticleMetaData(
            author = author,
            publishedAt = publishedAt,
            source = source,
            readingTime = readingTime
        )
    )