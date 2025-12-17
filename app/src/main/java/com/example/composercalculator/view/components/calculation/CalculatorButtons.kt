package com.example.composercalculator.view.components.calculation

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.R
import com.example.composercalculator.ui.theme.DarkGray
import com.example.composercalculator.ui.theme.LightGray
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.horizontalScroll

@Composable
fun CalculatorButtonGrid(
    viewModelSetting: SettingsViewModel = viewModel(),
    viewModelCalculation: CalculatorViewModel = viewModel()
) {

    val scope = rememberCoroutineScope()
    val expression = viewModelCalculation.expression.collectAsState().value

    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        val isInputEmpty = viewModelCalculation.expression.collectAsState().value.isEmpty()

        // --- Ряд 1: AC, +/-, %, ÷ ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp) // Расстояние между кнопками
        ) {
            BtnCalculation(
                text = if (isInputEmpty) "AC" else "C",
                color = LightGray,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 30.sp,
                onLongClick = {
                    viewModelCalculation.clearExpression()
                }
            ) {
                viewModelCalculation.removeLastCharacter()
            }
            BtnCalculation(
                text = "+/-",
                color = LightGray,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 30.sp
            ) {

            }
            BtnCalculation(
                text = "%",
                color = LightGray,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 45.sp
            ) {
                viewModelCalculation.onInput(input = "%")
            }
            BtnCalculation(
                text = "÷",
                color = Orange,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 60.sp
            ) {
                viewModelCalculation.onInput(input = "/")
            }
        }

        // --- Ряд 2: 7, 8, 9, × ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            BtnCalculation(
                text = "7",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f),
                onClick = {
                    viewModelCalculation.onInput(input = "7")
                }
            )
            BtnCalculation(
                text = "8",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "8")
            }
            BtnCalculation(
                text = "9",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "9")
            }
            BtnCalculation(
                text = "×",
                color = Orange,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 50.sp
            ) {
                viewModelCalculation.onInput(input = "*")
            }
        }

        // --- Ряд 3: 4, 5, 6, - ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            BtnCalculation(
                text = "4",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "4")
            }
            BtnCalculation(
                text = "5",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "5")
            }
            BtnCalculation(
                text = "6",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "6")
            }
            BtnCalculation(
                text = "-",
                color = Orange,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 70.sp
            ) {
                viewModelCalculation.onInput(input = "-")
            }
        }

        // --- Ряд 4: 1, 2, 3, + ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            BtnCalculation(
                text = "1",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "1")
            }
            BtnCalculation(
                text = "2",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "2")
            }
            BtnCalculation(
                text = "3",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = "3")
            }
            BtnCalculation(
                text = "+",
                color = Orange,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 60.sp
            ) {
                viewModelCalculation.onInput(input = "+")
            }
        }

        // --- Ряд 5: 0, ,, = ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {

            var menuExpanded by remember { mutableStateOf(value = false) }

            Box(
                modifier = Modifier.weight(weight = 1f)
            )
            {
                Button(
                    onClick = {
                        menuExpanded = true
                    }, // Передаем клик напрямую
                    modifier = Modifier
                        .aspectRatio(ratio = 1f),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGray),
                    contentPadding = PaddingValues(all = 0.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(size = 40.dp),
                        painter = painterResource(id = R.drawable.ic_calculate),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                if (menuExpanded) {
                    StyledDropdownMenu(
                        expanded = menuExpanded,
                        viewModel = viewModelSetting,
                        onDismissRequest = { menuExpanded = false }
                    )
                }
            }

            BtnCalculation(
                text = "0",
                color = DarkGray,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 45.sp
            ) {
                viewModelCalculation.onInput(input = "0")
            }
            BtnCalculation(
                text = ",",
                color = DarkGray,
                modifier = Modifier
                    .weight(weight = 1f)
            ) {
                viewModelCalculation.onInput(input = ".")
            }
            BtnCalculation(
                text = "=",
                color = Orange,
                modifier = Modifier.weight(weight = 1f),
                fontSize = 65.sp,
            ) {
                scope.launch {
                    viewModelCalculation.calculateAndSave()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BtnCalculation(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit = 40.sp,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit,
) {

    val scrollState = rememberScrollState()

    // Создаем едва заметный вертикальный градиент для фона
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            color,
            color
        )
    )

    // Создаем границу, которая имитирует внутреннюю тень
    val borderBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Transparent
        )
    )

    Button(
        onClick = { },
        modifier = modifier
            .aspectRatio(ratio = 1f),
        shape = CircleShape,
        contentPadding = PaddingValues(all = 0.dp),
        border = BorderStroke(width = 1.dp, borderBrush),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush, shape = CircleShape)
                .combinedClickable(
                    onClick = {
                        onClick()
                    },
                    onLongClick = {
                        onLongClick()
                    }
                ),

            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.horizontalScroll(scrollState),
                text = text,
                fontSize = fontSize,
                color = Color.White
            )
        }
    }
}