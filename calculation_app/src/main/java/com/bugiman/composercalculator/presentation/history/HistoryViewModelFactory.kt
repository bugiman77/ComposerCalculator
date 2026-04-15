package com.bugiman.composercalculator.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryCountUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyExpressionUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyResultUseCase
import com.bugiman.domain.usecase.history.HistoryItemDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryItemEditUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase
import com.bugiman.domain.usecase.history.HistoryItemUpdateNoteUseCase

class HistoryViewModelFactory(
    private val historyAllDeleteUseCase: HistoryAllDeleteUseCase,
    private val historyAllGetUseCase: HistoryAllGetUseCase,
    private val historyCountUseCase: HistoryCountUseCase,
    private val historyItemCopyExpressionUseCase: HistoryItemCopyExpressionUseCase,
    private val historyItemCopyResultUseCase: HistoryItemCopyResultUseCase,
    private val historyItemDeleteUseCase: HistoryItemDeleteUseCase,
    private val historyItemEditUseCase: HistoryItemEditUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val historyItemUpdateNoteUseCase: HistoryItemUpdateNoteUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(
                historyAllDeleteUseCase = historyAllDeleteUseCase,
                historyAllGetUseCase = historyAllGetUseCase,
                historyCountUseCase = historyCountUseCase,
                historyItemCopyExpressionUseCase = historyItemCopyExpressionUseCase,
                historyItemCopyResultUseCase = historyItemCopyResultUseCase,
                historyItemDeleteUseCase = historyItemDeleteUseCase,
                historyItemEditUseCase = historyItemEditUseCase,
                historyItemSaveUseCase = historyItemSaveUseCase,
                historyItemUpdateNoteUseCase = historyItemUpdateNoteUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}