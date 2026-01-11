package com.example.composercalculator.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.composercalculator.data.local.db.entity.BuiltInThemes
import kotlinx.coroutines.flow.Flow

@Dao
interface BuiltInThemesDao {

    @Query(value = "SELECT * FROM built_in_themes")
    fun getThemesAll(): Flow<List<BuiltInThemes>>

}