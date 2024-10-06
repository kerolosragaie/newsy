package com.likander.newsy.features.detail.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.likander.newsy.R
import com.likander.newsy.core.theme.DEFAULT_PADDING
import com.likander.newsy.core.theme.DEFAULT_SPACING
import com.likander.newsy.core.theme.ITEM_SPACING
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.utils.Utils
import com.likander.newsy.features.detail.domain.model.ArticleMetaData
import com.likander.newsy.features.detail.domain.model.DetailArticle

@Composable
fun ArticleContent(
    article: DetailArticle,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(DEFAULT_PADDING),
        state = state
    ) {
        articleContentItems(article)
    }
}

private fun LazyListScope.articleContentItems(article: DetailArticle) {
    item {
        ArticleImage(article.urlToImage.orEmpty())
        Spacer(modifier = Modifier.height(ITEM_SPACING))
        Text(text = article.title, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(ITEM_SPACING))
        if (article.description.isNotEmpty()) {
            Text(text = article.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(DEFAULT_SPACING))
        }
    }
    item {
        ArticleMetaDataContent(
            articleMetaData = article.articleMetaData,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
    items(
        items = article.paragraphs,
        key = { index -> index }
    ) { paragraph ->
        ParagraphContent(paragraph)
    }
    item {
        val navigateToUrl: () -> Unit = rememberIntentNavigate(article.urlToImage.orEmpty())

        TextButton(onClick = navigateToUrl) {
            Text("Click here to view full article")
        }
    }
}

@Composable
private fun rememberIntentNavigate(url: String): () -> Unit {
    val context = LocalContext.current
    return remember(url) {
        {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
    }
}

@Composable
private fun ArticleImage(url: String) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(true)
        .build()

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp, max = 300.dp),
        model = imgRequest,
        placeholder = painterResource(R.drawable.news_place_holder),
        error = painterResource(R.drawable.news_place_holder),
        contentDescription = "news image",
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun ArticleMetaDataContent(
    articleMetaData: ArticleMetaData,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = null,
            colorFilter = ColorFilter.tint(LocalContentColor.current),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(ITEM_SPACING))
        Column {
            val formattedDate = Utils.formatPublishedAtDate(articleMetaData.publishedAt)
            val readingTime = "${articleMetaData.readingTime} min read"
            Text(
                text = articleMetaData.author,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(top = 3.dp)
            )
            Text(
                text = "$formattedDate • $readingTime"
            )
        }
    }
}

@Composable
private fun ParagraphContent(paragraph: String) {
    Box(modifier = Modifier.padding(DEFAULT_PADDING)) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = paragraph,
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp)
        )
    }
}

@PreviewLightDark
@Composable
private fun PrevArticleContent() {
    NewsyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ArticleContent(article = fakeArticle)
        }
    }
}

@PreviewLightDark
@Composable
private fun PrevArticleMetaDataContent() {
    NewsyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ArticleMetaDataContent(fakeArticle.articleMetaData)
        }
    }
}

private val fakeArticle =
    DetailArticle(
        id = 123,
        content = """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
        Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
    """.trimIndent(),
        description = "An in-depth look into the effects of lorem ipsum on modern web design.",
        title = "The Impact of Lorem Ipsum on Web Design",
        url = "https://www.example.com/articles/lorem-ipsum-impact",
        urlToImage = "https://www.example.com/images/lorem-ipsum-impact.jpg",
        favourite = false,
        category = "Technology",
        articleMetaData = ArticleMetaData(
            author = "Jane Doe",
            publishedAt = "2024-07-13T14:30:00Z",
            source = "TechTimes",
            readingTime = 5
        )
    )