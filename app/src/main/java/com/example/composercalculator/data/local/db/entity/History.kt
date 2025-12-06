package com.example.composercalculator.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey val id: Int = 0,
    val expression: String = "0",
    val result: String = "",
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isEditedExpression: Boolean = false,
    val timestampEditedExpression: Long? = null
)