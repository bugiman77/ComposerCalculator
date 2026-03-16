package com.bugiman.composercalculator

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.bugiman.composercalculator.core.managers.SoundManager
import com.bugiman.composercalculator.core.managers.VibrationManager
import com.bugiman.composercalculator.data.CalculationRepositoryImpl
import com.bugiman.composercalculator.data.FeedbackRepositoryImpl
import com.bugiman.data.proto.SettingsProto
import com.bugiman.data.repository.settings.SettingsRepositoryImpl
import com.bugiman.data.serializer.SettingsProtoSerializer
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.feedback.TriggerFeedbackUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase
import com.bugiman.domain.usecase.settings.SettingsAllGetUseCase
import com.bugiman.domain.usecase.settings.SettingsItemUpdateUseCase
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

//@HiltAndroidApp
private val Context.settingsDataStore: DataStore<SettingsProto> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsProtoSerializer
)

class Application : Application() {
    // Эти переменные будут доступны во всем приложении через context
    lateinit var settingsAllGetUseCase: SettingsAllGetUseCase
    lateinit var settingsItemUpdateUseCase: SettingsItemUpdateUseCase
    lateinit var calculateExpressionUseCase: CalculateExpressionUseCase
    lateinit var triggerFeedbackUseCase: TriggerFeedbackUseCase
    lateinit var historyItemSaveUseCase: HistoryItemSaveUseCase

    override fun onCreate() {
        super.onCreate()

        // 1. Инициализация Python платформы
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        // 2. Получаем модуль Python (файл calculator.py)
        val py = Python.getInstance()
        val pythonModule = py.getModule("calculator")

        val calculationRepositoryImpl = CalculationRepositoryImpl(pythonModule)

        calculateExpressionUseCase =
            CalculateExpressionUseCase(repository = calculationRepositoryImpl)

        // Склеиваем слои: Data -> Domain
        val settingsRepositoryImpl = SettingsRepositoryImpl(
            settingsDataStore
        )

        settingsAllGetUseCase = SettingsAllGetUseCase(
            settingsRepositoryImpl
        )
        settingsItemUpdateUseCase = SettingsItemUpdateUseCase(
            settingsRepositoryImpl
        )

        val soundManager = SoundManager(this)
        val vibrationManager = VibrationManager(this)

        val feedbackRepositoryImpl =
            FeedbackRepositoryImpl(
                soundManager = soundManager,
                vibrationManager = vibrationManager,
                settingsRepository = settingsRepositoryImpl
            )
        triggerFeedbackUseCase = TriggerFeedbackUseCase(
            feedbackRepository = feedbackRepositoryImpl,
            settingsRepository = settingsRepositoryImpl
        )
    }
}