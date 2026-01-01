package com.example.composercalculator.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey var id: Int,
    var isDarkTheme: Boolean = true,
    var isSystemTheme: Boolean = false,
    var showHistoryButton: Boolean = true,
    var systemFontSize: Boolean = true,
    var displayFontSize: Float = 80f,
    var decimalFormat: String = "1,234.56",
    var isSaveHistoryData: Boolean = true,
    var isSaveSettingsData: Boolean = true,
    var isSwipeEnabled: Boolean = true,
    var isNoteEnabled: Boolean = true,
    var showIconButton: Boolean = true,
    var playSound: Boolean = false,
    var playVibration: Boolean = false,
    var bottomSpacer: Int = 24,
)