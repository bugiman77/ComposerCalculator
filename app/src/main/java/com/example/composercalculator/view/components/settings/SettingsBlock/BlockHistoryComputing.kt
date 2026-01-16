package com.example.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.ui.theme.iOSGray
import com.example.composercalculator.ui.theme.iOSGreen
import com.example.composercalculator.view.components.calculation.SettingsGroup
import com.example.composercalculator.view.components.calculation.SettingsRow
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun HistoryComputing(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel
) {

    val isSwipeEnabled = viewModelSettings.isSwipeEnabled.collectAsState()
    val isNoteEnabled = viewModelSettings.isNoteEnabled.collectAsState()
    val showHistoryButton = viewModelSettings.showHistoryButton.collectAsState()
    val currentLayout = viewModelSettings.historyHeaderLayout.collectAsState()

    SettingsGroup(title = "История вычислений") {
        SettingsRow(
            title = "Кнопка истории",
            subtitle = "Отображать кнопку истории вычислений в левом верхнем углу главного экрана",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = showHistoryButton.value,
                onCheckedChange = { viewModelSettings.onShowHistoryChange(show = it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        SettingsSelectionRow(
            settingsViewModel = viewModelSettings,
            title = "Расположение кнопок",
            subtitle = "Выберите, с какой стороны будет находиться кнопка закрытия в истории",
            option1Text = "Слева",
            option2Text = "Справа",
            selectedOption = currentLayout.value,
        )

        if (showHistoryButton.value) {
            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Использовать свайп",
                subtitle = "Для удаления элемента истории можно включить использование свайпа",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = isSwipeEnabled.value,
                    onCheckedChange = { viewModelSettings.onSaveSwipeDeleteItem(isEnabled = it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Поле для заметки",
                subtitle = "Для пометок к вычисленному выражению",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = isNoteEnabled.value,
                    onCheckedChange = { viewModelSettings.onSaveNoteItem(isEnabled = it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            SettingsRow(
                title = "Удалять историю",
                subtitle = "При включении переключателя, история будет удаляться при закрытии приложения",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = false,
                    onCheckedChange = { },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            SettingsRow(
                title = "Последнее вычисление",
                subtitle = "Отображать последнее вычисление при запуске приложения",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = false,
                    onCheckedChange = { },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

        }
    }

}

@Composable
private fun SettingsSelectionRow(
    settingsViewModel: SettingsViewModel,
    title: String,
    subtitle: String,
    option1Text: String,
    option2Text: String,
    selectedOption: Int, // 0 для первого, 1 для второго
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Заголовок и подзаголовок в стиле ваших SettingsRow
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = title, color = Color.White, fontSize = 16.sp)
            Text(text = subtitle, color = Color.Gray, fontSize = 13.sp, lineHeight = 18.sp)
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        // Блок выбора вариантов
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {
            SettingsRadioButton(
                text = option1Text,
                isSelected = selectedOption == 0,
                onClick = { settingsViewModel.toggleHistoryHeaderLayout(layout = 0) },
                modifier = Modifier.weight(weight = 1f)
            )
            SettingsRadioButton(
                text = option2Text,
                isSelected = selectedOption == 1,
                onClick = { settingsViewModel.toggleHistoryHeaderLayout(layout = 1) },
                modifier = Modifier.weight(weight = 1f)
            )
        }
    }
}

@Composable
private fun SettingsRadioButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF1C1C1E))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 14.sp,
            maxLines = 1
        )

        // Индикатор-галочка
        Box(
            modifier = Modifier
                .size(22.dp)
                .border(
                    width = 1.dp,
                    color = if (isSelected) iOSGreen else Color.Gray,
                    shape = CircleShape
                )
                .background(
                    color = if (isSelected) iOSGreen else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

