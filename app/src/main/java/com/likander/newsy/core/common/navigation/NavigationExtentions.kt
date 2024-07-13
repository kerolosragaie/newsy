package com.likander.newsy.core.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.likander.newsy.core.common.navigation.graphs.AppDestinations

/**
 * Safely navigates to a route in Nav-graph, handling errors with a callback or fallback screen.
 *
 * @param route The route to navigate to.
 * @param navOptions The navigation options to use, or null to use the default options.
 * @param navigatorExtras The navigator extras to use, or null to use no extras.
 * @param errorFallbackRoute A route to navigate to if the specified route is not found.
 * @param onDestinationNotFound A lambda function to execute if the destination is not found, or null to use the default behavior.
 *
 * @throws IllegalArgumentException if the destination specified by the route cannot be found in the navigation graph.
 *
 * @see NavOptions
 * @see Navigator.Extras
 */
fun NavController.navigateTo(
    route: String,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
    errorFallbackRoute: String = AppDestinations.ErrorScreen.route,
    onDestinationNotFound: (() -> Unit)? = null
) {
    try {
        navigate(route, navOptions, navigatorExtras)
    } catch (_: Exception) {
        onDestinationNotFound?.invoke() ?: run {
            navigate(errorFallbackRoute, null, navigatorExtras)
        }
    }
}

/**
 * This function ensures that the same instance of the ViewModel is returned within the same
 * nested-navigation graph context.
 *
 * @param currentBackStackEntry The current NavBackStackEntry to associate with the ViewModel.
 * Note: You need to provide this parameter, it can't be figured out
 * as this.currentBackStackEntry within the scope of sharedViewModelForGraph.
 * @param graphRoute The route name of the navigation graph containing the desired ViewModel.
 * @return The instance of the ViewModel associated with the specified graph.
 *
 */
@Composable
inline fun <reified VM : ViewModel> NavHostController.sharedViewModelForGraph(
    currentBackStackEntry: NavBackStackEntry,
    graphRoute: String
): VM {
    val parentEntry = remember(currentBackStackEntry) { this.getBackStackEntry(graphRoute) }
    return hiltViewModel(parentEntry)
}

/**
 * This is used to de-duplicate navigation events.
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 */
fun NavBackStackEntry.lifecycleIsResumed() = this.lifecycle.currentState == Lifecycle.State.RESUMED