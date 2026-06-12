package com.bugiman.composercalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.Navigator
import com.bugiman.composercalculator.presentation.calculation.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.presentation.convert.viewmodel.ConvertViewModel
import com.bugiman.composercalculator.presentation.history.viewmodel.HistoryViewModel
import com.bugiman.composercalculator.presentation.settings.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel,
    calculatorViewModel: CalculatorViewModel,
    historyViewModel: HistoryViewModel,
    converterViewModel: ConvertViewModel,
) {
    val settings by settingsViewModel.uiState.collectAsStateWithLifecycle()
    val isShowHistory = settings.isShowHistoryButton

    Navigator(
        screen = CalculatorScreenNavigation(
            settingsViewModel = settingsViewModel,
            calculatorViewModel = calculatorViewModel,
            viewModelHistory = historyViewModel,
            showHistoryButton = isShowHistory
        )
    ) { navigator ->
        TelegramSwipeContainer(navigator)
    }
}
