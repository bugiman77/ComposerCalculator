package com.bugiman.python

import android.app.Application
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

object PythonInitializer {

    fun initialize(application: Application) {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(application))
        }
    }

    fun getInstance(): Python {
        return Python.getInstance()
    }
}