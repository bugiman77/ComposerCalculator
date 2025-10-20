package com.example.composercalculator.previews

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview (
    name = "Pixel",
    showSystemUi = true,
    device = Devices.PIXEL_7_PRO
)
annotation class PixelPreview

@Preview (
    name = "PIXEL 7 PRO",
    showSystemUi = true,
    device = Devices.PIXEL_7_PRO
)
annotation class Pixel7proPreview

@Preview (
    name = "Nexus 5",
    showSystemUi = true,
    device = Devices.NEXUS_5
)
annotation class Nexus5Preview