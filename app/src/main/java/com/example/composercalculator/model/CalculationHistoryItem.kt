package com.example.composercalculator.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Модель данных, представляющая одну запись в истории вычислений.
 *
 * @param id Уникальный идентификатор записи.
 * @param expression Строковое представление всего выражения (например, "22 + 8").
 * @param result Строковое представление результата (например, "30").
 * @param timestamp Время, когда было выполнено вычисление. Хранится как Long для эффективности.
 */
data class CalculationHistoryItem(
    val id: Long,
    val expression: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis()
) {

    /**
     * Возвращает дату в виде строки, удобной для группировки.
     * Например, "2025-10-31".
     */
    fun getFormattedDate(): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }

    /**
     * Вспомогательная функция для форматирования timestamp в удобный для чтения вид.
     * @return Строка с временем, например, "14:35".
     */
    fun getFormattedTime(): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    /**
     * Вспомогательная функция для получения полного строкового представления для отображения в списке.
     * @return Строка вида "22 + 8 = 30".
     */
    fun getFullCalculationString(): String {
        return "$expression = $result"
    }
}

