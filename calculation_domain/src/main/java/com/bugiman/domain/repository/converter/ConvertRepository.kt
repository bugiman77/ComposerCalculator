package com.bugiman.domain.repository.converter

import com.bugiman.domain.models.converter.ConvertType

interface ConvertRepository {

    suspend fun convert(
        type: ConvertType,
        value: Double,
        from: String,
        to: String
    ): Result<Double>

}