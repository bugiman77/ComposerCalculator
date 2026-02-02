package com.bugiman.composercalculator.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bugiman.composercalculator.data.local.db.entity.CustomThemes
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomThemesDao {

    @Query(value = "SELECT * FROM custom_themes ORDER BY isPin DESC, name ASC")
    fun getThemesAll(): Flow<List<CustomThemes>>

    @Insert(entity = CustomThemes::class)
    suspend fun insertThemeItem(item: CustomThemes)

    @Delete
    suspend fun deleteThemeItem(item: CustomThemes)

    @Query("DELETE FROM custom_themes")
    suspend fun deleteThemesAll()

}