package com.example.composercalculator.view.components.settings.SettingsBlock

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composercalculator.ui.theme.iOSGray
import com.example.composercalculator.ui.theme.iOSGreen
import com.example.composercalculator.view.components.calculation.SettingsGroup
import com.example.composercalculator.view.components.calculation.SettingsRow
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun AppTheme(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel,
    onNavigateToCreateThemes: () -> Unit,
) {

    val isDarkTheme = viewModelSettings.isDarkTheme.collectAsState()
    val isSystemTheme = viewModelSettings.isSystemTheme.collectAsState()

    SettingsGroup(title = "Тема приложения") {
        SettingsRow(
            title = "Системная тема",
            subtitle = "Использовать тему Вашей операционной системы",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = isSystemTheme.value,
                onCheckedChange = { viewModelSettings.onSystemThemeChange(isSystem = it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        if (!isSystemTheme.value) {
            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Тёмная тема",
                subtitle = "Принудительное включение темного оформления",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = isDarkTheme.value,
                    onCheckedChange = { viewModelSettings.onDarkThemeChange(isDark = it) },
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

        Button(
            onClick = { onNavigateToCreateThemes() },
            modifier = Modifier
                .fillMaxWidth() // Кнопка на всю ширину
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp), // Отступы по краям
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(color = 0xFF007AFF), // Синий цвет в стиле iOS
                contentColor = Color.White // Белый текст
            ),
            shape = MaterialTheme.shapes.large // Округлые углы
        ) {
            Text(text = "Создать тему")
        }

    }
}