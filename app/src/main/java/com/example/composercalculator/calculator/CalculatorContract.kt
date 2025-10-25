package com.example.composercalculator.calculator

data class CalculatorState(
    val displayText: String = "0"
)

sealed interface CalculatorEvent {
    data class NumberClick(val number: String) : CalculatorEvent
    data class OperationClick(val operation: String) : CalculatorEvent
    object Calculate : CalculatorEvent
    object Clear : CalculatorEvent
    object DecimalClick : CalculatorEvent
    object Negate : CalculatorEvent
    object Paste : CalculatorEvent
    object Delete : CalculatorEvent
    object LongClear : CalculatorEvent

}
