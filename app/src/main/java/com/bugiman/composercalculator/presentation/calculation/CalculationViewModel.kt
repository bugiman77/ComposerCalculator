package com.bugiman.composercalculator.presentation.calculation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugiman.domain.models.history.HistoryModel
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDecimalUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDigitUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildOperatorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildZeroUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveLastCharUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val historyItemSaveUseCase: HistoryItemSaveUseCase,
    private val historyAllDeleteUseCase: HistoryAllDeleteUseCase,
    private val historyAllGetUseCase: HistoryAllGetUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase,
    private val buildDigitUseCase: CalculationBuildDigitUseCase,
    private val buildOperatorUseCase: CalculationBuildOperatorUseCase,
    private val buildBracketUseCase: CalculationBuildBracketUseCase,
    private val buildDecimalUseCase: CalculationBuildDecimalUseCase,
    private val buildZeroUseCase: CalculationBuildZeroUseCase,
    private val removeLastCharUseCase: CalculationRemoveLastCharUseCase
) : ViewModel() {

    private val _expression = MutableStateFlow(value = "")
    val expression = _expression.asStateFlow()

    val history: StateFlow<List<HistoryModel>> = historyAllGetUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onInputDigit(digit: String) {
        updateExpression { buildDigitUseCase(current = it, digit) }
    }

    fun onInputZero() {
        updateExpression { buildZeroUseCase(current = it) }
    }

    fun onInputMathOperation(operation: String) {
        updateExpression { buildOperatorUseCase(current = it, operator = operation) }
    }

    fun onInputBrackets(bracket: String) {
        updateExpression { buildBracketUseCase(current = it, bracket = bracket) }
    }

    fun onInputComma() {
        updateExpression { buildDecimalUseCase(current = it) }
    }

    fun removeLast() {
        updateExpression { removeLastCharUseCase(current = it) }
    }

    fun clear() {
        viewModelScope.launch {
            triggerFeedback()
            _expression.value = ""
        }
    }

    fun onCalculate() {
        val currentExpression = _expression.value
        if (currentExpression.isBlank()) return

        viewModelScope.launch {
            triggerFeedback()
            val result = calculateExpressionUseCase(currentExpression)

            result.onSuccess { successValue ->
                _expression.value = successValue
                historyItemSaveUseCase(
                    HistoryModel(
                        expression = currentExpression,
                        result = successValue
                    )
                )
            }.onFailure {
                triggerFeedback()
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            historyAllDeleteUseCase()
        }
    }

    private fun updateExpression(transform: (String) -> String) {
        viewModelScope.launch {
            triggerFeedback()
            _expression.value = transform(_expression.value)
        }
    }

    private suspend fun triggerFeedback() {
        feedbackTriggerUseCase()
    }
}


/*class CalculatorViewModel(
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

    private val _history = MutableStateFlow<List<HistoryModel>>(value = emptyList())
    val history: StateFlow<List<HistoryModel>> = _history

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
        val operators = setOf('+', '-', '/', '*', '%')

        // 1. Обработка ввода точки
        if (inputOperation == ".") {
            // Если поле пустое — превращаем в "0."
            if (currentExpression.isEmpty()) {
                _expression.value = "0."
                updateExpression(_expression.value)
                return
            }

            val lastChar = currentExpression.last()
            val lastNumber = currentExpression.split('+', '-', '*', '/', '%').last()

            // Если в последнем числе уже есть точка — игнорируем
            if (lastNumber.contains('.')) {
                return
            }

            // Если последний символ — оператор, добавляем "0."
            if (lastChar in operators) {
                _expression.value += "0."
            } else {
                // Если последний символ — цифра, просто добавляем точку
                _expression.value += "."
            }

            updateExpression(_expression.value)
            return
        }

        // 2. Обработка ввода операторов
        // Если поле пустое, оператор (кроме, возможно, минуса) обычно не ставится
        if (currentExpression.isEmpty()) return

        val lastChar = currentExpression.last()

        if (inputOperation.first() in operators) {
            // Не даем ставить оператор сразу после точки
            if (lastChar == '.') {
                return
            }

            if (lastChar in operators) {
                // Заменяем старый оператор на новый
                _expression.value = currentExpression.dropLast(1) + inputOperation
            } else {
                // "Причесываем" число и добавляем оператор
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
        val isShowHistoryLastCalculation = settingsViewModel.isShowHistoryLastCalculation.value
        _expression.value = newExpression
        if (isShowHistoryLastCalculation) {
            viewModelScope.launch(context = Dispatchers.IO) {
                inputStateDao.saveInput(state = InputState(currentText = newExpression))
            }
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
            item = HistoryModel(
                expression = expression,
                result = result,
                note = "",
                timestamp = System.currentTimeMillis(),
            )
        )
    }

}*/