package com.bugiman.domain.usecase.history

import com.bugiman.domain.repository.history.HistoryRepository

class HistoryItemEditUseCase(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke(itemId: Long) {
        repository.getItem(itemId = itemId)
    }
}