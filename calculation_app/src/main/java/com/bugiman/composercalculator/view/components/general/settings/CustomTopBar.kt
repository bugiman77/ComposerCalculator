package com.bugiman.composercalculator.view.components.general.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugiman.composercalculator.R

@Composable
fun CustomTopBar(
    screenTitle: String,
    onNavigateBack: () -> Unit,
    onScrollToTop: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding() // Только отступ, БЕЗ background
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {

        val backInteractionSource = remember {
            MutableInteractionSource()
        }

        Card(
            onClick = onNavigateBack,
            interactionSource = backInteractionSource,
            shape = CircleShape,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart)
                .iosNavigationPressAnimation(backInteractionSource),
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
                    painter = painterResource(R.drawable.back_ios),
                    contentDescription = "Назад",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        val islandInteractionSource = remember {
            MutableInteractionSource()
        }

        val isPressed by islandInteractionSource.collectIsPressedAsState()

        val textScale by animateFloatAsState(
            targetValue = if (isPressed) 1.05f else 1f,
            animationSpec = spring(
                dampingRatio = 0.5f,
                stiffness = 1000f
            ),
            label = "textScale"
        )

        Card(
            onClick = onScrollToTop,
            interactionSource = islandInteractionSource,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .iosNavigationPressAnimation(islandInteractionSource),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2C2C2E)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 10.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = screenTitle,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 17.sp,
                    modifier = Modifier.graphicsLayer {
                        scaleX = textScale
                        scaleY = textScale
                    }
                )
            }
        }
    }
}

@Composable
fun Modifier.iosNavigationPressAnimation(
    interactionSource: MutableInteractionSource
): Modifier {

    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.04f else 1f,
        animationSpec = spring(
            dampingRatio = 0.55f,
            stiffness = 1000f
        ),
        label = "navigationScale"
    )

    return this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}