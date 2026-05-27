package com.bugiman.composercalculator.view.components.calculation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.domain.models.settings.SettingModel
import kotlinx.coroutines.awaitCancellation

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DisplayArea(
    viewModelCalculation: CalculatorViewModel,
    settingModel: SettingModel,
) {
    val expression by viewModelCalculation.expression.collectAsStateWithLifecycle()

    var fontSize by remember {
        mutableStateOf(85.sp)
    }

    val minFontSize = 45.sp

    val scrollState = rememberScrollState()

    val textFieldState = remember {
        TextFieldState()
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    LaunchedEffect(expression) {
        textFieldState.edit {
            replace(
                start = 0,
                end = length,
                text = expression
            )

            placeCursorAtEnd()
        }

        scrollState.animateScrollTo(scrollState.maxValue)

    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.29f)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            ),
        contentAlignment = Alignment.BottomEnd,
    ) {

        val containerWidth = constraints.maxWidth

        if (settingModel.isShowPlaceholderInput && expression.isEmpty()) {
            Text(
                text = "0",
                color = Color.White.copy(alpha = 0.4f),
                textAlign = TextAlign.End,
                fontSize = fontSize,
                modifier = Modifier.fillMaxWidth()
            )
        }

        InterceptPlatformTextInput(
            interceptor = { _, _ ->
                // awaitCancellation() полностью отменяет сессию системного ввода
                awaitCancellation()
            }
        ) {

            BasicTextField(
                state = textFieldState,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState),

                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = fontSize,
                    textAlign = TextAlign.End,
                ),

                lineLimits = TextFieldLineLimits.SingleLine,

                cursorBrush = SolidColor(
                    Color.LightGray
                ),

                interactionSource = interactionSource,

                decorator = { innerTextField ->

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        innerTextField()
                    }
                },

                onTextLayout = { getResult ->

                    val textLayoutResult = getResult() ?: return@BasicTextField

                    if (
                        textLayoutResult.size.width > containerWidth &&
                        fontSize > minFontSize
                    ) {
                        fontSize *= 0.95f
                    }
                }

            )
        }

    }
}