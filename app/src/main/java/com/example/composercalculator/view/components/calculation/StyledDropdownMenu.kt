package com.example.composercalculator.view.components.calculation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.viewmodel.SettingsViewModel

@Composable
fun StyledDropdownMenu(
    expanded: Boolean,
    isEnableSwitchDarkMode: Boolean = true,
    viewModel: SettingsViewModel = viewModel(),
    onDismissRequest: () -> Unit,
    onOpenEngineeringMode: () -> Unit,
    onOpenScientificMode: () -> Unit,
    onOpenCurrencyConverter: () -> Unit,
    onOpenDistanceConverter: () -> Unit
) {

    val density = LocalDensity.current

    val isDarkTheme = viewModel.isDarkTheme.collectAsState()

    // Используем Popup для полного контроля над стилем
    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = onDismissRequest,
        offset = with(receiver = density) {
            IntOffset(x = 0.dp.roundToPx(), y = 0.dp.roundToPx())
        }
    ) {
        Column(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(size = 32.dp))
                .clip(shape = RoundedCornerShape(size = 20.dp))
                .background(Color(color = 0xFF2C2C2E))
                .padding(vertical = 8.dp)
                .width(width = 205.dp)
        ) {
            StyledMenuItem(
                text = "Инженерный",
                onClick = onOpenEngineeringMode
            )

            StyledMenuItem(
                text = "Научный",
                onClick = onOpenScientificMode
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness, color = Color.Gray.copy(alpha = 0.5f)
            )

            StyledMenuItem(
                text = "Конвертер валют",
                onClick = onOpenCurrencyConverter
            )

            StyledMenuItem(
                text = "Конвертер расстояния",
                onClick = onOpenDistanceConverter
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness, color = Color.Gray.copy(alpha = 0.5f)
            )

            StyledMenuItemWithSwitch(
                text = "Тёмная тема",
                checked = if (isEnableSwitchDarkMode) isDarkTheme.value else false,
                isEnableSwitchDarkMode = isEnableSwitchDarkMode,
                onCheckedChange = { viewModel.onDarkThemeChange(isDark = it) }
            )

        }
    }
}

@Composable
private fun StyledMenuItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp), // Увеличенные отступы
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

// Кастомный пункт меню с переключателем
@Composable
private fun StyledMenuItemWithSwitch(
    text: String,
    checked: Boolean,
    isEnableSwitchDarkMode: Boolean = true,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = isEnableSwitchDarkMode,
            colors = SwitchDefaults.colors( // Стилизуем переключатель
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(color = 0xFF34C759), // Зеленый цвет iOS
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}