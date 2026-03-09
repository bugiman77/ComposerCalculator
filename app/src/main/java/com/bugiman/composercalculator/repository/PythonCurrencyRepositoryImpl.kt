package com.bugiman.composercalculator.repository

import android.content.Context
import com.bugiman.domain.models.converter.ConverterType
import com.bugiman.domain.repository.converter.ConverterRepository
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PythonCurrencyRepositoryImpl(
    private val context: Context
) : ConverterRepository {

    override suspend fun convert(
        type: ConverterType,
        value: Double,
        from: String,
        to: String
    ): Result<Double> {
        return withContext(Dispatchers.IO) {
            try {
                if (!Python.isStarted()) {
                    Python.start(AndroidPlatform(context))
                }
                val py = Python.getInstance()
                val module = py.getModule("currency_script")
                val result = module.callAttr("convert_logic", value, from, to).toDouble()
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

/*    override suspend fun getUnits(type: ConverterType): List<String> {
    }*/
}