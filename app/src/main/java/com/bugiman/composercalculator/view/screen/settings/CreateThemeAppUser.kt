package com.bugiman.composercalculator.view.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugiman.composercalculator.view.components.general.settings.CustomTopBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateThemeAppUser(
    title: String,
    onNavigateBack: () -> Unit,
) {
    // 1. Состояния цветов (по умолчанию)
    var buttonColor by remember { mutableStateOf(value = Color(color = 0xFF4A90E2)) }
    var textColor by remember { mutableStateOf(value = Color.White) }

    // 2. Какой элемент сейчас редактируем
    val options = listOf("Кнопки", "Текст")
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(color = 0xFF161616),
        topBar = {
            CustomTopBar(screenTitle = title, onNavigateBack = onNavigateBack, onScrollToTop = {})
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Настройка темы",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            ButtonDigit()

            Spacer(modifier = Modifier.height(height = 16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(height = 16.dp))

            ButtonMathOperation()

            Spacer(modifier = Modifier.height(height = 16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(height = 16.dp))

            ButtonDelete()

            Spacer(modifier = Modifier.height(height = 16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(height = 16.dp))

            ButtonEqual()

            Spacer(modifier = Modifier.height(height = 32.dp))

            // --- КНОПКА СОХРАНЕНИЯ ---
            Button(
                onClick = {
                    // Конвертируем цвета в Long для записи в БД
                    val buttonColorLong = buttonColor.toArgb().toLong()
                    val textColorLong = textColor.toArgb().toLong()

                    // Вызов метода ViewModel для сохранения
                    // viewModelThemes.saveNewTheme(buttonColorLong, textColorLong)

                    // onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Text(text = "Сохранить тему", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun Color.toArgb(): Int {
    return (this.value shr 32).toInt()
}

@Composable
private fun ButtonDigit() {

    var buttonColor by remember { mutableStateOf(value = Color(color = 0xFF4A90E2)) }
    var textColor by remember { mutableStateOf(value = Color.White) }

    val options = listOf("Кнопка", "Текст")
    var selectedOption by remember { mutableStateOf(value = options[0]) }

    // --- ПРЕДПРОСМОТР ---
    Text(text = "Цифры", color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth())
    Text(
        text = "Предпросмотр",
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth()
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 120.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size = 70.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(size = 12.dp))
                .background(
                    buttonColor,
                    shape = RoundedCornerShape(size = 12.dp)
                ), // Состояние кнопки
            contentAlignment = Alignment.Center
        ) {
            Text(text = "7", color = textColor, fontSize = 28.sp) // Состояние текста
        }
    }

    Spacer(modifier = Modifier.height(height = 24.dp))

    // --- ВЫБОР РЕДАКТИРУЕМОГО ЭЛЕМЕНТА ---
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            Surface(
                modifier = Modifier.clickable { selectedOption = option },
                shape = CircleShape,
                color = if (isSelected) Color.White else Color(color = 0xFF2D2D2D)
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.Black else Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(height = 32.dp))

    // --- КРУГОВАЯ ПАЛИТРА (Слайдер оттенка) ---
    Text("Выберите оттенок для: $selectedOption", color = Color.Gray, fontSize = 14.sp)

    // Упрощенный выбор через Hue-слайдер (наиболее удобный для Compose)
    var hueValue by remember { mutableFloatStateOf(value = 0f) }

    Slider(
        value = hueValue,
        onValueChange = {
            hueValue = it
            val newColor = Color.hsv(hue = it, saturation = 0.7f, value = 0.9f)
            when (selectedOption) {
                "Кнопка" -> buttonColor = newColor
                "Текст" -> textColor = newColor
            }
        },
        valueRange = 0f..360f,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent
        )
    )

    // Визуальный градиент под слайдером
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 10.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Yellow,
                        Color.Green,
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta,
                        Color.Red
                    )
                )
            )
    )

}

@Composable
private fun ButtonMathOperation() {

    var buttonColor by remember { mutableStateOf(value = Color(color = 0xFF4A90E2)) }
    var textColor by remember { mutableStateOf(value = Color.White) }

    val options = listOf("Кнопка", "Текст")
    var selectedOption by remember { mutableStateOf(value = options[0]) }

    // --- ПРЕДПРОСМОТР ---
    Text(text = "Математические операции", color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth())
    Text(
        text = "Предпросмотр",
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth()
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 120.dp)
            .clip(shape = RoundedCornerShape(size = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size = 70.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(size = 12.dp))
                .background(
                    buttonColor,
                    shape = RoundedCornerShape(size = 12.dp)
                ), // Состояние кнопки
            contentAlignment = Alignment.Center
        ) {
            Text(text = "+", color = textColor, fontSize = 28.sp) // Состояние текста
        }
    }

    Spacer(modifier = Modifier.height(height = 24.dp))

    // --- ВЫБОР РЕДАКТИРУЕМОГО ЭЛЕМЕНТА ---
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            Surface(
                modifier = Modifier.clickable { selectedOption = option },
                shape = CircleShape,
                color = if (isSelected) Color.White else Color(color = 0xFF2D2D2D)
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.Black else Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(height = 32.dp))

    // --- КРУГОВАЯ ПАЛИТРА (Слайдер оттенка) ---
    Text("Выберите оттенок для: $selectedOption", color = Color.Gray, fontSize = 14.sp)

    // Упрощенный выбор через Hue-слайдер (наиболее удобный для Compose)
    var hueValue by remember { mutableFloatStateOf(value = 0f) }

    Slider(
        value = hueValue,
        onValueChange = {
            hueValue = it
            val newColor = Color.hsv(hue = it, saturation = 0.7f, value = 0.9f)
            when (selectedOption) {
                "Кнопка" -> buttonColor = newColor
                "Текст" -> textColor = newColor
            }
        },
        valueRange = 0f..360f,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent
        )
    )

    // Визуальный градиент под слайдером
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 10.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Yellow,
                        Color.Green,
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta,
                        Color.Red
                    )
                )
            )
    )

}

@Composable
private fun ButtonDelete() {

    var buttonColor by remember { mutableStateOf(value = Color(color = 0xFF4A90E2)) }
    var textColor by remember { mutableStateOf(value = Color.White) }

    val options = listOf("Кнопка", "Текст")
    var selectedOption by remember { mutableStateOf(value = options[0]) }

    // --- ПРЕДПРОСМОТР ---
    Text(text = "Кнопка удалить", color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth())
    Text(
        text = "Предпросмотр",
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth()
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 120.dp)
            .clip(shape = RoundedCornerShape(size = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size = 70.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(size = 12.dp))
                .background(
                    buttonColor,
                    shape = RoundedCornerShape(size = 12.dp)
                ), // Состояние кнопки
            contentAlignment = Alignment.Center
        ) {
            Text(text = "AC", color = textColor, fontSize = 28.sp) // Состояние текста
        }
    }

    Spacer(modifier = Modifier.height(height = 24.dp))

    // --- ВЫБОР РЕДАКТИРУЕМОГО ЭЛЕМЕНТА ---
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            Surface(
                modifier = Modifier.clickable { selectedOption = option },
                shape = CircleShape,
                color = if (isSelected) Color.White else Color(color = 0xFF2D2D2D)
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.Black else Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(height = 32.dp))

    // --- КРУГОВАЯ ПАЛИТРА (Слайдер оттенка) ---
    Text("Выберите оттенок для: $selectedOption", color = Color.Gray, fontSize = 14.sp)

    // Упрощенный выбор через Hue-слайдер (наиболее удобный для Compose)
    var hueValue by remember { mutableFloatStateOf(value = 0f) }

    Slider(
        value = hueValue,
        onValueChange = {
            hueValue = it
            val newColor = Color.hsv(hue = it, saturation = 0.7f, value = 0.9f)
            when (selectedOption) {
                "Кнопка" -> buttonColor = newColor
                "Текст" -> textColor = newColor
            }
        },
        valueRange = 0f..360f,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent
        )
    )

    // Визуальный градиент под слайдером
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 10.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Yellow,
                        Color.Green,
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta,
                        Color.Red
                    )
                )
            )
    )

}

@Composable
private fun ButtonEqual() {

    var buttonColor by remember { mutableStateOf(value = Color(color = 0xFF4A90E2)) }
    var textColor by remember { mutableStateOf(value = Color.White) }

    val options = listOf("Кнопки", "Текст")
    var selectedOption by remember { mutableStateOf(value = options[0]) }

    // --- ПРЕДПРОСМОТР ---
    Text(text = "Кнопка вычисления", color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth())
    Text(
        text = "Предпросмотр",
        color = Color.Gray,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth()
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 120.dp)
            .clip(shape = RoundedCornerShape(size = 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size = 70.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(size = 12.dp))
                .background(
                    buttonColor,
                    shape = RoundedCornerShape(size = 12.dp)
                ), // Состояние кнопки
            contentAlignment = Alignment.Center
        ) {
            Text(text = "=", color = textColor, fontSize = 28.sp) // Состояние текста
        }
    }

    Spacer(modifier = Modifier.height(height = 24.dp))

    // --- ВЫБОР РЕДАКТИРУЕМОГО ЭЛЕМЕНТА ---
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            Surface(
                modifier = Modifier.clickable { selectedOption = option },
                shape = CircleShape,
                color = if (isSelected) Color.White else Color(color = 0xFF2D2D2D)
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.Black else Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 14.sp
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(height = 32.dp))

    // --- КРУГОВАЯ ПАЛИТРА (Слайдер оттенка) ---
    Text("Выберите оттенок для: $selectedOption", color = Color.Gray, fontSize = 14.sp)

    // Упрощенный выбор через Hue-слайдер (наиболее удобный для Compose)
    var hueValue by remember { mutableFloatStateOf(value = 0f) }

    Slider(
        value = hueValue,
        onValueChange = {
            hueValue = it
            val newColor = Color.hsv(hue = it, saturation = 0.7f, value = 0.9f)
            when (selectedOption) {
                "Кнопка" -> buttonColor = newColor
                "Текст" -> textColor = newColor
            }
        },
        valueRange = 0f..360f,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.Transparent,
            inactiveTrackColor = Color.Transparent
        )
    )

    // Визуальный градиент под слайдером
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 10.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.Red,
                        Color.Yellow,
                        Color.Green,
                        Color.Cyan,
                        Color.Blue,
                        Color.Magenta,
                        Color.Red
                    )
                )
            )
    )

}