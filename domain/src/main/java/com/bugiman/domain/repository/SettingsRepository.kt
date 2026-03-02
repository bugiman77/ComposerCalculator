package com.bugiman.domain.repository

import com.bugiman.domain.models.SettingModel
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettingsAll(): Flow<SettingModel>

    suspend fun updateSettingItem(settingModel: SettingModel)

}