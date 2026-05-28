package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.models.calculation.CursorPosition

/**
 * Use Case для добавления оператора в позицию курсора.
 * Поддерживает вставку в середину, начало и конец строки.
 *
 * Правила валидации:
 * - Нельзя вставить оператор если предыдущий символ тоже оператор или точка
 * - Нельзя вставить оператор сразу после открывающей скобки '('
 * - Если после оператора идет точка, нужно её удалить
 * - Если после оператора идет цифра, они остаются
 */
class CalculationBuildOperatorCursorUseCase {
    private val operators = setOf('+', '-', '*', '/', '%')
    private val invalidBefore = setOf('(', '.') + operators

    /**
     * @param cursor информация о позиции курсора
     * @param operator добавляемый оператор
     * @return Pair(обновленное выражение, новая позиция курсора) или null если операция невозможна
     */
    operator fun invoke(
        cursor: CursorPosition,
        operator: String
    ): Pair<String, Int>? {
        val expression = cursor.expression
        val position = cursor.cursorPosition
        val previousChar = cursor.previousChar

        // Нельзя вставить оператор если перед ним недопустимый символ
        if (previousChar in invalidBefore) {
            return null
        }

        // Если выражение пустое, оператор нельзя вставить
        if (expression.isEmpty() && position == 0) {
            return null
        }

        // Если после оператора идет точка, её нужно удалить
        val nextChar = cursor.nextChar
        var result = if (nextChar == '.') {
            expression.substring(0, position) + operator + expression.substring(position + 1)
        } else {
            expression.substring(0, position) + operator + expression.substring(position)
        }

        val newPosition = position + operator.length

        return Pair(result, newPosition)
    }
}
