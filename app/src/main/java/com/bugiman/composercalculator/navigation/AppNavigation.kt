package com.bugiman.composercalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.Navigator
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel = viewModel(),
    calculatorViewModel: CalculatorViewModel = viewModel(),
) {
    // 1. Получаем всё состояние настроек одним объектом
    val settings by settingsViewModel.uiState.collectAsStateWithLifecycle()

    // 2. Достаем нужное поле напрямую из объекта settings
    val isShowHistory = settings.isShowHistoryButton

    // Запускаем Navigator, но внутри используем наш кастомный TelegramSwipeContainer
    Navigator(
        screen = CalculatorScreen(
            settingsViewModel = settingsViewModel,
            calculatorViewModel = calculatorViewModel,
            showHistoryButton = isShowHistory
        )
    ) { navigator ->
        TelegramSwipeContainer(navigator)
    }
}
