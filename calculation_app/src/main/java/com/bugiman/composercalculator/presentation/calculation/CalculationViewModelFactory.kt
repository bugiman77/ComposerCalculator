package com.bugiman.composercalculator.presentation.calculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketLeftUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketRigthUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildCommaUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDecimalUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDigitUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorDivisionUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorMinusUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorMultiplicationUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorPlusUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildOperatorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildZeroUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveLastCharUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase

class CalculationViewModelFactory(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val historyAllDeleteUseCase: HistoryAllDeleteUseCase,
    private val historyAllGetUseCase: HistoryAllGetUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase,
    private val buildDigitUseCase: CalculationBuildDigitUseCase,
    private val buildMathOperatorDivisionUseCase: CalculationBuildMathOperatorDivisionUseCase,
    private val buildMathOperatorMultiplicationUseCase: CalculationBuildMathOperatorMultiplicationUseCase,
    private val buildMathOperatorMinusUseCase: CalculationBuildMathOperatorMinusUseCase,
    private val buildMathOperatorPlusUseCase: CalculationBuildMathOperatorPlusUseCase,
    private val buildBracketLeftUseCase: CalculationBuildBracketLeftUseCase,
    private val buildBracketRigthUseCase: CalculationBuildBracketRigthUseCase,
    private val buildDecimalUseCase: CalculationBuildDecimalUseCase,
    private val buildZeroUseCase: CalculationBuildZeroUseCase,
    private val buildCommaUseCase: CalculationBuildCommaUseCase,
    private val removeLastCharUseCase: CalculationRemoveLastCharUseCase,
    private val calculationRemoveExpressionUseCase: CalculationRemoveExpressionUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(
                calculateExpressionUseCase = calculateExpressionUseCase,
                historyItemSaveUseCase = historyItemSaveUseCase,
                historyAllDeleteUseCase = historyAllDeleteUseCase,
                historyAllGetUseCase = historyAllGetUseCase,
                feedbackTriggerUseCase = feedbackTriggerUseCase,
                buildDigitUseCase = buildDigitUseCase,
                buildMathOperatorDivisionUseCase = buildMathOperatorDivisionUseCase,
                buildMathOperatorMultiplicationUseCase = buildMathOperatorMultiplicationUseCase,
                buildMathOperatorMinusUseCase = buildMathOperatorMinusUseCase,
                buildMathOperatorPlusUseCase = buildMathOperatorPlusUseCase,
                buildBracketLeftUseCase = buildBracketLeftUseCase,
                buildBracketRigthUseCase = buildBracketRigthUseCase,
                buildDecimalUseCase = buildDecimalUseCase,
                buildZeroUseCase = buildZeroUseCase,
                buildCommaUseCase = buildCommaUseCase,
                removeLastCharUseCase = removeLastCharUseCase,
                calculationRemoveExpressionUseCase = calculationRemoveExpressionUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

}