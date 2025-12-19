package com.example.composercalculator.view.components.calculation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Группа настроек в стиле iOS (блок с закругленными углами).
 * @param title Заголовок группы (отображается над блоком).
 * @param content Содержимое группы (обычно несколько SettingsRow).
 */
@Composable
fun SettingsGroup(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 32.dp))
                .background(Color(color = 0xFF2C2C2E))
        ) {
            content()
        }
    }
}

/**
 * Ряд в группе настроек.
 * @param title Основной текст.
 * @param subtitle Дополнительный текст под основным.
 * @param modifier Модификатор для настройки отступов.
 * @param content Слот для вставки управляющего элемента (Switch, Slider, иконка).
 */
@Composable
fun SettingsRow(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(weight = 1f, fill = false)) {
            Text(text = title, color = Color.White, fontSize = 17.sp)
            if (subtitle != null) {
                Text(text = subtitle, color = Color.Gray, fontSize = 14.sp)
            }
        }
        content()
    }
}