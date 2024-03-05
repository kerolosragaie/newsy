package com.likander.newsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.likander.newsy.core.theme.NewsyTheme
import com.likander.newsy.features.headline.presentation.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsyTheme {
                HomeScreen(
                    onViewMoreClick = {},
                    openDrawer = {},
                    onSearch = {},
                    onHeadlineItemClick = {},
                )
            }
        }
    }
}
