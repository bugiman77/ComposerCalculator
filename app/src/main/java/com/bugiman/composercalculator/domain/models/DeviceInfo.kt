package com.bugiman.composercalculator.domain.models

import android.os.Build

data class DeviceInfo(
    val deviceName: String = Build.MODEL,
    val manufacturer: String = Build.MANUFACTURER,
    val androidVersion: String = Build.VERSION.RELEASE,
    val apiLevel: Int = Build.VERSION.SDK_INT
)
