package com.bugiman.composercalculator.view.components.general.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugiman.composercalculator.ui.theme.iOSGreen

@Composable
fun SettingsSelectionRow(
    title: String,
    subtitle: String,
    option1Text: String,
    option2Text: String,
    onClick1: () -> Unit,
    onClick2: () -> Unit,
    selectedOption: Int, // 0 для первого, 1 для второго
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Заголовок и подзаголовок в стиле ваших SettingsRow
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(text = title, color = Color.White, fontSize = 16.sp)
            Text(text = subtitle, color = Color.Gray, fontSize = 13.sp, lineHeight = 18.sp)
        }

        Spacer(modifier = Modifier.height(height = 12.dp))

        // Блок выбора вариантов
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {
            SettingsRadioButton(
                text = option1Text,
                isSelected = selectedOption == 0,
                onClick = { onClick1() },
                modifier = Modifier.weight(weight = 1f)
            )
            SettingsRadioButton(
                text = option2Text,
                isSelected = selectedOption == 1,
                onClick = { onClick2() },
                modifier = Modifier.weight(weight = 1f)
            )
        }
    }
}

@Composable
private fun SettingsRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(Color(color = 0xFF1C1C1E))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 14.sp,
            maxLines = 1
        )

        // Индикатор-галочка
        Box(
            modifier = Modifier
                .size(size = 22.dp)
                .border(
                    width = 1.dp,
                    color = if (isSelected) iOSGreen else Color.Gray,
                    shape = CircleShape
                )
                .background(
                    color = if (isSelected) iOSGreen else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(size = 16.dp)
                )
            }
        }
    }
}
