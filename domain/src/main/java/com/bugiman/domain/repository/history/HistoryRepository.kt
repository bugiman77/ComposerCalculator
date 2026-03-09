package com.bugiman.domain.repository.history

import com.bugiman.domain.models.history.HistoryModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getItemsAll(): Flow<List<HistoryModel>>

    suspend fun getItem(itemId: Long): HistoryModel

    suspend fun deleteAll()

    suspend fun deleteItem(historyModel: HistoryModel)

    suspend fun insertItem(historyModel: HistoryModel)

    suspend fun updateItemNote(itemId: Long, newNote: String)

}