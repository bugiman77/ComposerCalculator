package com.bugiman.data.datasource.python

import com.bugiman.data.python.PythonBridge

class PythonConverterDataSource(
    private val pythonBridge: PythonBridge
) {

    fun convert(
        moduleName: String,
        value: Double,
        from: String,
        to: String
    ): Double {

        return pythonBridge
            .callModuleFunction(
                moduleName,
                "convert",
                value,
                from,
                to
            ) as Double
    }
}