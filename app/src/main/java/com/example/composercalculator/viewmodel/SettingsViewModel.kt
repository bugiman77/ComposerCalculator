package com.example.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composercalculator.data.local.db.AppDatabase
import com.example.composercalculator.data.local.db.dao.SettingsDao
//import com.example.composercalculator.data.local.db.dao.ThemeAppBuiltInDao
import com.example.composercalculator.data.local.db.entity.Settings
//import com.example.composercalculator.data.local.db.entity.ThemeAppBuiltIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsDao: SettingsDao = AppDatabase.getDatabase(application).settingsDao()
//    private val themeAppBuiltInDao: ThemeAppBuiltInDao = AppDatabase.getDatabase(application).themeAppBuiltInDao()

    // Состояния, которые будет наблюдать UI
    // SETTINGS
    private val _isDarkTheme = MutableStateFlow(value = true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val _isSystemTheme = MutableStateFlow(value = true)
    val isSystemTheme: StateFlow<Boolean> = _isSystemTheme

    private val _showHistoryButton = MutableStateFlow(value = true)
    val showHistoryButton: StateFlow<Boolean> = _showHistoryButton

    private val _systemFontSize = MutableStateFlow(value = true)
    val systemFontSize: StateFlow<Boolean> = _systemFontSize

    private val _displayFontSize = MutableStateFlow(value = 80f)
    val displayFontSize: StateFlow<Float> = _displayFontSize

    private val _decimalFormat = MutableStateFlow(value = "1,234.56")
    val decimalFormat: StateFlow<String> = _decimalFormat

    private val _isSaveHistoryData = MutableStateFlow(value = true)
    val isSaveHistoryData: StateFlow<Boolean> = _isSaveHistoryData

    private val _isSaveSettingsData = MutableStateFlow(value = true)
    val isSaveSettingsData: StateFlow<Boolean> = _isSaveSettingsData

    private val _isSwipeEnabled = MutableStateFlow(value = true)
    val isSwipeEnabled: StateFlow<Boolean> = _isSwipeEnabled

    private val _isNoteEnabled = MutableStateFlow(value = true)
    val isNoteEnabled: StateFlow<Boolean> = _isNoteEnabled

    private val _showIconButton = MutableStateFlow(value = true)
    val showIconButton: StateFlow<Boolean> = _showIconButton

    init {
        // Загружаем настройки при старте
        viewModelScope.launch {
            loadSettings()
        }
    }

    init {
        // Загружаем текущие настройки при инициализации ViewModel
        viewModelScope.launch {
            loadSettings()
        }
    }

    private suspend fun loadSettings() {
        val settings = settingsDao.getSettings()
        settings?.let {
            _isDarkTheme.value = it.isDarkTheme
            _isSystemTheme.value = it.isSystemTheme
            _showHistoryButton.value = it.showHistoryButton
            _systemFontSize.value = it.systemFontSize
            _displayFontSize.value = it.displayFontSize
            _decimalFormat.value = it.decimalFormat
            _isSaveHistoryData.value = it.isSaveHistoryData
            _isSaveSettingsData.value = it.isSaveSettingsData
            _isSwipeEnabled.value = it.isSwipeEnabled
            _isNoteEnabled.value = it.isNoteEnabled
            _showIconButton.value = it.showIconButton
        }
    }

    // Функции для сохранения изменений
    fun onDarkThemeChange(isDark: Boolean) {
        viewModelScope.launch {
            saveSetting { _isDarkTheme.value = isDark }
        }
    }

    fun onSystemThemeChange(isSystem: Boolean) {
        viewModelScope.launch {
            saveSetting { _isSystemTheme.value = isSystem }
        }
    }

    fun onShowHistoryChange(show: Boolean) {
        viewModelScope.launch {
            saveSetting { _showHistoryButton.value = show }
        }
    }

    fun onSystemFontSizeChange(isEnable: Boolean) {
        viewModelScope.launch {
            saveSetting { _systemFontSize.value = isEnable }
        }
    }

    fun onFontSizeChange(size: Float) {
        viewModelScope.launch {
            saveSetting { _displayFontSize.value = size }
        }
    }

    fun onDecimalFormatChange(format: String) {
        viewModelScope.launch {
            saveSetting { _decimalFormat.value = format }
        }
    }

    fun onSaveHistoryDataChange(isEnabled: Boolean) {
        viewModelScope.launch {
            saveSetting { _isSaveHistoryData.value = isEnabled }
        }
    }

    fun onSaveSettingsDataChange(isEnabled: Boolean) {
        viewModelScope.launch {
            saveSetting { _isSaveSettingsData.value = isEnabled }
        }
    }

    fun onSaveSwipeDeleteItem(isEnabled: Boolean) {
        viewModelScope.launch {
            saveSetting { _isSwipeEnabled.value = isEnabled }
        }
    }

    fun onSaveNoteItem(isEnabled: Boolean) {
        viewModelScope.launch {
            saveSetting { _isNoteEnabled.value = isEnabled }
        }
    }

    fun switchIconButton(switch: Boolean) {
        viewModelScope.launch {
            saveSetting { _showIconButton.value = switch }
        }
    }

    private suspend fun saveSetting(updateState: suspend () -> Unit) {
        // Обновляем состояние в UI
        updateState()

        // Сохраняем изменения в базу данных
        settingsDao.saveSettings(
            Settings(
                id = 0,  // Если у тебя один объект настроек, id можно фиксировать
                isDarkTheme = _isDarkTheme.value,
                isSystemTheme = _isSystemTheme.value,
                showHistoryButton = _showHistoryButton.value,
                systemFontSize = _systemFontSize.value,
                displayFontSize = _displayFontSize.value,
                decimalFormat = _decimalFormat.value,
                isSaveHistoryData = _isSaveHistoryData.value,
                isSaveSettingsData = _isSaveSettingsData.value,
                isSwipeEnabled = _isSwipeEnabled.value,
                isNoteEnabled = _isNoteEnabled.value,
                showIconButton = _showIconButton.value
            )
        )
    }
}
