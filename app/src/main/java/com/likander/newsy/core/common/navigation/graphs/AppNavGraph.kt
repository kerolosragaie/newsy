package com.likander.newsy.core.common.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.likander.newsy.R
import com.likander.newsy.core.common.components.ErrorContent
import com.likander.newsy.core.common.navigation.graphs.AppDestinations.DetailsScreen.ARTICLE_ID
import com.likander.newsy.core.common.navigation.graphs.AppDestinations.DetailsScreen.SCREEN_TYPE
import com.likander.newsy.core.common.navigation.navigateTo
import com.likander.newsy.features.detail.presentation.DetailScreen
import com.likander.newsy.features.headline.presentation.HeadlineScreen
import com.likander.newsy.features.headline.presentation.HomeScreen

sealed class AppDestinations(val route: String) {
    private companion object {
        const val HOME = "home"
        const val HEADLINE = "headline"
        const val DISCOVER = "discover"
        const val DETAILS = "details"
        const val SEARCH = "search"
        const val ERROR = "ERROR"
        const val ACCOUNT_GRAPH = "account_graph"
    }

    data object HomeScreen : AppDestinations(HOME)
    data object HeadlineScreen : AppDestinations(HEADLINE)
    data object DiscoverScreen : AppDestinations(DISCOVER)
    data object DetailsScreen : AppDestinations(DETAILS) {
        const val ARTICLE_ID = "article_id"
        const val SCREEN_TYPE = "screen_type"
    }

    data object SearchScreen : AppDestinations(SEARCH)
    data object ErrorScreen : AppDestinations(ERROR)
    data object AccountGraph : AppDestinations(ACCOUNT_GRAPH)
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    openDrawer: () -> Unit
) {
    val actions = remember(navController) { AppNavGraphActions(navController) }

    NavHost(
        navController = navController,
        startDestination = AppDestinations.HomeScreen.route
    ) {
        composable(route = AppDestinations.HomeScreen.route) {
            HomeScreen(
                onViewMoreClick = { actions.navigateToHeadlineScreen() },
                onHeadlineItemClick = {
                    actions.navigateToDetailsScreen(it.id, AppDestinations.HeadlineScreen.route)
                },
                onDiscoverItemClick = {
                    actions.navigateToDetailsScreen(it.id, AppDestinations.DiscoverScreen.route)
                },
                openDrawer = openDrawer,
                onSearch = { actions.navigateToSearchScreen() }
            )
        }

        composable(route = AppDestinations.HeadlineScreen.route) {
            HeadlineScreen(
                onItemClick = { actions.navigateToDetailsScreen(it, AppDestinations.HeadlineScreen.route) }
            )
        }

        composable(route = AppDestinations.DiscoverScreen.route) {

        }

        composable(route = AppDestinations.SearchScreen.route) {

        }

        composable(route = AppDestinations.ErrorScreen.route) {
            ErrorContent(
                buttonName = stringResource(R.string.go_back),
                retryFunc = { actions.navigateUp() }
            )
        }

        composable(
            route = "${AppDestinations.DetailsScreen.route}/{$ARTICLE_ID}&{$SCREEN_TYPE}",
            arguments = listOf(
                navArgument(ARTICLE_ID) {
                    type = NavType.IntType
                },
                navArgument(SCREEN_TYPE) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(onBackPressed = { actions.navigateUp() })
        }

        accountGraph(
            route = AppDestinations.AccountGraph.route,
            navController = navController
        )
    }
}

private class AppNavGraphActions(navController: NavController) {
    val navigateUp: () -> Unit = { navController.navigateUp() }

    val navigateToHomeScreen: () -> Unit = {
        navController.navigateTo(AppDestinations.HomeScreen.route)
    }

    val navigateToDetailsScreen: (id: Int, screenType: String) -> Unit = { id, screenType ->
        navController.navigateTo("${AppDestinations.DetailsScreen.route}/$id&$screenType")
    }

    val navigateToHeadlineScreen: () -> Unit = {
        navController.navigateTo(AppDestinations.HeadlineScreen.route)
    }

    val navigateToDiscoverScreen: () -> Unit = {
        navController.navigateTo(AppDestinations.DiscoverScreen.route)
    }

    val navigateToAccountGraph: () -> Unit = {
        navController.navigateTo(AppDestinations.AccountGraph.route)
    }

    val navigateToSearchScreen: () -> Unit = {
        navController.navigateTo(AppDestinations.SearchScreen.route)
    }
}