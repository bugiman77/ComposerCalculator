package com.bugiman.data.python

interface PythonBridge {

    fun callModuleFunction(
        module: String,
        function: String,
        vararg args: Any?
    ): Any?

}