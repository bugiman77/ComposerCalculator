package com.bugiman.composercalculator.presentation.main

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugiman.composercalculator.Application
import com.bugiman.composercalculator.navigation.AppNavigation
import com.bugiman.composercalculator.presentation.calculation.CalculationViewModelFactory
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModelFactory
import com.bugiman.composercalculator.ui.theme.ComposerCalculatorTheme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        val app = application as Application

        val viewModelSettings: SettingsViewModel by viewModels {
            SettingsViewModelFactory(
                settingsAllGetUseCase = app.settingsAllGetUseCase,
                settingsItemUpdateUseCase = app.settingsItemUpdateUseCase
            )
        }

        val viewModelCalculation: CalculatorViewModel by viewModels {
            CalculationViewModelFactory(
                calculateExpressionUseCase = app.calculateExpressionUseCase,
                historyItemSaveUseCase = app.historyItemSaveUseCase,
                triggerFeedbackUseCase = app.triggerFeedbackUseCase,
            )
        }

        setContent {

            val settings by viewModelSettings.uiState.collectAsStateWithLifecycle()

            val view = LocalView.current
            val keepScreenOn = settings.isKeepScreenOn
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

            val isDarkTheme = settings.isDarkTheme
            ComposerCalculatorTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false,
            ) {
                AppNavigation(
                    settingsViewModel = viewModelSettings,
                    calculatorViewModel = viewModelCalculation,
                )
            }

            val isClearHistoryOnClose = settings.isClearHistoryOnClose

            if (isClearHistoryOnClose) {
                viewModelCalculation.deleteAll()
            }

        }
    }
}