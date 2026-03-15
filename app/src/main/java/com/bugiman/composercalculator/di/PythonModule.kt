package com.bugiman.composercalculator.di

import com.bugiman.composercalculator.python.PythonBridgeImpl
import com.bugiman.data.python.PythonBridge
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

class PythonModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object PythonModule {

        @Provides
        @Singleton
        fun providePythonBridge(): PythonBridge {
            return PythonBridgeImpl()
        }
    }

}