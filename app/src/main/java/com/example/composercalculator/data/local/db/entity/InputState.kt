package com.example.composercalculator.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "input_state")
data class InputState(
    @PrimaryKey val id: Int = 1, // Фиксированный ID для единственной записи
    val currentText: String
)
