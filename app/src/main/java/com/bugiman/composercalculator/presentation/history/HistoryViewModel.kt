package com.bugiman.composercalculator.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugiman.domain.models.history.HistoryModel
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryCountUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyExpressionUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyResultUseCase
import com.bugiman.domain.usecase.history.HistoryItemDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryItemEditUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase
import com.bugiman.domain.usecase.history.HistoryItemUpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class HistoryViewModel /*@Inject constructor*/(
    private val historyAllDeleteUseCase: HistoryAllDeleteUseCase,
    private val historyAllGetUseCase: HistoryAllGetUseCase,
    private val historyCountUseCase: HistoryCountUseCase,
    private val historyItemCopyExpressionUseCase: HistoryItemCopyExpressionUseCase,
    private val historyItemCopyResultUseCase: HistoryItemCopyResultUseCase,
    private val historyItemDeleteUseCase: HistoryItemDeleteUseCase,
    private val historyItemEditUseCase: HistoryItemEditUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val historyItemUpdateNoteUseCase: HistoryItemUpdateNoteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    val history = historyAllGetUseCase()
    val historyCount = historyCountUseCase()

    fun deleteAll() {
        viewModelScope.launch {
            historyAllDeleteUseCase()
        }
    }

    fun deleteItem(historyModel: HistoryModel) {
        viewModelScope.launch {
            historyItemDeleteUseCase(historyModel)
        }
    }

    fun updateNote(itemId: Long, newNote: String) {
        viewModelScope.launch {
            historyItemUpdateNoteUseCase(itemId = itemId, newNote = newNote)
        }
    }

    fun copyExpression(itemId: Long) {
        viewModelScope.launch {
            historyItemCopyExpressionUseCase(itemId = itemId)
        }
    }

    fun copyResult(itemId: Long) {
        viewModelScope.launch {
            historyItemCopyResultUseCase(itemId = itemId)
        }
    }

    fun editExpression(itemId: Long) {
        viewModelScope.launch {
            historyItemEditUseCase(itemId = itemId)
        }
    }

}