package com.likander.newsy.features.discover.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.likander.newsy.core.common.components.NetworkImage
import com.likander.newsy.core.theme.ITEM_PADDING
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.headline.domain.model.Article
import com.likander.newsy.features.headline.presentation.components.fakeArticles

@Composable
fun ArticleItem(
    article: Article,
    onClick: (Article) -> Unit,
    onFavouriteChange: (Article) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(ITEM_PADDING)
            .clickable { onClick(article) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NetworkImage(
                modifier = Modifier.weight(2f),
                url = article.urlToImage.toString()
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = article.title.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = article.source.toString(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = Utils.formatPublishedAtDate(article.publishedAt.toString()),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    IconButton(
                        onClick = { onFavouriteChange(article) }
                    ) {
                        AnimatedContent(targetState = article.favourite, label = "") {
                            when (it) {
                                true ->
                                    Icon(
                                        imageVector = Icons.Default.BookmarkAdded,
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = "favourite Icon Btn"
                                    )

                                false ->
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        tint = LocalContentColor.current,
                                        contentDescription = "favourite Icon Btn"
                                    )
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewArticleItem() {
    NewsyTheme {
        ArticleItem(
            article = fakeArticles.first(),
            onClick = {},
            onFavouriteChange = {},
        )
    }
}