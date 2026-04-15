package com.bugiman.domain.usecase.history

import com.bugiman.domain.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryCountUseCase(
    private val repository: HistoryRepository
) {

    operator fun invoke(): Flow<Long> {
        return repository.getItemsCount()
    }

}