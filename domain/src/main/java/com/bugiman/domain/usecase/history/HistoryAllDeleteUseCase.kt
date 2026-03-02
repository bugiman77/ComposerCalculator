package com.bugiman.domain.usecase.history

import com.bugiman.domain.repository.HistoryRepository

class HistoryAllDeleteUseCase(
    private val repository: HistoryRepository
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}