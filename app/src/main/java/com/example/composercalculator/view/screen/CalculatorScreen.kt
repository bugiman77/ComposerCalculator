package com.example.composercalculator.view.screen

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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.R
import com.example.composercalculator.view.components.CalculatorButtonGrid
import com.example.composercalculator.view.components.DisplayArea
import com.example.composercalculator.view.components.HistoryBottomSheet
import com.example.composercalculator.model.CalculatorEvent
import com.example.composercalculator.model.CalculatorState
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import com.example.composercalculator.ui.theme.DarkGray

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    uiState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    showHistoryButton: Boolean,
    viewModelCalculation: CalculatorViewModel = viewModel(),
    viewModelSettings: SettingsViewModel = viewModel(),
) {

    val clipboardManager = LocalClipboardManager.current

    val displayText = remember(uiState) {
        val text = uiState.number1 + (uiState.operation ?: "") + uiState.number2
        text.ifEmpty { "0" }
    }

    var showHistorySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val uiState = viewModelCalculation.uiState
    val onEvent = viewModelCalculation::onEvent
    val history = viewModelCalculation.history
//    val history by viewModel.history.collectAsState()

//    val showHistoryButton = viewModelSettings.showHistoryButton.collectAsState().value
    val showIconButton = viewModelSettings.showIconButton.collectAsState().value

    LaunchedEffect(sheetState.isVisible) {
        if (!sheetState.isVisible) {
            showHistorySheet = false
        }
    }

    if (showHistorySheet) {
        HistoryBottomSheet(
            history = history,
            sheetState = sheetState,
            onAction = onEvent,
            onDismiss = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    showHistorySheet = false
                }
            }
        )
    }

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
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(id = R.drawable.ic_outline_list),
                                    contentDescription = "История вычислений",
                                    tint = Orange
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
                        Spacer(modifier = Modifier.size(40.dp))
                    }

                    if (showIconButton) {
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = "Настройки приложения",
                                tint = Orange
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
                    displayText = displayText,
                    onCopy = { textToCopy ->
                        clipboardManager.setText(AnnotatedString(textToCopy))
                    }
                )

                // Сетка кнопок
                CalculatorButtonGrid(
                    uiState = uiState,
                    viewModel = viewModelSettings,
                    onEvent = onEvent
                )
            }
        }

    }
}
