package com.bugiman.composercalculator.view.components.calculation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
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
    val textFieldState = remember { TextFieldState() }
    val interactionSource = remember { MutableInteractionSource() }

    var previousExpression by remember { mutableStateOf("") }
    var isUpdatingFromViewModel by remember { mutableStateOf(false) }

    // Синхронизация текста из ViewModel в TextFieldState
    LaunchedEffect(expression) {
        if (textFieldState.text.toString() != expression) {
            isUpdatingFromViewModel = true
            textFieldState.edit {
                replace(0, this.length, expression)
            }
            previousExpression = expression
            isUpdatingFromViewModel = false
        }
    }

    // Синхронизация позиции курсора из ViewModel в TextFieldState
    LaunchedEffect(cursorPosition, selectionStart, selectionEnd) {
        if (isUpdatingFromViewModel) return@LaunchedEffect

        val clampedCursorPos = cursorPosition.coerceIn(0, expression.length)

        if (selectionStart != selectionEnd) {
            // Устанавливаем выделение
            val start = selectionStart.coerceIn(0, expression.length)
            val end = selectionEnd.coerceIn(0, expression.length)
            textFieldState.edit {
                selection = TextRange(
                    minOf(start, end),
                    maxOf(start, end)
                )
            }
        } else {
            // Размещаем курсор без выделения
            textFieldState.edit {
                selection = TextRange(clampedCursorPos)
            }
        }
    }

    // Прокрутка к позиции курсора
    LaunchedEffect(cursorPosition) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    // Обработка изменений от пользователя в текстовом поле
    LaunchedEffect(textFieldState.text) {
        if (isUpdatingFromViewModel) return@LaunchedEffect

        val newText = textFieldState.text.toString()

        if (newText != previousExpression && newText != expression) {
            previousExpression = newText

            when {
                // Символ был добавлен
                newText.length > expression.length -> {
                    handleCharacterAddition(
                        newText = newText,
                        previousText = expression,
                        cursorPos = textFieldState.selection.start,
                        viewModel = viewModelCalculation
                    )
                }

                // Символ был удален
                newText.length < expression.length -> {
                    handleCharacterDeletion(
                        newText = newText,
                        deletionPos = textFieldState.selection.start,
                        viewModel = viewModelCalculation
                    )
                }
            }
        }
    }

    // Обработка изменения позиции курсора
    LaunchedEffect(textFieldState.selection.start, textFieldState.selection.end) {
        if (isUpdatingFromViewModel) return@LaunchedEffect

        val newCursorPos = textFieldState.selection.start
        val newSelectionStart = textFieldState.selection.start
        val newSelectionEnd = textFieldState.selection.end

        if (textFieldState.text.toString() == expression) {
            if (newCursorPos != cursorPosition) {
                viewModelCalculation.setCursorPosition(newCursorPos)
            }

            if (newSelectionStart != newSelectionEnd) {
                viewModelCalculation.setSelection(newSelectionStart, newSelectionEnd)
            } else {
                viewModelCalculation.clearSelection()
            }
        }
    }

    DisplayAreaContent(
        fontSize = fontSize,
        onFontSizeChange = { fontSize = it },
        minFontSize = minFontSize,
        expression = expression,
        scrollState = scrollState,
        textFieldState = textFieldState,
        interactionSource = interactionSource,
        settingModel = settingModel
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DisplayAreaContent(
    fontSize: TextUnit,
    onFontSizeChange: (TextUnit) -> Unit,
    minFontSize: TextUnit,
    expression: String,
    scrollState: ScrollState,
    textFieldState: TextFieldState,
    interactionSource: MutableInteractionSource,
    settingModel: SettingModel,
) {
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
            PlaceholderText(fontSize = fontSize)
        }

        TextInputField(
            textFieldState = textFieldState,
            fontSize = fontSize,
            onFontSizeChange = onFontSizeChange,
            minFontSize = minFontSize,
            scrollState = scrollState,
            interactionSource = interactionSource,
            containerWidth = containerWidth
        )
    }
}

@Composable
private fun PlaceholderText(fontSize: androidx.compose.ui.unit.TextUnit) {
    Text(
        text = "0",
        color = Color.White.copy(alpha = 0.4f),
        textAlign = TextAlign.End,
        fontSize = fontSize,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TextInputField(
    textFieldState: TextFieldState,
    fontSize: androidx.compose.ui.unit.TextUnit,
    onFontSizeChange: (androidx.compose.ui.unit.TextUnit) -> Unit,
    minFontSize: androidx.compose.ui.unit.TextUnit,
    scrollState: androidx.compose.foundation.ScrollState,
    interactionSource: MutableInteractionSource,
    containerWidth: Int,
) {
    var currentFontSize by remember { mutableStateOf(fontSize) }

    BasicTextField(
        state = textFieldState,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = currentFontSize,
            textAlign = TextAlign.End,
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(Color.LightGray),
        interactionSource = interactionSource,
        onTextLayout = { getResult ->
            val textLayoutResult = getResult() ?: return@BasicTextField

            if (textLayoutResult.size.width > containerWidth && currentFontSize > minFontSize) {
                currentFontSize *= 0.95f
                onFontSizeChange(currentFontSize)
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

/**
 * Обработка добавления символа пользователем
 * Вставляет символ в позицию курсора
 */
private fun handleCharacterAddition(
    newText: String,
    previousText: String,
    cursorPos: Int,
    viewModel: CalculatorViewModel,
) {
    val addedChar = if (cursorPos > 0 && cursorPos <= newText.length) {
        newText.substring(cursorPos - 1, cursorPos)
    } else {
        ""
    }

    if (addedChar.isNotEmpty()) {
        viewModel.handleCharacterInput(
            character = addedChar,
            expression = newText,
            cursorPosition = cursorPos - 1 // Позиция ДО добавленного символа
        )
    }
}

/**
 * Обработка удаления символа пользователем
 * Удаляет символ в позиции курсора
 */
private fun handleCharacterDeletion(
    newText: String,
    deletionPos: Int,
    viewModel: CalculatorViewModel,
) {
    viewModel.handleCharacterDeletion(
        expression = newText,
        deletionPosition = deletionPos
    )
}
