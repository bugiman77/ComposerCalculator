package com.example.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.composercalculator.data.local.db.AppDatabaseThemes
import com.example.composercalculator.data.local.db.dao.BuiltInThemesDao
import com.example.composercalculator.data.local.db.dao.CustomThemesDao
import com.example.composercalculator.data.local.db.entity.BuiltInThemes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemesViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val builtInThemesDao: BuiltInThemesDao =
        AppDatabaseThemes.getDatabase(context = application).builtInThemes()

    private val customThemesDao: CustomThemesDao =
        AppDatabaseThemes.getDatabase(context = application).customThemes()

    private val _builtInThemes = MutableStateFlow<List<BuiltInThemes>>(value = emptyList())
    val builtInThemes: StateFlow<List<BuiltInThemes>> = _builtInThemes

}