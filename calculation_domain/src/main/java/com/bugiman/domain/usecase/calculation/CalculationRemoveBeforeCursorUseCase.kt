package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.models.calculation.CursorPosition

/**
 * Use Case для удаления символа перед курсором (как удаление символа слева от курсора).
 * Это эквивалент клавиши Backspace.
 *
 * @param cursor информация о позиции курсора
 * @return Pair(обновленное выражение, новая позиция курсора)
 */
class CalculationRemoveBeforeCursorUseCase {
    operator fun invoke(cursor: CursorPosition): Pair<String, Int> {
        val expression = cursor.expression
        val position = cursor.cursorPosition

        if (position <= 0) {
            return Pair(expression, position)
        }

        val result = expression.substring(0, position - 1) + expression.substring(position)
        return Pair(result, position - 1)
    }
}
