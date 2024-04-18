package com.likander.newsy.features.headline.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.likander.newsy.R
import com.likander.newsy.core.common.components.NetworkImage
import com.likander.newsy.core.theme.DEFAULT_PADDING
import com.likander.newsy.core.theme.ITEM_PADDING
import com.likander.newsy.core.theme.ITEM_SPACING
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.headline.domain.model.Article
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlineItems(
    articles: List<Article>,
    articleCount: Int,
    onCardClick: (Article) -> Unit,
    onViewMoreClick: () -> Unit,
    onFavouriteChange: (Article) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { articleCount }
    )
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    var triggerAnimationKey by remember { mutableStateOf(false) }

    LaunchedEffect(triggerAnimationKey, isDragged) {
        delay(5000)
        with(pagerState) {
            val targetItemIndex = if (currentPage < pageCount - 1) currentPage + 1 else 0
            animateScrollToPage(targetItemIndex)
        }
        triggerAnimationKey = !triggerAnimationKey
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(DEFAULT_PADDING),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            pageSpacing = ITEM_SPACING,
        ) { page ->
            AnimatedContent(
                targetState = page,
                label = "Headline animated card",
            ) { index ->
                HeadlineCard(
                    article = articles[index],
                    onCardClick = onCardClick,
                    onFavouriteChange = onFavouriteChange,
                )
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        TextButton(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.End),
            onClick = onViewMoreClick,
        ) {
            Text(text = stringResource(R.string.view_more))
        }
    }
}

@Composable
private fun HeadlineCard(
    modifier: Modifier = Modifier,
    article: Article,
    onCardClick: (Article) -> Unit,
    onFavouriteChange: (Article) -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = { onCardClick.invoke(article) },
    ) {
        Column {
            NetworkImage(
                modifier = Modifier.height(150.dp),
                url = article.urlToImage.toString()
            )
            Text(
                modifier = Modifier.padding(ITEM_PADDING),
                text = article.title.toString(),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Row {
                Column(
                    modifier = Modifier.padding(ITEM_PADDING)
                ) {
                    Text(
                        text = article.source.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = Utils.formatPublishedAtDate(article.publishedAt.toString()),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                IconButton(onClick = { onFavouriteChange.invoke(article) }) {
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

@PreviewLightDark
@Composable
private fun PrevHeadlineItem() {
    NewsyTheme {
        Surface {
            HeadlineItems(
                articles = fakeArticles,
                articleCount = fakeArticles.size,
                onCardClick = {},
                onViewMoreClick = {},
                onFavouriteChange = {},
            )
        }
    }
}

val fakeArticles = listOf(
    Article(
        id = 1,
        author = "John Doe",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        description = "Sample article description.",
        publishedAt = "2024-02-27T12:34:56Z",
        source = "Fake News Network",
        title = "Sample Article Title, Fake News Network Title",
        url = "https://oilprice.com/Energy/Energy-General/Supply-Chain-Woes-Could-Derail-Bidens-Electric-Vehicle-Agenda.html",
        urlToImage = "https://d32r1sh890xpii.cloudfront.net/article/718x300/2024-02-27_zharv8scwu.jpg",
        favourite = false,
        category = "Technology",
        page = 1
    ),
    Article(
        id = 2,
        author = "Mark tom",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et aliquet risus. Nullam id ligula at justo suscipit lacinia. Morbi auctor, lorem in lacinia mollis, risus ipsum porttitor eros, sit amet tincidunt nulla purus a enim. Quisque eget sagittis ante.",
        description = "Another sample article description.",
        publishedAt = "2024-02-28T09:12:34Z",
        source = "Fictional News Agency",
        title = "Another Sample Article Title",
        url = "http://electrek.co/2024/02/27/aventon-ramblas-electric-mountain-bike-ankers-new-power-station-more/",
        urlToImage = "https://i0.wp.com/electrek.co/wp-content/uploads/sites/3/2024/02/aventon-ramblas-header.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1",
        favourite = true,
        category = "Business",
        page = 2
    ),
    Article(
        id = 3,
        author = "Mark tom",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed et aliquet risus. Nullam id ligula at justo suscipit lacinia. Morbi auctor, lorem in lacinia mollis, risus ipsum porttitor eros, sit amet tincidunt nulla purus a enim. Quisque eget sagittis ante.",
        description = "Another sample article description.",
        publishedAt = "2024-02-28T09:12:34Z",
        source = "Fictional News Agency",
        title = "Another Sample Article Title",
        url = "http://electrek.co/2024/02/27/aventon-ramblas-electric-mountain-bike-ankers-new-power-station-more/",
        urlToImage = "https://i0.wp.com/electrek.co/wp-content/uploads/sites/3/2024/02/aventon-ramblas-header.jpg?resize=1200%2C628&quality=82&strip=all&ssl=1",
        favourite = false,
        category = "Business",
        page = 3
    )
)