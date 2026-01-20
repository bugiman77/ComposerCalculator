package com.bugiman.composercalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.bugiman.composercalculator.core.managers.SoundManager
import com.bugiman.composercalculator.core.managers.VibrationManager
import com.bugiman.composercalculator.data.local.db.AppDatabaseHistory
import com.bugiman.composercalculator.data.local.db.dao.HistoryDao
import com.bugiman.composercalculator.data.local.db.dao.InputStateDao
import com.bugiman.composercalculator.data.local.db.entity.History
import com.bugiman.composercalculator.data.local.db.entity.InputState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel(
    application: Application,
    private val settingsViewModel: SettingsViewModel,
    private val soundManager: SoundManager,
    private val vibrationManager: VibrationManager
) : AndroidViewModel(application) {

    private val historyDao: HistoryDao =
        AppDatabaseHistory.getDatabase(context = application).historyDao()

    private val inputStateDao: InputStateDao =
        AppDatabaseHistory.getDatabase(context = application).inputStateDao()

    private val _id = MutableStateFlow(value = "")
    val id: StateFlow<String> = _id.asStateFlow()

    private val _expression = MutableStateFlow(value = "")
    val expression: StateFlow<String> = _expression.asStateFlow()

    private val _note = MutableStateFlow(value = "")
    val note: StateFlow<String> = _note.asStateFlow()

    private val _history = MutableStateFlow<List<History>>(value = emptyList())
    val history: StateFlow<List<History>> = _history

    init {
        viewModelScope.launch {
            loadHistoryAll()
        }
        viewModelScope.launch(Dispatchers.IO) {
            loadLastValue()
        }
    }

    private suspend fun loadHistoryAll() {
        historyDao.getHistoryAll().collect { list ->
            _history.value = list
        }
    }

    private suspend fun loadLastValue() {
        inputStateDao.getInputState().collect { savedState ->
            savedState?.let {
                _expression.value = it.currentText
            }
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

        if (currentExpression.isNotEmpty() && currentExpression.last() == ')') {
            _expression.value += "*$inputDigit"
            updateExpression(newExpression = _expression.value)
            triggerFeedback()
            return
        }

        if (currentExpression.isNotEmpty()) {
            val lastChar = currentExpression.last()

            if (lastChar == '0') {
                val parts = currentExpression.split('+', '-', '*', '/', '%', '(')
                val lastNumber = parts.last()

                if (lastNumber == "0") {
                    _expression.value += ".$inputDigit"
                    updateExpression(newExpression = _expression.value)
                    triggerFeedback()
                    return
                }
            }
        }

        _expression.value += inputDigit
        updateExpression(newExpression = _expression.value)
        triggerFeedback()
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

        val operators = setOf('+', '-', '/', '*', '%')
        val lastChar = currentExpression.last()

        if (inputOperation == ".") {
            val lastNumber = currentExpression.split('+', '-', '*', '/', '%').last()
            if (lastNumber.contains(char = '.') || lastChar in operators) {
                return
            }
            _expression.value += "."
            return
        }

        if (inputOperation.first() in operators) {
            if (lastChar == '.') {
                return
            }

            if (lastChar in operators) {
                _expression.value = currentExpression.dropLast(n = 1) + inputOperation
            } else {
                // Если последний символ — цифра, сначала "причесываем" число
                val trimmedExpression = trimTrailingZerosFromLastNumber(currentExpression)
                _expression.value = trimmedExpression + inputOperation
            }
        }

        updateExpression(newExpression = _expression.value)
    }

    suspend fun onInputNote(itemHistory: History, newNote: String) {
        historyDao.updateNote(itemId = itemHistory.id, newNote = newNote)
    }

    fun onToggleSign() {
        val currentExpression = _expression.value

        if (currentExpression.isEmpty()) return

        val lastTokenRegex = Regex(pattern = """(\(-\d+\.?\d*\)|(?<!\d)\d+\.?\d*)$""")
        val matchResult = lastTokenRegex.find(input = currentExpression)

        if (matchResult != null) {
            val lastToken = matchResult.value
            val prefix = currentExpression.take(n = matchResult.range.first)

            val updatedToken = if (lastToken.startsWith(prefix = "(-")) {
                lastToken.removeSurrounding(prefix = "(-", suffix = ")")
            } else {
                "(-$lastToken)"
            }

            if (settingsViewModel.playSound.value) {
                soundManager.playClick()
            }
            if (settingsViewModel.playVibration.value) {
                vibrationManager.vibrateClick()
            }

            _expression.value = prefix + updatedToken

            updateExpression(newExpression = _expression.value)

        }

    }

    fun removeLastCharacter() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(n = 1)

            updateExpression(newExpression = _expression.value)

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

            updateExpression(newExpression = _expression.value)

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

        updateExpression(newExpression = _expression.value)

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

        _expression.value = calculationResult

        if (settingsViewModel.playSound.value) {
            soundManager.playClick()
        }
        if (settingsViewModel.playVibration.value) {
            vibrationManager.vibrateClick()
        }

        saveToDatabase(currentExpression, calculationResult)

        updateExpression(newExpression = _expression.value)

    }

    private fun trimTrailingZerosFromLastNumber(expression: String): String {
        // Находим последнее число в строке
        val operators = charArrayOf('+', '-', '*', '/', '%')
        val lastOperatorIndex = expression.lastIndexOfAny(operators)

        val prefix = if (lastOperatorIndex != -1) expression.substring(0, lastOperatorIndex + 1) else ""
        var lastNumber = if (lastOperatorIndex != -1) expression.substring(lastOperatorIndex + 1) else expression

        // Удаляем нули только если в числе есть точка
        if (lastNumber.contains('.')) {
            // Удаляем все нули с конца, а затем саму точку, если она осталась в конце
            lastNumber = lastNumber.trimEnd('0').trimEnd('.')
        }

        return prefix + lastNumber
    }

    private fun updateExpression(newExpression: String) {
        _expression.value = newExpression
        viewModelScope.launch(context = Dispatchers.IO) {
            inputStateDao.saveInput(state = InputState(currentText = newExpression))
        }
    }

    private fun triggerFeedback() {
        if (settingsViewModel.playSound.value) {
            soundManager.playClick()
        }
        if (settingsViewModel.playVibration.value) {
            vibrationManager.vibrateClick()
        }
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
            )
        )
    }

}
