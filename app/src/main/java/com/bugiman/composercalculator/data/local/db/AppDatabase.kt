package com.bugiman.composercalculator.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bugiman.composercalculator.data.local.db.dao.BuiltInThemesDao
import com.bugiman.composercalculator.data.local.db.dao.CustomThemesDao
import com.bugiman.composercalculator.data.local.db.dao.HistoryDao
import com.bugiman.composercalculator.data.local.db.dao.InputStateDao
import com.bugiman.composercalculator.data.local.db.dao.SettingsDao
import com.bugiman.composercalculator.data.local.db.entity.BuiltInThemes
import com.bugiman.composercalculator.data.local.db.entity.CustomThemes
import com.bugiman.composercalculator.data.local.db.entity.History
import com.bugiman.composercalculator.data.local.db.entity.InputState
import com.bugiman.composercalculator.data.local.db.entity.Settings

@Database(entities = [Settings::class], version = 16)
abstract class AppDatabaseSetting : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao

    companion object {
        // Волатильная переменная для хранения единственного экземпляра базы данных
        @Volatile
        private var INSTANCE: AppDatabaseSetting? = null

        // Функция для получения единственного экземпляра базы данных
        fun getDatabase(context: Context): AppDatabaseSetting {
            // Проверяем, существует ли уже база данных
            return INSTANCE ?: synchronized(lock = this) {
                // Если базы данных нет, создаем новый экземпляр
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    klass = AppDatabaseSetting::class.java,
                    name = "settings_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Database(entities = [History::class, InputState::class], version = 5)
abstract class AppDatabaseHistory : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun inputStateDao(): InputStateDao

    companion object {
        // Волатильная переменная для хранения единственного экземпляра базы данных
        @Volatile
        private var INSTANCE: AppDatabaseHistory? = null

        // Функция для получения единственного экземпляра базы данных
        fun getDatabase(context: Context): AppDatabaseHistory {
            // Проверяем, существует ли уже база данных
            return INSTANCE ?: synchronized(lock = this) {
                // Если базы данных нет, создаем новый экземпляр
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    klass = AppDatabaseHistory::class.java,
                    name = "history_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Database(entities = [BuiltInThemes::class, CustomThemes::class], version = 2)
abstract class AppDatabaseThemes : RoomDatabase() {
    abstract fun builtInThemes(): BuiltInThemesDao
    abstract fun customThemes(): CustomThemesDao

    companion object {
        // Волатильная переменная для хранения единственного экземпляра базы данных
        @Volatile
        private var INSTANCE: AppDatabaseThemes? = null

        // Функция для получения единственного экземпляра базы данных
        fun getDatabase(context: Context): AppDatabaseThemes {
            // Проверяем, существует ли уже база данных
            return INSTANCE ?: synchronized(lock = this) {
                // Если базы данных нет, создаем новый экземпляр
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    klass = AppDatabaseThemes::class.java,
                    name = "themes_database" // Имя базы данных
                )
                    .addCallback(callback = ThemesDatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ThemesDatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // 1. Classic Dark
                db.execSQL(sql = """
                    INSERT INTO built_in_themes (name, primary_color, secondary_color, background_color, surface_color, onPrimary_color, is_dark_mode, is_pin) 
                    VALUES ('Classic Dark', 0xFF212121, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 1, 1)
                """)

                // 2. Light Breeze
                db.execSQL(sql = """
                    INSERT INTO built_in_themes (name, primary_color, secondary_color, background_color, surface_color, onPrimary_color, is_dark_mode, is_pin) 
                    VALUES ('Light Breeze', 0xFFF5F5F5, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF,0, 0)
                """)

                // 3. Deep Blue
                db.execSQL(sql = """
                    INSERT INTO built_in_themes (name, primary_color, secondary_color, background_color, surface_color, onPrimary_color, is_dark_mode, is_pin) 
                    VALUES ('Deep Blue', 0xFF0D47A1, 0xFF002171, 0xFF002171, 0xFF002171, 0xFF002171, 1, 0)
                """)
            }
        }

    }
}