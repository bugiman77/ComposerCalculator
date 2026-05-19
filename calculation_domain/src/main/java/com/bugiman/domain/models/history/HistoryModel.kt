package com.bugiman.domain.models.history

data class HistoryModel(
    val id: Long = 0,
    var expression: String = "0",
    var result: String = "",
    var note: String = "",
    var timestamp: Long = System.currentTimeMillis(),
)