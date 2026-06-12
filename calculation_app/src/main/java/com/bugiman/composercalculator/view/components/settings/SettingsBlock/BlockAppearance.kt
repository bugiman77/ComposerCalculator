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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.bugiman.composercalculator.presentation.settings.viewmodel.SettingsViewModel
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
            subtitle = if (settingsModel.isShowIconButton) "Будут отображаться иконки в верхней части главного экрана"
            else "Будут отображаться кнопки в верхней части главного экрана",
            option1Text = "Кнопки",
            option2Text = "Иконки",
            selectedOption = if (settingsModel.isShowIconButton) 1 else 0,
            onClick1 = { viewModelSettings.updateSettings { it.copy(isShowIconButton = false) } },
            onClick2 = { viewModelSettings.updateSettings { it.copy(isShowIconButton = true) } }
        )

        HorizontalDivider(color = Color(color = 0xFF3A3A3C))

        SettingsRow(
            title = "Плейсхолдер",
            subtitle = "Отображать плейсхолдер в пустом поле ввода выражения",
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
                        !settingsModel.isShowPlaceholderInput
                    }
            ) {
                Switch(
                    settingsModel.isShowPlaceholderInput,
                    onCheckedChange = {
                        viewModelSettings.updateSettings { current ->
                            current.copy(isShowPlaceholderInput = it)
                        }
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
                checked = settingsModel.isShowPlaceholderInput,
                onCheckedChange = {
                    viewModelSettings.updateSettings { current ->
                        current.copy(isShowPlaceholderInput = it)
                    }
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