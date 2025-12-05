package com.example.composercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.composercalculator.navigation.AppNavigation
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme
import com.example.composercalculator.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModelSettings = SettingsViewModel(application = application)

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposerCalculatorTheme {
                AppNavigation(
                    settingsViewModel = viewModelSettings
                )
            }
        }
    }
}
