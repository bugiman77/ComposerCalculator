package com.example.composercalculator.view.screen.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.R
import com.example.composercalculator.ui.theme.DarkGray
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.view.components.settings.SettingsBlock.Animations
import com.example.composercalculator.view.components.settings.SettingsBlock.App
import com.example.composercalculator.view.components.settings.SettingsBlock.AppTheme
import com.example.composercalculator.view.components.settings.SettingsBlock.Appearance
import com.example.composercalculator.view.components.settings.SettingsBlock.Display
import com.example.composercalculator.view.components.settings.SettingsBlock.HistoryComputing
import com.example.composercalculator.view.components.settings.SettingsBlock.SavingData
import com.example.composercalculator.view.components.settings.SettingsBlock.SoundAndVibration
import com.example.composercalculator.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModelSettings: SettingsViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToCreateThemes: () -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(color = 0xFF161616), // Фон всего экрана
        contentWindowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0),
        topBar = {
            CustomTopBar(
                onNavigateBack = onNavigateBack,
                onScrollToTop = {
                    coroutineScope.launch { scrollState.animateScrollTo(value = 0) }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState)
        ) {

            AppTheme(
                viewModelSettings = viewModelSettings,
                onNavigateToCreateThemes = onNavigateToCreateThemes
            )

            Spacer(modifier = Modifier.height(height = 24.dp))

            SoundAndVibration(viewModelSettings = viewModelSettings)

            Spacer(modifier = Modifier.height(height = 24.dp))

            Appearance(viewModelSettings = viewModelSettings)

            Spacer(modifier = Modifier.height(height = 24.dp))

            HistoryComputing(viewModelSettings = viewModelSettings)

            Spacer(modifier = Modifier.height(height = 24.dp))

            Display(viewModelSettings = viewModelSettings)

            Spacer(modifier = Modifier.height(height = 24.dp))

            Animations(viewModelSettings = viewModelSettings)

            Spacer(modifier = Modifier.height(height = 24.dp))

            SavingData(viewModelSettings = viewModelSettings)

            Spacer(modifier = Modifier.height(height = 24.dp))

            App(onNavigateToAbout = onNavigateToAbout)

            Spacer(modifier = Modifier.height(height = 24.dp))
        }
    }
}

@Composable
fun CustomTopBar(
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
        Card(
            onClick = onNavigateBack,
            shape = CircleShape,
            modifier = Modifier
                .size(size = 48.dp)
                .align(Alignment.CenterStart),
            colors = CardDefaults.cardColors(containerColor = Color(color = 0xFF2C2C2E)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(id = R.drawable.back_ios),
                    contentDescription = "Назад",
                    tint = Color.White
                )
            }
        }

        Card(
            shape = RoundedCornerShape(size = 24.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { onScrollToTop() },
            colors = CardDefaults.cardColors(containerColor = Color(color = 0xFF2C2C2E)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Настройки",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * Кастомная круглая кнопка "Назад" в стиле приложения.
 * @param onClick Действие при нажатии.
 * @param modifier Модификатор для позиционирования.
 */
@Composable
fun CustomBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Создаем едва заметный вертикальный градиент для фона
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            DarkGray,
            DarkGray
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
        onClick = onClick,
        modifier = modifier
            .padding(start = 16.dp)
            .size(size = 40.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent // Фон кнопки будет задан через Modifier.background
        ),
        // Убираем стандартную тень
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        // Устанавливаем кастомную границу
        border = BorderStroke(width = 1.dp, borderBrush),
        contentPadding = PaddingValues(all = 0.dp)
    ) {
        // Оборачиваем Icon в Box с градиентным фоном
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_ios),
                contentDescription = "Назад",
                tint = Color.White
            )
        }
    }
}

/**
 * Ряд с RadioButton для выбора опции.
 * @param title Текст опции.
 * @param isSelected Выбрана ли эта опция.
 * @param onClick Действие при нажатии.
 */
@Composable
private fun SettingsRadioRow(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, color = Color.White, fontSize = 17.sp)
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Orange)
        )
    }
}
