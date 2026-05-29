package com.bugiman.composercalculator.presentation.calculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugiman.domain.models.calculation.CursorPosition
import com.bugiman.domain.models.history.HistoryModel
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketLeftUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketLeftCursorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketRigthUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildCommaUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDecimalUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDecimalCursorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDigitUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDigitCursorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorDivisionUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorMinusUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorMultiplicationUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildMathOperatorPlusUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildOperatorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildOperatorCursorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildZeroUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveAtCursorUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveBeforeCursorUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveLastCharUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val historyAllDeleteUseCase: HistoryAllDeleteUseCase,
    private val historyAllGetUseCase: HistoryAllGetUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase,
    private val buildDigitUseCase: CalculationBuildDigitUseCase,
    private val buildDigitCursorUseCase: CalculationBuildDigitCursorUseCase,
    private val buildMathOperatorDivisionUseCase: CalculationBuildMathOperatorDivisionUseCase,
    private val buildMathOperatorMultiplicationUseCase: CalculationBuildMathOperatorMultiplicationUseCase,
    private val buildMathOperatorMinusUseCase: CalculationBuildMathOperatorMinusUseCase,
    private val buildMathOperatorPlusUseCase: CalculationBuildMathOperatorPlusUseCase,
    private val buildBracketLeftUseCase: CalculationBuildBracketLeftUseCase,
    private val buildBracketLeftCursorUseCase: CalculationBuildBracketLeftCursorUseCase,
    private val buildBracketRigthUseCase: CalculationBuildBracketRigthUseCase,
    private val buildDecimalUseCase: CalculationBuildDecimalUseCase,
    private val buildDecimalCursorUseCase: CalculationBuildDecimalCursorUseCase,
    private val buildZeroUseCase: CalculationBuildZeroUseCase,
    private val buildCommaUseCase: CalculationBuildCommaUseCase,
    private val buildOperatorCursorUseCase: CalculationBuildOperatorCursorUseCase,
    private val removeLastCharUseCase: CalculationRemoveLastCharUseCase,
    private val removeAtCursorUseCase: CalculationRemoveAtCursorUseCase,
    private val removeBeforeCursorUseCase: CalculationRemoveBeforeCursorUseCase,
    private val calculationRemoveExpressionUseCase: CalculationRemoveExpressionUseCase,
) : ViewModel() {

    private val _expression = MutableStateFlow(value = "")
    val expression: StateFlow<String> = _expression.asStateFlow()

    /**
     * Позиция курсора в выражении (0 = начало, length = конец)
     */
    private val _cursorPosition = MutableStateFlow(value = 0)
    val cursorPosition: StateFlow<Int> = _cursorPosition.asStateFlow()

    /**
     * Начало выделения текста (для поддержки выделения)
     */
    private val _selectionStart = MutableStateFlow(value = 0)
    val selectionStart: StateFlow<Int> = _selectionStart.asStateFlow()

    /**
     * Конец выделения текста (для поддержки выделения)
     */
    private val _selectionEnd = MutableStateFlow(value = 0)
    val selectionEnd: StateFlow<Int> = _selectionEnd.asStateFlow()

    val history: StateFlow<List<HistoryModel>> = historyAllGetUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList()
        )

    // ============= Методы ввода цифр (с поддержкой курсора) =============

    /**
     * Ввод цифры в позицию курсора
     */
    fun onInputDigitAtCursor(digit: String, cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val (result, newPos) = buildDigitCursorUseCase(cursor, digit)
            Triple(result, newPos, newPos)
        }
    }

    /**
     * Ввод цифры в конец (обратная совместимость)
     */
    fun onInputDigit(digit: String) {
        updateExpression { buildDigitUseCase(current = it, digit) }
    }

    // ============= Методы ввода нуля =============

    fun onInputZero() {
        updateExpression { buildZeroUseCase(current = it) }
    }

    // ============= Методы ввода операторов (с поддержкой курсора) =============

    /**
     * Ввод оператора деления в позицию курсора
     */
    fun onInputMathOperationDivisionAtCursor(operation: String, cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val result = buildOperatorCursorUseCase(cursor, operation)
            if (result != null) {
                val (newExpr, newPos) = result
                Triple(newExpr, newPos, newPos)
            } else {
                Triple(expression, cursorPos, cursorPos)
            }
        }
    }

    /**
     * Ввод оператора деления в конец (обратная совместимость)
     */
    fun onInputMathOperationDivision(operation: String) {
        updateExpression { buildMathOperatorDivisionUseCase(current = it, operator = operation) }
    }

    /**
     * Ввод оператора умножения в позицию курсора
     */
    fun onInputMathOperationMultiplicationAtCursor(operation: String, cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val result = buildOperatorCursorUseCase(cursor, operation)
            if (result != null) {
                val (newExpr, newPos) = result
                Triple(newExpr, newPos, newPos)
            } else {
                Triple(expression, cursorPos, cursorPos)
            }
        }
    }

    /**
     * Ввод оператора умножения в конец (обратная совместимость)
     */
    fun onInputMathOperationMultiplication(operation: String) {
        updateExpression { buildMathOperatorMultiplicationUseCase(current = it, operator = operation) }
    }

    /**
     * Ввод оператора плюса в позицию курсора
     */
    fun onInputMathOperationPlusAtCursor(operation: String, cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val result = buildOperatorCursorUseCase(cursor, operation)
            if (result != null) {
                val (newExpr, newPos) = result
                Triple(newExpr, newPos, newPos)
            } else {
                Triple(expression, cursorPos, cursorPos)
            }
        }
    }

    /**
     * Ввод оператора плюса в конец (обратная совместимость)
     */
    fun onInputMathOperationPlus(operation: String) {
        updateExpression { buildMathOperatorPlusUseCase(current = it, operator = operation) }
    }

    /**
     * Ввод оператора минуса в позицию курсора
     */
    fun onInputMathOperationMinusAtCursor(operation: String, cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val result = buildOperatorCursorUseCase(cursor, operation)
            if (result != null) {
                val (newExpr, newPos) = result
                Triple(newExpr, newPos, newPos)
            } else {
                Triple(expression, cursorPos, cursorPos)
            }
        }
    }

    /**
     * Ввод оператора минуса в конец (обратная совместимость)
     */
    fun onInputMathOperationMinus(operation: String) {
        updateExpression { buildMathOperatorMinusUseCase(current = it, operator = operation) }
    }

    // ============= Методы ввода скобок (с поддержкой курсора) =============

    /**
     * Ввод открывающей скобки в позицию курсора
     */
    fun onInputBracketLeftAtCursor(bracket: String, cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val (result, newPos) = buildBracketLeftCursorUseCase(cursor, bracket)
            Triple(result, newPos, newPos)
        }
    }

    /**
     * Ввод открывающей скобки в конец (обратная совместимость)
     */
    fun onInputBracketLeft(bracket: String) {
        updateExpression { buildBracketLeftUseCase(current = it, bracketLeft = bracket) }
    }

    /**
     * Ввод закрывающей скобки в конец (только в конец, т.к. логика сложная)
     */
    fun onInputBracketRigth(bracket: String) {
        updateExpression { buildBracketRigthUseCase(current = it, bracketRigth = bracket) }
    }

    // ============= Методы ввода десятичной точки (с поддержкой курсора) =============

    /**
     * Ввод десятичной точки в позицию курсора
     */
    fun onInputDecimalAtCursor(cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val result = buildDecimalCursorUseCase(cursor)
            if (result != null) {
                val (newExpr, newPos) = result
                Triple(newExpr, newPos, newPos)
            } else {
                Triple(expression, cursorPos, cursorPos)
            }
        }
    }

    /**
     * Ввод десятичной точки в конец (обратная совместимость)
     */
    fun onInputDecimal() {
        updateExpression { buildDecimalUseCase(current = it) }
    }

    /**
     * Ввод запятой в конец
     */
    fun onInputComma(comma: String) {
        updateExpression { buildCommaUseCase(current = it, comma = comma) }
    }

    // ============= Методы удаления =============

    /**
     * Удаление символа перед курсором (Backspace)
     */
    fun removeBeforeCursor(cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val (result, newPos) = removeBeforeCursorUseCase(cursor)
            Triple(result, newPos, newPos)
        }
    }

    /**
     * Удаление символа в позиции курсора (Delete)
     */
    fun removeAtCursor(cursorPos: Int) {
        updateExpressionWithCursor(cursorPos) { expression ->
            val cursor = CursorPosition(expression, cursorPos)
            val (result, newPos) = removeAtCursorUseCase(cursor)
            Triple(result, newPos, newPos)
        }
    }

    /**
     * Удаление выделенного текста
     */
    fun removeSelection(selStart: Int, selEnd: Int) {
        if (selStart >= selEnd) return

        updateExpressionWithCursor(selStart) { expression ->
            val result = expression.substring(0, selStart) + expression.substring(selEnd)
            Triple(result, selStart, selStart)
        }
    }

    /**
     * Удаление последнего символа (обратная совместимость)
     */
    fun removeLast() {
        updateExpression { removeLastCharUseCase(current = it) }
    }

    /**
     * Очистка всего выражения
     */
    fun clear() {
        updateExpression { calculationRemoveExpressionUseCase(current = it) }
        setCursorPosition(0)
    }

    // ============= Методы управления курсором =============

    /**
     * Установка позиции курсора
     */
    fun setCursorPosition(position: Int) {
        val clampedPosition = position.coerceIn(0, _expression.value.length)
        _cursorPosition.value = clampedPosition
        _selectionStart.value = clampedPosition
        _selectionEnd.value = clampedPosition
    }

    /**
     * Установка выделения текста
     */
    fun setSelection(start: Int, end: Int) {
        val clampedStart = start.coerceIn(0, _expression.value.length)
        val clampedEnd = end.coerceIn(0, _expression.value.length)

        _selectionStart.value = minOf(clampedStart, clampedEnd)
        _selectionEnd.value = maxOf(clampedStart, clampedEnd)
        _cursorPosition.value = maxOf(clampedStart, clampedEnd)
    }

    /**
     * Очистка выделения (курсор в конец выделения)
     */
    fun clearSelection() {
        val endPos = _selectionEnd.value
        _selectionStart.value = endPos
        _selectionEnd.value = endPos
        _cursorPosition.value = endPos
    }

    // ============= Метод вычисления =============

    fun onCalculate() {
        val currentExpression = _expression.value

        viewModelScope.launch {
            val result = calculateExpressionUseCase(currentExpression)

            result.onSuccess { successValue ->
                _expression.value = successValue
                setCursorPosition(successValue.length)
                historyItemSaveUseCase(
                    HistoryModel(
                        expression = currentExpression,
                        result = successValue
                    )
                )
            }.onFailure {
                // Обработка ошибки
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            historyAllDeleteUseCase()
        }
    }

    // ============= Внутренние вспомогательные методы =============

    /**
     * Обновление выражения с поддержкой курсора
     * @param currentCursorPos текущая позиция курсора
     * @param transform функция трансформации (expression) -> Triple(newExpression, newCursorPos, newSelectionEnd)
     */
    private fun updateExpressionWithCursor(
        currentCursorPos: Int,
        transform: (String) -> Triple<String, Int, Int>
    ) {
        viewModelScope.launch {
            triggerFeedback()
            val (newExpression, newCursorPos, newSelectionEnd) = transform(_expression.value)
            _expression.value = newExpression
            _cursorPosition.value = newCursorPos.coerceIn(0, newExpression.length)
            _selectionStart.value = newCursorPos.coerceIn(0, newExpression.length)
            _selectionEnd.value = newSelectionEnd.coerceIn(0, newExpression.length)
        }
    }

    /**
     * Обновление выражения в конец (обратная совместимость)
     */
    private fun updateExpression(transform: (String) -> String) {
        viewModelScope.launch {
            triggerFeedback()
            val newExpression = transform(_expression.value)
            _expression.value = newExpression
            // Переместить курсор в конец
            _cursorPosition.value = newExpression.length
            _selectionStart.value = newExpression.length
            _selectionEnd.value = newExpression.length
        }
    }

    private suspend fun triggerFeedback() {
        feedbackTriggerUseCase()
    }
}
