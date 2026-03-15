package com.bugiman.composercalculator.presentation.calculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.composercalculator.core.managers.SoundManager
import com.bugiman.composercalculator.core.managers.VibrationManager
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase

class CalculationViewModelFactory(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val settingsViewModel: SettingsViewModel,
    private val soundManager: SoundManager,
    private val vibrationManager: VibrationManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверяем, что создается именно наша SettingsViewModel
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(
                calculateExpressionUseCase = calculateExpressionUseCase,
                historyItemSaveUseCase = historyItemSaveUseCase,
                settingsViewModel = settingsViewModel,
                soundManager = soundManager,
                vibrationManager = vibrationManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}