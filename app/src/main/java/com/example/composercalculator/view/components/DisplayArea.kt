package com.example.composercalculator.view.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.DisplayArea(
    displayText: String,
    onCopy: (String) -> Unit
) {
    var fontSize by remember { mutableStateOf(80.sp) }
    val minFontSize = 28.sp
    val scrollState = rememberScrollState()

    LaunchedEffect(displayText) {
        scrollState.animateScrollTo(scrollState.maxValue)
        // Сбрасываем размер шрифта при коротком тексте
        if (displayText.length < 10) {
            fontSize = 85.sp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.27f)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text(
            modifier = Modifier
                .verticalScroll(scrollState)
                .combinedClickable(
                    onClick = {},
                    onLongClick = { onCopy(displayText) }
                ),
            text = displayText,
            color = Color.White,
            textAlign = TextAlign.End,
            fontSize = fontSize,
            maxLines = Int.MAX_VALUE,
            softWrap = true,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow && fontSize > minFontSize) {
                    fontSize *= 0.95f
                }
            }
        )
    }
}
