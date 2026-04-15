package com.bugiman.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
//import com.bugiman.data.local.entity.SettingEntity

/*@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: SettingEntity)

    @Query(value = "SELECT * FROM settings LIMIT 1")
    suspend fun getSettings(): SettingEntity?

    // Получаем настройки как Flow
    @Query(value = "SELECT * FROM settings LIMIT 1")
    fun getSettingsAllFlow(): Flow<SettingEntity>

    // Вставка новых настроек
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingEntity)

    // Обновление существующих настроек
    @Update
    suspend fun updateSettings(settings: SettingEntity)
}*/

