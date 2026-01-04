package com.example.composercalculator.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composercalculator.data.local.db.entity.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertItem(item: History)

    @Query(value = "SELECT * FROM history ORDER BY timestamp DESC LIMIT 1")
    fun getHistoryLast(): History?

    @Query(value = "SELECT * FROM history ORDER BY timestamp DESC")
    fun getHistoryAll(): Flow<List<History>>

    @Delete
    suspend fun deleteItem(item: History)

    @Query("DELETE FROM history")
    suspend fun deleteAll()



}