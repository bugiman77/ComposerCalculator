package com.bugiman.data.repository.converter

import com.bugiman.domain.models.converter.ConvertType
import com.bugiman.domain.repository.converter.ConvertRepository

class ConvertRepositoryImpl(

) : ConvertRepository {
    override suspend fun convert(
        type: ConvertType,
        value: Double,
        from: String,
        to: String
    ): Result<Double> {
        TODO("Not yet implemented")
    }
}