package com.bugiman.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bugiman.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .addCallback(themesCallback())
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun themesCallback(): RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                db.execSQL("""
                    INSERT INTO built_in_themes 
                    (name, primary_color, secondary_color, background_color, surface_color, onPrimary_color, is_dark_mode, is_pin) 
                    VALUES ('Classic Dark', 0xFF212121, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 1, 1)
                """)

                db.execSQL("""
                    INSERT INTO built_in_themes 
                    (name, primary_color, secondary_color, background_color, surface_color, onPrimary_color, is_dark_mode, is_pin) 
                    VALUES ('Light Breeze', 0xFFF5F5F5, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF, 0, 0)
                """)

                db.execSQL("""
                    INSERT INTO built_in_themes 
                    (name, primary_color, secondary_color, background_color, surface_color, onPrimary_color, is_dark_mode, is_pin) 
                    VALUES ('Deep Blue', 0xFF0D47A1, 0xFF002171, 0xFF002171, 0xFF002171, 0xFF002171, 1, 0)
                """)
            }
        }
    }

    @Provides
    fun provideHistoryDao(db: AppDatabase) = db.historyDao()

    @Provides
    fun provideInputStateDao(db: AppDatabase) = db.inputStateDao()

    @Provides
    fun provideSettingsDao(db: AppDatabase) = db.settingsDao()

    @Provides
    fun provideBuiltInThemesDao(db: AppDatabase) = db.builtInThemesDao()

    @Provides
    fun provideCustomThemesDao(db: AppDatabase) = db.customThemesDao()
}