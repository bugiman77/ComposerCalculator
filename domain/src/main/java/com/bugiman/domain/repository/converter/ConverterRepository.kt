package com.bugiman.domain.repository.converter

import com.bugiman.domain.models.converter.ConverterType

interface ConverterRepository {

    suspend fun convert(
        type: ConverterType,
        value: Double,
        from: String,
        to: String
    ): Result<Double>

//    suspend fun getUnits(type: ConverterType): List<String>
}