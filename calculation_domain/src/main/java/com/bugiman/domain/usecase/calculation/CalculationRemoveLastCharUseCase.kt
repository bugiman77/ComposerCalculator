package com.bugiman.domain.usecase.calculation

class CalculationRemoveLastCharUseCase {
    operator fun invoke(current: String): String {
        if (current.isEmpty()) return ""
        return current.dropLast(1)
    }
}