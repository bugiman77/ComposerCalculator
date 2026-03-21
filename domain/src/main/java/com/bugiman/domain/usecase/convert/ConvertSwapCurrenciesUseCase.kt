package com.bugiman.domain.usecase.convert

import com.bugiman.domain.models.converter.ConvertSwapResult

class ConvertSwapCurrenciesUseCase {
    operator fun invoke(from: String, to: String): ConvertSwapResult {
        return ConvertSwapResult(from = to, to = from)
    }
}