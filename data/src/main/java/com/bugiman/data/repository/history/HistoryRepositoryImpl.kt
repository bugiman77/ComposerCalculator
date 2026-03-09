package com.bugiman.data.repository

import com.bugiman.data.local.dao.HistoryDao
import com.bugiman.data.mapper.toDomain
import com.bugiman.data.mapper.toEntity
import com.bugiman.domain.models.HistoryModel
import com.bugiman.domain.repository.HistoryRepository
import com.bugiman.domain.models.history.HistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
): HistoryRepository {

    override fun getItemsAll(): Flow<List<HistoryModel>> {
        return historyDao.getHistoryAllFlow()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun getItem(itemId: Long): HistoryModel {
        return historyDao
            .getHistoryItem(itemId)
            .toDomain()
    }

    override suspend fun deleteAll() {
        historyDao.deleteAll()
    }

    override suspend fun deleteItem(historyModel: HistoryModel) {
        historyDao.deleteItem(item = historyModel.toEntity())
    }

    override suspend fun insertItem(historyModel: HistoryModel) {
        historyDao.insertItem(item = historyModel.toEntity())
    }

    override suspend fun updateItemNote(id: Long, newNote: String) {
        historyDao.updateNote(itemId = id, newNote = newNote)
    }

}