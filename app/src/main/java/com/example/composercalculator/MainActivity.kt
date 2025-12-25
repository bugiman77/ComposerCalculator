package com.example.composercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.composercalculator.core.managers.SoundManager
import com.example.composercalculator.core.managers.VibrationManager
import com.example.composercalculator.navigation.AppNavigation
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val managerSound = SoundManager(application)
        val managerVibration = VibrationManager(application)

        val viewModelSettings = SettingsViewModel(application = application)
        val viewModelCalculation = CalculatorViewModel(
            settingsViewModel = viewModelSettings,
            soundManager = managerSound,
            vibrationManager = managerVibration,
            application = application
        )

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = viewModelSettings.isDarkTheme.collectAsState().value
            ComposerCalculatorTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false
            ) {
                AppNavigation(
                    settingsViewModel = viewModelSettings,
                    calculatorViewModel = viewModelCalculation
                )
            }
        }
    }
}
