package com.bugiman.composercalculator.python

import com.bugiman.data.python.PythonBridge
import com.bugiman.python.PythonInitializer

class PythonBridgeImpl : PythonBridge {

    override fun callModuleFunction(
        module: String,
        function: String,
        vararg args: Any?
    ): Any {
        // Новый вызов из отдельного модуля
        val python = PythonInitializer.getInstance()
        val pyModule = python.getModule(module)
        return pyModule.callAttr(function, *args).toJava(Any::class.java)
    }
}