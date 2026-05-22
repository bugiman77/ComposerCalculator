package com.bugiman.composercalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.Navigator
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.convert.ConvertViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel,
    calculatorViewModel: CalculatorViewModel,
    converterViewModel: ConvertViewModel,
) {
    val settings by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val isShowHistory = settings.isShowHistoryButton

    Navigator(
        screen = CalculatorScreenNavigation(
            settingsViewModel = settingsViewModel,
            calculatorViewModel = calculatorViewModel,
            showHistoryButton = isShowHistory
        )
    ) { navigator ->
        TelegramSwipeContainer(navigator)
    }
}
