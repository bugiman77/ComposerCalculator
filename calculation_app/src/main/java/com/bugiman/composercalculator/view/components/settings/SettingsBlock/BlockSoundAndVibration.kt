package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.ui.theme.iOSGray
import com.bugiman.composercalculator.ui.theme.iOSGreen
import com.bugiman.composercalculator.view.components.calculation.SettingsGroup
import com.bugiman.composercalculator.view.components.calculation.SettingsRow
import com.bugiman.domain.models.settings.SettingModel

@Composable
fun SoundAndVibration(
    modifier: Modifier = Modifier,
    settingsModel: SettingModel,
    viewModelSettings: SettingsViewModel,
) {

    SettingsGroup(title = "Отклик") {

        SettingsRow(
            title = "Звук",
            subtitle = "Воспроизводить звук при нажатии кнопок",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            val interactionSource = rememberSaveable { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 1.13f else 1f,
                animationSpec = spring(
                    dampingRatio = 0.45f,
                    stiffness = 900f
                ),
                label = "switchScale"
            )

            Box(
                modifier = modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        !settingsModel.isPlaySound
                    }
            ) {
                Switch(
                    settingsModel.isPlaySound,
                    onCheckedChange = { enabled ->
                        viewModelSettings.updateSettings { it.copy(isPlaySound = enabled) }
                    },
                    interactionSource = interactionSource,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }
            /*Switch(
                checked = settingsModel.isPlaySound,
                onCheckedChange = { enabled ->
                    viewModelSettings.updateSettings { it.copy(isPlaySound = enabled) }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )*/
        }

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Вибрация",
            subtitle = "Воспроизводить вибрацию при нажатии кнопок",
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            val interactionSource = rememberSaveable { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 1.13f else 1f,
                animationSpec = spring(
                    dampingRatio = 0.45f,
                    stiffness = 900f
                ),
                label = "switchScale"
            )

            Box(
                modifier = modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        !settingsModel.isPlayVibration
                    }
            ) {
                Switch(
                    settingsModel.isPlayVibration,
                    onCheckedChange = { enabled ->
                        viewModelSettings.updateSettings { it.copy(isPlayVibration = enabled) }
                    },
                    interactionSource = interactionSource,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }
            /*Switch(
                checked = settingsModel.isPlayVibration,
                onCheckedChange = { enabled ->
                    viewModelSettings.updateSettings { it.copy(isPlayVibration = enabled) }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )*/
        }

    }
}