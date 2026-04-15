package com.bugiman.composercalculator.core.managers

import android.content.Context
import android.media.AudioManager

class SoundManager(context: Context) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun playClick() {
        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f)
    }
}