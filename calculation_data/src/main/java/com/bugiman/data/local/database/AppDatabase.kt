package com.bugiman.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bugiman.data.local.dao.BuiltInThemesDao
import com.bugiman.data.local.dao.CustomThemesDao
import com.bugiman.data.local.dao.HistoryDao
import com.bugiman.data.local.dao.InputStateDao
//import com.bugiman.data.local.dao.SettingsDao
import com.bugiman.data.local.entity.BuiltInThemesEntity
import com.bugiman.data.local.entity.CustomThemesEntity
import com.bugiman.data.local.entity.HistoryEntity
import com.bugiman.data.local.entity.InputState

@Database(
    entities = [
        HistoryEntity::class,
        InputState::class,
        BuiltInThemesEntity::class,
        CustomThemesEntity::class
    ],
    version = 16
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
    abstract fun inputStateDao(): InputStateDao
    abstract fun builtInThemesDao(): BuiltInThemesDao
    abstract fun customThemesDao(): CustomThemesDao

}