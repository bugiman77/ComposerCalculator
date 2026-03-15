package com.bugiman.domain.usecase.calculation

import com.bugiman.domain.repository.calculation.CalculationRepository

class CalculateExpressionUseCase(
    private val repository: CalculationRepository
) {
    suspend operator fun invoke(expression: String): Result<String> {
        // Базовая проверка: не отправляем пустую строку
        if (expression.isBlank()) return Result.failure(Exception("Пустое выражение"))

        return repository.calculate(expression)
    }
}