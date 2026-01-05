package com.example.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
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
fun Animations(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel,
) {
    SettingsGroup(title = "Анимации") {
        SettingsRow(
            title = "Анимации",
            subtitle = "Использовать анимации",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = viewModelSettings.isAnimationAll.collectAsState().value,
                onCheckedChange = { viewModelSettings.isAnimationAllChange(enable = it) },
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