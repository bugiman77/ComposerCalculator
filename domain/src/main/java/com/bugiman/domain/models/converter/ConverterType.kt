package com.bugiman.domain.models.converter

sealed class ConverterType(val key: String) {
    object Angle: ConverterType(key = "angle")
    object Area: ConverterType(key = "area")
    object Currency : ConverterType(key = "currency")
    object Data : ConverterType(key = "data")
    object Energy : ConverterType(key = "energy")
    object Force : ConverterType(key = "force")
    object Fuel : ConverterType(key = "fuel")
    object Length : ConverterType(key = "length")
    object Power : ConverterType(key = "power")
    object Pressure : ConverterType(key = "pressure")
    object Speed : ConverterType(key = "speed")
    object Temperature : ConverterType(key = "temperature")
    object Time : ConverterType(key = "time")
    object Volume : ConverterType(key = "volume")
    object Weight : ConverterType(key = "weight")
}