package com.example.composercalculator.data.repository

import com.example.composercalculator.data.local.db.dao.SettingsDao
import com.example.composercalculator.data.local.db.entity.Settings
import kotlinx.coroutines.flow.Flow

/*
class SettingsRepository(
    private val settingsDao: SettingsDao // Получаем доступ к DAO, который взаимодействует с базой данных
) {

    // Получаем "живой" поток данных с настройками
    fun getSettingsFlow(): Flow<Settings> {
        return settingsDao.getSettingsFlow() // Используем DAO для получения Flow
    }

    // Универсальный метод для обновления любого поля в настройках
    suspend fun updateSettings(updater: (Settings) -> Unit) {
        // Получаем текущие настройки
        val settings = settingsDao.getSettings() ?: Settings(id = 0) // Если настроек нет, создаем новый объект
        updater(settings)
        settingsDao.updateSettings(settings) // Обновляем в базе
    }

    // Инициализация настроек при первом запуске
    suspend fun initializeSettings() {
        // Проверяем, есть ли настройки в базе
        val existingSettings = settingsDao.getSettings()
        if (existingSettings == null) {
            val settings = Settings(id = 0) // Создаем новый объект настроек
            settingsDao.insertSettings(settings) // Сохраняем в базе
        }
    }
}*/
