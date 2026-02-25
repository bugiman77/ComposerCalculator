package com.bugiman.composercalculator.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bugiman.composercalculator.view.screen.main.CalculatorScreen
import com.bugiman.composercalculator.view.screen.settings.AboutScreen
import com.bugiman.composercalculator.view.screen.settings.CreateThemeAppUser
import com.bugiman.composercalculator.view.screen.settings.PrivacyPolicyScreen
import com.bugiman.composercalculator.view.screen.settings.SettingsScreen
import com.bugiman.composercalculator.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.viewmodel.SettingsViewModel

class CalculatorScreen(
    val settingsViewModel: SettingsViewModel,
    val calculatorViewModel: CalculatorViewModel,
    val showHistoryButton: Boolean
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        CalculatorScreen(
            onNavigateToSettings = {
                navigator.push(
                    SettingsScreen(
                        settingsViewModel,
                        calculatorViewModel
                    )
                )
            },
            onNavigateToHistory = { /* navigator.push(HistoryScreen()) */ },
            showHistoryButton = showHistoryButton,
            viewModelSettings = settingsViewModel,
            viewModelCalculation = calculatorViewModel
        )
    }
}

class AboutScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AboutScreen(
            title = "О приложении",
            onNavigateBack = { navigator.pop() },
            onNavigateToPrivacy = { navigator.push(PrivacyPoliceScreen()) }
        )
    }
}

class SettingsScreen(
    val viewModelSettings: SettingsViewModel,
    val viewModelCalculation: CalculatorViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        SettingsScreen(
            title = "Настройки",
            viewModelSettings = viewModelSettings,
            viewModelCalculation = viewModelCalculation,
            onNavigateBack = { navigator.pop() },
            onNavigateToAbout = { navigator.push(AboutScreen()) },
            onNavigateToCreateThemes = { navigator.push(CreateThemeAppUser()) },
            onNavigateToViewThemes = { }
        )
    }
}

class CreateThemeAppUser : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        CreateThemeAppUser(
            title = "Новая тема",
            onNavigateBack = { navigator.pop() }
        )
    }
}

class PrivacyPoliceScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        PrivacyPolicyScreen(
            title = "О данных",
            onNavigateBack = { navigator.pop() }
        )
    }
}