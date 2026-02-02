package com.bugiman.composercalculator.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.bugiman.composercalculator.data.local.db.entity.BuiltInThemes
import kotlinx.coroutines.flow.Flow

@Dao
interface BuiltInThemesDao {

    @Query(value = "SELECT * FROM built_in_themes ORDER BY isPin DESC, name ASC")
    fun getThemesAll(): Flow<List<BuiltInThemes>>

}