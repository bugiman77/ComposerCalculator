package com.example.composercalculator.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Создаем экземпляр DataStore на уровне всего приложения
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(context: Context) {

    private val dataStore = context.dataStore

    // Определяем ключи для каждой нашей настройки
    companion object {
        val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_enabled")
        val SHOW_HISTORY_KEY = booleanPreferencesKey("show_history_button")
        val FONT_SIZE_KEY = floatPreferencesKey("display_font_size")
        val DECIMAL_FORMAT_KEY = stringPreferencesKey("decimal_format")
        val SAVE_DATA_KEY = booleanPreferencesKey("save_data_enabled")
    }

    // --- Функции для получения настроек ---

    // Получаем Flow, который будет автоматически обновляться при изменении настроек
    val getDarkTheme: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: true // Значение по умолчанию: true
        }

    val getShowHistoryButton: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SHOW_HISTORY_KEY] ?: true // Значение по умолчанию: true
        }

    val getFontSize: Flow<Float> = dataStore.data
        .map { preferences ->
            preferences[FONT_SIZE_KEY] ?: 80f // Значение по умолчанию: 80f
        }

    val getDecimalFormat: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[DECIMAL_FORMAT_KEY] ?: "1,234.56" // Значение по умолчанию
        }

    val getSaveDataEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SAVE_DATA_KEY] ?: true // По умолчанию сохранение включено
        }

    // --- Функции для сохранения настроек ---

    suspend fun saveDarkTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDark
        }
    }

    suspend fun saveShowHistoryButton(show: Boolean) {
        dataStore.edit { preferences ->
            preferences[SHOW_HISTORY_KEY] = show
        }
    }

    suspend fun saveFontSize(size: Float) {
        dataStore.edit { preferences ->
            preferences[FONT_SIZE_KEY] = size
        }
    }

    suspend fun saveDecimalFormat(format: String) {
        dataStore.edit { preferences ->
            preferences[DECIMAL_FORMAT_KEY] = format
        }
    }

    suspend fun saveSaveDataEnabled(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[SAVE_DATA_KEY] = isEnabled
        }
    }

}
