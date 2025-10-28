package com.example.composercalculator.calculator

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.composercalculator.R
import com.example.composercalculator.ui.theme.DarkGray
import com.example.composercalculator.ui.theme.LightGray
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
    val context = LocalContext.current

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

                    IconButton(onClick = onNavigateToSettings) { // <-- Сделали кликабельной
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
                        Toast.makeText(context, "Скопировано: $textToCopy", Toast.LENGTH_SHORT)
                            .show()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    history: List<String>, // Список с историей вычислений
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF2C2C2E), // Цвет фона в стиле iOS
        dragHandle = {
            // "Ручка" для перетаскивания вверху
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "История",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // LazyColumn используется для отображения списков, особенно длинных
            LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) {
                items(history) { calculation ->
                    Text(
                        text = calculation,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.5f))
                }
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
            fontSize = 85.sp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.27f)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
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

@Composable
fun StyledDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    // Состояние для переключателя
    val switchState = remember { mutableStateOf(true) }
    val density = LocalDensity.current

    // Используем Popup для полного контроля над стилем
    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = onDismissRequest,
        offset = with(density) {
            IntOffset(x = 0.dp.roundToPx(), y = -100.dp.roundToPx())
        }
    ) {
        // Внешний вид меню
        Column(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                .background(Color(0xFF2C2C2E))
                .padding(vertical = 8.dp)
                .width(220.dp)
        ) {
            StyledMenuItem(text = "Инженерный") {
                onDismissRequest()
            }
            StyledMenuItem(text = "Научный") {
                onDismissRequest()
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness, color = Color.Gray.copy(alpha = 0.5f)
            )

            StyledMenuItemWithSwitch(
                text = "Тёмная тема",
                checked = switchState.value,
                onCheckedChange = { switchState.value = it }
            )
        }
    }
}

@Composable
private fun StyledMenuItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp), // Увеличенные отступы
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp // Увеличенный шрифт
        )
    }
}

// Кастомный пункт меню с переключателем
@Composable
private fun StyledMenuItemWithSwitch(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors( // Стилизуем переключатель
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF34C759), // Зеленый цвет iOS
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}
