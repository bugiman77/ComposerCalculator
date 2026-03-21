package com.bugiman.composercalculator.view.screen.main

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.bugiman.composercalculator.R
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.ui.theme.DarkGray
import com.bugiman.composercalculator.view.components.calculation.CalculatorButtonGrid
import com.bugiman.composercalculator.view.components.calculation.DisplayArea
import com.bugiman.composercalculator.view.components.calculation.HistoryBottomSheet
import com.bugiman.domain.models.settings.SettingModel
//import com.bugiman.composercalculator.view.components.calculation.CalculatorButtonGrid
//import com.bugiman.composercalculator.view.components.calculation.DisplayArea
//import com.bugiman.composercalculator.view.components.calculation.HistoryBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    viewModelCalculation: CalculatorViewModel,
    settingModel: SettingModel,
    onUpdateSettings: ((SettingModel) -> SettingModel) -> Unit = {}
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val isSheetVisible = sheetState.isVisible
    ForceWhiteStatusBarIcons(trigger = isSheetVisible)

    var showHistorySheet by remember { mutableStateOf(value = false) }

    val scope = rememberCoroutineScope()

    val showIconButton = settingModel.isShowHistoryButton
    val isShowHistoryBotton = settingModel.isShowHistoryButton

    LaunchedEffect(key1 = sheetState.isVisible) {
        if (!sheetState.isVisible) {
            showHistorySheet = false
        }
    }

    if (showHistorySheet) {

        HistoryBottomSheet(
            calculatorViewModel = viewModelCalculation,
            settingModel = settingModel,
            sheetState = sheetState,
            onDismiss = { showHistorySheet = false },
            onCloseClick = {
                scope.launch {
                    sheetState.hide()
                    showHistorySheet = false
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Black
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.End
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        if (isShowHistoryBotton) {
                            if (showIconButton) {
                                Card(
                                    onClick = { showHistorySheet = true },
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(48.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(
                                            0xFF2C2C2E
                                        )
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_history),
                                            contentDescription = "История вычислений",
                                            tint = Color.White,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            } else {
                                Button(
                                    onClick = { showHistorySheet = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = DarkGray)
                                ) {
                                    Text(
                                        text = "История",
                                        fontSize = 14.sp, // Уменьшаем размер шрифта для компактности
                                        color = MaterialTheme.colors.onPrimary // Цвет текста
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.size(size = 40.dp))
                        }

                        if (showIconButton) {
                            Card(
                                onClick = onNavigateToSettings,
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(48.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(
                                        0xFF2C2C2E
                                    )
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_more),
                                        contentDescription = "Настройки приложения",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        } else {
                            Button(
                                onClick = onNavigateToSettings,
                                colors = ButtonDefaults.buttonColors(containerColor = DarkGray)
                            ) {
                                Text(
                                    text = "Настройки",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colors.onPrimary // Цвет текста
                                )
                            }
                        }
                    }

                    DisplayArea(
                        viewModelCalculation = viewModelCalculation,
                        viewModelSettings = viewModelSettings,
                    )

                    // Сетка кнопок
                    CalculatorButtonGrid(
                        viewModelSetting = viewModelSettings,
                        viewModelCalculation = viewModelCalculation
                    )
                }
            }
        }
    }
}

@Composable
private fun ForceWhiteStatusBarIcons(
    trigger: Any? = null
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window

        // Используем SideEffect, который срабатывает после каждой успешной рекомпозиции
        SideEffect {
            val insetsController = WindowCompat.getInsetsController(window, view)
            // false означает, что мы НЕ используем темные иконки (т.е. они будут белыми)
            insetsController.isAppearanceLightStatusBars = false
        }
    }
}
