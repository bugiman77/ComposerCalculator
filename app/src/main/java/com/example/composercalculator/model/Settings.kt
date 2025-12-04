package com.example.composercalculator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey var id: Int,
    var isDarkTheme: Boolean = true,
    var showHistoryButton: Boolean = true,
    var displayFontSize: Float = 80f,
    var decimalFormat: String = "1,234.56",
    var isSaveDataEnabled: Boolean = true,
    var isSwipeEnabled: Boolean = true,
    var isNoteEnabled: Boolean = true,
)