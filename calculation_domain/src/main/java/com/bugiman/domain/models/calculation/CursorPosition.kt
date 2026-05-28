package com.bugiman.domain.models.calculation

/**
 * Модель для представления информации о позиции курсора в выражении.
 * Используется для валидации ввода символов в середину строки.
 *
 * @property expression полное выражение в поле ввода
 * @property cursorPosition индекс позиции курсора (от 0 до length)
 */
data class CursorPosition(
    val expression: String,
    val cursorPosition: Int
) {
    /**
     * Предыдущий символ относительно курсора (может быть null если курсор в начале)
     */
    val previousChar: Char?
        get() = if (cursorPosition > 0) expression.getOrNull(cursorPosition - 1) else null

    /**
     * Следующий символ относительно курсора (может быть null если курсор в конце)
     */
    val nextChar: Char?
        get() = expression.getOrNull(cursorPosition)

    /**
     * Текст до курсора
     */
    val textBeforeCursor: String
        get() = expression.substring(0, cursorPosition)

    /**
     * Текст после курсора
     */
    val textAfterCursor: String
        get() = expression.substring(cursorPosition)

    /**
     * Проверка, находится ли курсор в конце выражения
     */
    val isAtEnd: Boolean
        get() = cursorPosition == expression.length

    /**
     * Проверка, находится ли курсор в начале выражения
     */
    val isAtStart: Boolean
        get() = cursorPosition == 0
}
