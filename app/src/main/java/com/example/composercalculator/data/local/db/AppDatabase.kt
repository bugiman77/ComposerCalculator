package com.example.composercalculator.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composercalculator.data.local.db.dao.HistoryDao
import com.example.composercalculator.data.local.db.dao.SettingsDao
import com.example.composercalculator.data.local.db.entity.History
import com.example.composercalculator.data.local.db.entity.Settings

@Database(entities = [Settings::class], version = 12)
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
                    name = "settings_database" // Имя базы данных
                )
                    .fallbackToDestructiveMigration() // Если схема изменится, база будет перезаписана
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}

@Database(entities = [History::class], version = 3)
abstract class AppDatabaseHistory : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

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
                    name = "history_database" // Имя базы данных
                )
                    .fallbackToDestructiveMigration() // Если схема изменится, база будет перезаписана
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
