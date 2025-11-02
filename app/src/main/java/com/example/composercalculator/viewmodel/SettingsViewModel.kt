package com.example.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composercalculator.data.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    // Создаем экземпляр нашего DataStore
    private val settingsDataStore = SettingsDataStore(application)

    // Преобразуем Flow из DataStore в StateFlow, на который подпишется UI
    val isDarkTheme: StateFlow<Boolean> = settingsDataStore.getDarkTheme
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val showHistoryButton: StateFlow<Boolean> = settingsDataStore.getShowHistoryButton
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val displayFontSize: StateFlow<Float> = settingsDataStore.getFontSize
        .stateIn(viewModelScope, SharingStarted.Eagerly, 80f)

    val decimalFormat: StateFlow<String> = settingsDataStore.getDecimalFormat
        .stateIn(viewModelScope, SharingStarted.Eagerly, "1,234.56")

    val isSaveDataEnabled: StateFlow<Boolean> = settingsDataStore.getSaveDataEnabled
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val isSwipeEnabled: StateFlow<Boolean> = settingsDataStore.getSwipeItem
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val isNoteEnabled: StateFlow<Boolean> = settingsDataStore.getNoteItem
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)



    // --- Функции, которые будет вызывать UI для сохранения настроек ---

    fun onDarkThemeChange(isDark: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveDarkTheme(isDark)
        }
    }

    fun onShowHistoryChange(show: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveShowHistoryButton(show)
        }
    }

    fun onFontSizeChange(size: Float) {
        viewModelScope.launch {
            settingsDataStore.saveFontSize(size)
        }
    }

    fun onDecimalFormatChange(format: String) {
        viewModelScope.launch {
            settingsDataStore.saveDecimalFormat(format)
        }
    }

    fun onSaveDataChange(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveSaveDataEnabled(isEnabled)
        }
    }

    fun onSaveSwipeDeleteItem(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveSwipeItem(isEnabled)
        }
    }

    fun onSaveNoteItem(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveNoteItem(isEnabled)
        }
    }

}
