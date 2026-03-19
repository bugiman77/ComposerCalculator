package com.bugiman.composercalculator.presentation.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugiman.domain.models.converter.ConverterType
import com.bugiman.domain.usecase.convert.ConvertValueUseCase
import com.bugiman.domain.usecase.feedback.TriggerFeedbackUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ConverterViewModel(
    private val convertValueUseCase: ConvertValueUseCase,
    private val triggerFeedbackUseCase: TriggerFeedbackUseCase
) : ViewModel() {

    val amount = MutableStateFlow("1")
    val fromCurrency = MutableStateFlow("USD")
    val toCurrency = MutableStateFlow("RUB")

    private val _result = MutableStateFlow("0.0")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    @OptIn(FlowPreview::class)
    val conversionTrigger = combine(amount, fromCurrency, toCurrency) { a, f, t ->
        Triple(a, f, t)
    }.debounce(500) // Задержка, чтобы не спамить запросами при вводе цифр

    init {
        viewModelScope.launch {
            conversionTrigger.collect { (a, f, t) ->
                performConversion(a, f, t)
            }
        }
    }

    private suspend fun performConversion(a: String, f: String, t: String) {
        val valToConvert = a.toDoubleOrNull() ?: 0.0
        if (valToConvert <= 0.0) return

        _isLoading.value = true
        val response = convertValueUseCase(
            type = ConverterType.CURRENCY,
            value = valToConvert,
            from = f,
            to = t
        )

        response.onSuccess {
            _result.value = String.format("%.2f", it)
        }.onFailure {
            _result.value = "Ошибка"
        }
        _isLoading.value = false
    }

    fun onAmountChanged(newAmount: String) {
        amount.value = newAmount
    }

    fun onCurrencySwap() {
        viewModelScope.launch {
            triggerFeedbackUseCase()
            val temp = fromCurrency.value
            fromCurrency.value = toCurrency.value
            toCurrency.value = temp
        }
    }
}