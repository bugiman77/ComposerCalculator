package com.bugiman.data.repository.converter

import com.bugiman.data.network.CurrencyApiService
import com.bugiman.domain.models.converter.ConvertType
import com.bugiman.domain.repository.converter.ConvertRepository

class ConvertRepositoryImpl(
    private val currencyApiService: CurrencyApiService,
    private val apiKey: String
) : ConvertRepository {

    override suspend fun convert(
        type: ConvertType,
        value: Double,
        from: String,
        to: String
    ): Result<Double> {
        return try {
            when (type) {
                is ConvertType.CURRENCY -> {
                    val response = currencyApiService.getPairRate(apiKey, from, to)
                    // Предположим, что CurrencyPairResponseDto содержит поле 'rate' типа Double
                    val rate = response.conversionRate
                    Result.success(value * rate)
                }
                else -> {
                    // Если тип конвертации не валютный
                    Result.failure(IllegalArgumentException("Unsupported convert type: ${type.key}"))
                }
            }
        } catch (e: Exception) {
            // Ловим любые исключения (сеть, парсинг и т.д.)
            Result.failure(e)
        }
    }
}