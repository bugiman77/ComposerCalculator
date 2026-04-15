package com.bugiman.domain.usecase.convert

import com.bugiman.domain.models.converter.ConvertType
import com.bugiman.domain.repository.converter.ConvertRepository

class ConvertValueUseCase(
    private val repository: ConvertRepository
) {
    /**
     * Выполняет конвертацию значения.
     * Возвращает Result<Double>, чтобы ViewModel могла обработать ошибку (например, нет сети).
     */
    suspend operator fun invoke(
        type: ConvertType,
        value: Double,
        from: String,
        to: String
    ): Result<Double> {

        // Бизнес-логика: если единицы измерения одинаковы, возвращаем успех сразу
        if (from == to) return Result.success(value)

        // Вызываем репозиторий
        return repository.convert(type, value, from, to)
    }
}