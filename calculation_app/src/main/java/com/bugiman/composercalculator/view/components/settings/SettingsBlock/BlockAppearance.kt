package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.ui.theme.iOSGray
import com.bugiman.composercalculator.ui.theme.iOSGreen
import com.bugiman.composercalculator.view.components.calculation.SettingsGroup
import com.bugiman.composercalculator.view.components.calculation.SettingsRow
import com.bugiman.composercalculator.view.components.general.settings.SettingsSelectionRow
import com.bugiman.domain.models.settings.SettingModel

@Composable
fun Appearance(
    modifier: Modifier = Modifier,
    settingsModel: SettingModel,
    viewModelSettings: SettingsViewModel
) {

    SettingsGroup(title = "Внешний вид") {

        SettingsSelectionRow(
            title = "Кнопки/Иконки",
            subtitle = if (settingsModel.isShowHistoryButton) "Будут отображаться иконки в верхней части главного экрана"
                        else "Будут отображаться кнопки в верхней части главного экрана",
            option1Text = "Кнопки",
            option2Text = "Иконки",
            selectedOption = if (settingsModel.isShowHistoryButton) 1 else 0,
            onClick1 = { viewModelSettings.updateSettings { it.copy(isShowIconButton = false) } },
            onClick2 = { viewModelSettings.updateSettings { it.copy(isShowIconButton = true) } }
        )

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Плейсхолдер",
            subtitle = "Отображать плейсхолдер в пустом поле ввода выражения",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = settingsModel.isShowPlaceholderInput,
                onCheckedChange = { viewModelSettings.updateSettings { current ->
                    current.copy(isShowPlaceholderInput = it)
                }},
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