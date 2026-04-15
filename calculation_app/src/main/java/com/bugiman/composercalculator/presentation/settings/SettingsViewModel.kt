package com.bugiman.composercalculator.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugiman.domain.models.settings.SettingModel
import com.bugiman.domain.usecase.settings.SettingsAllGetUseCase
import com.bugiman.domain.usecase.settings.SettingsItemUpdateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSettingsUseCase: SettingsAllGetUseCase,
    private val updateSettingsUseCase: SettingsItemUpdateUseCase
) : ViewModel() {

    val uiState: StateFlow<SettingModel> = getSettingsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingModel() // Дефолтные значения до загрузки из БД/Prefs
        )

    fun updateSettings(transform: (SettingModel) -> SettingModel) {
        viewModelScope.launch {
            val newSettings = transform(uiState.value)
            updateSettingsUseCase(newSettings)
        }
    }

}
