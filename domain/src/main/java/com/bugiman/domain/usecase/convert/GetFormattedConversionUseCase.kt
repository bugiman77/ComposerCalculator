package com.bugiman.domain.usecase.convert

import com.bugiman.domain.models.converter.ConverterType

class GetFormattedConversionUseCase(
    private val convertValueUseCase: ConvertValueUseCase
) {
    suspend operator fun invoke(
        amount: String,
        from: String,
        to: String
    ): Result<String> {
        val valueToConvert = amount.toDoubleOrNull() ?: 0.0
        if (valueToConvert <= 0.0) return Result.success("0.0")

        val response = convertValueUseCase(
            type = ConverterType.CURRENCY,
            value = valueToConvert,
            from = from,
            to = to
        )

        return response.map { resultValue ->
            // Логика форматирования — это бизнес-правило
            String.format("%.2f", resultValue)
        }
    }
}