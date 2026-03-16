package com.bugiman.domain.usecase.feedback

import com.bugiman.domain.repository.feedback.FeedbackRepository
import com.bugiman.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.first

class TriggerFeedbackUseCase(
    private val feedbackRepository: FeedbackRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke() {
        // Получаем текущий снимок настроек
        val settings = settingsRepository.getSettings().first()

        // Если хоть что-то включено — дергаем репозиторий
        if (settings.isPlaySound || settings.isPlayVibration) {
            feedbackRepository.triggerClick()
        }
    }

}