package com.bugiman.domain.usecase.feedback

import com.bugiman.domain.repository.feedback.FeedbackRepository
import com.bugiman.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.first

class FeedbackTriggerUseCase(
    private val feedbackRepository: FeedbackRepository,
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke() {
        val settings = settingsRepository.getSettings().first()

        if (settings.isPlaySound || settings.isPlayVibration) {
            feedbackRepository.triggerClick()
        }
    }

}