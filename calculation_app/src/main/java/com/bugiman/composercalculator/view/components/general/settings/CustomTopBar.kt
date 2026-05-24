package com.bugiman.composercalculator.view.components.general.settings

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugiman.composercalculator.R
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun CustomTopBar(
    hazeState: HazeState,
    screenTitle: String,
    onNavigateBack: () -> Unit,
    onScrollToTop: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {

        // Кнопка назад
        Card(
            onClick = onNavigateBack,
            shape = CircleShape,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterStart)
                .hazeChild(
                    state = hazeState,
                    style = HazeMaterials.ultraThin()
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x662C2C2E)
            )
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.back_ios),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Заголовок
        Card(
            onClick = onScrollToTop,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .hazeChild(
                    state = hazeState,
                    style = HazeMaterials.ultraThin()
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x662C2C2E)
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
                    fontSize = 17.sp
                )
            }
        }
    }
}