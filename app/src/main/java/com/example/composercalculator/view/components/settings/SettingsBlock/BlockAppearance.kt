package com.example.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
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
import com.example.composercalculator.view.components.calculation.SettingsGroup
import com.example.composercalculator.view.components.calculation.SettingsRow
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun Appearance(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel
) {

    val showIconButton = viewModelSettings.showIconButton.collectAsState()

    SettingsGroup(title = "Внешний вид") {
        SettingsRow(
            title = "Отображать иконки",
            subtitle = "Вместо кнопок-иконок будут отображаться кнопки с текстом",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = showIconButton.value,
                onCheckedChange = { viewModelSettings.switchIconButton(switch = it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Orange,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }

        SettingsRow(
            title = "Отступ снизу",
            subtitle = "Отступ между элементами интерфейса и нижним краем экрана",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Slider(
//                value = displayFontSize.value,
                value = 0f,
//                onValueChangeFinished = { viewModelSettings.onFontSizeChange(displayFontSize.value) },
                onValueChangeFinished = {  },
//                onValueChange = { viewModelSettings.onFontSizeChange(it) },
                onValueChange = {  },
                valueRange = 40f..120f,
                steps = 7, // 8 позиций
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Orange,
                    inactiveTrackColor = Color.Gray
                ),
                modifier = Modifier.weight(weight = 1f)
            )
        }

    }
}