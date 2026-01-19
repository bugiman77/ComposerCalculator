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
import com.example.composercalculator.view.components.general.settings.SettingsSelectionRow
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun Appearance(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel
) {

    val showIconButton = viewModelSettings.showIconButton.collectAsState()
    val bottomSpacer = viewModelSettings.bottomSpacer.collectAsState()
    val showPlaceholderInput = viewModelSettings.showPlaceholderInput.collectAsState()

    SettingsGroup(title = "Внешний вид") {

        SettingsSelectionRow(
            title = "Кнопки/Иконки",
            subtitle = "Вместо кнопок-иконок будут отображаться кнопки с текстом",
            option1Text = "Кнопки",
            option2Text = "Иконки",
            selectedOption = if (showIconButton.value) 1 else 0,
            onClick1 = { viewModelSettings.switchIconButton(switch = false) },
            onClick2 = { viewModelSettings.switchIconButton(switch = true) }
        )

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Плейсхолдер",
            subtitle = "Отображать плейсхолдер в пустом поле ввода выражения",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = showPlaceholderInput.value,
                onCheckedChange = { viewModelSettings.toggleShowPlaceholderInput(enabled = it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

/*        SettingsRow(
            title = "Отступ снизу",
            subtitle = "Отступ между элементами интерфейса и нижним краем экрана",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Slider(
                value = bottomSpacer.value.toFloat(),
                onValueChangeFinished = { viewModelSettings.onBottomChangeChange(spacer = bottomSpacer.value) },
                onValueChange = { viewModelSettings.onBottomChangeChange(spacer = it.toInt()) },
                valueRange = 0f..30f,
                steps = 30,
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Orange,
                    inactiveTrackColor = Color.Gray
                ),
                modifier = Modifier.weight(weight = 1f)
            )
        }*/

    }
}