package com.bugiman.composercalculator.view.components.calculation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.domain.models.settings.SettingModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayArea(
    viewModelCalculation: CalculatorViewModel,
    settingModel: SettingModel,
) {
    var fontSize by remember { mutableStateOf(value = 80.sp) }
    val minFontSize = 40.sp
    val scrollState = rememberScrollState()
    
    // ✅ Получаем expression из ViewModel
    val displayText by viewModelCalculation.expression.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = displayText) {
        scrollState.animateScrollTo(scrollState.maxValue)
        // Сбрасываем размер шрифта при коротком тексте
        if (displayText.length < 10) fontSize = 85.sp
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.29f)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
        contentAlignment = Alignment.BottomEnd,
    ) {
        val containerWidth = constraints.maxWidth

        // ✅ Плейсхолдер "0" когда поле пустое и включен в настройках
        if (displayText.isEmpty() && settingModel.isShowPlaceholderInput) {
            Text(
                text = "0",
                color = Color.White.copy(alpha = 0.4f),
                textAlign = TextAlign.End,
                fontSize = fontSize,
            )
        }

        // ✅ Основной текст выражения
        Text(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .combinedClickable(
                    onClick = {},
                    onLongClick = { viewModelCalculation.removeLast() },  // ✅ Долгий клик для удаления
                    hapticFeedbackEnabled = false,
                ),
            text = displayText.ifEmpty { "0" },  // ✅ Показываем "0" если пусто
            color = Color.White,
            textAlign = TextAlign.End,
            fontSize = fontSize,
            maxLines = 1,
            softWrap = false,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.size.width > containerWidth && fontSize > minFontSize) {
                    fontSize *= 0.95f
                }
            }
        )
    }
}
