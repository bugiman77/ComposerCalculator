package com.example.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.composercalculator.data.local.db.AppDatabaseThemes
import com.example.composercalculator.data.local.db.dao.BuiltInThemesDao
import com.example.composercalculator.data.local.db.dao.CustomThemesDao

class ThemesViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val builtInThemesDao: BuiltInThemesDao =
        AppDatabaseThemes.getDatabase(context = application).builtInThemes()

    private val customThemesDao: CustomThemesDao =
        AppDatabaseThemes.getDatabase(context = application).customThemes()

}