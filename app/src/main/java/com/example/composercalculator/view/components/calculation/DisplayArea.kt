package com.example.composercalculator.view.components.calculation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.viewmodel.SettingsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DisplayArea(
    viewModelCalculation: CalculatorViewModel = viewModel(),
    viewModelSettings: SettingsViewModel = viewModel(),
    onPositioned: (Offset) -> Unit
) {
    var fontSize by remember { mutableStateOf(value = 80.sp) }
    val minFontSize = 45.sp
    val scrollState = rememberScrollState()
    val displayText = viewModelCalculation.expression.collectAsState().value
    var inputPosition by remember { mutableStateOf(value = Offset.Zero) }

    LaunchedEffect(key1 = displayText) {
        scrollState.animateScrollTo(scrollState.maxValue)
        // Сбрасываем размер шрифта при коротком тексте
        if (displayText.length < 10) {
            fontSize = 85.sp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.27f)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            )
            .onGloballyPositioned { coordinates ->
                // Передаем координаты всего контейнера или текста в родителя
                onPositioned(coordinates.positionInRoot())
            },
        contentAlignment = Alignment.BottomEnd
    ) {

        if (displayText.isEmpty() && viewModelSettings.showPlaceholderInput.collectAsState().value) {
            Text(
                text = "0",
                color = Color.White.copy(alpha = 0.4f),
                textAlign = TextAlign.End,
                fontSize = fontSize,
                maxLines = 1,
                softWrap = false
            )
        }

        Text(
            modifier = Modifier
                .verticalScroll(scrollState)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {},
                    hapticFeedbackEnabled = false,
                )
                .onGloballyPositioned { it.positionInRoot() },
            text = displayText,
            color = Color.White,
            textAlign = TextAlign.End,
            fontSize = fontSize,
            maxLines = 1,
            softWrap = false,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow && fontSize > minFontSize) {
                    fontSize *= 0.95f
                }
            }
        )

    }
}
