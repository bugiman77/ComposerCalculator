package com.bugiman.composercalculator.repository

import android.content.Context
import com.bugiman.domain.models.converter.ConvertType
import com.bugiman.domain.repository.converter.ConvertRepository
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PythonCurrencyRepositoryImpl(
    private val context: Context
) : ConvertRepository {

    override suspend fun convert(
        type: ConvertType,
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