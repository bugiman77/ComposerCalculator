package com.bugiman.domain.usecase.convert

import com.bugiman.domain.models.converter.SwapResult

class SwapCurrenciesUseCase {
    operator fun invoke(from: String, to: String): SwapResult {
        return SwapResult(from = to, to = from)
    }
}