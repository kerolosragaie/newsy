package com.likander.newsy.core.common.navigation.graphs

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.likander.newsy.core.common.navigation.navigateTo

private sealed class AccountDestinations(val route: String) {
    companion object {
        const val SETTINGS = "settings"
        const val FAVOURITE = "favourite"
    }

    data object SettingsScreen : AccountDestinations(SETTINGS)
    data object FavouriteScreen : AccountDestinations(FAVOURITE)
}

fun NavGraphBuilder.accountGraph(route: String, navController: NavController) {
    navigation(
        route = route,
        startDestination = AccountDestinations.SettingsScreen.route
    ) {
        composable(route = AccountDestinations.SettingsScreen.route) {
            val actions = remember { AccountGraphActions(navController) }

        }

        composable(route = AccountDestinations.FavouriteScreen.route) {
            val actions = remember { AccountGraphActions(navController) }

        }
    }
}

private class AccountGraphActions(navController: NavController) {
    val navigateUp: () -> Unit = { navController.navigateUp() }

    val navigateToFavouriteScreen: () -> Unit = {
        navController.navigateTo(AccountDestinations.FavouriteScreen.route)
    }
}