package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.models.calculation.CursorPosition

/**
 * Use Case для добавления десятичной точки в позицию курсора.
 *
 * Правила валидации:
 * - Если в текущем числе уже есть точка, не добавляем
 * - Если до курсора нет числа (пусто или оператор), добавляем "0."
 * - После закрывающей скобки ')' точку нельзя добавлять напрямую
 * - После открывающей скобки '(' добавляем "0."
 */
class CalculationBuildDecimalCursorUseCase {
    private val operators = setOf('+', '-', '*', '/', '%', '(')

    /**
     * @param cursor информация о позиции курсора
     * @return Pair(обновленное выражение, новая позиция курсора) или null если операция невозможна
     */
    operator fun invoke(cursor: CursorPosition): Pair<String, Int>? {
        val expression = cursor.expression
        val position = cursor.cursorPosition
        val previousChar = cursor.previousChar
        val textBeforeCursor = cursor.textBeforeCursor

        // Нельзя добавить точку после закрывающей скобки
        if (previousChar == ')') {
            return null
        }

        // Проверяем, есть ли уже точка в теку��ем числе
        // Находим последний оператор перед курсором
        val lastOperatorIndex = textBeforeCursor.indexOfLast { it in operators }
        val lastNumberPart = if (lastOperatorIndex >= 0) {
            textBeforeCursor.substring(lastOperatorIndex + 1)
        } else {
            textBeforeCursor
        }

        // Если в последнем числе уже есть точка, не добавляем
        if (lastNumberPart.contains('.')) {
            return null
        }

        val result: String
        val newPosition: Int

        // Если перед курсором оператор или пусто, добавляем "0."
        if (previousChar == null || previousChar in operators) {
            result = expression.substring(0, position) + "0." + expression.substring(position)
            newPosition = position + 2
        } else {
            // Иначе просто добавляем точку
            result = expression.substring(0, position) + "." + expression.substring(position)
            newPosition = position + 1
        }

        return Pair(result, newPosition)
    }
}
