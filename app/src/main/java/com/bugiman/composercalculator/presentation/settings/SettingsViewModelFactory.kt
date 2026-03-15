package com.bugiman.composercalculator.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.settings.SettingsAllGetUseCase
import com.bugiman.domain.usecase.settings.SettingsItemUpdateUseCase

class SettingsViewModelFactory(
    private val getSettingsUseCase: SettingsAllGetUseCase,
    private val updateSettingsUseCase: SettingsItemUpdateUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверяем, что создается именно наша SettingsViewModel
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                getSettingsUseCase = getSettingsUseCase,
                updateSettingsUseCase = updateSettingsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}