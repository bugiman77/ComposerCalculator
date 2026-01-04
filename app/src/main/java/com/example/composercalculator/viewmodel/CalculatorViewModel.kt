package com.example.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.composercalculator.core.managers.SoundManager
import com.example.composercalculator.core.managers.VibrationManager
import com.example.composercalculator.data.local.db.AppDatabaseHistory
import com.example.composercalculator.data.local.db.dao.HistoryDao
import com.example.composercalculator.data.local.db.entity.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalculatorViewModel(
    application: Application,
    private val settingsViewModel: SettingsViewModel,
    private val soundManager: SoundManager,
    private val vibrationManager: VibrationManager
) : AndroidViewModel(application) {

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

    private val _history = MutableStateFlow<List<History>>(value = emptyList())
    val history: StateFlow<List<History>> = _history

    init {
        viewModelScope.launch {
            loadHistoryLast()
            loadHistoryAll()
        }
    }

    suspend fun loadHistoryLast() {
        val history = withContext(context = Dispatchers.IO) {
            historyDao.getHistoryLast() // Run the DB query on a background thread
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

    suspend fun loadHistoryAll() {
        historyDao.getHistoryAll().collect { list ->
            _history.value = list
        }
    }

    fun deleteHistoryItemAll() {
        viewModelScope.launch {
            historyDao.deleteAll()
            loadHistoryAll()
        }
    }

    fun deleteHistoryItem(itemHistory: History) {
        viewModelScope.launch {
            historyDao.deleteItem(item = itemHistory)
            loadHistoryAll()
        }
    }

    fun onInputDigit(inputDigit: String) {

        val currentExpression = _expression.value

        // Проверяем, не является ли последний символ закрывающей скобкой
        if (currentExpression.isNotEmpty() && currentExpression.last() == ')') {
            // Если была скобка, добавляем знак умножения перед цифрой
            _expression.value += "*$inputDigit"
        } else {
            // В обычном случае просто добавляем цифру
            _expression.value += inputDigit
        }

        if (settingsViewModel.playSound.value) {
            soundManager.playClick()
        }
        if (settingsViewModel.playVibration.value) {
            vibrationManager.vibrateClick()
        }
    }

    fun onInputMathematicalOperations(inputOperation: String) {

        if (settingsViewModel.playSound.value) {
            soundManager.playClick()
        }
        if (settingsViewModel.playVibration.value) {
            vibrationManager.vibrateClick()
        }

        val currentExpression = _expression.value
        if (currentExpression.isEmpty()) return

        val operators =
            setOf('+', '-', '/', '*', '%') // Выносим операторы в Set для быстрого поиска
        val lastChar = currentExpression.last()

        // 1. Обработка ввода точки
        if (inputOperation == ".") {
            // Находим часть строки после последнего оператора (текущее число)
            val lastNumber = currentExpression.split('+', '-', '*', '/', '%').last()

            // Если в текущем числе уже есть точка или последний символ — оператор, игнорируем ввод
            if (lastNumber.contains(char = '.') || lastChar in operators) {
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
                _expression.value = currentExpression.dropLast(n = 1) + inputOperation
            } else {
                // Если последний символ — цифра, добавляем оператор
                _expression.value += inputOperation
            }
        }
    }

    fun onInputNote(itemHistory: History) {

    }

    fun onToggleSign() {
        val currentExpression = _expression.value
        if (currentExpression.isEmpty()) return

        // Регулярное выражение находит последнее число, включая отрицательные в скобках
        // Ищет либо (-число), либо просто число в конце строки
        val lastTokenRegex = Regex(pattern = """(\(-\d+\.?\d*\)|(?<!\d)\d+\.?\d*)$""")
        val matchResult = lastTokenRegex.find(input = currentExpression)

        if (matchResult != null) {
            val lastToken = matchResult.value
            val prefix = currentExpression.take(n = matchResult.range.first)

            val updatedToken = if (lastToken.startsWith(prefix = "(-")) {
                // Если число уже отрицательное (в скобках), убираем скобки и минус
                lastToken.removeSurrounding(prefix = "(-", suffix = ")")
            } else {
                // Если число положительное, оборачиваем в (-...)
                "(-$lastToken)"
            }

            if (settingsViewModel.playSound.value) {
                soundManager.playClick()
            }
            if (settingsViewModel.playVibration.value) {
                vibrationManager.vibrateClick()
            }

            _expression.value = prefix + updatedToken
        }

    }

    fun removeLastCharacter() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(n = 1)

            if (settingsViewModel.playSound.value) {
                soundManager.playClick()
            }
            if (settingsViewModel.playVibration.value) {
                vibrationManager.vibrateClick()
            }

        }
    }

    fun clearExpression() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = ""

            if (settingsViewModel.playSound.value) {
                soundManager.playClick()
            }
            if (settingsViewModel.playVibration.value) {
                vibrationManager.vibrateClick()
            }

        }
    }

    fun editingExpression(itemHistory: History) {
        _expression.value = itemHistory.expression
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

        if (settingsViewModel.playSound.value) {
            soundManager.playClick()
        }
        if (settingsViewModel.playVibration.value) {
            vibrationManager.vibrateClick()
        }

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
        } catch (_: PyException) {
            "Ошибка вычислений"
        } catch (_: Exception) {
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
