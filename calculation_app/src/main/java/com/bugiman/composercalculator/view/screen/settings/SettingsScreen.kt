package com.bugiman.composercalculator.view.screen.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.view.components.general.settings.CustomTopBar
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.App
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.AppTheme
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.Appearance
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.Display
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.SoundAndVibration
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    title: String,
    viewModelSettings: SettingsViewModel,
    viewModelCalculation: CalculatorViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToCreateThemes: () -> Unit,
    onNavigateToViewThemes: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val settingsModel by viewModelSettings.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFF000000),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 120.dp, start = 16.dp, end = 16.dp)
            ) {
                AppTheme(
                    settingsModel = settingsModel,
                    viewModelSettings = viewModelSettings,
                    onNavigateToCreateThemes = onNavigateToCreateThemes,
                    onNavigateToViewThemes = onNavigateToViewThemes
                )

                Spacer(modifier = Modifier.height(18.dp))

                SoundAndVibration(
                    settingsModel = settingsModel,
                    viewModelSettings = viewModelSettings
                )

                Spacer(modifier = Modifier.height(18.dp))

                Appearance(
                    settingsModel = settingsModel,
                    viewModelSettings = viewModelSettings
                )

                Spacer(modifier = Modifier.height(18.dp))

                Display(
                    settingsModel = settingsModel,
                    viewModelSettings = viewModelSettings
                )

                Spacer(modifier = Modifier.height(18.dp))

                App(onNavigateToAbout = onNavigateToAbout)

                Spacer(modifier = Modifier.height(32.dp))
            }

            CustomTopBar(
                screenTitle = title,
                onNavigateBack = onNavigateBack,
                onScrollToTop = {
                    coroutineScope.launch { scrollState.animateScrollTo(0) }
                }
            )
        }
    }
}