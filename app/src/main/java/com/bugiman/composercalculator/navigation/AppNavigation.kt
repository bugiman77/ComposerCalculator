package com.bugiman.composercalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.Navigator
import com.bugiman.composercalculator.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel = viewModel(),
    calculatorViewModel: CalculatorViewModel = viewModel(),
) {
    val showHistoryButton by settingsViewModel.showHistoryButton.collectAsState()

    // Запускаем Navigator, но внутри используем наш кастомный TelegramSwipeContainer
    Navigator(
        screen = CalculatorScreen(settingsViewModel, calculatorViewModel, showHistoryButton)
    ) { navigator ->
        TelegramSwipeContainer(navigator)
    }
}
