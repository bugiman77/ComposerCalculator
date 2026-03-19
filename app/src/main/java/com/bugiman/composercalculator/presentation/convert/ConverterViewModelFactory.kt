package com.bugiman.composercalculator.presentation.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.convert.ConvertValueUseCase
import com.bugiman.domain.usecase.feedback.TriggerFeedbackUseCase

class ConverterViewModelFactory(
    private val convertValueUseCase: ConvertValueUseCase,
    private val triggerFeedbackUseCase: TriggerFeedbackUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверяем, что создается именно ConverterViewModel
        if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            return ConverterViewModel(
                convertValueUseCase = convertValueUseCase,
                triggerFeedbackUseCase = triggerFeedbackUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}