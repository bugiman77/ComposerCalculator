package com.bugiman.composercalculator.view.components.calculation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugiman.composercalculator.R
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.ui.theme.DarkGray
import com.bugiman.composercalculator.ui.theme.DarkOrange
import com.bugiman.composercalculator.ui.theme.LightGray
import com.bugiman.composercalculator.ui.theme.Orange
import com.bugiman.domain.models.settings.SettingModel
import kotlinx.coroutines.launch

@Composable
fun CalculatorButtonGrid(
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
private fun AnimatedCalculatorButton(
    modifier: Modifier = Modifier,
    color: Color,
    borderBrush: Brush,
    enabled: Boolean = true,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit,
    content: @Composable (Float) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.08f else 1f,
        animationSpec = spring(
            dampingRatio = 0.45f,
            stiffness = 900f
        ),
        label = "button_scale"
    )

    val contentScale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = spring(
            dampingRatio = 0.4f,
            stiffness = 950f
        ),
        label = "content_scale"
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 10.dp else 2.dp,
        animationSpec = tween(90),
        label = "elevation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation.toPx()
                shape = CircleShape
                clip = false
            }
            .border(
                width = 1.dp,
                brush = borderBrush,
                shape = CircleShape
            )
            .background(
                color = color,
                shape = CircleShape
            )
            .combinedClickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
                onLongClick = onLongClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content(contentScale)
    }
}

@Composable
private fun BtnCalculationText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit = 40.sp,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    val borderBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Transparent
        )
    )

    AnimatedCalculatorButton(
        modifier = modifier.aspectRatio(1f),
        color = color,
        borderBrush = borderBrush,
        enabled = isButtonEnabled,
        onClick = onClick,
        onLongClick = onLongClick
    ) { contentScale ->

        Text(
            text = text,
            fontSize = fontSize,
            color = Color.White,
            modifier = Modifier.graphicsLayer {
                scaleX = contentScale
                scaleY = contentScale
            }
        )
    }
}

@Composable
private fun BtnCalculationIcon(
    modifier: Modifier = Modifier,
    iconId: Painter,
    color: Color,
    tint: Color,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    val borderBrush = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.Transparent
        )
    )

    AnimatedCalculatorButton(
        modifier = modifier.aspectRatio(1f),
        color = color,
        borderBrush = borderBrush,
        enabled = isButtonEnabled,
        onClick = onClick,
        onLongClick = onLongClick
    ) { contentScale ->

        Icon(
            painter = iconId,
            contentDescription = null,
            tint = tint,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .graphicsLayer {
                    scaleX = contentScale
                    scaleY = contentScale
                }
        )
    }
}

@Composable
private fun LineCalculation1(
    viewModelCalculation: CalculatorViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "C", // if (isInputEmpty) "AC" else "C"
            color = LightGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 45.sp,
            onClick = {
                viewModelCalculation.removeLast()
            },
            onLongClick = {
                viewModelCalculation.clear()
            },
        )

        BtnCalculationText(
            text = "(",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp,
            onClick = {

            },
            onLongClick = {

            },
        )

        BtnCalculationText(
            text = ")",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp,
            onClick = {

            },
            onLongClick = {

            },
        )

        BtnCalculationText(
            text = "/",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp,
            onClick = {

            },
            onLongClick = {

            },
        )
    }
}

@Composable
private fun LineCalculation2(
    viewModelCalculation: CalculatorViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "7",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("7")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("7")
            },
        )

        BtnCalculationText(
            text = "8",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("8")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("8")
            },
        )

        BtnCalculationText(
            text = "9",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("9")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("9")
            },
        )

        BtnCalculationIcon(
            iconId = painterResource(id = R.drawable.ic_multiply),
            color = Orange,
            tint = Color.White,
            modifier = Modifier.weight(weight = 1f),
            onClick = {

            },
            onLongClick = {

            },
        )
    }
}

@Composable
private fun LineCalculation3(
    viewModelCalculation: CalculatorViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "4",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("4")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("4")
            },
        )

        BtnCalculationText(
            text = "5",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("5")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("5")
            },
        )

        BtnCalculationText(
            text = "6",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("6")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("6")
            },
        )

        BtnCalculationText(
            text = "-",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp,
            onClick = {

            },
            onLongClick = {

            },
        )

    }
}

@Composable
private fun LineCalculation4(
    viewModelCalculation: CalculatorViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {

        BtnCalculationText(
            text = "1",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("1")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("1")
            },
        )

        BtnCalculationText(
            text = "2",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("2")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("2")
            },
        )

        BtnCalculationText(
            text = "3",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            onClick = {
                viewModelCalculation.onInputDigit("3")
            },
            onLongClick = {
                viewModelCalculation.onInputDigit("3")
            },
        )

        BtnCalculationText(
            text = "+",
            color = Orange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 50.sp,
            onClick = {

            },
            onLongClick = {

            },
        )

    }
}

@Composable
private fun LineCalculation5(
    viewModelCalculation: CalculatorViewModel
) {
    var menuExpanded by remember { mutableStateOf(value = false) }
    val isSwitchEnableDarkMode = false

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        Box(modifier = Modifier.weight(weight = 1f)) {
            Button(
                onClick = { menuExpanded = true },
                modifier = Modifier.aspectRatio(ratio = 1f),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = DarkGray),
                contentPadding = PaddingValues(all = 0.dp)
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.ic_calculate),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            if (menuExpanded) {
                StyledDropdownMenu(
                    expanded = true,
                    isEnableSwitchDarkMode = isSwitchEnableDarkMode,
                    onDismissRequest = { menuExpanded = false },
                    onOpenEngineeringMode = {},
                    onOpenScientificMode = {},
                    onOpenCurrencyConverter = {},
                    onOpenDistanceConverter = {}
                )
            }

        }

        BtnCalculationText(
            text = "0",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 45.sp,
            onClick = {
                viewModelCalculation.onInputZero()
            },
            onLongClick = {
                viewModelCalculation.onInputZero()
            },
        )

        BtnCalculationText(
            text = ".",
            color = DarkGray,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 60.sp,
            onClick = {

            },
            onLongClick = {

            },
        )

        BtnCalculationText(
            text = "=",
            color = DarkOrange,
            modifier = Modifier.weight(weight = 1f),
            fontSize = 65.sp,
            onClick = {

            },
            onLongClick = {

            },
        )
    }
}