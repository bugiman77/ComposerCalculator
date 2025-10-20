package com.example.composercalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.ui.theme.ComposerCalculatorTheme
import com.example.composercalculator.ui.theme.DarkGray
import com.example.composercalculator.ui.theme.LightGray
import com.example.composercalculator.ui.theme.Orange

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposerCalculatorTheme {
                CalculatorScreen()
            }
        }
    }
}

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier
) {

    var displayText by remember { mutableStateOf("0") }

    Box(
        modifier = modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp
            ) // Отступы слева и справа от всего экрана
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp) // Расстояние между рядами
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f) // Задаем высоту для контейнера
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                contentAlignment = Alignment.BottomEnd // Привязываем контент к нижнему правому углу
            ) {
                Text(
                    text = displayText,
                    fontSize = 80.sp, // Крупный шрифт для результата
                    color = Color.White,
                    textAlign = TextAlign.End, // Выравнивание текста по правому краю
                    maxLines = 1
                )
            }

            /*buttonLayout.forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp) // Расстояние между кнопками
                ) {
                    rowItems.forEachIndexed { colIndex, buttonText ->
                        // Определяем цвет кнопки на основе ее положения
                        val buttonColor = when {
                            // Последняя кнопка в ряду (оранжевая)
                            colIndex == rowItems.size - 1 || (rowIndex == buttonLayout.size - 1 && colIndex == rowItems.size - 1) -> Orange
                            // Первые три кнопки в первом ряду (светло-серые)
                            rowIndex == 0 && colIndex < 3 -> LightGray
                            // Кнопка "0" занимает две ячейки, поэтому для нее особый случай
                            buttonText == "0" -> DarkGray
                            // Все остальные (темно-серые)
                            else -> DarkGray
                        }

                        val weight = 1f

                        // Для кнопки "0" пропускаем создание второй ячейки, так как она уже занята
                        if (buttonText != "." && buttonText != "=") {
                            BtnCalculation(
                                text = buttonText,
                                modifier = Modifier
                                    .weight(weight)
                                    .aspectRatio(weight) // Сохраняем круглую форму
                                    .align(Alignment.CenterVertically),
                                color = buttonColor
                            )
                        } else if (rowIndex == buttonLayout.size - 1 && colIndex > 0) {
                            BtnCalculation(
                                text = buttonText,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f) // Сохраняем круглую форму
                                    .align(Alignment.CenterVertically),
                                color = if (buttonText == "=") Orange else DarkGray
                            )
                        }
                    }
                }
            }*/

            // --- Ряд 1: AC, +/-, %, ÷ ---
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp) // Расстояние между кнопками
            ) {
                BtnCalculation(
                    text = "AC",
                    color = LightGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 20.sp
                )
                BtnCalculation(
                    text = "+/-",
                    color = LightGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 20.sp
                )
                BtnCalculation(
                    text = "%",
                    color = LightGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 60.sp
                )
                BtnCalculation(
                    text = "÷",
                    color = Orange,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 60.sp
                )
            }

            // --- Ряд 2: 7, 8, 9, × ---
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BtnCalculation(
                    text = "7",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "8",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "9",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "×",
                    color = Orange,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 60.sp
                )
            }

            // --- Ряд 3: 4, 5, 6, - ---
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BtnCalculation(
                    text = "4",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "5",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "6",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "-",
                    color = Orange,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 60.sp
                )
            }

            // --- Ряд 4: 1, 2, 3, + ---
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BtnCalculation(
                    text = "1",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "2",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "3",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = "+",
                    color = Orange,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 60.sp
                )
            }

            // --- Ряд 5: 0, ,, = ---
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BtnCalculation(
                    text = "0",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 20.sp
                )
                BtnCalculation(
                    text = "0",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 45.sp
                )
                BtnCalculation(
                    text = ",",
                    color = DarkGray,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 60.sp
                )
                BtnCalculation(
                    text = "=",
                    color = Orange,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f) // Сохраняем круглую форму
                        .align(Alignment.CenterVertically),
                    fontSize = 60.sp
                )
            }
        }
    }
}


@Composable
private fun BtnCalculation(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    fontSize: TextUnit,
) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = color) // Устанавливаем цвет кнопки
    ) {
        Text(
            text = text,
            fontSize = fontSize, // Увеличим размер текста для лучшей читаемости
            color = Color.White, // Устанавливаем цвет текста
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.ExtraLight
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainScreenLight() {
    ComposerCalculatorTheme(
        darkTheme = false
    ) {
        CalculatorScreen()
    }
}
