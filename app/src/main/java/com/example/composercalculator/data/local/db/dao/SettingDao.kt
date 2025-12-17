package com.example.composercalculator.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.composercalculator.data.local.db.entity.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: Settings)

    @Query(value = "SELECT * FROM settings LIMIT 1")
    suspend fun getSettings(): Settings?

    // Получаем настройки как Flow
    @Query(value = "SELECT * FROM settings LIMIT 1")
    fun getSettingsFlow(): Flow<Settings>

    // Вставка новых настроек
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: Settings)

    // Обновление существующих настроек
    @Update
    suspend fun updateSettings(settings: Settings)
}

