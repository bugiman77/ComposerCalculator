package com.example.composercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.calculator.CalculatorScreen
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposerCalculatorTheme(darkTheme = true) {
                // Создаем экземпляр ViewModel
                val viewModel: CalculatorViewModel = viewModel()
                // Получаем состояние из ViewModel
                val uiState = viewModel.uiState
                // Передаем состояние и обработчик событий в наш экран
                CalculatorScreen(
                    uiState = uiState,
                    onEvent = viewModel::onEvent // Передаем ссылку на функцию
                )
            }
        }
    }
}
