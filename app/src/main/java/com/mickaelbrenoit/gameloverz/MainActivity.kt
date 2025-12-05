package com.mickaelbrenoit.gameloverz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mickaelbrenoit.gameloverz.ui.theme.GameLoverzTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameLoverzTheme {
                GameLoverzApp()
            }
        }
    }
}

@Composable
fun GameLoverzApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val destinations = AppDestination.entries
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationScaffold(
        destinations = destinations,
        currentDestination = currentDestination,
        onDestinationSelected = { destination ->
            navController.navigate(destination.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = modifier
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Collection.route,
            modifier = paddingValues
        ) {
            composable(AppDestination.Collection.route) { CollectionScreen() }
            composable(AppDestination.Wishlist.route) { WishlistScreen() }
            composable(AppDestination.Settings.route) { SettingsScreen() }
        }
    }
}

private enum class AppDestination(
    val route: String,
    val labelRes: Int,
    val icon: ImageVector,
) {
    Collection(
        route = "collection",
        labelRes = R.string.collection_title,
        icon = Icons.Default.Home
    ),
    Wishlist(
        route = "wishlist",
        labelRes = R.string.wishlist_title,
        icon = Icons.Default.Favorite
    ),
    Settings(
        route = "settings",
        labelRes = R.string.settings_title,
        icon = Icons.Default.Settings
    ),
}

@Composable
private fun NavigationScaffold(
    destinations: List<AppDestination>,
    currentDestination: NavDestination?,
    onDestinationSelected: (AppDestination) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit,
) {
    androidx.compose.material3.Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    val selected = currentDestination.isRouteInHierarchy(destination.route)
                    NavigationBarItem(
                        selected = selected,
                        onClick = { onDestinationSelected(destination) },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = stringResource(destination.labelRes)
                            )
                        },
                        label = { Text(text = stringResource(destination.labelRes)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

@Composable
private fun NavDestination?.isRouteInHierarchy(route: String): Boolean {
    return this?.hierarchy?.any { it.route == route } == true
}

@Composable
private fun CollectionScreen() {
    ScreenPlaceholder(text = stringResource(R.string.collection_title))
}

@Composable
private fun WishlistScreen() {
    ScreenPlaceholder(text = stringResource(R.string.wishlist_title))
}

@Composable
private fun SettingsScreen() {
    ScreenPlaceholder(text = stringResource(R.string.settings_title))
}

@Composable
private fun ScreenPlaceholder(text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }
}

@PreviewScreenSizes
@Preview(showBackground = true)
@Composable
fun GameLoverzAppPreview() {
    GameLoverzTheme {
        GameLoverzApp()
    }
}
