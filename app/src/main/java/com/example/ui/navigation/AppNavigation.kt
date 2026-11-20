package com.example.ui.navigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.ui.screens.AsmaUlHusnaScreen
import com.example.ui.screens.DuasScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PrayersAndQiblaScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.QuranHomeScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SurahReaderScreen
import com.example.ui.screens.TasbeehScreen
import com.example.ui.viewmodel.ExploreViewModel
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.PrayersQiblaViewModel
import com.example.ui.viewmodel.QuranViewModel
import kotlinx.serialization.Serializable

@Serializable object HomeRoute
@Serializable object QuranRoute
@Serializable data class SurahReaderRoute(val surahNumber: Int, val initialAyah: Int = 1)
@Serializable data class PrayersRoute(val initialTab: Int = 0)
@Serializable object TasbeehRoute
@Serializable object DuasRoute
@Serializable object AsmaUlHusnaRoute
@Serializable object QuizRoute
@Serializable object SettingsRoute

private data class TopLevelNavItem(
    val label: String,
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun HafizIslamicHubRoot(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.Factory),
    quranViewModel: QuranViewModel = viewModel(factory = QuranViewModel.Factory),
    prayersQiblaViewModel: PrayersQiblaViewModel = viewModel(factory = PrayersQiblaViewModel.Factory),
    exploreViewModel: ExploreViewModel = viewModel(factory = ExploreViewModel.Factory)
) {
    val navController = rememberNavController()
    val preferences by mainViewModel.preferences.collectAsStateWithLifecycle()
    val prayerSchedule by mainViewModel.prayerSchedule.collectAsStateWithLifecycle()
    val statusMessage by mainViewModel.statusMessage.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(statusMessage) {
        statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            mainViewModel.clearStatusMessage()
        }
    }

    val topLevelItems = remember {
        listOf(
            TopLevelNavItem("Home", HomeRoute, Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
            TopLevelNavItem("Quran", QuranRoute, Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook, "nav_quran"),
            TopLevelNavItem("Prayers", PrayersRoute(0), Icons.Filled.AccessTime, Icons.Outlined.AccessTime, "nav_prayers"),
            TopLevelNavItem("Tasbeeh", TasbeehRoute, Icons.Filled.Fingerprint, Icons.Outlined.Fingerprint, "nav_tasbeeh"),
            TopLevelNavItem("Settings", SettingsRoute, Icons.Filled.Settings, Icons.Outlined.Settings, "nav_settings")
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDest = navBackStackEntry?.destination

    val showChrome = currentDest?.let { dest ->
        dest.hasRoute<HomeRoute>() ||
            dest.hasRoute<QuranRoute>() ||
            dest.hasRoute<PrayersRoute>() ||
            dest.hasRoute<TasbeehRoute>() ||
            dest.hasRoute<SettingsRoute>()
    } ?: true

    val navigateTopLevel: (Any) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (showChrome && !useNavigationRail) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 4.dp
                    ) {
                        topLevelItems.forEach { item ->
                            val selected = when (item.route) {
                                is HomeRoute -> currentDest?.hasRoute<HomeRoute>() == true
                                is QuranRoute -> currentDest?.hasRoute<QuranRoute>() == true
                                is PrayersRoute -> currentDest?.hasRoute<PrayersRoute>() == true
                                is TasbeehRoute -> currentDest?.hasRoute<TasbeehRoute>() == true
                                is SettingsRoute -> currentDest?.hasRoute<SettingsRoute>() == true
                                else -> false
                            }
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateTopLevel(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = { Text(item.label, maxLines = 1) },
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showChrome && useNavigationRail) {
                    NavigationRail(containerColor = MaterialTheme.colorScheme.surface) {
                        topLevelItems.forEach { item ->
                            val selected = when (item.route) {
                                is HomeRoute -> currentDest?.hasRoute<HomeRoute>() == true
                                is QuranRoute -> currentDest?.hasRoute<QuranRoute>() == true
                                is PrayersRoute -> currentDest?.hasRoute<PrayersRoute>() == true
                                is TasbeehRoute -> currentDest?.hasRoute<TasbeehRoute>() == true
                                is SettingsRoute -> currentDest?.hasRoute<SettingsRoute>() == true
                                else -> false
                            }
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTopLevel(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.label
                                    )
                                },
                                label = { Text(item.label) },
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = HomeRoute,
                    modifier = Modifier.weight(1f)
                ) {
                    composable<HomeRoute> {
                        HomeScreen(
                            preferences = preferences,
                            prayerSchedule = prayerSchedule,
                            dailyVerse = mainViewModel.dailyVerse,
                            dailyReminder = mainViewModel.dailyReminder,
                            onOpenQuran = { navigateTopLevel(QuranRoute) },
                            onResumeLastRead = { surah, ayah ->
                                navController.navigate(SurahReaderRoute(surah, ayah))
                            },
                            onOpenPrayers = { navigateTopLevel(PrayersRoute(0)) },
                            onOpenQibla = { navController.navigate(PrayersRoute(1)) },
                            onOpenDuas = { navController.navigate(DuasRoute) },
                            onOpenAsmaUlHusna = { navController.navigate(AsmaUlHusnaRoute) },
                            onOpenTasbeeh = { navigateTopLevel(TasbeehRoute) },
                            onOpenQuiz = { navController.navigate(QuizRoute) },
                            onOpenSettings = { navigateTopLevel(SettingsRoute) }
                        )
                    }

                    composable<QuranRoute> {
                        QuranHomeScreen(
                            quranViewModel = quranViewModel,
                            preferences = preferences,
                            onOpenSurah = { surahNum, ayahNum ->
                                navController.navigate(SurahReaderRoute(surahNum, ayahNum))
                            }
                        )
                    }

                    composable<SurahReaderRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<SurahReaderRoute>()
                        SurahReaderScreen(
                            surahNumber = args.surahNumber,
                            initialAyah = args.initialAyah,
                            quranViewModel = quranViewModel,
                            preferences = preferences,
                            onUpdateFontSizes = { ar, ur -> mainViewModel.setFontSizes(ar, ur) },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable<PrayersRoute> { backStackEntry ->
                        val args = backStackEntry.toRoute<PrayersRoute>()
                        PrayersAndQiblaScreen(
                            initialTab = args.initialTab,
                            preferences = preferences,
                            prayerSchedule = prayerSchedule,
                            viewModel = prayersQiblaViewModel,
                            onOpenNotificationSettings = { navigateTopLevel(SettingsRoute) }
                        )
                    }

                    composable<TasbeehRoute> {
                        TasbeehScreen(
                            preferences = preferences,
                            mainViewModel = mainViewModel
                        )
                    }

                    composable<DuasRoute> {
                        DuasScreen(
                            viewModel = exploreViewModel,
                            preferences = preferences,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable<AsmaUlHusnaRoute> {
                        AsmaUlHusnaScreen(
                            viewModel = exploreViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable<QuizRoute> {
                        QuizScreen(
                            viewModel = exploreViewModel,
                            preferences = preferences,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable<SettingsRoute> {
                        SettingsScreen(
                            preferences = preferences,
                            mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }
    }
}
