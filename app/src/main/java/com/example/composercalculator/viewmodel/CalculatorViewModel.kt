package com.example.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.composercalculator.data.local.db.AppDatabaseHistory
import com.example.composercalculator.data.local.db.dao.HistoryDao
import com.example.composercalculator.data.local.db.entity.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val historyDao: HistoryDao =
        AppDatabaseHistory.getDatabase(context = application).historyDao()

    private val _id = MutableStateFlow(value = "")
    val id: StateFlow<String> = _id.asStateFlow()

    private val _expression = MutableStateFlow(value = "")
    val expression: StateFlow<String> = _expression.asStateFlow()

    private val _result = MutableStateFlow(value = "")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _note = MutableStateFlow(value = "")
    val note: StateFlow<String> = _note.asStateFlow()

    private val _timestamp = MutableStateFlow(value = "")
    val timestamp: StateFlow<String> = _timestamp.asStateFlow()

    private val _isEditedExpression = MutableStateFlow(value = "")
    val isEditedExpression: StateFlow<String> = _isEditedExpression.asStateFlow()

    private val _timestampEditedExpression = MutableStateFlow(value = "")
    val timestampEditedExpression: StateFlow<String> = _timestampEditedExpression.asStateFlow()

    init {
        viewModelScope.launch {
            loadHistory()
        }
    }

    suspend fun loadHistory() {
        val history = withContext(context = Dispatchers.IO) {
            historyDao.getHistory() // Run the DB query on a background thread
        }
        history?.let {
            _id.value = it.id.toString()
            _expression.value = it.expression
            _result.value = it.result
            _note.value = it.note
            _timestamp.value = it.timestamp.toString()
            _isEditedExpression.value = it.isEditedExpression.toString()
            _timestampEditedExpression.value = it.timestampEditedExpression.toString()
        }
    }

    fun onInputDigit(inputDigit: String) {
        viewModelScope.launch {
            _expression.value += inputDigit
        }
    }

    fun onInputMathematicalOperations(inputOperation: String) {
        val currentExpression = _expression.value
        if (currentExpression.isEmpty()) return

        val operators = setOf('+', '-', '/', '*', '%') // Выносим операторы в Set для быстрого поиска
        val lastChar = currentExpression.last()

        // 1. Обработка ввода точки
        if (inputOperation == ".") {
            // Находим часть строки после последнего оператора (текущее число)
            val lastNumber = currentExpression.split('+', '-', '*', '/', '%').last()

            // Если в текущем числе уже есть точка или последний символ — оператор, игнорируем ввод
            if (lastNumber.contains('.') || lastChar in operators) {
                return
            }
            _expression.value += "."
            return
        }

        // 2. Обработка ввода математических операторов
        if (inputOperation.first() in operators) {
            if (lastChar == '.') {
                // Не позволяем ставить оператор сразу после точки (например, "12.+")
                return
            }

            if (lastChar in operators) {
                // Если последний символ — оператор, заменяем его
                _expression.value = currentExpression.dropLast(1) + inputOperation
            } else {
                // Если последний символ — цифра, добавляем оператор
                _expression.value += inputOperation
            }
        }
    }

    fun onToggleSign() {
        val currentExpression = _expression.value
        if (currentExpression.isEmpty()) return

        // Регулярное выражение находит последнее число, включая отрицательные в скобках
        // Ищет либо (-число), либо просто число в конце строки
        val lastTokenRegex = Regex("""(\(-\d+\.?\d*\)|(?<!\d)\d+\.?\d*)$""")
        val matchResult = lastTokenRegex.find(currentExpression)

        if (matchResult != null) {
            val lastToken = matchResult.value
            val prefix = currentExpression.substring(0, matchResult.range.first)

            val updatedToken = if (lastToken.startsWith("(-")) {
                // Если число уже отрицательное (в скобках), убираем скобки и минус
                lastToken.removeSurrounding("(-", ")")
            } else {
                // Если число положительное, оборачиваем в (-...)
                "(-$lastToken)"
            }

            _expression.value = prefix + updatedToken
        }
    }


    fun removeLastCharacter() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(n = 1)
        }
    }

    fun clearExpression() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = ""
        }
    }

    suspend fun calculateAndSave() {
        val currentExpression = _expression.value
        if (currentExpression.isBlank() || currentExpression.last() in listOf(
                '+',
                '-',
                '/',
                '*',
                '.',
                '%'
            )
        ) return

        val calculationResult = evaluateExpressionWithPython(expressionStr = currentExpression)
        _result.value = calculationResult

        _expression.value = calculationResult

        saveToDatabase(currentExpression, calculationResult)
    }

    private fun evaluateExpressionWithPython(expressionStr: String): String {
        return try {
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(application))  // Инициализация с AndroidPlatform
            }
            val python = Python.getInstance()
            val pythonFile = python.getModule("calculator")
            val expression = pythonFile.callAttr("evaluate_expression", expressionStr).toString()
            expression
        } catch (e: PyException) {
            "Ошибка вычислений"
        } catch (e: Exception) {
            "Критическая ошибка"
        }
    }

    private suspend fun saveToDatabase(expression: String, result: String) {
        historyDao.insertItem(
            item = History(
                expression = expression,
                result = result,
                note = "",
                timestamp = System.currentTimeMillis(),
                isEditedExpression = false,
                timestampEditedExpression = null
            )
        )
    }

}
