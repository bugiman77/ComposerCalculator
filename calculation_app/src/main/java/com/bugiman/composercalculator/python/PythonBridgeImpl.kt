package com.bugiman.composercalculator.python

import com.bugiman.data.python.PythonBridge
import com.chaquo.python.Python

class PythonBridgeImpl : PythonBridge {

    override fun callModuleFunction(
        module: String,
        function: String,
        vararg args: Any?
    ): Any {
        val python = Python.getInstance()
        val pyModule = python.getModule(module)
        return pyModule.callAttr(function, *args).toJava(Any::class.java)
    }
}