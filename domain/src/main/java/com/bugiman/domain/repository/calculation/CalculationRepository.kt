package com.bugiman.domain.repository.calculation

interface CalculationRepository {
    suspend fun calculate(expression: String): Result<String>
}