package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import android.R.attr.checked
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
fun AppTheme(
    modifier: Modifier = Modifier,
    settingsModel: SettingModel,
    viewModelSettings: SettingsViewModel,
    onNavigateToCreateThemes: () -> Unit,
    onNavigateToViewThemes: () -> Unit,
) {

    SettingsGroup(title = "Тема приложения") {
        SettingsRow(
            title = "Системная тема",
            subtitle = "Использовать тему операционной системы",
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
                        !settingsModel.isSystemTheme
                    }
            ) {
                Switch(
                    settingsModel.isSystemTheme,
                    onCheckedChange = { enabled ->
                        viewModelSettings.updateSettings { it.copy(isSystemTheme = enabled) }
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
        }

        if (!settingsModel.isSystemTheme) {
            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Тёмная тема",
                subtitle = "Принудительное включение темного оформления",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                val interactionSourceIsDarkTheme = rememberSaveable { MutableInteractionSource() }
                val isPressedIsDarkTheme by interactionSourceIsDarkTheme.collectIsPressedAsState()
                val scale by animateFloatAsState(
                    targetValue = if (isPressedIsDarkTheme) 1.13f else 1f,
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
                            interactionSource = interactionSourceIsDarkTheme,
                            indication = null
                        ) {
                            !settingsModel.isDarkTheme
                        }
                ) {
                    Switch(
                        settingsModel.isDarkTheme,
                        onCheckedChange = { enabled ->
                            viewModelSettings.updateSettings { it.copy(isDarkTheme = enabled) }
                        },
                        interactionSource = interactionSourceIsDarkTheme,
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
                    checked = settingsModel.isDarkTheme,
                    onCheckedChange = { enabled ->
                        viewModelSettings.updateSettings { it.copy(isDarkTheme = enabled) }
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

        /*Button(
            onClick = { onNavigateToViewThemes() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(color = 0xFF4F703D),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(text = "Темы приложения")
        }*/

        val interactionSourceThemes = rememberSaveable {
            MutableInteractionSource()
        }

        val isPressedThemes by interactionSourceThemes.collectIsPressedAsState()

        val textScaleThemes by animateFloatAsState(
            targetValue = if (isPressedThemes) 1.05f else 1f,
            animationSpec = spring(
                dampingRatio = 0.5f,
                stiffness = 1000f
            ),
            label = "textScale"
        )

        Button(
            onClick = onNavigateToViewThemes,
            interactionSource = interactionSourceThemes,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .iosActionButtonAnimation(interactionSourceThemes),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4F703D),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(
                text = "Темы приложения",
                modifier = Modifier.graphicsLayer {
                    scaleX = textScaleThemes
                    scaleY = textScaleThemes
                }
            )
        }

        /*Button(
            onClick = { onNavigateToCreateThemes() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(color = 0xFF007AFF),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(text = "Создать тему")
        }*/

        val interactionSourceCreate = rememberSaveable {
            MutableInteractionSource()
        }

        val isPressedCreate by interactionSourceCreate.collectIsPressedAsState()

        val textScaleCreate by animateFloatAsState(
            targetValue = if (isPressedCreate) 1.05f else 1f,
            animationSpec = spring(
                dampingRatio = 0.5f,
                stiffness = 1000f
            ),
            label = "textScale"
        )

        Button(
            onClick = onNavigateToCreateThemes,
            interactionSource = interactionSourceCreate,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .iosActionButtonAnimation(interactionSourceCreate),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007AFF),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(
                text = "Создать тему",
                modifier = Modifier.graphicsLayer {
                    scaleX = textScaleCreate
                    scaleY = textScaleCreate
                }
            )
        }

    }
}

@Composable
fun Modifier.iosActionButtonAnimation(
    interactionSource: MutableInteractionSource
): Modifier {

    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.03f else 1f,
        animationSpec = spring(
            dampingRatio = 0.55f,
            stiffness = 1000f
        ),
        label = "buttonScale"
    )

    return graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}