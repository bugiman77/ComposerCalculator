package com.bugiman.domain.models

data class DeviceInfo(
    val deviceName: String,
    val manufacturer: String,
    val androidVersion: String,
    val apiLevel: Int
)
