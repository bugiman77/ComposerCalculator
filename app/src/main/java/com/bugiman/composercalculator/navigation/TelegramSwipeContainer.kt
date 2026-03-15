package com.bugiman.composercalculator.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.launch

@Composable
fun TelegramSwipeContainer(navigator: Navigator) {
    val density = LocalDensity.current
    val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val touchSlop = with(density) { 18.dp.toPx() }

    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var isDragging by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var lastBackPressTime by remember { mutableLongStateOf(0L) }

    val maxCornerRadius = 32.dp

    BackHandler(enabled = true) {
        if (navigator.canPop) {
            scope.launch {
                offsetX.animateTo(screenWidthPx, tween(300))
                navigator.pop()
                offsetX.snapTo(0f)
            }
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastBackPressTime < 2000) {
                (context as? Activity)?.finish()
            } else {
                Toast.makeText(context, "Нажмите ещё раз для выхода", Toast.LENGTH_SHORT).show()
                lastBackPressTime = currentTime
            }
        }
    }

    val currentScreen = navigator.lastItem
    val previousScreen = if (navigator.items.size > 1) navigator.items[navigator.items.size - 2] else null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(navigator.canPop) {
                if (!navigator.canPop) return@pointerInput

                awaitPointerEventScope {
                    while (true) {
                        var totalDragX = 0f
                        var totalDragY = 0f

                        // Локальные флаги для этого конкретного касания
                        var isDragging = false
                        var isVerticalLocked = false
                        var hasDecided = false // Флаг: принято ли решение о направлении

                        do {
                            val event = awaitPointerEvent()
                            val dragChange = event.changes.first()

                            val deltaX = dragChange.position.x - dragChange.previousPosition.x
                            val deltaY = dragChange.position.y - dragChange.previousPosition.y

                            totalDragX += deltaX
                            totalDragY += kotlin.math.abs(deltaY)

                            // 1. ПРИНЯТИЕ РЕШЕНИЯ (Decision Phase)
                            if (!hasDecided) {
                                // Ждем, пока суммарное движение превысит небольшой порог (напр. 8-10 пикселей)
                                if (kotlin.math.abs(totalDragX) > 10f || totalDragY > 10f) {
                                    hasDecided = true
                                    // Если горизонталь явно доминирует над вертикалью
                                    if (totalDragX > 0 && totalDragX > totalDragY * 1.2f) {
                                        isDragging = true
                                        isVerticalLocked = false
                                    } else {
                                        // Иначе отдаем приоритет вертикальному скроллу
                                        isDragging = false
                                        isVerticalLocked = true
                                    }
                                }
                            }

                            // 2. ИСПОЛНЕНИЕ (Execution Phase)
                            if (isDragging) {
                                // Если мы решили, что это свайп назад — МЫ ПОГЛОЩАЕМ ВСЁ.
                                // Даже если палец пойдет под 45 градусов, список этого не увидит.
                                val newOffset = (offsetX.value + deltaX).coerceAtLeast(0f)
                                scope.launch { offsetX.snapTo(newOffset) }

                                // Критически важно: consume() блокирует передачу жеста детям
                                dragChange.consume()
                            } else if (isVerticalLocked) {
                                // Если решили, что это скролл — мы НИЧЕГО не трогаем,
                                // позволяя LazyColumn забрать жест себе.
                                // offsetX остается 0.
                            }

                        } while (event.changes.any { it.pressed })

                        // 3. ЗАВЕРШЕНИЕ (После отпускания пальца)
                        if (isDragging) {
                            if (offsetX.value > screenWidthPx / 4) {
                                scope.launch {
                                    offsetX.animateTo(screenWidthPx, tween(250))
                                    navigator.pop()
                                    offsetX.snapTo(0f)
                                }
                            } else {
                                scope.launch { offsetX.animateTo(0f, spring()) }
                            }
                        }
                        // Сбрасываем флаги для следующего касания
                        isDragging = false
                        hasDecided = false
                    }
                }
            }
    ) {
        // Отрисовка слоев (предыдущий экран, тень, текущий экран) — остается без изменений
        // --- 1. ПРЕДЫДУЩИЙ ЭКРАН ---
        previousScreen?.let { prev ->
            Box(modifier = Modifier.graphicsLayer {
                translationX = (offsetX.value - screenWidthPx) * 0.3f
                alpha = 0.8f + (offsetX.value / screenWidthPx) * 0.2f
            }) {
                prev.Content()
            }
            Box(Modifier.fillMaxSize().background(
                Color.Black.copy(alpha = (0.4f * (1f - offsetX.value / screenWidthPx)).coerceIn(0f, 0.4f))
            ))
        }

        // --- 2. ТЕНЬ ---
        if (offsetX.value > 0) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(25.dp)
                    .graphicsLayer { translationX = offsetX.value - 25.dp.toPx() }
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.35f))
                        )
                    )
            )
        }

        // --- 3. ТЕКУЩИЙ ЭКРАН ---
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = offsetX.value

                    // РАСЧЕТ ЗАКРУГЛЕНИЯ:
                    // Если экран сдвинут хотя бы на 1 пиксель, начинаем скруглять.
                    // Когда экран полностью на месте (offsetX == 0), радиус 0.
                    val progress = (offsetX.value / screenWidthPx).coerceIn(0f, 1f)

                    // Если свайп начат, плавно увеличиваем радиус до 32.dp
                    val currentRadius = if (offsetX.value > 0) {
                        maxCornerRadius.toPx()
                    } else 0f

                    shape = androidx.compose.foundation.shape.RoundedCornerShape(currentRadius)
                    clip = offsetX.value > 0 // Обрезаем контент по углам только при свайпе
                },
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 1.dp
        ) {
            Box(Modifier.fillMaxSize()) {
                currentScreen.Content()

                // Защитный слой (тоже должен скругляться, чтобы не вылезать за края)
                if (offsetX.value > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) { detectTapGestures { } }
                    )
                }
            }
        }
    }
}
