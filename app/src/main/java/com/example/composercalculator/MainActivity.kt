package com.example.composercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.composercalculator.navigation.AppNavigation
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposerCalculatorTheme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}
