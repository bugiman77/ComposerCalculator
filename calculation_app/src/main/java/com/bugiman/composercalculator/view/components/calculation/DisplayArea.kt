package com.bugiman.composercalculator.view.components.calculation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
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
    val expression by viewModelCalculation.expression.collectAsStateWithLifecycle()
    val cursorPosition by viewModelCalculation.cursorPosition.collectAsStateWithLifecycle()
    val selectionStart by viewModelCalculation.selectionStart.collectAsStateWithLifecycle()
    val selectionEnd by viewModelCalculation.selectionEnd.collectAsStateWithLifecycle()

    var fontSize by remember {
        mutableStateOf(85.sp)
    }

    val minFontSize = 45.sp

    val scrollState = rememberScrollState()

    val textFieldState = remember {
        TextFieldState()
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var previousExpression by remember { mutableStateOf("") }

    // Синхронизация выражения и курсора из ViewModel в TextFieldState
    LaunchedEffect(expression, cursorPosition, selectionStart, selectionEnd) {
        textFieldState.edit {
            // Обновляем текст, если он изменился из ViewModel
            if (this.text != expression) {
                replace(0, this.length, expression)
                previousExpression = expression
            }

            // Установка позиции курсора
            val clampedCursorPos = cursorPosition.coerceIn(0, expression.length)

            // Если есть выделение, используем его
            if (selectionStart != selectionEnd) {
                val start = selectionStart.coerceIn(0, expression.length)
                val end = selectionEnd.coerceIn(0, expression.length)
                // setSelection устанавливает выделение
                setSelection(minOf(start, end), maxOf(start, end))
            } else {
                // Иначе просто размещаем курсор
                // placeCursorAtIndex размещает курсор в указанную позицию
                placeCursorAtIndex(clampedCursorPos)
            }
        }

        // Прокрутка к концу текста
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    // Обработка изменений, которые делает пользователь в текстовом поле
    LaunchedEffect(textFieldState.text, textFieldState.selection.start) {
        val newText = textFieldState.text
        val newCursorPos = textFieldState.selection.start
        val newSelectionStart = textFieldState.selection.start
        val newSelectionEnd = textFieldState.selection.end

        // Если текст отличается от текущего выражения в ViewModel
        if (newText != previousExpression && newText != expression) {
            previousExpression = newText

            when {
                // Символ был добавлен
                newText.length > expression.length -> {
                    // Находим добавленный символ
                    val addedChar = if (newCursorPos > 0) {
                        newText.substring(newCursorPos - 1, newCursorPos)
                    } else {
                        ""
                    }

                    if (addedChar.isNotEmpty()) {
                        // Передаем в ViewModel
                        viewModelCalculation.handleCharacterInput(
                            character = addedChar,
                            expression = newText,
                            cursorPosition = newCursorPos - 1 // позиция ДО добавленного символа
                        )
                    }
                }

                // Символ был удален
                newText.length < expression.length -> {
                    // Определяем позицию удаленного символа
                    val deletionPos = newCursorPos
                    viewModelCalculation.handleCharacterDeletion(
                        expression = newText,
                        deletionPosition = deletionPos
                    )
                }

                // Только курсор изменился
                else -> {
                    viewModelCalculation.setCursorPosition(newCursorPos)

                    // Если есть выделение
                    if (newSelectionStart != newSelectionEnd) {
                        viewModelCalculation.setSelection(newSelectionStart, newSelectionEnd)
                    } else {
                        viewModelCalculation.clearSelection()
                    }
                }
            }
        } else if (newText == expression && textFieldState.selection.start != cursorPosition) {
            // Только курсор изменился, текст остался тем же
            viewModelCalculation.setCursorPosition(newCursorPos)

            if (newSelectionStart != newSelectionEnd) {
                viewModelCalculation.setSelection(newSelectionStart, newSelectionEnd)
            } else {
                viewModelCalculation.clearSelection()
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.29f)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
        contentAlignment = Alignment.BottomEnd,
    ) {

        val containerWidth = constraints.maxWidth

        if (settingModel.isShowPlaceholderInput && expression.isEmpty()) {
            Text(
                text = "0",
                color = Color.White.copy(alpha = 0.4f),
                textAlign = TextAlign.End,
                fontSize = fontSize,
                modifier = Modifier.fillMaxWidth()
            )
        }

        BasicTextField(
            state = textFieldState,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),

            textStyle = TextStyle(
                color = Color.White,
                fontSize = fontSize,
                textAlign = TextAlign.End,
            ),

            lineLimits = TextFieldLineLimits.SingleLine,

            cursorBrush = SolidColor(
                Color.LightGray
            ),

            interactionSource = interactionSource,

            onTextLayout = { getResult ->

                val textLayoutResult = getResult() ?: return@BasicTextField

                if (
                    textLayoutResult.size.width > containerWidth &&
                    fontSize > minFontSize
                ) {
                    fontSize *= 0.95f
                }
            },

            decorator = { innerTextField ->

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    innerTextField()
                }
            }
        )
    }
}