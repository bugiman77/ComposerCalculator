package com.example.composercalculator.calculator

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.R
import com.example.composercalculator.ui.theme.DarkGray
import com.example.composercalculator.ui.theme.LightGray
import com.example.composercalculator.ui.theme.Orange

// Главный экран, который принимает состояние и обработчик событий
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorScreen(
    uiState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit
) {

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val displayText = remember(uiState) {
        val text = uiState.number1 + (uiState.operation ?: "") + uiState.number2
        text.ifEmpty { "0" }
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

                Row {
                    Icon(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 12.dp,
                                top = 16.dp
                            )
                            .size(40.dp),
                        painter = painterResource(id = R.drawable.ic_outline_list),
                        contentDescription = null,
                        tint = Orange
                    )
                }

                DisplayArea(
                    displayText = displayText,
                    onCopy = { textToCopy ->
                        clipboardManager.setText(AnnotatedString(textToCopy))
                        Toast.makeText(context, "Скопировано: $textToCopy", Toast.LENGTH_SHORT)
                            .show()
                    }
                )

                // Сетка кнопок
                CalculatorButtonGrid(
                    uiState = uiState,
                    onEvent = onEvent,
//                resetFontSize = { fontSize = 80.sp },
                    /*onPaste = {
                        val text = clipboardManager.getText()?.text
                        onEvent(CalculatorEvent.Paste(text))
                        Toast.makeText(context, "Вставлено", Toast.LENGTH_SHORT).show()
                    }*/
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayArea(
    displayText: String,
    onCopy: (String) -> Unit
) {
    var fontSize by remember { mutableStateOf(80.sp) }
    val minFontSize = 28.sp
    val scrollState = rememberScrollState()

    LaunchedEffect(displayText) {
        scrollState.animateScrollTo(scrollState.maxValue)
        // Сбрасываем размер шрифта при коротком тексте
        if (displayText.length < 10) {
            fontSize = 80.sp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            modifier = Modifier
                .verticalScroll(scrollState)
                .combinedClickable(
                    onClick = {},
                    onLongClick = { onCopy(displayText) }
                ),
            text = displayText,
            color = Color.White,
            textAlign = TextAlign.End,
            fontSize = fontSize,
            maxLines = Int.MAX_VALUE,
            softWrap = true,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow && fontSize > minFontSize) {
                    fontSize *= 0.95f
                }
            }
        )
    }
}

@Composable
private fun CalculatorButtonGrid(
    uiState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    // Убрали лишний Box, используем Column напрямую
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
                fontSize = 30.sp
            ) { onEvent(CalculatorEvent.OperationClick("%")) }
            BtnCalculation(
                text = "÷",
                color = Orange,
                modifier = Modifier.weight(1f),
                fontSize = 40.sp
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
                fontSize = 40.sp
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
                fontSize = 40.sp
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
                fontSize = 40.sp
            ) {
                onEvent(CalculatorEvent.OperationClick("+"))
            }
        }

        // --- Ряд 5: 0, ,, = ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Кнопка "0" занимает место двух кнопок
            BtnCalculation(
                text = "0",
                color = DarkGray,
                modifier = Modifier.weight(1f)
            ) {
                onEvent(CalculatorEvent.NumberClick("0"))
            }
            BtnCalculation(
                text = "0",
                color = DarkGray,
                modifier = Modifier.weight(1f)
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
                fontSize = 40.sp,
                onLongClick = {
                    if (isInputEmpty) {
                        // Получаем текст и передаем его в событие
                        val text = clipboardManager.getText()?.text
                        onEvent(CalculatorEvent.Paste(text))
//                        Toast.makeText("Вставлено", Toast.LENGTH_SHORT).show()
                    }
                }
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
    fontSize: TextUnit = 36.sp,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, // Передаем клик напрямую
        modifier = modifier
            .aspectRatio(1f) // Все кнопки квадратные
            .combinedClickable( // Добавляем Cюда ТОЛЬКО долгое нажатие, если оно есть
                enabled = onLongClick != null,
                onLongClick = onLongClick,
                onClick = onClick // Передаем основной клик и сюда тоже, чтобы сохранить ripple-эффект
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
