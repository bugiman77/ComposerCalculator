package com.bugiman.composercalculator.view.screen.main

import android.R.attr.scaleY
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugiman.composercalculator.R
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.history.HistoryViewModel
import com.bugiman.composercalculator.presentation.settings.SettingsViewModel
import com.bugiman.composercalculator.ui.theme.DarkGray
import com.bugiman.composercalculator.view.components.calculation.CalculatorButtonGrid
import com.bugiman.composercalculator.view.components.calculation.DisplayArea
import com.bugiman.composercalculator.view.components.calculation.HistoryBottomSheet
import com.bugiman.domain.models.settings.SettingModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    viewModelCalculation: CalculatorViewModel,
    viewModelHistory: HistoryViewModel,
    settingModel: SettingModel,
    onNavigateToSettings: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onUpdateSettings: ((SettingModel) -> SettingModel) -> Unit = {}
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val isSheetVisible = sheetState.isVisible

    var showHistorySheet by remember { mutableStateOf(value = false) }

    val scope = rememberCoroutineScope()

    val isShowHistoryBotton = settingModel.isShowHistoryButton
    val isShowBottonLabel = settingModel.isShowIconButton

    LaunchedEffect(key1 = sheetState.isVisible) {
        if (!sheetState.isVisible) {
            showHistorySheet = false
        }
    }

    if (showHistorySheet) {

        HistoryBottomSheet(
            calculatorViewModel = viewModelCalculation,
            settingModel = settingModel,
            sheetState = sheetState,
            onDismiss = { showHistorySheet = false },
            onCloseClick = {
                scope.launch {
                    sheetState.hide()
                    showHistorySheet = false
                }
            },
            // Действия приходят извне согласно чистой архитектуре
            onDeleteItem = { historyModel ->
                scope.launch {
                     viewModelHistory.deleteItem(historyModel)
                }
            },
            onUpdateNote = { historyModel ->
                scope.launch {
                     viewModelHistory.updateNote(historyModel)
                }
            },
            onEditExpression = { historyModel ->
//                 viewModelHistory._expression.value = historyModel.expression
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.Black
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.End
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        if (isShowHistoryBotton) {
                            val interactionSource = remember { MutableInteractionSource() }
                            val isPressedHistory by interactionSource.collectIsPressedAsState()
                            val contentScale by animateFloatAsState(
                                targetValue = if (isPressedHistory) 1.12f else 1f,
                                animationSpec = spring(
                                    dampingRatio = 0.4f,
                                    stiffness = 950f
                                ),
                                label = "contentScale"
                            )
                            if (isShowBottonLabel) {

                                Card(
                                    onClick = { showHistorySheet = true },
                                    interactionSource = interactionSource,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .iosPressAnimation(interactionSource),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFF2C2C2E)
                                    ),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 4.dp
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.ic_history),
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .graphicsLayer {
                                                    scaleX = contentScale
                                                    scaleY = contentScale
                                                }
                                        )
                                    }
                                }
                            } else {
                                Button(
                                    onClick = { showHistorySheet = true },
                                    interactionSource = interactionSource,
                                    modifier = Modifier.iosPressAnimation(interactionSource),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = DarkGray
                                    )
                                ) {
                                    Text(
                                        text = "История",
                                        fontSize = 14.sp,
                                        color = Color.White,
                                        modifier = Modifier.graphicsLayer {
                                            scaleX = contentScale
                                            scaleY = contentScale
                                        }
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.size(size = 40.dp))
                        }

                        val interactionSource = remember { MutableInteractionSource() }
                        val isPressedSettings by interactionSource.collectIsPressedAsState()
                        val contentScale by animateFloatAsState(
                            targetValue = if (isPressedSettings) 1.12f else 1f,
                            animationSpec = spring(
                                dampingRatio = 0.4f,
                                stiffness = 950f
                            ),
                            label = "contentScale"
                        )
                        if (isShowBottonLabel) {
                            Card(
                                onClick = onNavigateToSettings,
                                interactionSource = interactionSource,
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(48.dp)
                                    .iosPressAnimation(interactionSource),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF2C2C2E)
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_more),
                                        contentDescription = "Настройки приложения",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .graphicsLayer {
                                                scaleX = contentScale
                                                scaleY = contentScale
                                            }
                                    )
                                }
                            }
                            /*Card(
                                onClick = onNavigateToSettings,
                                shape = CircleShape,
                                modifier = Modifier
                                    .size(48.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(
                                        0xFF2C2C2E
                                    )
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_more),
                                        contentDescription = "Настройки приложения",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }*/
                        } else {
                            Button(
                                onClick = onNavigateToSettings,
                                interactionSource = interactionSource,
                                modifier = Modifier.iosPressAnimation(interactionSource),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DarkGray
                                )
                            ) {
                                Text(
                                    text = "Настройки",
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.graphicsLayer {
                                        scaleX = contentScale
                                        scaleY = contentScale
                                    }
                                )
                            }

                            /*Button(
                                onClick = onNavigateToSettings,
                                colors = ButtonDefaults.buttonColors(containerColor = DarkGray)
                            ) {
                                Text(
                                    text = "Настройки",
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }*/
                        }
                    }

                    DisplayArea(
                        viewModelCalculation = viewModelCalculation,
                        settingModel = settingModel
                    )

                    CalculatorButtonGrid(
                        viewModelCalculation = viewModelCalculation,
                        settingModel = settingModel
                    )
                }
            }
        }
    }
}

@Composable
fun Modifier.iosPressAnimation(
    interactionSource: MutableInteractionSource
): Modifier {

    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.08f else 1f,
        animationSpec = spring(
            dampingRatio = 0.45f,
            stiffness = 900f
        ),
        label = "scale"
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 10.dp else 4.dp,
        animationSpec = tween(90),
        label = "elevation"
    )

    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
        shadowElevation = elevation.toPx()
    }
}