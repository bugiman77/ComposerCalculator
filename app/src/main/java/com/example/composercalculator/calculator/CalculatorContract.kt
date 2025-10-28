package com.example.composercalculator.calculator

data class CalculatorState(
    val number1: String = "",        // Первое число
    val number2: String = "",        // Второе число
    val operation: String? = null
)

sealed interface CalculatorEvent {
    data class NumberClick(val number: String) : CalculatorEvent
    data class OperationClick(val operation: String) : CalculatorEvent
    data class Paste(val text: String?) : CalculatorEvent
    object Negate : CalculatorEvent
    object Calculate : CalculatorEvent
    object LongClear : CalculatorEvent
    object DecimalClick : CalculatorEvent
    object Delete : CalculatorEvent
}
