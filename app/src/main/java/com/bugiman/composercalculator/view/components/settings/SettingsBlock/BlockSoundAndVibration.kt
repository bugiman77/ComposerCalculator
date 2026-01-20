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
import com.bugiman.composercalculator.viewmodel.SettingsViewModel

@Composable
fun SoundAndVibration(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel,
) {

    val isPlaySound = viewModelSettings.playSound.collectAsState()
    val isPlayVibration = viewModelSettings.playVibration.collectAsState()

    SettingsGroup(title = "Отклик") {

//        if (mode != SoundMode.SILENT || mode != SoundMode.VIBRATE) {
        SettingsRow(
            title = "Звук",
            subtitle = "Воспроизводить звук при нажатии кнопок",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = isPlaySound.value,
                onCheckedChange = { viewModelSettings.onClickPlaySound(isPlay = it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )
//            }
        }

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Вибрация",
            subtitle = "Воспроизводить вибрацию при нажатии кнопок",
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = isPlayVibration.value,
                onCheckedChange = { viewModelSettings.onClickPlayVibration(isPlay = it) },
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