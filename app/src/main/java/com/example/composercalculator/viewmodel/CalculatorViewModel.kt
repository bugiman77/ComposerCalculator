package com.example.composercalculator.viewmodel

import androidx.compose.animation.core.copy
import com.example.composercalculator.calculator.CalculatorEvent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.composercalculator.calculator.CalculatorState

class CalculatorViewModel : ViewModel() {

    // Состояние, на которое будет подписан UI. `private set` означает, что изменить его можно только внутри ViewModel.
    var uiState by mutableStateOf(CalculatorState())
        private set

    // Функция для обработки всех событий от UI
    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.NumberClick -> handleNumberClick(event.number)
            is CalculatorEvent.OperationClick -> { /* TODO: Логика операций */ }
            is CalculatorEvent.Clear -> handleClear()
            is CalculatorEvent.Calculate -> { /* TODO: Логика вычислений */ }
            is CalculatorEvent.DecimalClick -> { /* TODO: Логика добавления точки */ }
        }
    }

    private fun handleNumberClick(number: String) {
        if (uiState.displayText == "0") {
            uiState = uiState.copy(displayText = number)
        } else {
            uiState = uiState.copy(displayText = uiState.displayText + number)
        }
    }

    private fun handleClear() {
        uiState = CalculatorState() // Сброс к начальному состоянию
    }

    // Здесь будут другие приватные функции для остальной логики
}
