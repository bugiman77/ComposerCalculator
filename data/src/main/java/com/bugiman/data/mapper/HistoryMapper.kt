package com.bugiman.data.mapper

import com.bugiman.data.local.entity.HistoryEntity
import com.bugiman.domain.models.HistoryModel

fun HistoryEntity.toDomain(): HistoryModel {
    return HistoryModel(
        id = id,
        expression = expression,
        result = result,
        note = note,
        timestamp = timestamp
    )
}

fun HistoryModel.toEntity(): HistoryEntity {
    return HistoryEntity(
        id = id,
        expression = expression,
        result = result,
        note = note,
        timestamp = timestamp
    )
}