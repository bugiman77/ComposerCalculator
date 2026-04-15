package com.bugiman.composercalculator.data

import com.bugiman.composercalculator.core.managers.SoundManager
import com.bugiman.composercalculator.core.managers.VibrationManager
import com.bugiman.domain.repository.feedback.FeedbackRepository
import com.bugiman.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first

class FeedbackRepositoryImpl(
    private val soundManager: SoundManager,
    private val vibrationManager: VibrationManager,
    private val settingsRepository: SettingsRepository
) : FeedbackRepository {

    override fun triggerClick() {
        // Мы используем runBlocking только потому, что менеджеры обычно синхронные,
        // а настройки во Flow. Для клика это допустимо.
        runBlocking {
            val settings = settingsRepository.getSettings().first()
            if (settings.isPlaySound) soundManager.playClick()
            if (settings.isPlayVibration) vibrationManager.vibrateClick()
        }
    }
}