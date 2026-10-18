package com.obzrai.observer.feature.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.obzrai.observer.R
import com.obzrai.observer.core.prefs.ObserverPreferences
import com.obzrai.observer.core.ui.ObserverRing
import com.obzrai.observer.feature.home.HomeScreen
import com.obzrai.observer.feature.home.HomeViewModel
import com.obzrai.observer.feature.library.LibraryScreen
import com.obzrai.observer.feature.onboarding.OnboardingScreen
import com.obzrai.observer.feature.settings.SettingsScreen
import com.obzrai.observer.feature.today.TodayScreen
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private object Routes {
    const val HOME = "home"
    const val LIBRARY = "library"
    const val TODAY = "today"
    const val SETTINGS = "settings"
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PrefsEntryPoint {
    fun preferences(): ObserverPreferences
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObserverApp(
    homeViewModel: HomeViewModel? = null,
) {
    val context = LocalContext.current
    val prefs =
        remember {
            EntryPointAccessors.fromApplication(context, PrefsEntryPoint::class.java).preferences()
        }
    val scope = rememberCoroutineScope()
    var showOnboarding by remember { mutableStateOf<Boolean?>(null) }
    LaunchedEffect(Unit) {
        showOnboarding = !prefs.onboardingComplete.first()
    }

    when (showOnboarding) {
        null -> return
        true -> {
            OnboardingScreen(
                onFinished = {
                    scope.launch {
                        prefs.setOnboardingComplete(true)
                        showOnboarding = false
                    }
                },
            )
            return
        }
        false -> Unit
    }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    ObserverRing(
                        modifier = Modifier.padding(start = 12.dp),
                        size = 28.dp,
                        showGlyph = true,
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SETTINGS) }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = stringResource(R.string.settings),
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Routes.HOME } == true,
                    onClick = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                    label = { Text(stringResource(R.string.nav_home)) },
                )
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Routes.TODAY } == true,
                    onClick = {
                        navController.navigate(Routes.TODAY) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Outlined.Today, contentDescription = null) },
                    label = { Text(stringResource(R.string.nav_today)) },
                )
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Routes.LIBRARY } == true,
                    onClick = {
                        navController.navigate(Routes.LIBRARY) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(Icons.AutoMirrored.Outlined.LibraryBooks, contentDescription = null)
                    },
                    label = { Text(stringResource(R.string.nav_library)) },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Routes.HOME) {
                if (homeViewModel != null) {
                    HomeScreen(viewModel = homeViewModel)
                } else {
                    HomeScreen()
                }
            }
            composable(Routes.TODAY) { TodayScreen() }
            composable(Routes.LIBRARY) { LibraryScreen() }
            composable(Routes.SETTINGS) { SettingsScreen() }
        }
    }
}
