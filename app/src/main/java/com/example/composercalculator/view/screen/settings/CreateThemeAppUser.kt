package com.example.composercalculator.view.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composercalculator.viewmodel.ThemesViewModel

@Composable
fun CreateThemeAppUser(
    viewModelThemes: ThemesViewModel = viewModel(),
    onNavigateBack: () -> Unit,
) {

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(color = 0xFF161616),
        contentWindowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0),
        topBar = {
            CustomTopBar(
                screenTitle = "Новая тема",
                onNavigateBack = onNavigateBack,
                onScrollToTop = { },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Text(
                text = "Настройка темы",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(text = "Предпросмотр", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 16.dp))
                    .background(Color(color = 0xFF121212))
                    .padding(all = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(size = 70.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(size = 12.dp), ambientColor = Color.White)
                        .background(Color.White, shape = RoundedCornerShape(size = 12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("7", color = Color.White, fontSize = 28.sp)
                }
            }

            Spacer(modifier = Modifier.height(height = 32.dp))

            // --- ВЫБОР ЦВЕТА КНОПОК ---
            Text(text = "Цвет кнопок", color = Color.White, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 5),
                horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                verticalArrangement = Arrangement.spacedBy(space = 12.dp)
            ) {
                val colorPresets = listOf(
                    Color(color = 0xFF1E1E1E), Color(color = 0xFF2D2D2D), Color(color = 0xFF3E3E3E),
                    Color(color = 0xFF4A90E2), Color(color = 0xFFE91E63), Color(color = 0xFF4CAF50),
                    Color(color = 0xFFFF9800), Color(color = 0xFF9C27B0), Color(color = 0xFF795548)
                )
                items(items = colorPresets) { color ->
                    Box(
                        modifier = Modifier
                            .size(size = 45.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = /*if (colors.buttonColor == color) 2.dp else*/ 0.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
//                            .clickable { viewModel.updateButtonColor(color) }
                    )
                }

            }

            Box(
                modifier = Modifier
                    .size(size = 50.dp)
                    .clip(CircleShape)
                    .background(Brush.sweepGradient(listOf(Color.Red, Color.Yellow, Color.Green, Color.Cyan, Color.Blue, Color.Magenta, Color.Red)))
                    .clickable { /*showColorPickerDialog = true*/ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(size = 30.dp)
                )
            }

            Spacer(modifier = Modifier.weight(weight = 1f))

            // Кнопка сохранения (стилизованная под вашу навигацию)
            Button(
                onClick = { /*navController.popBackStack()*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(size = 16.dp)
            ) {
                Text(text = "Применить", color = Color.Black, fontWeight = FontWeight.Bold)
            }

        }
    }

}