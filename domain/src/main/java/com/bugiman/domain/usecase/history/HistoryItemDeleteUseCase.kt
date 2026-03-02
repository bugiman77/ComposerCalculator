package com.bugiman.domain.usecase.history

import com.bugiman.domain.models.HistoryModel
import com.bugiman.domain.repository.HistoryRepository

class HistoryItemDeleteUseCase(
    private val repository: HistoryRepository
) {

    suspend operator fun invoke(historyModel: HistoryModel) {
        repository.deleteItem(historyModel = historyModel)
    }

}