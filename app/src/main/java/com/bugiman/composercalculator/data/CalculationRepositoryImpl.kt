package com.bugiman.composercalculator.data

import com.bugiman.domain.repository.calculation.CalculationRepository
import com.chaquo.python.PyException
import com.chaquo.python.PyObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculationRepositoryImpl(
    private val pythonModule: PyObject
) : CalculationRepository {

    override suspend fun calculate(expression: String): Result<String> = withContext(Dispatchers.Default) {
        try {
            // Вызываем функцию из вашего .py файла
            val result = pythonModule.callAttr("evaluate_expression", expression).toString()
            Result.success(result)
        } catch (e: PyException) {
            Result.failure(Exception("Ошибка вычислений"))
        } catch (e: Exception) {
            Result.failure(Exception("Критическая ошибка"))
        }
    }
}