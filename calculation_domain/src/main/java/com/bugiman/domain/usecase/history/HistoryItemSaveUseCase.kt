package com.bugiman.domain.usecase.history

import com.bugiman.domain.models.history.HistoryModel
import com.bugiman.domain.repository.history.HistoryRepository

class HistoryItemSaveUseCase(
    private val repository: HistoryRepository
) {

    suspend operator fun invoke(historyModel: HistoryModel) {
        repository.insertItem(historyModel = historyModel)
    }

}