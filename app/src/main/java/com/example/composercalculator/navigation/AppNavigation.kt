package com.example.composercalculator.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composercalculator.calculator.CalculatorScreen
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.view.settings.AboutScreen
import com.example.composercalculator.view.settings.SettingsScreen
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntOffset

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    var showHistoryButton by remember { mutableStateOf(true) }

    val animationDuration = 300 // Длительность анимации в миллисекундах
    val animationSpec = tween<IntOffset>(animationDuration)

    NavHost(
        navController = navController,
        startDestination = Routes.CALCULATOR
    ) {
        composable(
            route = Routes.CALCULATOR,
            // Анимация исчезновения (уезжает влево при переходе на Настройки)
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            // Анимация появления при возврате с Настроек
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            }
        ) {
            val viewModel: CalculatorViewModel = viewModel()
            CalculatorScreen(
                uiState = viewModel.uiState,
                onEvent = viewModel::onEvent,
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                onNavigateToHistory = { navController.navigate(Routes.HISTORY) },
                showHistoryButton = showHistoryButton
            )
        }

        composable(
            route = Routes.SETTINGS,
            // Анимация появления (экран въезжает слева)
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            // Анимация исчезновения (уезжает влево при переходе на "О приложении")
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            // Анимация появления при возврате назад с "О приложении"
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            },
            // Анимация исчезновения при возврате на главный экран
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            }
        ) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAbout = { navController.navigate(Routes.ABOUT) },
                showHistoryButton = showHistoryButton,
                onShowHistoryChange = { newState -> showHistoryButton = newState }
            )
        }

        composable(
            route = Routes.ABOUT,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec
                )
            }

        ) {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}
