package com.bugiman.data.repository.settings

import androidx.datastore.core.DataStore
import com.bugiman.data.mapper.toDomain
import com.bugiman.data.proto.SettingsProto
import com.bugiman.data.mapper.toProto
import com.bugiman.domain.models.settings.SettingModel
import com.bugiman.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepositoryImpl(
    private val dataStore: DataStore<SettingsProto>
) : SettingsRepository {

    override fun getSettings(): Flow<SettingModel> {
        return dataStore.data.map { it.toDomain() }
    }

    override suspend fun updateSettings(transform: (SettingModel) -> SettingModel) {
        dataStore.updateData { currentProto ->
            val currentDomain = currentProto.toDomain()
            val updatedDomain = transform(currentDomain)
            updatedDomain.toProto(currentProto)
        }
    }

}
