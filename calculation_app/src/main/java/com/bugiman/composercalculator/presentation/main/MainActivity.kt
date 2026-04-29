package com.bugiman.composercalculator.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugiman.composercalculator.CalcApplication
import com.bugiman.composercalculator.navigation.AppNavigation
import com.bugiman.composercalculator.presentation.calculation.CalculationViewModelFactory
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.convert.ConvertViewModel
import com.bugiman.composercalculator.presentation.convert.ConvertViewModelFactory
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModelFactory
import com.bugiman.composercalculator.ui.theme.ComposerCalculatorTheme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        val app = application as CalcApplication

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
                historyAllDeleteUseCase = app.historyAllDeleteUseCase,
                historyAllGetUseCase = app.historyAllGetUseCase,
                feedbackTriggerUseCase = app.feedbackTriggerUseCase,
                buildDigitUseCase = app.buildDigitUseCase,
                buildOperatorUseCase = app.buildOperatorUseCase,
                buildBracketUseCase = app.buildBracketUseCase,
                buildDecimalUseCase = app.buildDecimalUseCase,
                buildZeroUseCase = app.buildZeroUseCase,
                removeLastCharUseCase = app.removeLastCharUseCase
            )
        }

        val viewModelConverter: ConvertViewModel by viewModels {
            ConvertViewModelFactory(
                convertGetFormattedConversionUseCase = app.convertGetFormattedConversionUseCase,
                convertSwapCurrenciesUseCase = app.convertSwapCurrenciesUseCase,
                feedbackTriggerUseCase = app.feedbackTriggerUseCase
            )
        }

        setContent {
            val settings by viewModelSettings.uiState.collectAsStateWithLifecycle()

            val view = LocalView.current
            DisposableEffect(settings.isKeepScreenOn) {
                view.keepScreenOn = settings.isKeepScreenOn
                onDispose { view.keepScreenOn = false }
            }

            LaunchedEffect(Unit) {
                if (settings.isClearHistoryOnClose) {
                    viewModelCalculation.deleteAll()
                }
            }

            ComposerCalculatorTheme(
                darkTheme = settings.isDarkTheme,
                dynamicColor = false,
            ) {
                AppNavigation(
                    settingsViewModel = viewModelSettings,
                    calculatorViewModel = viewModelCalculation,
                    converterViewModel = viewModelConverter
                )
            }
        }
    }
}