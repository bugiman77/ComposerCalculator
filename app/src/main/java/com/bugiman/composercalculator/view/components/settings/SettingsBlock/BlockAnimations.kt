package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
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

/*
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
}*/
