package com.bugiman.composercalculator.presentation.calculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDecimalUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDigitUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildOperatorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildZeroUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveLastCharUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase

class CalculationViewModelFactory(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase,
    private val historyAllGetUseCase: HistoryAllGetUseCase,
    private val historyAllDeleteUseCase: HistoryAllDeleteUseCase,
    private val buildDigitUseCase: CalculationBuildDigitUseCase,
    private val buildOperatorUseCase: CalculationBuildOperatorUseCase,
    private val buildBracketUseCase: CalculationBuildBracketUseCase,
    private val buildDecimalUseCase: CalculationBuildDecimalUseCase,
    private val buildZeroUseCase: CalculationBuildZeroUseCase,
    private val removeLastCharUseCase: CalculationRemoveLastCharUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(
                calculateExpressionUseCase = calculateExpressionUseCase,
                historyItemSaveUseCase = historyItemSaveUseCase,
                feedbackTriggerUseCase = feedbackTriggerUseCase,
                historyAllGetUseCase = historyAllGetUseCase,
                historyAllDeleteUseCase = historyAllDeleteUseCase,
                buildDigitUseCase = buildDigitUseCase,
                buildOperatorUseCase = buildOperatorUseCase,
                buildBracketUseCase = buildBracketUseCase,
                buildDecimalUseCase = buildDecimalUseCase,
                buildZeroUseCase = buildZeroUseCase,
                removeLastCharUseCase = removeLastCharUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}