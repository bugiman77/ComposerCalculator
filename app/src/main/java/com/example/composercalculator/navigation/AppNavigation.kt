package com.example.composercalculator.navigation

/*@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel = viewModel(),
    calculatorViewModel: CalculatorViewModel = viewModel(),
    appListViewModel: AppListViewModel = viewModel()
) {
    val navController = rememberNavController()

    val showHistoryButton = settingsViewModel.showHistoryButton.collectAsState()

    val animationDuration = 300 // Длительность анимации в миллисекундах
    val animationSpec = tween<IntOffset>(durationMillis = animationDuration)

    NavHost(
        navController = navController,
        startDestination = Routes.CALCULATOR
    ) {
        composable(
            route = Routes.CALCULATOR,
            // Анимация исчезновения (уезжает влево при переходе на Настройки)
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            // Анимация появления при возврате с Настроек
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            }
        ) {
            CalculatorScreen(
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                onNavigateToHistory = { navController.navigate(Routes.HISTORY) },
                showHistoryButton = showHistoryButton.value,
                viewModelSettings = settingsViewModel,
                viewModelCalculation = calculatorViewModel
            )
        }

        composable(
            route = Routes.SETTINGS,
            // Анимация появления (экран въезжает слева)
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            // Анимация исчезновения (уезжает влево при переходе на "О приложении")
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            // Анимация появления при возврате назад с "О приложении"
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            },
            // Анимация исчезновения при возврате на главный экран
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            }
        ) {
            SettingsScreen(
                viewModelSettings = settingsViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAbout = { navController.navigate(Routes.ABOUT) },
            )
        }

        composable(
            route = Routes.ABOUT,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            }

        ) {
            AboutScreen(
                appListViewModel = appListViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPrivacy = { navController.navigate(Routes.PRIVACY_POLICY) }
            )
        }

        composable(
            route = Routes.PRIVACY_POLICY,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            }
        ) {
            PrivacyPolicyScreen(
                title = "Политика конфиденциальности",
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}*/

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.composercalculator.view.screen.main.CalculatorScreen
import com.example.composercalculator.view.screen.settings.AboutScreen
import com.example.composercalculator.view.screen.settings.CreateThemeAppUser
import com.example.composercalculator.view.screen.settings.PrivacyPolicyScreen
import com.example.composercalculator.view.screen.settings.SettingsScreen
import com.example.composercalculator.viewmodel.AppListViewModel
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.viewmodel.SettingsViewModel
import com.example.composercalculator.viewmodel.ThemesViewModel
import kotlin.math.roundToInt

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel = viewModel(),
    calculatorViewModel: CalculatorViewModel = viewModel(),
    appListViewModel: AppListViewModel = viewModel(),
    themesUserViewModel: ThemesViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val showHistoryButton = settingsViewModel.showHistoryButton.collectAsState()

    val canGoBack = navController.previousBackStackEntry != null

    BackHandler(enabled = canGoBack) {
        navController.popBackStack()
    }

    LaunchedEffect(key1 = Unit) {
        if (navController.currentDestination == null) {
            navController.graph = navController.createGraph(startDestination = Routes.CALCULATOR) {
                composable(Routes.CALCULATOR) { }
                composable(Routes.SETTINGS) { }
                composable(Routes.ABOUT) { }
                composable(Routes.PRIVACY_POLICY) { }
                composable(Routes.CREATE_THEME_USER) {  }
            }
        }
    }

    val visibleEntries by navController.visibleEntries.collectAsState()
    val currentEntry = visibleEntries.lastOrNull()
    val previousEntry =
        if (visibleEntries.size > 1) visibleEntries[visibleEntries.size - 2] else null

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value * 3

    var offsetX by remember { mutableStateOf(value = 0f) }
    val animatedOffset by animateFloatAsState(targetValue = offsetX)

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Отрисовка ПРЕДЫДУЩЕГО экрана
            previousEntry?.let { entry ->
                val parallaxOffset = -(screenWidth / 4) + (animatedOffset / 4)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { IntOffset(x = parallaxOffset.roundToInt(), y = 0) }
                ) {
                    ScreenContent(
                        entry = entry,
                        settingsViewModel = settingsViewModel,
                        calculatorViewModel = calculatorViewModel,
                        themesUserViewModel = themesUserViewModel,
                        appListViewModel = appListViewModel,
                        showHistoryButton = showHistoryButton.value,
                        navController = navController
                    )
                    // Затемнение
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Black.copy(
                                    alpha = (0.4f - (offsetX / screenWidth) * 0.4f).coerceIn(
                                        0f,
                                        0.4f
                                    )
                                )
                            )
                    )
                }
            }

            // ТЕНЬ
            if (previousEntry != null && offsetX > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(width = 20.dp)
                        .offset {
                            IntOffset(
                                x = animatedOffset.roundToInt() - 20.dp.value.toInt() * 3,
                                y = 0
                            )
                        }
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f))
                            )
                        )
                )
            }

            // ТЕКУЩИЙ ЭКРАН
            currentEntry?.let { entry ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset { IntOffset(x = animatedOffset.roundToInt(), y = 0) }
                        .pointerInput(key1 = Unit) {
                            detectHorizontalDragGestures(
                                onDragStart = { /* Проверка края экрана */ },
                                onHorizontalDrag = { change, dragAmount ->
                                    if (navController.previousBackStackEntry != null) {
                                        offsetX =
                                            (offsetX + dragAmount).coerceAtLeast(minimumValue = 0f)
                                        change.consume()
                                    }
                                },
                                onDragEnd = {
                                    if (offsetX > screenWidth / 5) {
                                        navController.popBackStack()
                                    }
                                    offsetX = 0f
                                }
                            )
                        }
                ) {
                    ScreenContent(
                        entry = entry,
                        settingsViewModel = settingsViewModel,
                        calculatorViewModel = calculatorViewModel,
                        themesUserViewModel = themesUserViewModel,
                        appListViewModel = appListViewModel,
                        showHistoryButton = showHistoryButton.value,
                        navController = navController
                    )
                }
            }
        }
    }
}

/**
 * Вспомогательная функция для отрисовки контента экрана по его Route
 */
@Composable
private fun ScreenContent(
    entry: NavBackStackEntry,
    settingsViewModel: SettingsViewModel,
    calculatorViewModel: CalculatorViewModel,
    themesUserViewModel: ThemesViewModel,
    appListViewModel: AppListViewModel,
    showHistoryButton: Boolean,
    navController: NavHostController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(color = 0xFFF5F5F5)
    ) {
        when (entry.destination.route) {
            Routes.CALCULATOR -> CalculatorScreen(
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                onNavigateToHistory = { navController.navigate(Routes.HISTORY) },
                showHistoryButton = showHistoryButton,
                viewModelSettings = settingsViewModel,
                viewModelCalculation = calculatorViewModel
            )

            Routes.SETTINGS -> SettingsScreen(
                title = "Настройки",
                viewModelSettings = settingsViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAbout = { navController.navigate(Routes.ABOUT) },
                onNavigateToCreateThemes = { navController.navigate(Routes.CREATE_THEME_USER) }
            )

            Routes.ABOUT -> AboutScreen(
                title = "О приложении",
                appListViewModel = appListViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPrivacy = { navController.navigate(Routes.PRIVACY_POLICY) }
            )

            Routes.PRIVACY_POLICY -> PrivacyPolicyScreen(
                title = "О данных",
                onNavigateBack = { navController.popBackStack() }
            )

            Routes.CREATE_THEME_USER -> CreateThemeAppUser(
                title = "Новая тема",
                viewModelThemes = themesUserViewModel,
                onNavigateBack = { navController.popBackStack() }
            )

        }
    }
}
