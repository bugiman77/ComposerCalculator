package com.example.composercalculator.calculator.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.R
import com.example.composercalculator.model.CalculatorEvent
import com.example.composercalculator.model.CalculatorState
import com.example.composercalculator.ui.theme.DarkGray
import com.example.composercalculator.ui.theme.LightGray
import com.example.composercalculator.ui.theme.Orange

@Composable
fun CalculatorButtonGrid(
    uiState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
//    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 24.dp), // Отступы по бокам и снизу
        verticalArrangement = Arrangement.spacedBy(8.dp) // Расстояние между рядами
    ) {

        val isInputEmpty =
            uiState.number1.isEmpty() && uiState.number2.isEmpty() && uiState.operation == null

        // --- Ряд 1: AC, +/-, %, ÷ ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Расстояние между кнопками
        ) {
            BtnCalculation(
                text = if (isInputEmpty) "AC" else "C",
                color = LightGray,
                modifier = Modifier.weight(1f),
                fontSize = 30.sp,
                onClick = {
                    if (!isInputEmpty) { // Кнопка AC неактивна
                        onEvent(CalculatorEvent.Delete)
                    }
                },
                onLongClick = {
                    if (!isInputEmpty) { // Долгое нажатие тоже работает только для "C"
                        onEvent(CalculatorEvent.LongClear)
                    }
                }
            )
            BtnCalculation(
                text = "+/-",
                color = LightGray,
                modifier = Modifier.weight(1f),
                fontSize = 30.sp
            ) { onEvent(CalculatorEvent.Negate) }
            BtnCalculation(
                text = "%",
                color = LightGray,
                modifier = Modifier.weight(1f),
                fontSize = 45.sp
            ) { onEvent(CalculatorEvent.OperationClick("%")) }
            BtnCalculation(
                text = "÷",
                color = Orange,
                modifier = Modifier.weight(1f),
                fontSize = 60.sp
            ) { onEvent(CalculatorEvent.OperationClick("÷")) }
        }

        // --- Ряд 2: 7, 8, 9, × ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BtnCalculation(
                text = "7",
                color = DarkGray,
                modifier = Modifier.weight(1f),
                onClick = {
                    onEvent(CalculatorEvent.NumberClick("7")) // <-- Отправляем событие
                }
            )
            BtnCalculation(text = "8", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("8")
                )
            }
            BtnCalculation(text = "9", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("9")
                )
            }
            BtnCalculation(
                text = "×",
                color = Orange,
                modifier = Modifier.weight(1f),
                fontSize = 50.sp
            ) { onEvent(CalculatorEvent.OperationClick("×")) }
        }

        // --- Ряд 3: 4, 5, 6, - ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BtnCalculation(text = "4", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("4")
                )
            }
            BtnCalculation(text = "5", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("5")
                )
            }
            BtnCalculation(text = "6", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("6")
                )
            }
            BtnCalculation(
                text = "-",
                color = Orange,
                modifier = Modifier.weight(1f),
                fontSize = 70.sp
            ) { onEvent(CalculatorEvent.OperationClick("-")) }
        }

        // --- Ряд 4: 1, 2, 3, + ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BtnCalculation(text = "1", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("1")
                )
            }
            BtnCalculation(text = "2", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("2")
                )
            }
            BtnCalculation(text = "3", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("3")
                )
            }
            BtnCalculation(
                text = "+",
                color = Orange,
                modifier = Modifier.weight(1f),
                fontSize = 60.sp
            ) {
                onEvent(CalculatorEvent.OperationClick("+"))
            }
        }

        // --- Ряд 5: 0, ,, = ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            var menuExpanded by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier.weight(1f)
            )
            {
                Button(
                    onClick = {
                        menuExpanded = true
                    }, // Передаем клик напрямую
                    modifier = Modifier
                        .aspectRatio(1f),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGray),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.ic_calculate),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                if (menuExpanded) { // Оборачиваем в if, чтобы Popup корректно исчезал
                    StyledDropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    )
                }
            }

            BtnCalculation(
                text = "0",
                color = DarkGray,
                modifier = Modifier.weight(1f),
                fontSize = 45.sp
            ) {
                onEvent(CalculatorEvent.NumberClick("0"))
            }
            BtnCalculation(
                text = ",",
                color = DarkGray,
                modifier = Modifier
                    .weight(1f)
            ) {
                onEvent(
                    CalculatorEvent.DecimalClick
                )
            }
            BtnCalculation(
                text = "=",
                color = Orange,
                modifier = Modifier.weight(1f),
                fontSize = 65.sp,
            ) {
                onEvent(CalculatorEvent.Calculate)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BtnCalculation(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit = 40.sp,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)
            .combinedClickable(
                enabled = onLongClick != null,
                onLongClick = onLongClick,
                onClick = onClick
            ),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = fontSize,
                color = Color.White
            )
        }
    }
}
