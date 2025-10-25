package com.example.composercalculator.calculator

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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

    Box(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {

            Row {
                Icon(
                    modifier = Modifier
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

            var fontSize by remember { mutableStateOf(80.sp) }
            val minFontSize = 28.sp // Минимальный размер шрифта
            val maxLinesBeforeWrap = Int.MAX_VALUE // Сколько строк разрешить после достижения minFontSize

            val scrollState = rememberScrollState()

            LaunchedEffect(uiState.displayText) {
                scrollState.animateScrollTo(scrollState.maxValue)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f) // Задаем высоту для контейнера
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.BottomEnd // Привязываем контент к нижнему правому углу
            ) {
                Text(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .combinedClickable(
                            onClick = {}, // Оставляем пустым
                            onLongClick = {
                                // При долгом нажатии копируем текст
                                clipboardManager.setText(
                                    AnnotatedString(
                                        uiState.displayText
                                    )
                                )
                                // Показываем уведомление
                                Toast
                                    .makeText(
                                        context,
                                        "Скопировано: ${uiState.displayText}",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        ),
                    text = uiState.displayText,
                    color = Color.White,
                    textAlign = TextAlign.End,
                    fontSize = fontSize,
                    // Если шрифт минимальный, разрешаем перенос, иначе — только одна строка
                    maxLines = if (fontSize <= minFontSize) maxLinesBeforeWrap else 1,
                    softWrap = true, // Включаем мягкий перенос
                    onTextLayout = { textLayoutResult ->
                        // Этот колбэк вызывается после того, как текст был измерен
                        if (textLayoutResult.hasVisualOverflow) {
                            // Если текст "вылезает" за границы (в ширину или высоту)...
                            if (fontSize > minFontSize) {
                                // ...и мы еще не достигли минимального размера, уменьшаем шрифт
                                fontSize *= 0.9f // Уменьшаем на 10%
                            }
                        } else {
                            // Если текст помещается, но можно увеличить шрифт (опционально)
                            // Можно добавить логику для увеличения шрифта, если текст удаляется,
                            // но для простоты пока оставим сброс до 80.sp при очистке (в ViewModel)
                        }
                    }
                )
            }

            // Сетка кнопок
            CalculatorButtonGrid(
                uiState = uiState,
                onEvent = onEvent,
                resetFontSize = { fontSize = 80.sp }
            )
        }
    }
}

@Composable
private fun CalculatorButtonGrid(
    uiState: CalculatorState,
    onEvent: (CalculatorEvent) -> Unit,
    resetFontSize: () -> Unit
) {
    // Убрали лишний Box, используем Column напрямую
    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 24.dp), // Отступы по бокам и снизу
        verticalArrangement = Arrangement.spacedBy(8.dp) // Расстояние между рядами
    ) {

        // --- Ряд 1: AC, +/-, %, ÷ ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Расстояние между кнопками
        ) {
            BtnCalculation(
                text = "AC",
                color = LightGray,
                modifier = Modifier.weight(1f),
                fontSize = 30.sp
            ) {
                onEvent(CalculatorEvent.Clear)
                resetFontSize()
            }
            BtnCalculation(
                text = "+/-",
                color = LightGray,
                modifier = Modifier.weight(1f),
                fontSize = 30.sp
            ) { /* onEvent */ }
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
            BtnCalculation(text = "7", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.NumberClick("7")
                )
            }
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
            ) { onEvent(CalculatorEvent.OperationClick("+")) }
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
            BtnCalculation(text = ",", color = DarkGray, modifier = Modifier.weight(1f)) {
                onEvent(
                    CalculatorEvent.DecimalClick
                )
            }
            BtnCalculation(
                text = "=",
                color = Orange,
                modifier = Modifier.weight(1f),
                fontSize = 40.sp
            ) { onEvent(CalculatorEvent.Calculate) }
        }
    }
}

@Composable
private fun BtnCalculation(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit = 36.sp,
    iconRes: Int? = null, // ID ресурса иконки
    isZero: Boolean = false,
    isToggle: Boolean = false, // Наша кнопка AC/C
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit
) {
    // Для кнопки "0" устанавливаем aspectRatio 2:1, для остальных - 1:1
    val aspectRatio = 1f

    Button(
        onClick = onClick,
        modifier = modifier.aspectRatio(aspectRatio), // Используем вычисленное соотношение
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(0.dp) // Убираем внутренние отступы, чтобы текст лучше центрировался
    ) {
        // Для кнопки "0" текст нужно выравнивать по левому краю внутри кнопки
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp),
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
