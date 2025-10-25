package com.example.composercalculator.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.semantics.text
import androidx.lifecycle.ViewModel
import com.example.composercalculator.calculator.CalculatorEvent
import com.example.composercalculator.calculator.CalculatorState

class CalculatorViewModel(
    private val context: Context
) : ViewModel() {

    // Состояние, на которое будет подписан UI. `private set` означает, что изменить его можно только внутри ViewModel.
    var uiState by mutableStateOf(CalculatorState())
        private set

    @SuppressLint("ServiceCast")
    private val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    // Функция для обработки всех событий от UI
    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.NumberClick -> handleNumberClick(event.number)
            is CalculatorEvent.Clear -> handleClear()
            is CalculatorEvent.Negate -> handleNegate()
            is CalculatorEvent.OperationClick -> {}
            is CalculatorEvent.Calculate -> {}
            is CalculatorEvent.DecimalClick -> {}
            is CalculatorEvent.Paste -> handlePaste()
            is CalculatorEvent.Delete -> handleDelete()           // <-- ИЗМЕНЕНО
            is CalculatorEvent.LongClear -> handleLongClear()
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

    private fun handleNegate() {
        val currentText = uiState.displayText
        // Не меняем знак, если на экране "0"
        if (currentText == "0") {
            return
        }

        uiState = if (currentText.startsWith("-")) {
            // Если число уже отрицательное, убираем минус
            uiState.copy(displayText = currentText.removePrefix("-"))
        } else {
            // Если число положительное, добавляем минус
            uiState.copy(displayText = "-$currentText")
        }
    }

    private fun handlePaste() {
        val clipData = clipboardManager.primaryClip
        if (clipData != null && clipData.itemCount > 0) {
            val pasteText = clipData.getItemAt(0).text?.toString()
            if (!pasteText.isNullOrEmpty() && pasteText.toDoubleOrNull() != null) {
                // Вставляем только если это валидное число
                uiState = uiState.copy(displayText = pasteText)
            }
        }
    }

    private fun handleDelete() {
        val currentText = uiState.displayText
        if (currentText.length > 1) {
            // Если в строке больше одного символа, удаляем последний
            uiState = uiState.copy(displayText = currentText.dropLast(1))
        } else {
            // Если остался один символ, сбрасываем к "0"
            uiState = uiState.copy(displayText = "0")
        }
    }

    private fun handleLongClear() {
        // Просто сбрасываем состояние к начальному
        uiState = CalculatorState()
    }

}
