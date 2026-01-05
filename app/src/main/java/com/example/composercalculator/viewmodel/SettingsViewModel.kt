package com.example.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.composercalculator.data.local.db.AppDatabaseSetting
import com.example.composercalculator.data.local.db.dao.SettingsDao
import com.example.composercalculator.data.local.db.entity.Settings
import com.example.composercalculator.data.repository.DeviceSettingsRepository
import com.example.composercalculator.data.repository.SoundMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val repository: DeviceSettingsRepository
) : AndroidViewModel(application) {

/*    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Получаем объект Application из extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return SettingsViewModel(
                    application,
                    DeviceSettingsRepository(application)
                ) as T
            }
        }
    }*/

    private val settingsDao: SettingsDao = AppDatabaseSetting.getDatabase(application).settingsDao()

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

    private val _playSound = MutableStateFlow(value = false)
    val playSound: StateFlow<Boolean> = _playSound

    private val _playVibration = MutableStateFlow(value = false)
    val playVibration: StateFlow<Boolean> = _playVibration

    private val _bottomSpacer = MutableStateFlow(value = 24)
    val bottomSpacer: StateFlow<Int> = _bottomSpacer

    private val _isAnimationAll = MutableStateFlow(value = false)
    val isAnimationAll: StateFlow<Boolean> = _isAnimationAll

    private val _keepScreenOn = MutableStateFlow(value = false)
    val keepScreenOn: StateFlow<Boolean> = _keepScreenOn

    init {
        // Загружаем настройки при старте
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
            _playSound.value = it.playSound
            _playVibration.value = it.playVibration
            _bottomSpacer.value = it.bottomSpacer
            _isAnimationAll.value = it.isAnimationAll
            _keepScreenOn.value = it.keepScreenOn
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

    fun onClickPlaySound(isPlay: Boolean) {
        viewModelScope.launch {
            saveSetting { _playSound.value = isPlay }
        }
    }

    fun onClickPlayVibration(isPlay: Boolean) {
        viewModelScope.launch {
            saveSetting { _playVibration.value = isPlay }
        }
    }

    fun onBottomChangeChange(spacer: Int) {
        viewModelScope.launch {
            saveSetting { _bottomSpacer.value = spacer }
        }
    }

    fun isAnimationAllChange(enable: Boolean) {
        viewModelScope.launch {
            saveSetting { _isAnimationAll.value = enable }
        }
    }

    fun toggleKeepScreenOn(enabled: Boolean) {
        viewModelScope.launch {
            saveSetting { _keepScreenOn.value = enabled }
        }
    }

    private suspend fun saveSetting(updateState: suspend () -> Unit) {
        // Обновляем состояние в UI
        updateState()

        // Сохраняем изменения в базу данных
        settingsDao.saveSettings(
            Settings(
                id = 0,
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
                showIconButton = _showIconButton.value,
                playSound = _playSound.value,
                playVibration = _playVibration.value,
                bottomSpacer = _bottomSpacer.value,
                isAnimationAll = _isAnimationAll.value,
                keepScreenOn = _keepScreenOn.value,
            )
        )
    }
}
