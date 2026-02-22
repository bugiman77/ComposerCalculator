package com.bugiman.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bugiman.data.local.entity.CustomThemesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomThemesDao {

    @Query(value = "SELECT * FROM custom_themes ORDER BY isPin DESC, name ASC")
    fun getThemesAll(): Flow<List<CustomThemesEntity>>

    @Insert(entity = CustomThemesEntity::class)
    suspend fun insertThemeItem(item: CustomThemesEntity)

    @Delete
    suspend fun deleteThemeItem(item: CustomThemesEntity)

    @Query("DELETE FROM custom_themes")
    suspend fun deleteThemesAll()

}