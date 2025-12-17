package com.example.composercalculator.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composercalculator.view.screen.main.CalculatorScreen
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.view.screen.settings.AboutScreen
import com.example.composercalculator.view.screen.settings.SettingsScreen
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.IntOffset
import com.example.composercalculator.view.screen.settings.PrivacyPolicyScreen
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel = viewModel(),
    calculatorViewModel: CalculatorViewModel = viewModel()
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
}
