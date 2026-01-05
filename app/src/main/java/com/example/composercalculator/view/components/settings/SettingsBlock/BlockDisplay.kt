package com.example.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.ui.theme.iOSGray
import com.example.composercalculator.ui.theme.iOSGreen
import com.example.composercalculator.view.components.calculation.SettingsGroup
import com.example.composercalculator.view.components.calculation.SettingsRow
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun Display(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel,
) {

    val isSystemFontSize = viewModelSettings.systemFontSize.collectAsState()
    val displayFontSize = viewModelSettings.displayFontSize.collectAsState()

    SettingsGroup(title = "Дисплей") {
        // Настройка размера шрифта
        SettingsRow(
            title = "Системный размер шрифта",
            subtitle = "Использовать размер шрифта операционной системы",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = isSystemFontSize.value,
                onCheckedChange = { viewModelSettings.onSystemFontSizeChange(isEnable = it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        if (!isSystemFontSize.value) {

            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Размер шрифта",
                subtitle = "Настройка изменения размера шрифта в приложении"
            ) {
                Slider(
                    value = displayFontSize.value,
                    onValueChangeFinished = { viewModelSettings.onFontSizeChange(size = displayFontSize.value) },
                    onValueChange = { viewModelSettings.onFontSizeChange(size = it) },
                    valueRange = 12f..24f,
                    steps = 5,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Orange,
                        inactiveTrackColor = Color.Gray
                    ),
                    modifier = Modifier.weight(weight = 1f)
                )
            }
        }

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Отключать экран",
            subtitle = "При использовании приложения экран не будет отключаться при долгих паузах",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = false,
                onCheckedChange = {  },
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