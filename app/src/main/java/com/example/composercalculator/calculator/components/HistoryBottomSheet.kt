package com.example.composercalculator.calculator.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissValue
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissDirection
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwipeToDismiss
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.model.CalculationHistoryItem
import com.example.composercalculator.model.CalculatorEvent
import androidx.compose.runtime.getValue

@OptIn(
    ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun HistoryBottomSheet(
    history: List<CalculationHistoryItem>,
    onAction: (CalculatorEvent) -> Unit,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF2C2C2E),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {

            Text(
                text = "",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "История",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TextButton(onClick = { onAction(CalculatorEvent.ClearHistory) }) {
                    Text("Очистить", color = Color.Red, fontSize = 16.sp)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
            ) {
                if (history.isEmpty()) {
                    item {
                        Text(
                            text = "История вычислений пуста",
                            color = Color.Gray,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp)
                        )
                    }
                }

                items(
                    items = history,
                    key = { item -> item.id }
                ) { item ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                onAction(CalculatorEvent.DeleteHistoryItem(item.id))
                                return@rememberDismissState true
                            }
                            false
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.8f)
                                    else -> Color.Transparent
                                }
                            )
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.DismissedToStart) 1.2f else 0.8f
                            )
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Удалить",
                                    modifier = Modifier.scale(scale),
                                    tint = Color.White
                                )
                            }
                        },
                        dismissContent = {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF1C1C1E) // Задаем фон
                                ),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                HistoryItemRow(
                                    item = item,
                                    onAction = onAction
                                )
                            }
                        }
                    )
                    HorizontalDivider(color = Color(0xFF3A3A3C))
                }
            }
        }
    }
}

@Composable
private fun HistoryItemRow(    item: CalculationHistoryItem,
                               onAction: (CalculatorEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = item.getFormattedTime(),
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.BottomStart)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = item.expression,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Text(
                    text = "= ${item.result}",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                IconButton(
                    onClick = { /* TODO: Логика редактирования */ },
                    modifier = Modifier.size(24.dp) // Уменьшаем размер кнопок
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Редактировать",
                        tint = Color(0xFFFF9800)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { onAction(CalculatorEvent.DeleteHistoryItem(item.id)) },
                    modifier = Modifier.size(24.dp) // Уменьшаем размер кнопок
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = Color(0xFFF8665B)
                    )
                }
            }
        }
    }
}
