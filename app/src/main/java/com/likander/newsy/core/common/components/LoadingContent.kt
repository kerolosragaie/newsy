package com.likander.newsy.core.common.components

import android.content.res.Configuration
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.core.theme.DEFAULT_PADDING

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = false,
    color: Color = MaterialTheme.colorScheme.onSurface,
) {
    Box(
        modifier = modifier
            .padding(DEFAULT_PADDING)
            .fillMaxSize()
            .background(if (isOverlay) Color(0xCC000000) else Color.Transparent)
            .testTag(LOADING_CONTAINER_ID)
    ) {
        LoadingSpinnerIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .testTag(LOADING_INDICATOR_ID),
            color = color
        )
    }
}

@Composable
private fun LoadingSpinnerIndicator(
    modifier: Modifier = Modifier,
    sections: Int = 12,
    color: Color,
    sectionLength: Dp = 8.dp,
    sectionWidth: Dp = 4.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite_transition")
    val sectionOffset by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = sections,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            keyframes { delayMillis = 0 },
            repeatMode = RepeatMode.Restart
        ),
        label = "angle_animation"
    )

    Canvas(modifier) {
        val radius = size.height / 2
        val angle = 360f / sections
        val alpha = 1f / sections

        rotate(sectionOffset * angle) {
            for (i in 1..sections) {
                rotate(angle * i) {
                    drawLine(
                        color = color.copy(alpha = alpha * i),
                        strokeWidth = sectionWidth.toPx(),
                        start = Offset(
                            x = radius,
                            y = sectionLength.toPx()
                        ),
                        end = Offset(
                            x = radius,
                            y = sectionLength.toPx() * 2
                        ),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Preview("Light", showSystemUi = true, showBackground = true)
@Preview(
    "Dark",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewLoadingContent() {
    NewsyTheme {
        LoadingContent(isOverlay = true)
    }
}

internal const val LOADING_CONTAINER_ID = "overlay_loading_container"
internal const val LOADING_INDICATOR_ID = "overlay_loading_indicator"