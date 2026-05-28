package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.models.calculation.CursorPosition

/**
 * Use Case для добавления открывающей скобки '(' в позицию курсора.
 *
 * Правила валидации:
 * - Перед скобкой можно добавить умножение если перед ней цифра или закрывающая скобка
 * - Нельзя добавить скобку сразу после точки (удалим точку)
 */
class CalculationBuildBracketLeftCursorUseCase {

    /**
     * @param cursor информация о позиции курсора
     * @param bracketLeft строка с открывающей скобкой (обычно "(" )
     * @return Pair(обновленное выражение, новая позиция курсора)
     */
    operator fun invoke(
        cursor: CursorPosition,
        bracketLeft: String
    ): Pair<String, Int> {
        val expression = cursor.expression
        val position = cursor.cursorPosition
        val previousChar = cursor.previousChar

        var result = expression
        var newPosition = position

        // Если перед скобкой цифра, добавляем умножение
        if (previousChar?.isDigit() == true) {
            result = expression.substring(0, position) + "*" + bracketLeft + expression.substring(position)
            newPosition = position + bracketLeft.length + 1
        }
        // Если перед скобкой закрывающая скобка, добавляем умножение
        else if (previousChar == ')') {
            result = expression.substring(0, position) + "*" + bracketLeft + expression.substring(position)
            newPosition = position + bracketLeft.length + 1
        }
        // Если перед скобкой точка, удаляем точку и добавляем скобку
        else if (previousChar == '.') {
            result = expression.substring(0, position - 1) + bracketLeft + expression.substring(position)
            newPosition = position
        }
        // Иначе просто добавляем скобку
        else {
            result = expression.substring(0, position) + bracketLeft + expression.substring(position)
            newPosition = position + bracketLeft.length
        }

        return Pair(result, newPosition)
    }
}
