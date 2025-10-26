package com.example.composercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.calculator.CalculatorScreen
//import com.example.composercalculator.calculator.ViewModelFactory
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposerCalculatorTheme(darkTheme = true) {
//                val context = LocalContext.current
//                val factory = ViewModelFactory(context.applicationContext)
                val viewModel: CalculatorViewModel = viewModel()
                val uiState = viewModel.uiState
                CalculatorScreen(
                    uiState = uiState,
                    onEvent = viewModel::onEvent // Передаем ссылку на функцию
                )
            }
        }
    }
}
