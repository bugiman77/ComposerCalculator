package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bugiman.composercalculator.view.components.calculation.SettingsGroup
import com.bugiman.composercalculator.view.components.calculation.SettingsRow

@Composable
fun App(
    modifier: Modifier = Modifier,
    onNavigateToAbout: () -> Unit
) {

    SettingsGroup(title = "Приложение") {
        SettingsRow(
            title = "О приложении",
            subtitle = "Информация о приложении",
            modifier = modifier
                .clickable { onNavigateToAbout() }
                .padding(vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Перейти",
                tint = Color.Gray
            )
        }
    }

}