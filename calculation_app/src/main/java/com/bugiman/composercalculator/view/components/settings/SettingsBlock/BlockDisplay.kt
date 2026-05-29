package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
        /*SettingsRow(
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
        }*/

        /*if (!settingsModel.isSystemFontSize) {

            HorizoalDivider(color = Color(color = 0xFF3A3A3C))

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
            }
        }*/

//        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Не отключать экран",
            subtitle = "При использовании приложения экран не будет отключаться при долгих паузах",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            val interactionSource = remember { MutableInteractionSource() }
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
                        !settingsModel.isKeepScreenOn
                    }
            ) {
                Switch(
                    settingsModel.isKeepScreenOn,
                    onCheckedChange = { enabled ->
                        viewModelSettings.updateSettings { it.copy(isKeepScreenOn = enabled) }
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
            )*/
        }

    }

}