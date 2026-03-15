package com.bugiman.composercalculator.presentation.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugiman.domain.models.converter.ConverterType
import com.bugiman.domain.usecase.convert.ConvertValueUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConverterViewModel(
    private val convertValueUseCase: ConvertValueUseCase
) : ViewModel() {

    // Вводные данные
    val amount = MutableStateFlow("1")
    val fromCurrency = MutableStateFlow("USD")
    val toCurrency = MutableStateFlow("RUB")
    private val converterType = MutableStateFlow(ConverterType.Currency) // Укажите нужный тип по умолчанию

    // Состояние результата (Число, Ошибка или Загрузка)
    private val _conversionResult = MutableStateFlow("0.0")
    val conversionResult: StateFlow<String> = _conversionResult.asStateFlow()

    init {
        // Автоматически запускаем конвертацию при любом изменении параметров
        observeChanges()
    }

    @OptIn(FlowPreview::class)
    private fun observeChanges() {
        combine(amount, fromCurrency, toCurrency, converterType) { a, f, t, type ->
            // Упаковываем все параметры в один объект для удобства
            DataPackage(a, f, t, type)
        }
            .debounce(500) // Ждем 500мс после ввода, чтобы не спамить запросами в сеть
            .onEach { data ->
                performConversion(data)
            }
            .launchIn(viewModelScope)
    }

    private suspend fun performConversion(data: DataPackage) {
        val valueToConvert = data.amount.toDoubleOrNull() ?: 0.0

        if (valueToConvert <= 0.0) {
            _conversionResult.value = "0.0"
            return
        }

        // Вызываем UseCase
        val result = convertValueUseCase(
            type = data.type,
            value = valueToConvert,
            from = data.from,
            to = data.to
        )

        // Обрабатываем Result<Double>
        result.onSuccess { convertedValue ->
            _conversionResult.value = String.format("%.2f", convertedValue)
        }.onFailure { error ->
            _conversionResult.value = "Ошибка сети" // Или error.message
        }
    }

    // Методы для UI
    fun onAmountChanged(newAmount: String) { amount.value = newAmount }

    fun selectFromCurrency(code: String) { fromCurrency.value = code }

    fun selectToCurrency(code: String) { toCurrency.value = code }

    // Вспомогательный класс для группировки данных
    private data class DataPackage(val amount: String, val from: String, val to: String, val type: ConverterType)
}