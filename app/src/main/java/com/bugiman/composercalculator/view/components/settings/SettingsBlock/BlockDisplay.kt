package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.ui.theme.Orange
import com.bugiman.composercalculator.ui.theme.iOSGray
import com.bugiman.composercalculator.ui.theme.iOSGreen
import com.bugiman.composercalculator.view.components.calculation.SettingsGroup
import com.bugiman.composercalculator.view.components.calculation.SettingsRow
import com.bugiman.domain.models.settings.SettingModel

@Composable
fun Display(
    modifier: Modifier = Modifier,
    settingsModel: SettingModel,
    viewModelSettings: SettingsViewModel,
) {

    SettingsGroup(title = "Дисплей") {
        // Настройка размера шрифта
        SettingsRow(
            title = "Системный размер шрифта",
            subtitle = "Использовать размер шрифта операционной системы",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = settingsModel.isSystemFontSize,
                onCheckedChange = { enabled ->
                    viewModelSettings.updateSettings { it.copy(isSystemFontSize = enabled) }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        if (!settingsModel.isSystemFontSize) {

            /*HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Размер шрифта",
                subtitle = "Настройка изменения размера шрифта в приложении"
            ) {
                Slider(
                    value = settingsModel.,
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
            }*/
        }

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Не отключать экран",
            subtitle = "При использовании приложения экран не будет отключаться при долгих паузах",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = settingsModel.isKeepScreenOn,
                onCheckedChange = { enabled ->
                    viewModelSettings.updateSettings { it.copy(isKeepScreenOn = enabled) }
                },
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