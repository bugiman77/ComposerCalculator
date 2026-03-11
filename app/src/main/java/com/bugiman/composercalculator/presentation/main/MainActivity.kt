package com.bugiman.composercalculator.presentation.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bugiman.composercalculator.core.managers.SoundManager
import com.bugiman.composercalculator.core.managers.VibrationManager
import com.bugiman.composercalculator.navigation.AppNavigation
import com.bugiman.composercalculator.ui.theme.ComposerCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val managerSound = SoundManager(context = application)
        val managerVibration = VibrationManager(context = application)

        val viewModelSettings = SettingsViewModel(
            application = application,
        )

        val viewModelCalculation = CalculatorViewModel(
            application = application,
            settingsViewModel = viewModelSettings,
            soundManager = managerSound,
            vibrationManager = managerVibration,
        )

        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val view = LocalView.current
            val keepScreenOn = viewModelSettings.keepScreenOn.collectAsState().value
            DisposableEffect(key1 = keepScreenOn) {
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
                dynamicColor = false,
            ) {
                AppNavigation(
                    settingsViewModel = viewModelSettings,
                    calculatorViewModel = viewModelCalculation,
                )
            }

            val isClearHistoryOnClose =
                viewModelSettings.isClearHistoryOnClose.collectAsState().value

            if (isClearHistoryOnClose) {
                viewModelCalculation.deleteHistoryItemAll()
            }

        }
    }
}