package com.example.composercalculator.features.settings.savingData.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
fun SavingData(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel,
) {

    val isSaveHistoryData = viewModelSettings.isSaveHistoryData.collectAsState()
    val isSaveSettingsData = viewModelSettings.isSaveSettingsData.collectAsState()

    SettingsGroup(title = "Сохранение данных") {
        SettingsRow(
            title = "Сохранить настройки",
            subtitle = "Сохранить настройки приложения в Google Drive",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = isSaveSettingsData.value,
                onCheckedChange = { viewModelSettings.onSaveSettingsDataChange(isEnabled = it) },
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
            title = "Сохранить историю",
            subtitle = "Сохранить историю вычислений в Google Drive",
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = isSaveHistoryData.value,
                onCheckedChange = { viewModelSettings.onSaveHistoryDataChange(isEnabled = it) },
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