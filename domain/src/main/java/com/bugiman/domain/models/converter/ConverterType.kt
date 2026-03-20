package com.bugiman.domain.models.converter

sealed class ConverterType(val key: String) {
    object ANGLE: ConverterType(key = "angle")
    object AREA: ConverterType(key = "area")
    object CURRENCY : ConverterType(key = "currency")
    object DATA : ConverterType(key = "data")
    object ENERGY : ConverterType(key = "energy")
    object FORCE : ConverterType(key = "force")
    object FUEL : ConverterType(key = "fuel")
    object LENGTH : ConverterType(key = "length")
    object POWER : ConverterType(key = "power")
    object PRESSURE : ConverterType(key = "pressure")
    object SPEED : ConverterType(key = "speed")
    object TEMPERATURE : ConverterType(key = "temperature")
    object TIME : ConverterType(key = "time")
    object VOLUME : ConverterType(key = "volume")
    object WEIGHT : ConverterType(key = "weight")
}