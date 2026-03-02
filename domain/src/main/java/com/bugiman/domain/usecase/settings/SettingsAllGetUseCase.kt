package com.bugiman.domain.usecase.settings

import com.bugiman.domain.models.SettingModel
import com.bugiman.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsAllGetUseCase(
    private val repository: SettingsRepository
) {

    operator fun invoke(): Flow<SettingModel> {
        return repository.getSettingsAll()
    }

}