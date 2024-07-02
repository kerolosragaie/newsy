package com.likander.newsy.core

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.likander.newsy.core.common.navigation.graphs.AppNavGraph
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsyApp : Application()

@Composable
fun NewsyApplication() {
    val navController = rememberNavController()

    AppNavGraph(
        navController = navController,
        openDrawer = {}
    )
}