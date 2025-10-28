package com.example.composercalculator.view.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme
import com.example.composercalculator.ui.theme.Orange

// --- Основная Composable-функция экрана настроек ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit, // Лямбда для возврата на предыдущий экран
    onNavigateToAbout: () -> Unit,
    showHistoryButton: Boolean, // Текущее состояние переключателя
    onShowHistoryChange: (Boolean) -> Unit
) {
    // Локальные состояния для настроек. В реальном приложении их нужно будет
    // получать из ViewModel, которая работает с DataStore или SharedPreferences.
    var isDarkTheme by remember { mutableStateOf(true) }
    var displayFontSize by remember { mutableFloatStateOf(80f) }
    var decimalFormat by remember { mutableStateOf("1,234.56") }

    Scaffold(
        containerColor = Color(0xFF161616), // Фон всего экрана
        topBar = {
            CenterAlignedTopAppBar (
                title = { Text("Настройки", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Orange
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Добавляем прокрутку
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // --- Блок "Внешний вид" ---
            SettingsGroup(title = "ВНЕШНИЙ ВИД") {
                SettingsRow(
                    title = "Тёмная тема",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { isDarkTheme = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Orange,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.Gray
                        )
                    )
                }

                HorizontalDivider(color = Color(0xFF3A3A3C))

                SettingsRow(
                    title = "Кнопка истории",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = showHistoryButton, // Используем новый параметр
                        onCheckedChange = onShowHistoryChange, // Вызываем лямбду при изменении
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Orange,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.Gray
                        )
                    )
                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Блок "Дисплей" ---
            SettingsGroup(title = "ДИСПЛЕЙ") {
                // Настройка размера шрифта
                SettingsRow(
                    title = "Размер шрифта",
                    subtitle = "${displayFontSize.toInt()} sp"
                ) {
                    Slider(
                        value = displayFontSize,
                        onValueChange = { displayFontSize = it },
                        valueRange = 40f..120f, // от 40sp до 120sp
                        steps = 7, // 8 позиций (120-40)/10 = 8
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Orange,
                            inactiveTrackColor = Color.Gray
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                HorizontalDivider(color = Color(0xFF3A3A3C))

                // Настройка формата чисел
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = "Формат чисел",
                        color = Color.White,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                    SettingsRadioRow(
                        title = "1,234.56",
                        isSelected = decimalFormat == "1,234.56",
                        onClick = { decimalFormat = "1,234.56" }
                    )
                    SettingsRadioRow(
                        title = "1 234,56",
                        isSelected = decimalFormat == "1 234,56",
                        onClick = { decimalFormat = "1 234,56" }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- ДОБАВЛЯЕМ НОВЫЙ БЛОК ---
            SettingsGroup(title = "ОБЩЕЕ") {
                SettingsRow(
                    title = "О приложении",
                    // Этот modifier можно убрать, если не нужны особые отступы
                    modifier = Modifier
                        .clickable { onNavigateToAbout() } // <-- Вся строка становится кликабельной
                        .padding(vertical = 4.dp)
                ) {
                    // В качестве контента передаем иконку стрелки
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Перейти",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

// --- Вспомогательные Composable-функции для стилизации ---

/**
 * Группа настроек в стиле iOS (блок с закругленными углами).
 * @param title Заголовок группы (отображается над блоком).
 * @param content Содержимое группы (обычно несколько SettingsRow).
 */
@Composable
private fun SettingsGroup(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2C2C2E))
        ) {
            content()
        }
    }
}

/**
 * Ряд в группе настроек.
 * @param title Основной текст.
 * @param subtitle Дополнительный текст под основным.
 * @param modifier Модификатор для настройки отступов.
 * @param content Слот для вставки управляющего элемента (Switch, Slider, иконка).
 */
@Composable
private fun SettingsRow(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f, fill = false)) {
            Text(text = title, color = Color.White, fontSize = 17.sp)
            if (subtitle != null) {
                Text(text = subtitle, color = Color.Gray, fontSize = 14.sp)
            }
        }
        content()
    }
}

/**
 * Ряд с RadioButton для выбора опции.
 * @param title Текст опции.
 * @param isSelected Выбрана ли эта опция.
 * @param onClick Действие при нажатии.
 */
@Composable
private fun SettingsRadioRow(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, color = Color.White, fontSize = 17.sp)
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Orange)
        )
    }
}
