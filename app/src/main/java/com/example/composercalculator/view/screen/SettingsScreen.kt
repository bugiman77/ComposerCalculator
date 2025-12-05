package com.example.composercalculator.view.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    onNavigateBack: () -> Unit, // Лямбда для возврата на предыдущий экран
    onNavigateToAbout: () -> Unit,
) {

    val isDarkTheme = viewModel.isDarkTheme.collectAsState()
    val isSystemTheme = viewModel.isSystemTheme.collectAsState()
    val showHistoryButton = viewModel.showHistoryButton.collectAsState()
    val displayFontSize = viewModel.displayFontSize.collectAsState()
    val decimalFormat = viewModel.decimalFormat.collectAsState()
    val isSwipeEnabled = viewModel.isSwipeEnabled.collectAsState()
    val isNoteEnabled = viewModel.isNoteEnabled.collectAsState()
    val isSaveDataEnabled = viewModel.isSaveDataEnabled.collectAsState()

    Scaffold(
        containerColor = Color(0xFF161616), // Фон всего экрана
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Настройки", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    CustomBackButton(onClick = onNavigateBack)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SettingsGroup(title = "Тема приложения") {
                SettingsRow(
                    title = "Системная тема",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = isSystemTheme.value,
                        onCheckedChange = { viewModel.onSystemThemeChange(it) },
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
                    title = "Тёмная тема",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = isDarkTheme.value,
                        onCheckedChange = { viewModel.onDarkThemeChange(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Orange,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.Gray
                        )
                    )
                }

                Button(
                    onClick = { /* Handle click */ },
                    modifier = Modifier
                        .fillMaxWidth() // Кнопка на всю ширину
                        .padding(16.dp), // Отступы по краям
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF007AFF), // Синий цвет в стиле iOS
                        contentColor = Color.White // Белый текст
                    ),
                    shape = MaterialTheme.shapes.medium // Округлые углы
                ) {
                    Text(text = "Создать тему")
                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsGroup(title = "История вычислений") {
                SettingsRow(
                    title = "Кнопка истории",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = showHistoryButton.value,
                        onCheckedChange = { viewModel.onShowHistoryChange(it) },
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
                    title = "Использовать свайп",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = isSwipeEnabled.value,
                        onCheckedChange = { viewModel.onSaveSwipeDeleteItem(it) },
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
                    title = "Поле для заметки",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = isNoteEnabled.value,
                        onCheckedChange = { viewModel.onSaveNoteItem(isEnabled = it) },
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
            SettingsGroup(title = "Дисплей") {
                // Настройка размера шрифта
                SettingsRow(
                    title = "Размер шрифта",
                ) {
                    Slider(
                        value = displayFontSize.value,
                        onValueChangeFinished = { viewModel.onFontSizeChange(displayFontSize.value) },
                        onValueChange = { viewModel.onFontSizeChange(it) },
                        valueRange = 40f..120f, // от 40sp до 120sp
                        steps = 7, // 8 позиций
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
                        isSelected = decimalFormat.value == "1,234.56",
                        onClick = { viewModel.onDecimalFormatChange("1,234.56") }
                    )
                    SettingsRadioRow(
                        title = "1 234,56",
                        isSelected = decimalFormat.value == "1 234,56",
                        onClick = { viewModel.onDecimalFormatChange("1 234,56") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SettingsGroup(title = "Сохранение данных") {
                SettingsRow(
                    title = "Сохранять данные",
                    subtitle = "Настройки и история",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = isSaveDataEnabled.value,
                        onCheckedChange = { viewModel.onSaveDataChange(isEnabled = it) },
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

            SettingsGroup(title = "Приложение") {
                SettingsRow(
                    title = "О приложении",
                    modifier = Modifier
                        .clickable { onNavigateToAbout() }
                        .padding(vertical = 4.dp)
                ) {
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

/**
 * Кастомная круглая кнопка "Назад" в стиле приложения.
 * @param onClick Действие при нажатии.
 * @param modifier Модификатор для позиционирования.
 */
@Composable
fun CustomBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Создаем едва заметный вертикальный градиент для фона
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Orange.copy(alpha = 0.9f),
            Orange
        )
    )

    // Создаем границу, которая имитирует внутреннюю тень
    val borderBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Transparent
        )
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .padding(start = 16.dp)
            .size(40.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent // Фон кнопки будет задан через Modifier.background
        ),
        // Убираем стандартную тень
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        // Устанавливаем кастомную границу
        border = BorderStroke(1.dp, borderBrush),
        contentPadding = PaddingValues(0.dp)
    ) {
        // Оборачиваем Icon в Box с градиентным фоном
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Назад",
                tint = Color.White
            )
        }
    }
}

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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
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
