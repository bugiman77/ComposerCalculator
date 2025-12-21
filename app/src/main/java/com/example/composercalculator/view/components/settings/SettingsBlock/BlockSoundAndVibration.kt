package com.example.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.view.components.calculation.SettingsGroup
import com.example.composercalculator.view.components.calculation.SettingsRow
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun SoundAndVibration(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel,
) {
    SettingsGroup(title = "Звук и вибрация") {
        SettingsRow(
            title = "Звук",
            subtitle = "Воспроизводить звук при нажатии кнопок",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = false,
                onCheckedChange = { },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Orange,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Вибрация",
            subtitle = "Воспроизводить вибрацию при нажатии кнопок",
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = false,
                onCheckedChange = { },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Orange,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }
    }
}