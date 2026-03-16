package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.models.settings.SettingModel
import com.bugiman.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsAllGetUseCase(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<SettingModel> {
        return repository.getSettings()
    }

}