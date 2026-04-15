package com.bugiman.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.bugiman.data.local.entity.BuiltInThemesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BuiltInThemesDao {

    @Query(value = "SELECT * FROM built_in_themes ORDER BY isPin DESC, name ASC")
    fun getThemesAll(): Flow<List<BuiltInThemesEntity>>

    @Query("UPDATE built_in_themes SET isPin = :isPinned WHERE id = :themeId")
    suspend fun updatePinStatus(themeId: Long, isPinned: Boolean)

}