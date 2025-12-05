package com.example.composercalculator.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.composercalculator.data.local.db.dao.SettingsDao
import com.example.composercalculator.model.Settings

@Database(entities = [Settings::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao

    companion object {
        // Волатильная переменная для хранения единственного экземпляра базы данных
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Функция для получения единственного экземпляра базы данных
        fun getDatabase(context: Context): AppDatabase {
            // Проверяем, существует ли уже база данных
            return INSTANCE ?: synchronized(this) {
                // Если базы данных нет, создаем новый экземпляр
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "settings_database" // Имя базы данных
                )
                    .fallbackToDestructiveMigration() // Если схема изменится, база будет перезаписана
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
