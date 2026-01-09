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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.Dp

@Composable
fun CalculatorButtonGrid(
    viewModelSetting: SettingsViewModel = viewModel(),
    viewModelCalculation: CalculatorViewModel = viewModel(),
    onDigitClick: (String, Offset) -> Unit
) {

    val bottomSpacer = viewModelSetting.bottomSpacer.collectAsState()

    Column(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        LineCalculation1(
            viewModelCalculation = viewModelCalculation,
            onDigitClick = onDigitClick
        )

        LineCalculation2(
            viewModelCalculation = viewModelCalculation,
            onDigitClick = onDigitClick
        )

        LineCalculation3(
            viewModelCalculation = viewModelCalculation,
            onDigitClick = onDigitClick
        )

        LineCalculation4(
            viewModelCalculation = viewModelCalculation,
            onDigitClick = onDigitClick
        )

        LineCalculation5(
            viewModelCalculation = viewModelCalculation,
            viewModelSetting = viewModelSetting,
            onDigitClick = onDigitClick
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
    isButtonEnabled: Boolean = true,
    onLongClick: () -> Unit = {},
    onClick: (Offset) -> Unit,
) {

    val scrollState = rememberScrollState()
    var buttonOffset by remember { mutableStateOf(Offset.Zero) }

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
            .aspectRatio(ratio = 1f)
            /*.onGloballyPositioned { coordinates ->
                buttonOffset = coordinates.positionInRoot()
            }*/,
        shape = CircleShape,
        contentPadding = PaddingValues(all = 0.dp),
        border = BorderStroke(width = 1.dp, borderBrush),
        enabled = isButtonEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush, shape = CircleShape)
                .combinedClickable(
                    onClick = {
                        onClick(buttonOffset)
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
    iconId: Painter,
    color: Color,
    tint: Color,
    iconSize: Dp = 40.dp,
    isButtonEnabled: Boolean = true,
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
        enabled = isButtonEnabled
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
                painter = iconId,
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                tint = tint
            )
        }
    }
}

@Composable
private fun LineCalculation1(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
    onDigitClick: (String, Offset) -> Unit
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
            onClick = {
                viewModelCalculation.removeLastCharacter()
            },
            onLongClick = {
                viewModelCalculation.clearExpression()
            },
        )

        BtnCalculationText(
            text = "+/-",
            color = LightGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 30.sp,
            onClick = {
                viewModelCalculation.onToggleSign()
            },
        )

        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_percent),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            iconSize = 50.dp,
            onClick = {

            },
        )

        BtnCalculationText(
            text = "/",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp,
            onClick = {
                viewModelCalculation.onInputMathematicalOperations(inputOperation = "/")
            },
        )
    }

}

@Composable
private fun LineCalculation2(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
    onDigitClick: (String, Offset) -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "7",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("7", offset)
                viewModelCalculation.onInputDigit(inputDigit = "7")
            },
        )

        BtnCalculationText(
            text = "8",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("8", offset)
                viewModelCalculation.onInputDigit(inputDigit = "8")
            },
        )

        BtnCalculationText(
            text = "9",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("9", offset)
                viewModelCalculation.onInputDigit(inputDigit = "9")
            },
        )

        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_multiply),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            iconSize = 50.dp,
            onClick = {
                viewModelCalculation.onInputMathematicalOperations(inputOperation = "*")
            },
            onLongClick = {
//                TODO
            },
        )

    }
}

@Composable
private fun LineCalculation3(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
    onDigitClick: (String, Offset) -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "4",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("4", offset)
                viewModelCalculation.onInputDigit(inputDigit = "4")
            },
        )

        BtnCalculationText(
            text = "5",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("5", offset)
                viewModelCalculation.onInputDigit(inputDigit = "5")
            },
        )

        BtnCalculationText(
            text = "6",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("6", offset)
                viewModelCalculation.onInputDigit(inputDigit = "6")
            }
        )

        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_minus),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            iconSize = 70.dp,
            onClick = {
                viewModelCalculation.onInputMathematicalOperations(inputOperation = "-")
            },
        )

    }
}

@Composable
private fun LineCalculation4(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
    onDigitClick: (String, Offset) -> Unit
) {

    val isInputEmpty = viewModelCalculation.expression.collectAsState().value.isEmpty()

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "1",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("1", offset)
                viewModelCalculation.onInputDigit(inputDigit = "1")
            },
        )

        BtnCalculationText(
            text = "2",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("2", offset)
                viewModelCalculation.onInputDigit(inputDigit = "2")
            },
        )

        BtnCalculationText(
            text = "3",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = { offset ->
                onDigitClick("3", offset)
                viewModelCalculation.onInputDigit(inputDigit = "3")
            },
        )

        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_plus),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            iconSize = 50.dp,
            onClick = {
                viewModelCalculation.onInputMathematicalOperations(inputOperation = "+")
            },
        )

    }
}

@Composable
private fun LineCalculation5(
    modifier: Modifier = Modifier,
    viewModelCalculation: CalculatorViewModel,
    viewModelSetting: SettingsViewModel,
    onDigitClick: (String, Offset) -> Unit
) {

    val isInputEmpty = viewModelCalculation.expression.collectAsState().value.isEmpty()
    val isSwitchEnableDarkMode = !viewModelSetting.isSystemTheme.collectAsState().value

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
                    expanded = true,
                    isEnableSwitchDarkMode = isSwitchEnableDarkMode,
                    viewModel = viewModelSetting,
                    onDismissRequest = { menuExpanded = false }
                )
            }
        }

        BtnCalculationText(
            text = "0",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 45.sp,
            onClick = {
                viewModelCalculation.onInputDigit(inputDigit = "0")
            },
        )

        BtnCalculationText(
            text = ".",
            color = DarkGray,
            modifier = Modifier
                .weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputMathematicalOperations(inputOperation = ".")
            },
        )

        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_equal),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            iconSize = 55.dp,
            onClick = {
                scope.launch {
                    viewModelCalculation.calculateAndSave()
                }
            },
        )

    }
}