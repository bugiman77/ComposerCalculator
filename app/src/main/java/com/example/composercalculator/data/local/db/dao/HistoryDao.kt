package com.example.composercalculator.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composercalculator.data.local.db.entity.History

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertItem(item: History)

    @Query(value = "SELECT * FROM history ORDER BY timestamp DESC")
    fun getHistory(): History?

    @Delete
    suspend fun deleteItem(item: History)
}