package com.example.composercalculator.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.composercalculator.model.CalculationHistoryItem

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertItem(item: CalculationHistoryItem)

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun getHistory(): LiveData<List<CalculationHistoryItem>>

    @Delete
    suspend fun deleteItem(item: CalculationHistoryItem)
}