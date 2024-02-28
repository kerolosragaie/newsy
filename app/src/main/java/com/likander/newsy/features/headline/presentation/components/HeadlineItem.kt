package com.likander.newsy.features.home.components

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.likander.newsy.R
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.theme.defaultPadding
import com.likander.newsy.core.theme.itemPadding
import com.likander.newsy.core.theme.itemSpacing
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.headline.domain.model.Article
import kotlinx.coroutines.delay


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeadlineItem(
    articles: List<Article>,
    articleCount: Int,
    onCardClick: (Article) -> Unit,
    onViewMoreClick: () -> Unit,
    onFavouriteChange: (Article) -> Unit,
) {
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { articleCount }
    )

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pagerState.currentPage) {
        if (isDragged && !isAutoScrolling) {
            isAutoScrolling = false
            delay(8000)
            isAutoScrolling = true
        } else {
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < articleCount - 1) currentPage + 1 else 0
                scrollToPage(target)
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(defaultPadding),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            pageSpacing = itemSpacing,
        ) { page ->
            if (isAutoScrolling) {
                AnimatedContent(
                    targetState = page,
                    label = "",
                ) { index ->
                    HeadlineCard(
                        article = articles[index],
                        onCardClick = onCardClick,
                        onFavouriteChange = onFavouriteChange,
                    )
                }
            } else {
                HeadlineCard(
                    article = articles[page],
                    onCardClick = onCardClick,
                    onFavouriteChange = onFavouriteChange,
                )
            }
        }
        Spacer(modifier = Modifier.size(2.dp))
        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onViewMoreClick,
        ) {
            Text(text = "View more")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeadlineCard(
    modifier: Modifier = Modifier,
    article: Article,
    onCardClick: (Article) -> Unit,
    onFavouriteChange: (Article) -> Unit,
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(article.urlToImage)
        .crossfade(true).build()
    val favouriteIcon =
        if (article.favourite) Icons.Default.BookmarkAdded else Icons.Default.Bookmark

    Card(
        modifier = modifier,
        onClick = { onCardClick.invoke(article) },
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.height(150.dp),
                model = imageRequest,
                contentDescription = "news image",
                placeholder = painterResource(R.drawable.ideogram_2_),
                error = painterResource(id = R.drawable.ideogram_2_),
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier.padding(itemPadding),
                text = article.title.toString(),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
            )

            Row {
                Column(
                    modifier = Modifier.padding(itemPadding)
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
                    Icon(
                        imageVector = favouriteIcon,
                        contentDescription = "favourite",
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PrevHeadlineItem() {
    NewsyTheme {
        Surface {
            HeadlineItem(
                articles = articles,
                articleCount = articles.size,
                onCardClick = {},
                onViewMoreClick = { /*TODO*/ },
                onFavouriteChange = {},
            )
        }
    }
}

private val articles = listOf(
    Article(
        id = 1,
        author = "John Doe",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        description = "Sample article description.",
        publishedAt = "2024-02-27T12:34:56Z",
        source = "Fake News Network",
        title = "Sample Article Title",
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