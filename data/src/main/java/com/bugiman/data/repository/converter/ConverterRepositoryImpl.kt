package com.bugiman.data.repository.converter

import com.bugiman.domain.models.converter.ConverterType
import com.bugiman.domain.repository.converter.ConverterRepository

class ConverterRepositoryImpl(

) : ConverterRepository {
    override suspend fun convert(
        type: ConverterType,
        value: Double,
        from: String,
        to: String
    ): Result<Double> {
        TODO("Not yet implemented")
    }
}