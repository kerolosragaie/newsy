package com.likander.newsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.likander.newsy.core.NewsyApplication
import com.likander.newsy.core.theme.NewsyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsyTheme {
                NewsyApplication()
            }
        }
    }
}
