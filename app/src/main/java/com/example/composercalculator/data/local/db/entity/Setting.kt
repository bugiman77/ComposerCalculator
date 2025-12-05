package com.example.composercalculator.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int = 0,
    val isDarkTheme: Boolean = true,
    val isSystemTheme: Boolean = false,
    val showHistoryButton: Boolean = true,
    val displayFontSize: Float = 80f,
    val decimalFormat: String = "1,234.56",
    val isSaveDataEnabled: Boolean = true,
    val isSwipeEnabled: Boolean = true,
    val isNoteEnabled: Boolean = true
)
