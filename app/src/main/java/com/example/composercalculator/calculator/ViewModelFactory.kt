package com.example.composercalculator.calculator

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composercalculator.viewmodel.CalculatorViewModel

/*class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalculatorViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
