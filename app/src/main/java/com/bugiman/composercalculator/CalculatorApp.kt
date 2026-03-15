package com.bugiman.composercalculator

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.bugiman.data.proto.SettingsProto
import com.bugiman.data.repository.settings.SettingsRepositoryImpl
import com.bugiman.data.serializer.SettingsProtoSerializer
import com.bugiman.domain.usecase.settings.SettingsAllGetUseCase
import com.bugiman.domain.usecase.settings.SettingsItemUpdateUseCase
import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
private val Context.settingsDataStore: DataStore<SettingsProto> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsProtoSerializer
)

class Application : Application() {
    // Эти переменные будут доступны во всем приложении через context
    lateinit var getSettingsUseCase: SettingsAllGetUseCase
    lateinit var updateSettingsUseCase: SettingsItemUpdateUseCase

    override fun onCreate() {
        super.onCreate()

        // Склеиваем слои: Data -> Domain
        val repository = SettingsRepositoryImpl(settingsDataStore)

        getSettingsUseCase = SettingsAllGetUseCase(repository)
        updateSettingsUseCase = SettingsItemUpdateUseCase(repository)
    }
}