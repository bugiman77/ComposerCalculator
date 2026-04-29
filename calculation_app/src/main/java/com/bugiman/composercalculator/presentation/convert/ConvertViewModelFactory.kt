package com.bugiman.composercalculator.presentation.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.convert.ConvertGetFormattedConversionUseCase
import com.bugiman.domain.usecase.convert.ConvertSwapCurrenciesUseCase
import com.bugiman.domain.usecase.convert.ConvertValueUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase

class ConvertViewModelFactory(
    private val convertGetFormattedConversionUseCase: ConvertGetFormattedConversionUseCase,
    private val convertSwapCurrenciesUseCase: ConvertSwapCurrenciesUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConvertViewModel::class.java)) {
            return ConvertViewModel(
                convertGetFormattedConversionUseCase = convertGetFormattedConversionUseCase,
                convertSwapCurrenciesUseCase = convertSwapCurrenciesUseCase,
                feedbackTriggerUseCase = feedbackTriggerUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}