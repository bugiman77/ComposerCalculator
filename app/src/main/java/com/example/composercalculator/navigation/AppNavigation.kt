package com.example.composercalculator.navigation

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

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    var showHistoryButton by remember { mutableStateOf(true) }

    NavHost(
        navController = navController,
        startDestination = Routes.CALCULATOR
    ) {
        composable(route = Routes.CALCULATOR) {
            val viewModel: CalculatorViewModel = viewModel()
            CalculatorScreen(
                uiState = viewModel.uiState,
                onEvent = viewModel::onEvent,
                onNavigateToSettings = { navController.navigate(Routes.SETTINGS) },
                onNavigateToHistory = { navController.navigate(Routes.HISTORY) },
                showHistoryButton = showHistoryButton
            )
        }

        composable(route = Routes.SETTINGS) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAbout = { navController.navigate(Routes.ABOUT) },
                showHistoryButton = showHistoryButton,
                onShowHistoryChange = { newState -> showHistoryButton = newState }
            )
        }

        composable(route = Routes.ABOUT) {
            AboutScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

    }
}
