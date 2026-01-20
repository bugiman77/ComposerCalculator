package com.bugiman.composercalculator.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_themes")
data class CustomThemes(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val backgroundColor: Long,
    val surfaceColor: Long,
    val onPrimaryColor: Long,
    val isDarkMode: Boolean = true,
    val isPin: Boolean = false,
)