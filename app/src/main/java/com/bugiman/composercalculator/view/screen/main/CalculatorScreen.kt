package com.bugiman.composercalculator.view.screen.main

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bugiman.composercalculator.R
import com.bugiman.composercalculator.view.components.calculation.CalculatorButtonGrid
import com.bugiman.composercalculator.view.components.calculation.DisplayArea
import com.bugiman.composercalculator.view.components.calculation.HistoryBottomSheet
import com.bugiman.composercalculator.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import com.bugiman.composercalculator.model.FlyingDigit
import com.bugiman.composercalculator.ui.theme.DarkGray
import com.bugiman.composercalculator.ui.theme.Gray

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    showHistoryButton: Boolean,
    viewModelCalculation: CalculatorViewModel = viewModel(),
    viewModelSettings: SettingsViewModel = viewModel(),
) {

    var showHistorySheet by remember { mutableStateOf(value = false) }
    var canCloseProgrammatically by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { sheetValue ->
            if (canCloseProgrammatically) true else sheetValue != SheetValue.Hidden
        }
    )
    val scope = rememberCoroutineScope()

    val showIconButton = viewModelSettings.showIconButton.collectAsState().value

    LaunchedEffect(key1 = sheetState.isVisible) {
        if (!sheetState.isVisible) {
            showHistorySheet = false
        }
    }

    if (showHistorySheet) {
        HistoryBottomSheet(
            calculatorViewModel = viewModelCalculation,
            settingsViewModel = viewModelSettings,
            sheetState = sheetState,
            onDismiss = { showHistorySheet = false },
            onCloseClick = {
                scope.launch {
                    canCloseProgrammatically = true // Разрешаем анимацию скрытия
                    sheetState.hide() // Запускаем анимацию
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showHistorySheet = false // Удаляем окно из композиции
                    }
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

                        if (showHistoryButton) {
                            if (showIconButton) {
                                IconButton(onClick = { showHistorySheet = true }) {
                                    Icon(
                                        modifier = Modifier.size(size = 32.dp),
                                        painter = painterResource(id = R.drawable.ic_history),
                                        contentDescription = "История вычислений",
                                        tint = Gray
                                    )
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
                            IconButton(onClick = onNavigateToSettings) {
                                Icon(
                                    modifier = Modifier.size(size = 32.dp),
                                    painter = painterResource(id = R.drawable.ic_more),
                                    contentDescription = "Настройки приложения",
                                    tint = Gray
                                )
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
                        onPositioned = { /*offset -> targetOffset = offset*/ },
                    )

                    // Сетка кнопок
                    CalculatorButtonGrid(
                        viewModelSetting = viewModelSettings,
                        viewModelCalculation = viewModelCalculation,
                        onDigitClick = { text, offset ->
                            // Здесь мы добавляем новую летящую цифру в список
                            val newFlyingDigit = FlyingDigit(
                                text = text,
                                startOffset = offset
                            )
//                            flyingDigits = flyingDigits + newFlyingDigit
                        }
                    )
                }
            }

        }

       /* flyingDigits.forEach { digit ->
            FlyingDigitAnimation(
                data = digit,
                targetOffset = targetOffset,
                onReached = {
                    flyingDigits = flyingDigits.filter { it.id != digit.id }
                    // В этот момент можно добавить символ в ViewModel,
                    // если вы хотите, чтобы он появлялся ПОСЛЕ прилета
                }
            )
        }*/

    }
}
