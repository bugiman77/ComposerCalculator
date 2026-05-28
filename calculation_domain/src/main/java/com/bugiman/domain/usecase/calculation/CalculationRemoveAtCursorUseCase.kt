package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.models.calculation.CursorPosition

/**
 * Use Case для удаления символа в позиции курсора (как удаление символа справа от курсора).
 * Это эквивалент клавиши Delete.
 *
 * @param cursor информация о позиции курсора
 * @return Pair(обновленное выражение, новая позиция курсора)
 */
class CalculationRemoveAtCursorUseCase {
    operator fun invoke(cursor: CursorPosition): Pair<String, Int> {
        val expression = cursor.expression
        val position = cursor.cursorPosition

        if (position >= expression.length) {
            return Pair(expression, position)
        }

        val result = expression.substring(0, position) + expression.substring(position + 1)
        return Pair(result, position)
    }
}
