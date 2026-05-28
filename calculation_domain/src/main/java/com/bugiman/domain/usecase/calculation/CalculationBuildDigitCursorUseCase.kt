package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.models.calculation.CursorPosition

/**
 * Use Case для добавления цифры в позицию курсора.
 * Поддерживает вставку в середину, начало и конец строки.
 *
 * Правила валидации:
 * - После точки можно вставить цифру
 * - После оператора можно вставить цифру
 * - После скобки ')' перед цифрой нужно вставить умножение
 * - Нельзя вставить '0' если это удвоит нули в числе (обработка в отдельном use case)
 */
class CalculationBuildDigitCursorUseCase {
    private val operators = setOf('+', '-', '*', '/', '%')
    private val invalidBefore = setOf('.')

    /**
     * @param cursor информация о позиции курсора
     * @param digit добавляемая цифра
     * @return обновленное выражение и новая позиция курсора
     */
    operator fun invoke(
        cursor: CursorPosition,
        digit: String
    ): Pair<String, Int> {
        val expression = cursor.expression
        val position = cursor.cursorPosition
        val previousChar = cursor.previousChar
        val nextChar = cursor.nextChar

        // Вставляем цифру в позицию курсора
        var result = expression.substring(0, position) + digit + expression.substring(position)
        var newPosition = position + 1

        // Обработка автоматического умножения перед скобкой ')'
        if (nextChar == ')') {
            result = expression.substring(0, position) + digit + "*" + expression.substring(position)
            newPosition = position + 1
        }

        // Обработка автоматического умножения после скобки ')'
        if (previousChar == ')') {
            result = expression.substring(0, position) + "*" + digit + expression.substring(position)
            newPosition = position + 2
        }

        return Pair(result, newPosition)
    }
}
