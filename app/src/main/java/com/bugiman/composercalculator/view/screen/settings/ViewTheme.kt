package com.bugiman.composercalculator.view.screen.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.bugiman.composercalculator.data.local.db.entity.BuiltInThemes
//import com.bugiman.composercalculator.data.local.db.entity.CustomThemes
import kotlin.collections.isNotEmpty

@Composable
fun ViewTheme(
    builtInThemes: List<BuiltInThemes>,
    userThemes: List<CustomThemes>,
    selectedThemeId: Long?,
//    onThemeSelected: (AppTheme) -> Unit
) {

    Scaffold(
        containerColor = Color(0xFF161616)
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {

            // --- ВСТРОЕННЫЕ ТЕМЫ ---
            item {
                SectionTitle("Встроенные темы")
            }

            items(builtInThemes) { theme ->
                ThemeCard(
                    theme = theme,
                    isSelected = theme.id == selectedThemeId,
                    onClick = { onThemeSelected(theme) }
                )
            }

            // --- ПОЛЬЗОВАТЕЛЬСКИЕ ТЕМЫ ---
            if (userThemes.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    SectionTitle("Пользовательские темы")
                }

                items(userThemes) { theme ->
                    ThemeCard(
                        theme = theme,
                        isSelected = theme.id == selectedThemeId,
                        onClick = { onThemeSelected(theme) }
                    )
                }
            }
        }
    }

}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun ThemeCard(
//    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) BorderStroke(2.dp, Color.White) else null,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {


            Text(
                text = theme.name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                MiniButton("7", theme.digitButtonColor, theme.digitTextColor)
//                MiniButton("+", theme.operationButtonColor, theme.operationTextColor)
//                MiniButton("AC", theme.deleteButtonColor, theme.deleteTextColor)
//                MiniButton("=", theme.equalButtonColor, theme.equalTextColor)
            }
        }
    }
}

@Composable
private fun MiniButton(
    text: String,
    buttonColorLong: Long,
    textColorLong: Long
) {
    val buttonColor = Color(buttonColorLong)
    val textColor = Color(textColorLong)

    Box(
        modifier = Modifier
            .size(50.dp)
            .background(
                buttonColor,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}