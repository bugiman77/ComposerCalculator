package com.example.composercalculator.model

data class CalculatorState(

    val number1: String = "",
    val number2: String = "",
    val operation: String? = null

)

sealed interface CalculatorEvent {

    data class NumberClick(val number: String) : CalculatorEvent
    data class OperationClick(val operation: String) : CalculatorEvent
    data class Paste(val text: String?) : CalculatorEvent
    data class DeleteHistoryItem(val id: Long) : CalculatorEvent
    data class UpdateHistoryLabel(val id: Long, val label: String) : CalculatorEvent
    data class RecallCalculation(val expression: String) : CalculatorEvent

    object Negate : CalculatorEvent
    object Calculate : CalculatorEvent
    object LongClear : CalculatorEvent
    object DecimalClick : CalculatorEvent
    object Delete : CalculatorEvent
    object ClearHistory : CalculatorEvent

}
