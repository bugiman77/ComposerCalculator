package com.bugiman.data.repository.calculation

import com.bugiman.domain.repository.calculation.CalculationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculationRepositoryImpl(
    private val pythonModule: PyModule // Получаем уже готовый модуль
) : CalculationRepository {

    override suspend fun calculate(expression: String): Result<String> = withContext(Dispatchers.Default) {
        try {
            // Вызываем функцию из вашего calculator.py
            val result = pythonModule.callAttr("evaluate_expression", expression).toString()
            Result.success(result)
        } catch (e: PyException) {
            Result.failure(Exception("Ошибка вычислений"))
        } catch (e: Exception) {
            Result.failure(Exception("Критическая ошибка"))
        }
    }
}