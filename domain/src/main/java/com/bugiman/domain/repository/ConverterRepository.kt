package com.bugiman.domain.repository

import com.bugiman.domain.models.ConverterType

interface ConverterRepository {

    suspend fun convert(
        type: ConverterType,
        value: Double,
        from: String,
        to: String
    ): Double

    suspend fun getUnits(type: ConverterType): List<String>
}