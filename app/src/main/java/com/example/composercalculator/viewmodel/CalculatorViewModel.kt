package com.example.composercalculator.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.composercalculator.calculator.CalculatorEvent
import com.example.composercalculator.calculator.CalculatorState

class CalculatorViewModel : ViewModel() {

    companion object {
        private const val MAX_NUM_LENGTH = 15 // Максимальная длина числа
    }

    var uiState by mutableStateOf(CalculatorState())
        private set

    // Единая точка входа для всех событий из UI
    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.NumberClick -> enterNumber(event.number)
            is CalculatorEvent.OperationClick -> enterOperation(event.operation)
            is CalculatorEvent.DecimalClick -> enterDecimal()
            is CalculatorEvent.Negate -> performNegate()
            is CalculatorEvent.Delete -> performDelete()
            is CalculatorEvent.LongClear -> resetState()
            is CalculatorEvent.Calculate -> performCalculation()
            is CalculatorEvent.Paste -> handlePaste(event.text)
            else -> {}
        }
    }

    // --- Реализация НОВОЙ, правильной логики ---

    // ИСПРАВЛЕНО: Убрана ошибочная логика
    private fun enterNumber(number: String) {
        // Логика для первого числа
        if (uiState.operation == null) {
            // Не позволяем вводить больше одной цифры, если текущее число "0"
            if (uiState.number1 == "0" && number == "0") return
            // Заменяем "0" на новую цифру, если это не точка
            if (uiState.number1 == "0" && number != ".") {
                uiState = uiState.copy(number1 = number)
                return
            }
            if (uiState.number1.length >= MAX_NUM_LENGTH) return
            uiState = uiState.copy(number1 = uiState.number1 + number)
            return
        }

        // Логика для второго числа (аналогично)
        if (uiState.number2 == "0" && number == "0") return
        if (uiState.number2 == "0" && number != ".") {
            uiState = uiState.copy(number2 = number)
            return
        }
        if (uiState.number2.length >= MAX_NUM_LENGTH) return
        uiState = uiState.copy(number2 = uiState.number2 + number)
    }

    private fun enterOperation(operation: String) {
        // Если первое число заканчивается на точку, убираем ее
        if (uiState.number1.endsWith(".")) {
            uiState = uiState.copy(number1 = uiState.number1.dropLast(1))
        }
        // Если уже есть какая-то операция, сначала вычисляем результат
        if (uiState.number1.isNotBlank() && uiState.number2.isNotBlank()) {
            performCalculation()
        }
        // Устанавливаем новую операцию, если первое число введено
        if (uiState.number1.isNotBlank()) {
            uiState = uiState.copy(operation = operation)
        }
    }

    private fun enterDecimal() {
        if (uiState.operation == null) {
            if (!uiState.number1.contains(".") && uiState.number1.isNotBlank()) {
                uiState = uiState.copy(number1 = uiState.number1 + ".")
            } else if (uiState.number1.isEmpty()) {
                uiState = uiState.copy(number1 = "0.")
            }
            return
        }

        if (!uiState.number2.contains(".") && uiState.number2.isNotBlank()) {
            uiState = uiState.copy(number2 = uiState.number2 + ".")
        } else if (uiState.number2.isEmpty()) {
            uiState = uiState.copy(number2 = "0.")
        }
    }

    private fun performNegate() {
        if (uiState.operation == null && uiState.number1.isNotBlank()) {
            uiState = uiState.copy(number1 = toggleSign(uiState.number1))
            return
        }
        if (uiState.number2.isNotBlank()) {
            uiState = uiState.copy(number2 = toggleSign(uiState.number2))
        }
    }

    private fun performDelete() {
        when {
            uiState.number2.isNotBlank() -> uiState = uiState.copy(number2 = uiState.number2.dropLast(1))
            uiState.operation != null -> uiState = uiState.copy(operation = null)
            uiState.number1.isNotBlank() -> uiState = uiState.copy(number1 = uiState.number1.dropLast(1))
        }
    }

    private fun performCalculation() {
        val number1 = uiState.number1.toDoubleOrNull()
        val number2 = uiState.number2.toDoubleOrNull()

        if (number1 != null && number2 != null) {
            val result = when (uiState.operation) {
                "+" -> number1 + number2
                "-" -> number1 - number2
                "×" -> number1 * number2
                "÷" -> if (number2 != 0.0) number1 / number2 else Double.NaN
                "%" -> (number1 / 100) * number2
                else -> return
            }

            val resultString = if (result.isNaN()) "Ошибка" else if (result % 1.0 == 0.0) {
                result.toLong().toString()
            } else {
                result.toString()
            }.take(MAX_NUM_LENGTH)

            uiState = CalculatorState(number1 = resultString)
        }
    }

    private fun resetState() {
        uiState = CalculatorState(number1 = "") // Сбрасываем к пустой строке
    }

    private fun handlePaste(pasteText: String?) {
        if (!pasteText.isNullOrEmpty() && pasteText.toDoubleOrNull() != null) {
            if (uiState.operation == null) {
                uiState = uiState.copy(number1 = pasteText.take(MAX_NUM_LENGTH))
            } else {
                uiState = uiState.copy(number2 = pasteText.take(MAX_NUM_LENGTH))
            }
        }
    }

    private fun toggleSign(number: String): String {
        return if (number.startsWith("-")) {
            number.removePrefix("-")
        } else {
            "-$number"
        }
    }
}
