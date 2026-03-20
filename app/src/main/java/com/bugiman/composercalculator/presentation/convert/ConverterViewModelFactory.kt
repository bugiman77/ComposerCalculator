package com.bugiman.composercalculator.presentation.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.convert.ConvertValueUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase

class ConverterViewModelFactory(
    private val convertValueUseCase: ConvertValueUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверяем, что создается именно ConverterViewModel
        if (modelClass.isAssignableFrom(ConverterViewModel::class.java)) {
            return ConverterViewModel(
                convertValueUseCase = convertValueUseCase,
                feedbackTriggerUseCase = feedbackTriggerUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}