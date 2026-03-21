package com.bugiman.domain.models.converter

sealed class ConvertType(val key: String) {
    object ANGLE: ConvertType(key = "angle")
    object AREA: ConvertType(key = "area")
    object CURRENCY : ConvertType(key = "currency")
    object DATA : ConvertType(key = "data")
    object ENERGY : ConvertType(key = "energy")
    object FORCE : ConvertType(key = "force")
    object FUEL : ConvertType(key = "fuel")
    object LENGTH : ConvertType(key = "length")
    object POWER : ConvertType(key = "power")
    object PRESSURE : ConvertType(key = "pressure")
    object SPEED : ConvertType(key = "speed")
    object TEMPERATURE : ConvertType(key = "temperature")
    object TIME : ConvertType(key = "time")
    object VOLUME : ConvertType(key = "volume")
    object WEIGHT : ConvertType(key = "weight")
}