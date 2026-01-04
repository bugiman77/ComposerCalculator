package com.example.composercalculator.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var expression: String = "0",
    var result: String = "",
    var note: String = "",
    var timestamp: Long = System.currentTimeMillis(),
    var isEditedExpression: Boolean = false,
    var timestampEditedExpression: Long? = null
)