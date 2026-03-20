package com.bugiman.composercalculator.presentation.calculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase

class CalculationViewModelFactory(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase,
    private val historyAllGetUseCase: HistoryAllGetUseCase,
    private val historyAllDeleteUseCase: HistoryAllDeleteUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверяем, что создается именно наша SettingsViewModel
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(
                calculateExpressionUseCase = calculateExpressionUseCase,
                historyItemSaveUseCase = historyItemSaveUseCase,
                feedbackTriggerUseCase = feedbackTriggerUseCase,
                historyAllGetUseCase = historyAllGetUseCase,
                historyAllDeleteUseCase = historyAllDeleteUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}