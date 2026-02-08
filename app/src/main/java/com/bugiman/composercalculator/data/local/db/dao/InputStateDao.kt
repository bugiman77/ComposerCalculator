package com.bugiman.composercalculator.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bugiman.composercalculator.data.local.db.entity.InputState
import kotlinx.coroutines.flow.Flow

@Dao
interface InputStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInput(state: InputState)

    @Query(value = "SELECT * FROM input_state WHERE id = 1")
    fun getInputState(): Flow<InputState?>

    @Query("DELETE FROM input_state WHERE id = 1")
    suspend fun deleteItem()
}