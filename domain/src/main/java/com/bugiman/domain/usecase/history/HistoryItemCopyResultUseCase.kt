package com.bugiman.domain.usecase.history

import com.bugiman.domain.repository.HistoryRepository

class HistoryItemCopyResultUseCase(
    private val repository: HistoryRepository
) {

    suspend operator fun invoke(itemId: Long): String {
        val itemResult = repository.getItem(itemId = itemId)
        return itemResult.result
    }

}