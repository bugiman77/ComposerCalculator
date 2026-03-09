package com.bugiman.domain.usecase.settings

import com.bugiman.domain.models.settings.SettingModel
import com.bugiman.domain.repository.settings.SettingsRepository

class SettingsItemUpdateUseCase(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(settingModel: SettingModel) {
        repository.updateSettingItem(settingModel)
    }

}