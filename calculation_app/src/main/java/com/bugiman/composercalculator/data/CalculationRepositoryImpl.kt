package com.bugiman.composercalculator.data

import com.bugiman.domain.repository.calculation.CalculationRepository
import com.bugiman.python.PythonInitializer
import com.chaquo.python.PyException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculationRepositoryImpl : CalculationRepository {

    override suspend fun calculate(expression: String): Result<String> =
        withContext(Dispatchers.Default) {
            try {
                val python = PythonInitializer.getInstance()
                val pythonFile = python.getModule("calculator")
                val result = pythonFile.callAttr("evaluate_expression", expression).toString()
                Result.success(result)
            } catch (e: PyException) {
                Result.failure(Exception("Ошибка вычислений: ${e.message}"))
            } catch (e: Exception) {
                Result.failure(Exception("Критическая ошибка: ${e.message}"))
            }
        }
}