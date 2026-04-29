package com.bugiman.composercalculator.view.components.calculation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugiman.composercalculator.R
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.ui.theme.DarkGray
import com.bugiman.composercalculator.ui.theme.LightGray
import com.bugiman.composercalculator.ui.theme.Orange
import com.bugiman.domain.models.settings.SettingModel
import kotlinx.coroutines.launch

@Composable
fun CalculatorButtonGrid(
    // ✅ Получаем ViewModel в качестве параметра
    viewModelCalculation: CalculatorViewModel,
    settingModel: SettingModel,
) {
    Column(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
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
            viewModelCalculation = viewModelCalculation
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
    onClick: () -> Unit,
) {
    val backgroundBrush = Brush.verticalGradient(colors = listOf(color, color))
    val borderBrush =
        Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.2f), Color.Transparent))

    Button(
        onClick = { },
        modifier = modifier.aspectRatio(ratio = 1f),
        shape = CircleShape,
        contentPadding = PaddingValues(all = 0.dp),
        border = BorderStroke(width = 1.dp, borderBrush),
        enabled = isButtonEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundBrush, shape = CircleShape)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, fontSize = fontSize, color = Color.White)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BtnCalculationIcon(
    modifier: Modifier = Modifier,
    iconId: androidx.compose.ui.graphics.painter.Painter,
    color: Color,
    tint: Color,
    isButtonEnabled: Boolean = true,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit,
) {
    val backgroundBrush = Brush.verticalGradient(colors = listOf(color, color))
    val borderBrush =
        Brush.verticalGradient(colors = listOf(Color.White.copy(alpha = 0.2f), Color.Transparent))

    Button(
        onClick = { },
        modifier = modifier.aspectRatio(ratio = 1f),
        shape = CircleShape,
        contentPadding = PaddingValues(all = 0.dp),
        border = BorderStroke(width = 1.dp, borderBrush),
        enabled = isButtonEnabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundBrush, shape = CircleShape)
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = iconId,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                tint = tint
            )
        }
    }
}

@Composable
private fun LineCalculation1(viewModelCalculation: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        BtnCalculationText(
            text = "C",
            color = LightGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 30.sp,
            onClick = { viewModelCalculation.clear() },  // ✅ Подключено!
            onLongClick = { viewModelCalculation.clear() },
        )
        BtnCalculationText(
            text = "+/-",
            color = LightGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 30.sp,
            onClick = { /* TODO: Toggle sign */ },
        )
        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_percent),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputMathOperation("%") },  // ✅ П��дключено!
        )
        BtnCalculationText(
            text = "/",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp,
            onClick = { viewModelCalculation.onInputMathOperation("/") },  // ✅ Подключено!
        )
    }
}

@Composable
private fun LineCalculation2(viewModelCalculation: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        BtnCalculationText(
            text = "7", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("7") })
        BtnCalculationText(
            text = "8", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("8") })
        BtnCalculationText(
            text = "9", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("9") })
        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_multiply),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputMathOperation("*") },
        )
    }
}

@Composable
private fun LineCalculation3(viewModelCalculation: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        BtnCalculationText(
            text = "4", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("4") })
        BtnCalculationText(
            text = "5", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("5") })
        BtnCalculationText(
            text = "6", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("6") })
        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_minus),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputMathOperation("-") },
        )
    }
}

@Composable
private fun LineCalculation4(viewModelCalculation: CalculatorViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        BtnCalculationText(
            text = "1", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("1") })
        BtnCalculationText(
            text = "2", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("2") })
        BtnCalculationText(
            text = "3", color = DarkGray, modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputDigit("3") })
        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_plus),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onInputMathOperation("+") },
        )
    }
}

@Composable
private fun LineCalculation5(viewModelCalculation: CalculatorViewModel) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Box(modifier = Modifier.weight(weight = 1f)) {
            Button(
                onClick = { /* TODO: Menu */ },
                modifier = Modifier.aspectRatio(ratio = 1f),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = DarkGray),
                contentPadding = PaddingValues(all = 0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calculate),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        BtnCalculationText(
            text = "0",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 45.sp,
            onClick = { viewModelCalculation.onInputZero() }
        )

        BtnCalculationText(
            text = ".",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 60.sp,
            onClick = { viewModelCalculation.onInputComma() }
        )

        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_equal),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            onClick = { viewModelCalculation.onCalculate() }
        )
    }
}