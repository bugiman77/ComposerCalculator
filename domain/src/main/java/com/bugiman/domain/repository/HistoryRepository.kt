package com.bugiman.domain.repository

import com.bugiman.domain.models.HistoryModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getItemsAll(): Flow<List<HistoryModel>>

    suspend fun getItem(itemId: Long): HistoryModel

    suspend fun deleteAll()

    suspend fun deleteItem(historyModel: HistoryModel)

    suspend fun insertItem(historyModel: HistoryModel)

    suspend fun updateItemNote(itemId: Long, newNote: String)

}