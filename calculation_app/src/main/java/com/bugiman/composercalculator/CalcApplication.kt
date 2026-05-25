package com.bugiman.composercalculator

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.room.Room
import com.bugiman.composercalculator.core.managers.SoundManager
import com.bugiman.composercalculator.core.managers.VibrationManager
import com.bugiman.composercalculator.data.CalculationRepositoryImpl
import com.bugiman.composercalculator.data.FeedbackRepositoryImpl
import com.bugiman.data.datasource.remote.CurrencyApiDataSource
import com.bugiman.data.local.database.AppDatabase
import com.bugiman.data.network.CurrencyApiService
import com.bugiman.data.proto.SettingsProto
import com.bugiman.data.repository.converter.ConvertRepositoryImpl
import com.bugiman.data.repository.history.HistoryRepositoryImpl
import com.bugiman.data.repository.settings.SettingsRepositoryImpl
import com.bugiman.data.serializer.SettingsProtoSerializer
import com.bugiman.domain.usecase.calculation.CalculateExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildBracketUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDecimalUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildDigitUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildOperatorUseCase
import com.bugiman.domain.usecase.calculation.CalculationBuildZeroUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveExpressionUseCase
import com.bugiman.domain.usecase.calculation.CalculationRemoveLastCharUseCase
import com.bugiman.domain.usecase.calculation.CalculationSettingsAllGetUseCase
import com.bugiman.domain.usecase.convert.ConvertGetFormattedConversionUseCase
import com.bugiman.domain.usecase.convert.ConvertSwapCurrenciesUseCase
import com.bugiman.domain.usecase.convert.ConvertValueUseCase
import com.bugiman.domain.usecase.feedback.FeedbackTriggerUseCase
import com.bugiman.domain.usecase.history.HistoryAllDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryAllGetUseCase
import com.bugiman.domain.usecase.history.HistoryCountUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyExpressionUseCase
import com.bugiman.domain.usecase.history.HistoryItemCopyResultUseCase
import com.bugiman.domain.usecase.history.HistoryItemDeleteUseCase
import com.bugiman.domain.usecase.history.HistoryItemEditUseCase
import com.bugiman.domain.usecase.history.HistoryItemSaveUseCase
import com.bugiman.domain.usecase.history.HistoryItemUpdateNoteUseCase
import com.bugiman.domain.usecase.settings.SettingsAllGetUseCase
import com.bugiman.domain.usecase.settings.SettingsItemUpdateUseCase
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java
import com.bugiman.python.PythonInitializer

//@HiltAndroidApp
private val Context.settingsDataStore: DataStore<SettingsProto> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsProtoSerializer
)

/*
// CalcApplication.kt
class CalcApplication : Application() {
    // === CALCULATION USE CASES ===
    val calculateExpressionUseCase: CalculateExpressionUseCase by lazy { /* ... */ }
    val historyItemSaveUseCase: HistoryItemSaveUseCase by lazy { /* ... */ }
    val historyAllDeleteUseCase: HistoryAllDeleteUseCase by lazy { /* ... */ }
    val historyAllGetUseCase: HistoryAllGetUseCase by lazy { /* ... */ }
    val feedbackTriggerUseCase: FeedbackTriggerUseCase by lazy { /* ... */ }

    // === BUILDING USE CASES ===
    val buildDigitUseCase: CalculationBuildDigitUseCase by lazy { /* ... */ }
    val buildOperatorUseCase: CalculationBuildOperatorUseCase by lazy { /* ... */ }
    val buildBracketUseCase: CalculationBuildBracketUseCase by lazy { /* ... */ }
    val buildDecimalUseCase: CalculationBuildDecimalUseCase by lazy { /* ... */ }
    val buildZeroUseCase: CalculationBuildZeroUseCase by lazy { /* ... */ }
    val removeLastCharUseCase: CalculationRemoveLastCharUseCase by lazy { /* ... */ }

    // === CONVERT USE CASES ===
    val convertGetFormattedConversionUseCase: ConvertGetFormattedConversionUseCase by lazy { /* ... */ }
    val convertSwapCurrenciesUseCase: ConvertSwapCurrenciesUseCase by lazy { /* ... */ }

    // === SETTINGS USE CASES ===
    val settingsAllGetUseCase: SettingsAllGetUseCase by lazy { /* ... */ }
    val settingsItemUpdateUseCase: SettingsItemUpdateUseCase by lazy { /* ... */ }
}
* */

class CalcApplication : Application() {
    lateinit var calculateExpressionUseCase: CalculateExpressionUseCase
    lateinit var calculationBuildBracketUseCase: CalculationBuildBracketUseCase
    lateinit var calculationBuildDecimalUseCase: CalculationBuildDecimalUseCase
    lateinit var calculationBuildDigitUseCase: CalculationBuildDigitUseCase
    lateinit var calculationBuildOperatorUseCase: CalculationBuildOperatorUseCase
    lateinit var calculationBuildZeroUseCase: CalculationBuildZeroUseCase
    lateinit var calculationRemoveLastCharUseCase: CalculationRemoveLastCharUseCase
    lateinit var calculationRemoveExpressionUseCase: CalculationRemoveExpressionUseCase
    lateinit var calculationSettingsAllGetUseCase: CalculationSettingsAllGetUseCase

    lateinit var convertValueUseCase: ConvertValueUseCase
    lateinit var convertGetFormattedConversionUseCase: ConvertGetFormattedConversionUseCase
    lateinit var convertSwapCurrenciesUseCase: ConvertSwapCurrenciesUseCase

    lateinit var settingsAllGetUseCase: SettingsAllGetUseCase
    lateinit var settingsItemUpdateUseCase: SettingsItemUpdateUseCase

    lateinit var historyAllDeleteUseCase: HistoryAllDeleteUseCase
    lateinit var historyAllGetUseCase: HistoryAllGetUseCase
    lateinit var historyCountUseCase: HistoryCountUseCase
    lateinit var historyItemCopyExpressionUseCase: HistoryItemCopyExpressionUseCase
    lateinit var historyItemCopyResultUseCase: HistoryItemCopyResultUseCase
    lateinit var historyItemDeleteUseCase: HistoryItemDeleteUseCase
    lateinit var historyItemEditUseCase: HistoryItemEditUseCase
    lateinit var historyItemSaveUseCase: HistoryItemSaveUseCase
    lateinit var historyItemUpdateNoteUseCase: HistoryItemUpdateNoteUseCase

    lateinit var feedbackTriggerUseCase: FeedbackTriggerUseCase

    lateinit var buildDigitUseCase: CalculationBuildDigitUseCase
    lateinit var buildOperatorUseCase: CalculationBuildOperatorUseCase
    lateinit var buildBracketUseCase: CalculationBuildBracketUseCase
    lateinit var buildDecimalUseCase: CalculationBuildDecimalUseCase
    lateinit var buildZeroUseCase: CalculationBuildZeroUseCase
    lateinit var removeLastCharUseCase: CalculationRemoveLastCharUseCase

    override fun onCreate() {
        super.onCreate()

        PythonInitializer.initialize(this)

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        val calculationRepositoryImpl = CalculationRepositoryImpl()

        val settingsRepositoryImpl = SettingsRepositoryImpl(
            settingsDataStore
        )

        calculateExpressionUseCase = CalculateExpressionUseCase(repository = calculationRepositoryImpl)
        calculationBuildBracketUseCase = CalculationBuildBracketUseCase()
        calculationBuildDecimalUseCase = CalculationBuildDecimalUseCase()
        calculationBuildDigitUseCase = CalculationBuildDigitUseCase()
        calculationBuildOperatorUseCase = CalculationBuildOperatorUseCase()
        calculationBuildZeroUseCase = CalculationBuildZeroUseCase()
        calculationRemoveLastCharUseCase = CalculationRemoveLastCharUseCase()
        calculationRemoveExpressionUseCase = CalculationRemoveExpressionUseCase()
        calculationSettingsAllGetUseCase =
            CalculationSettingsAllGetUseCase(repository = settingsRepositoryImpl)

        buildDigitUseCase = CalculationBuildDigitUseCase()
        buildOperatorUseCase = CalculationBuildOperatorUseCase()
        buildBracketUseCase = CalculationBuildBracketUseCase()
        buildDecimalUseCase = CalculationBuildDecimalUseCase()
        buildZeroUseCase = CalculationBuildZeroUseCase()
        removeLastCharUseCase = CalculationRemoveLastCharUseCase()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val currencyApiService = retrofit.create(CurrencyApiService::class.java)
        val currencyApiDataSource = CurrencyApiDataSource(currencyApiService)

        val converterRepositoryImpl = ConvertRepositoryImpl(
            currencyApiService = currencyApiService,
            apiKey = "8d9f1a2bf35e857c45b22c1c"
        )

        convertValueUseCase = ConvertValueUseCase(repository = converterRepositoryImpl)

        convertGetFormattedConversionUseCase = ConvertGetFormattedConversionUseCase(convertValueUseCase = convertValueUseCase)
        convertSwapCurrenciesUseCase = ConvertSwapCurrenciesUseCase()

        settingsAllGetUseCase = SettingsAllGetUseCase(settingsRepositoryImpl)
        settingsItemUpdateUseCase = SettingsItemUpdateUseCase(settingsRepositoryImpl)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "calculator_db" // Имя файла базы данных
        ).build()

        val historyDao = database.historyDao()

        val historyRepositoryImpl = HistoryRepositoryImpl(
            historyDao = historyDao
        )

        historyAllDeleteUseCase = HistoryAllDeleteUseCase(repository = historyRepositoryImpl)
        historyAllGetUseCase = HistoryAllGetUseCase(repository = historyRepositoryImpl)
        historyCountUseCase = HistoryCountUseCase(repository = historyRepositoryImpl)
        historyItemCopyExpressionUseCase =
            HistoryItemCopyExpressionUseCase(repository = historyRepositoryImpl)
        historyItemCopyResultUseCase =
            HistoryItemCopyResultUseCase(repository = historyRepositoryImpl)
        historyItemDeleteUseCase = HistoryItemDeleteUseCase(repository = historyRepositoryImpl)
        historyItemEditUseCase = HistoryItemEditUseCase(repository = historyRepositoryImpl)
        historyItemSaveUseCase = HistoryItemSaveUseCase(repository = historyRepositoryImpl)
        historyItemUpdateNoteUseCase =
            HistoryItemUpdateNoteUseCase(repository = historyRepositoryImpl)

        val soundManager = SoundManager(this)
        val vibrationManager = VibrationManager(this)

        val feedbackRepositoryImpl =
            FeedbackRepositoryImpl(
                soundManager = soundManager,
                vibrationManager = vibrationManager,
                settingsRepository = settingsRepositoryImpl
            )
        feedbackTriggerUseCase = FeedbackTriggerUseCase(
            feedbackRepository = feedbackRepositoryImpl,
            settingsRepository = settingsRepositoryImpl
        )
    }
}