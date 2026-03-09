package com.bugiman.domain.repository.settings

import com.bugiman.domain.models.settings.SettingModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<SettingModel>

    suspend fun updateSettings(transform: (SettingModel) -> SettingModel)

}