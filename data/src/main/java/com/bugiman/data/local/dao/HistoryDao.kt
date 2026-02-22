package com.bugiman.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bugiman.data.local.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(entity = HistoryEntity::class)
    suspend fun insertItem(item: HistoryEntity)

    @Query(value = "SELECT * FROM history ORDER BY timestamp DESC LIMIT 1")
    fun getHistoryLast(): HistoryEntity?

    @Query(value = "SELECT * FROM history WHERE id = :itemId")
    fun getHistoryItem(itemId: Long): HistoryEntity

    @Query(value = "SELECT * FROM history ORDER BY timestamp DESC")
    fun getHistoryAll(): Flow<List<HistoryEntity>>

    @Delete
    suspend fun deleteItem(item: HistoryEntity)

    @Query("DELETE FROM history")
    suspend fun deleteAll()

    @Query(value = "UPDATE history SET note = :newNote WHERE id = :itemId")
    suspend fun updateNote(itemId: Long, newNote: String)

}