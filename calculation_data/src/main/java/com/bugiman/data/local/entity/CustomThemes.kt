package com.bugiman.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_themes")
data class CustomThemesEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val backgroundColor: Long,
    val surfaceColor: Long,
    val onPrimaryColor: Long,
    val isDarkMode: Boolean = true,
    val isPin: Boolean = false,
    val note: String = "",

    val digitButtonColor: Long,
    val digitTextColor: Long,
    val operationButtonColor: Long,
    val operationTextColor: Long,
    val deleteButtonColor: Long,
    val deleteTextColor: Long,
    val equalButtonColor: Long,
    val equalTextColor: Long
)