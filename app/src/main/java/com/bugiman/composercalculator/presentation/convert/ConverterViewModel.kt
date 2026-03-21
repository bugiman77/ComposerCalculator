package com.bugiman.composercalculator.presentation.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugiman.domain.models.converter.ConverterType
import com.bugiman.domain.usecase.convert.ConvertValueUseCase
import com.bugiman.domain.usecase.convert.GetFormattedConversionUseCase
import com.bugiman.domain.usecase.convert.SwapCurrenciesUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class ConverterViewModel(
    private val getFormattedConversionUseCase: GetFormattedConversionUseCase,
    private val swapCurrenciesUseCase: SwapCurrenciesUseCase,
    private val feedbackTriggerUseCase: FeedbackTriggerUseCase
) : ViewModel() {

    val amount = MutableStateFlow("1")
    val fromCurrency = MutableStateFlow("USD")
    val toCurrency = MutableStateFlow("RUB")

    private val _result = MutableStateFlow("0.0")
    val result: StateFlow<String> = _result.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    @OptIn(FlowPreview::class)
    private val conversionTrigger = combine(amount, fromCurrency, toCurrency) { a, f, t ->
        Triple(a, f, t)
    }.debounce(500)

    init {
        viewModelScope.launch {
            conversionTrigger.collect { (a, f, t) ->
                performConversion(a, f, t)
            }
        }
    }

    private suspend fun performConversion(a: String, f: String, t: String) {
        _isLoading.value = true

        val response = getFormattedConversionUseCase(amount = a, from = f, to = t)

        response.onSuccess {
            _result.value = it
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
            feedbackTriggerUseCase()
            val swapped = swapCurrenciesUseCase(fromCurrency.value, toCurrency.value)
            fromCurrency.value = swapped.from
            toCurrency.value = swapped.to
        }
    }
}