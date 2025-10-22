package com.example.composercalculator.calculator

// Класс данных для хранения состояния всего экрана
data class CalculatorState(
    val displayText: String = "0"
)

// Запечатанный интерфейс для всех возможных действий пользователя (событий)
sealed interface CalculatorEvent {
    data class NumberClick(val number: String) : CalculatorEvent
    data class OperationClick(val operation: String) : CalculatorEvent
    object Calculate : CalculatorEvent
    object Clear : CalculatorEvent
    object DecimalClick : CalculatorEvent
    // Добавьте другие события по мере необходимости (например, для +/- или %)
}
