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
import androidx.compose.ui.unit.Dp

@Composable
fun CalculatorButtonGrid(
    viewModelSetting: SettingsViewModel = viewModel(),
    viewModelCalculation: CalculatorViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        LineCalculation1(
            viewModelCalculation = viewModelCalculation
        )
        LineCalculation2(
            viewModelCalculation = viewModelCalculation
        )
        LineCalculation3(
            viewModelCalculation = viewModelCalculation
        )
        LineCalculation4(
            viewModelCalculation = viewModelCalculation
        )
        LineCalculation5(
            viewModelCalculation = viewModelCalculation,
            viewModelSetting = viewModelSetting
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BtnCalculationText(
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

@Composable
private fun BtnCalculationIcon(
    modifier: Modifier = Modifier,
    iconId: Int,
    color: Color,
    iconSize: Dp = 40.dp,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit,
) {

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
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

@Composable
private fun LineCalculation1(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
) {

    val isInputEmpty = viewModelCalculation.expression.collectAsState().value.isEmpty()

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp) // Расстояние между кнопками
    ) {

        BtnCalculationText(
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

        BtnCalculationText(
            text = "+/-",
            color = LightGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 30.sp
        ) {

        }

        BtnCalculationText(
            text = "%",
            color = LightGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 45.sp
        ) {
            viewModelCalculation.onInputMathematicalOperations(inputOperation = "%")
        }

        BtnCalculationText(
            text = "÷",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 60.sp
        ) {
            viewModelCalculation.onInputMathematicalOperations(inputOperation = "/")
        }
    }

}

@Composable
private fun LineCalculation2(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "7",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit(inputDigit = "7")
            }
        )

        BtnCalculationText(
            text = "8",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "8")
        }

        BtnCalculationText(
            text = "9",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "9")
        }

        BtnCalculationText(
            text = "×",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp
        ) {
            viewModelCalculation.onInputMathematicalOperations(inputOperation = "*")
        }

    }
}

@Composable
private fun LineCalculation3(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "4",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "4")
        }

        BtnCalculationText(
            text = "5",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "5")
        }

        BtnCalculationText(
            text = "6",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "6")
        }

        BtnCalculationText(
            text = "-",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 70.sp
        ) {
            viewModelCalculation.onInputMathematicalOperations(inputOperation = "-")
        }

    }
}

@Composable
private fun LineCalculation4(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "1",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "1")
        }

        BtnCalculationText(
            text = "2",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "2")
        }

        BtnCalculationText(
            text = "3",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f)
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "3")
        }

        BtnCalculationText(
            text = "+",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 60.sp
        ) {
            viewModelCalculation.onInputMathematicalOperations(inputOperation = "+")
        }

    }
}

@Composable
private fun LineCalculation5(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
    viewModelSetting: SettingsViewModel
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        var menuExpanded by remember { mutableStateOf(value = false) }
        val scope = rememberCoroutineScope()

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

        BtnCalculationText(
            text = "0",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 45.sp
        ) {
            viewModelCalculation.onInputDigit(inputDigit = "0")
        }

        BtnCalculationText(
            text = ",",
            color = DarkGray,
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            viewModelCalculation.onInputMathematicalOperations(inputOperation = ".")
        }

        BtnCalculationText(
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