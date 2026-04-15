package com.bugiman.composercalculator.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.settings.SettingsAllGetUseCase
import com.bugiman.domain.usecase.settings.SettingsItemUpdateUseCase

class SettingsViewModelFactory(
    private val settingsAllGetUseCase: SettingsAllGetUseCase,
    private val settingsItemUpdateUseCase: SettingsItemUpdateUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Проверяем, что создается именно наша SettingsViewModel
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                getSettingsUseCase = settingsAllGetUseCase,
                updateSettingsUseCase = settingsItemUpdateUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}