package com.bugiman.domain.usecase.convert

import com.bugiman.domain.models.converter.ConverterType
import com.bugiman.domain.repository.converter.ConverterRepository

class ConvertValueUseCase(
    private val repository: ConverterRepository
) {
    /**
     * Выполняет конвертацию значения.
     * Возвращает Result<Double>, чтобы ViewModel могла обработать ошибку (например, нет сети).
     */
    suspend operator fun invoke(
        type: ConverterType,
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