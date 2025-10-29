package com.example.composercalculator.calculator

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.composercalculator.R
import com.example.composercalculator.calculator.components.CalculatorButtonGrid
import com.example.composercalculator.calculator.components.DisplayArea
import com.example.composercalculator.calculator.components.HistoryBottomSheet
import com.example.composercalculator.model.CalculatorEvent
import com.example.composercalculator.model.CalculatorState
import com.example.composercalculator.ui.theme.Orange

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorScreen(
    uiState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit,
    onNavigateToSettings: () -> Unit, // <-- ДОБАВЛЕНО
    onNavigateToHistory: () -> Unit,
    showHistoryButton: Boolean
) {

    val clipboardManager = LocalClipboardManager.current

    val displayText = remember(uiState) {
        val text = uiState.number1 + (uiState.operation ?: "") + uiState.number2
        text.ifEmpty { "0" }
    }

    var showHistorySheet by remember { mutableStateOf(false) }

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
                        IconButton(onClick = { showHistorySheet = true }) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = R.drawable.ic_outline_list),
                                contentDescription = "История",
                                tint = Orange
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
                    }

                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = "Настройки",
                            tint = Orange
                        )
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
                    onEvent = onEvent
                )
            }
        }

        if (showHistorySheet) {
            HistoryBottomSheet(
                // Пока что передаем пустой список для примера
                history = listOf("2+2 = 4", "100-50 = 50", "10*10 = 100", "99/3 = 33"),
                onDismiss = { showHistorySheet = false }
            )
        }
    }
}
