package com.example.composercalculator

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.composercalculator.core.managers.SoundManager
import com.example.composercalculator.core.managers.VibrationManager
import com.example.composercalculator.data.repository.DeviceSettingsRepository
import com.example.composercalculator.navigation.AppNavigation
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val managerSound = SoundManager(context = application)
        val managerVibration = VibrationManager(context = application)
        val deviceSettingsRepository = DeviceSettingsRepository(context = application)

        val viewModelSettings = SettingsViewModel(
            application = application,
            repository = deviceSettingsRepository,
        )
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

            val view = LocalView.current
            val keepScreenOn = viewModelSettings.keepScreenOn.collectAsState().value
            DisposableEffect(keepScreenOn) {
                view.keepScreenOn = keepScreenOn
                if (keepScreenOn) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                onDispose {
                    view.keepScreenOn = false
                    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }

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
