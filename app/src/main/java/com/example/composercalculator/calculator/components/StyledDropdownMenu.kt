package com.example.composercalculator.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun StyledDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit
) {
    // Состояние для переключателя
    val switchState = remember { mutableStateOf(true) }
    val density = LocalDensity.current

    // Используем Popup для полного контроля над стилем
    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = onDismissRequest,
        offset = with(density) {
            IntOffset(x = 0.dp.roundToPx(), y = -100.dp.roundToPx())
        }
    ) {
        Column(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                .background(Color(0xFF2C2C2E))
                .padding(vertical = 8.dp)
                .width(220.dp)
        ) {
            StyledMenuItem(text = "Инженерный") {
                onDismissRequest()
            }
            StyledMenuItem(text = "Научный") {
                onDismissRequest()
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness, color = Color.Gray.copy(alpha = 0.5f)
            )

            StyledMenuItemWithSwitch(
                text = "Тёмная тема",
                checked = switchState.value,
                onCheckedChange = { switchState.value = it }
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
    onCheckedChange: (Boolean) -> Unit
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
            colors = SwitchDefaults.colors( // Стилизуем переключатель
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF34C759), // Зеленый цвет iOS
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f),
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}