package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bugiman.composercalculator.ui.theme.iOSGray
import com.bugiman.composercalculator.ui.theme.iOSGreen
import com.bugiman.composercalculator.view.components.calculation.SettingsGroup
import com.bugiman.composercalculator.view.components.calculation.SettingsRow
import com.bugiman.composercalculator.view.components.general.settings.SettingsSelectionRow
import com.bugiman.composercalculator.viewmodel.SettingsViewModel

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

    }
}