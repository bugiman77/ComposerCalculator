package com.bugiman.composercalculator.navigation

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.Navigator
import com.bugiman.composercalculator.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    settingsViewModel: SettingsViewModel = viewModel(),
    calculatorViewModel: CalculatorViewModel = viewModel(),
) {
    val showHistoryButton = settingsViewModel.showHistoryButton.collectAsState()

    Navigator(
        screen = CalculatorScreen(settingsViewModel, calculatorViewModel, showHistoryButton.value)
    ) { navigator ->
        TelegramSwipeContainer(navigator)
    }
}

@Composable
fun TelegramSwipeContainer(navigator: Navigator) {
    val screenWidthPx =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthPx.toFloat() }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Берем текущий и предыдущий экраны из стека навигатора
    val currentScreen = navigator.lastItem
    val previousScreen =
        if (navigator.items.size > 1) navigator.items[navigator.items.size - 2] else null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Фон под всеми экранами
            .pointerInput(navigator.canPop) {
                if (!navigator.canPop) return@pointerInput

                detectHorizontalDragGestures(
                    onDragStart = { },
                    onHorizontalDrag = { change, dragAmount ->
                        // Начинаем свайп только от левого края (например, первые 50dp)
//                        if (change.position.x < 150f || offsetX.value > 0) {
                        val newOffset = (offsetX.value + dragAmount).coerceAtLeast(0f)
                        scope.launch { offsetX.snapTo(newOffset) }
                        change.consume()
//                        }
                    },
                    onDragEnd = {
                        if (offsetX.value > screenWidthPx / 4) {
                            // Если дотянули до четверти — закрываем экран с анимацией до конца
                            scope.launch {
                                offsetX.animateTo(screenWidthPx, animationSpec = tween(250))
                                navigator.pop()
                                offsetX.snapTo(0f)
                            }
                        } else {
                            // Иначе возвращаем экран на место
                            scope.launch {
                                offsetX.animateTo(0f, animationSpec = spring())
                            }
                        }
                    }
                )
            }
    ) {
        // 1. Отрисовываем ПРЕДЫДУЩИЙ экран (он лежит снизу)
        previousScreen?.let { prev ->
            Box(modifier = Modifier.graphicsLayer {
                // Эффект параллакса: предыдущий экран движется медленнее (как в Telegram/iOS)
                translationX = (offsetX.value - screenWidthPx) * 0.3f
                // Затемнение предыдущего экрана (уменьшается по мере свайпа)
                alpha = 0.8f + (offsetX.value / screenWidthPx) * 0.2f
            }) {
                prev.Content()
            }
            // Слой затемнения над предыдущим экраном
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(
                            alpha = (1f - offsetX.value / screenWidthPx).coerceIn(0f, 0.5f)
                        )
                    )
            )
        }

        // 2. Тень перед текущим экраном
        if (offsetX.value > 0) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(20.dp)
                    .graphicsLayer { translationX = offsetX.value - 20.dp.toPx() }
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.25f))
                        )
                    )
            )
        }

        // 3. Отрисовываем ТЕКУЩИЙ экран (сверху)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationX = offsetX.value }
                .background(MaterialTheme.colorScheme.background) // Важно, чтобы у экрана был фон
        ) {
            currentScreen.Content()
        }
    }
}

// Полезное расширение для получения ширины в PX
val Configuration.screenWidthPx: Int
    @Composable
    get() = with(LocalDensity.current) { screenWidthDp.dp.roundToPx() }
