package com.example.composercalculator.data.repository

import android.content.Context
import android.media.AudioManager

enum class SoundMode { NORMAL, VIBRATE, SILENT }

class DeviceSettingsRepository(private val context: Context) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun getCurrentSoundMode(): SoundMode {
        return when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> SoundMode.NORMAL
            AudioManager.RINGER_MODE_VIBRATE -> SoundMode.VIBRATE
            else -> SoundMode.SILENT
        }
    }
}