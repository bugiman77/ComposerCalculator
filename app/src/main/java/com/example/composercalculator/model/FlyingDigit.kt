package com.example.composercalculator.model

import androidx.compose.ui.geometry.Offset

data class FlyingDigit(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val startOffset: Offset
)
