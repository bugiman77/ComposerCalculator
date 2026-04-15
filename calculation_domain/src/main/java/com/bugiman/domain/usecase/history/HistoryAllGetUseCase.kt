package com.bugiman.domain.usecase.history

import com.bugiman.domain.models.history.HistoryModel
import com.bugiman.domain.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryAllGetUseCase(
    private val repository: HistoryRepository
) {

    operator fun invoke(): Flow<List<HistoryModel>> {
        return repository.getItemsAll()
    }

}