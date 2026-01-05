package com.example.composercalculator.view.components.calculation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.composercalculator.model.FlyingDigit
import com.example.composercalculator.R

@Composable
fun FlyingDigitAnimation(
    data: FlyingDigit,
    targetOffset: Offset,
    onReached: () -> Unit
) {
    // Анимация прогресса от 0 до 1
    val progress = remember { Animatable(0f) }
    var isHit by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
        )
        isHit = true // Включаем Lottie эффект
    }

    // Вычисляем текущие координаты на основе прогресса
    val x = lerp(data.startOffset.x, targetOffset.x, progress.value)
    val y = lerp(data.startOffset.y, targetOffset.y, progress.value)
    val scale = lerp(1.2f, 0.8f, progress.value)
    val alpha = lerp(1f, 0f, if (isHit) 1f else 0f) // Исчезает при попадании

    if (!isHit) {
        Text(
            text = data.text,
            modifier = Modifier
                .offset { IntOffset(x.toInt(), y.toInt()) }
                .scale(scale),
            fontSize = 40.sp,
            color = Color.White
        )
    } else {
        // ЭФФЕКТ LOTTIE ПРИ ПОПАДАНИИ
        LottieHitEffect(
            modifier = Modifier.offset { IntOffset(targetOffset.x.toInt(), targetOffset.y.toInt()) },
            onFinished = onReached
        )
    }
}

@Composable
fun LottieHitEffect(modifier: Modifier, onFinished: () -> Unit) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sparkle_hit))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        modifier = modifier.size(80.dp)
    )

    if (progress == 1f) {
        onFinished()
    }
}

// Вспомогательная функция для плавных чисел
fun lerp(start: Float, stop: Float, fraction: Float): Float = (1 - fraction) * start + fraction * stop