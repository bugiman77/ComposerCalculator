package com.bugiman.composercalculator.model

/*data class CalculatorState(

    val number1: String = "",
    val number2: String = "",
    val operation: String? = null

)*/

sealed interface CalculatorEvent {

    data class DeleteHistoryItem(val id: Long) : CalculatorEvent
    data class UpdateHistoryLabel(val id: Long, val label: String) : CalculatorEvent
    data class RecallCalculation(val expression: String) : CalculatorEvent

    object ClearHistory : CalculatorEvent

}
