package com.bugiman.composercalculator.presentation

import com.bugiman.domain.models.history.HistoryModel

data class HistoryUiState(
    val items: List<HistoryModel> = emptyList(),
    val isNoteEnabled: Boolean = false,
    val isSwipeEnabled: Boolean = false,
    val isTitleNote: Boolean = false,
    val headerLayout: Int = 0
)
