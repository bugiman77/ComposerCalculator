package com.bugiman.composercalculator.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var expression: String = "0",
    var result: String = "",
    var note: String = "",
    var timestamp: Long = System.currentTimeMillis(),
)