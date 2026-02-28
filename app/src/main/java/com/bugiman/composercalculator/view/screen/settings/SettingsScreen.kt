package com.bugiman.composercalculator.view.screen.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bugiman.composercalculator.R
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.App
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.AppTheme
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.Appearance
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.Display
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.HistoryComputing
import com.bugiman.composercalculator.view.components.settings.SettingsBlock.SoundAndVibration
import com.bugiman.composercalculator.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    title: String,
    viewModelSettings: SettingsViewModel = viewModel(),
    viewModelCalculation: CalculatorViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToCreateThemes: () -> Unit,
    onNavigateToViewThemes: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(0xFF000000),
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 120.dp, start = 16.dp, end = 16.dp)
            ) {
                AppTheme(
                    viewModelSettings = viewModelSettings,
                    onNavigateToCreateThemes = onNavigateToCreateThemes,
                    onNavigateToViewThemes = onNavigateToViewThemes
                )

                Spacer(modifier = Modifier.height(18.dp))

                SoundAndVibration(viewModelSettings = viewModelSettings)

                Spacer(modifier = Modifier.height(18.dp))

                Appearance(viewModelSettings = viewModelSettings)

                Spacer(modifier = Modifier.height(18.dp))

                HistoryComputing(
                    viewModelSettings = viewModelSettings,
                    viewModelCalculation = viewModelCalculation,
                )

                Spacer(modifier = Modifier.height(18.dp))

                Display(viewModelSettings = viewModelSettings)

                Spacer(modifier = Modifier.height(18.dp))

                App(onNavigateToAbout = onNavigateToAbout)

                // Дополнительный отступ снизу для красоты
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

@Composable
fun CustomTopBar(
    screenTitle: String,
    onNavigateBack: () -> Unit,
    onScrollToTop: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding() // Только отступ, БЕЗ background
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        // Островок кнопки "Назад"
        Card(
            onClick = onNavigateBack,
            shape = CircleShape,
            modifier = Modifier.size(48.dp).align(Alignment.CenterStart),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2E)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(id = R.drawable.back_ios),
                    contentDescription = "Назад",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Островок заголовка
        Card(
            onClick = onScrollToTop,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2E)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = screenTitle,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }
    }
}