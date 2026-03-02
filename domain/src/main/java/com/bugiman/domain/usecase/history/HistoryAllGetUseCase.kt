package com.bugiman.domain.usecase.history

import com.bugiman.domain.models.HistoryModel
import com.bugiman.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryAllGetUseCase(
    private val repository: HistoryRepository
) {

    operator fun invoke(): Flow<List<HistoryModel>> {
        return repository.getItemsAll()
    }

}