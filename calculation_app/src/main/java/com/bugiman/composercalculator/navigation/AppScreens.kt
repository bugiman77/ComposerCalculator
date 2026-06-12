package com.bugiman.composercalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bugiman.composercalculator.presentation.calculation.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.presentation.settings.viewmodel.SettingsViewModel
import com.bugiman.composercalculator.presentation.main.view.CalculatorScreen
import com.bugiman.composercalculator.presentation.aboutscreen.AboutScreen
import com.bugiman.composercalculator.presentation.createtheme.CreateThemeAppUser
import com.bugiman.composercalculator.presentation.privacypolicy.PrivacyPolicyScreen
import com.bugiman.composercalculator.presentation.settings.view.SettingsScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugiman.composercalculator.presentation.history.viewmodel.HistoryViewModel

class CalculatorScreenNavigation(
    val settingsViewModel: SettingsViewModel,
    val calculatorViewModel: CalculatorViewModel,
    val viewModelHistory: HistoryViewModel,
    val showHistoryButton: Boolean
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settingsModel by settingsViewModel.uiState.collectAsStateWithLifecycle()

        CalculatorScreen(
            viewModelCalculation = calculatorViewModel,
            viewModelHistory = viewModelHistory,
            settingModel = settingsModel,
            onNavigateToSettings = {
                navigator.push(
                    SettingsScreenNavigation(
                        viewModelSettings = settingsViewModel,
                        viewModelCalculation = calculatorViewModel,
                        viewModelHistory = viewModelHistory,
                    )
                )
            },
            onNavigateToHistory = { /* navigator.push(HistoryScreen()) */ }
        )
    }
}

class AboutScreenNavigation : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AboutScreen(
            title = "О приложении",
            onNavigateBack = { navigator.pop() },
            onNavigateToPrivacy = { navigator.push(PrivacyPolicyScreenNavigation()) }
        )
    }
}

class SettingsScreenNavigation(
    val viewModelSettings: SettingsViewModel,
    val viewModelCalculation: CalculatorViewModel,
    val viewModelHistory: HistoryViewModel,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SettingsScreen(
            title = "Настройки",
            viewModelSettings = viewModelSettings,
            viewModelCalculation = viewModelCalculation,
            viewModelHistory = viewModelHistory,
            onNavigateBack = { navigator.pop() },
            onNavigateToAbout = { navigator.push(AboutScreenNavigation()) },
            onNavigateToCreateThemes = { navigator.push(CreateThemeAppUserNavigation()) },
            onNavigateToViewThemes = { }
        )
    }
}

class CreateThemeAppUserNavigation : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        CreateThemeAppUser(
            title = "Новая тема",
            onNavigateBack = { navigator.pop() }
        )
    }
}

class PrivacyPolicyScreenNavigation : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        PrivacyPolicyScreen(
            title = "О данных",
            onNavigateBack = { navigator.pop() }
        )
    }
}