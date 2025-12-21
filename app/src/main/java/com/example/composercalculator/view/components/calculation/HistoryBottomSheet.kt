package com.example.composercalculator.view.components.calculation

import android.icu.util.Calendar
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composercalculator.model.CalculationHistoryItem
import com.example.composercalculator.model.CalculatorEvent
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.viewmodel.CalculatorViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    calculatorViewModel: CalculatorViewModel,
    onAction: (CalculatorEvent) -> Unit = {},
    sheetState: SheetState,
    onDismiss: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(color = 0xFF1C1C1E),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(width = 40.dp)
                    .height(height = 4.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
    ) {
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
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { onAction(CalculatorEvent.ClearHistory) }) {
                Text(text = "Очистить", color = Color.Red, fontSize = 16.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxHeight(fraction = 0.6f), // Увеличим высоту
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp) // Расстояние между группами
        ) {
            /*if (groupedHistory.isEmpty()) {
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

            groupedHistory.forEach { (date, items) ->
                item(key = date) {
                    HistoryGroup(
                        date = date,
                        items = items,
                        onAction = onAction,
                    )
                }
            }*/
        }
    }
}

@Composable
private fun HistoryGroup(
    date: String,
    items: List<CalculationHistoryItem>,
    onAction: (CalculatorEvent) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(true) } // Состояние "свернуто/развернуто"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(Color(color = 0xFF2C2C2E))
            .animateContentSize() // Анимация изменения размера
    ) {
        GroupHeader(
            date = date,
            isExpanded = isExpanded,
            onClick = { isExpanded = !isExpanded }
        )

        if (isExpanded) {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                items.forEach { item ->
                    HistoryItemRow(
                        item = item,
                        onAction = onAction
                    )
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                }
            }
        }
    }
}

@Composable
private fun GroupHeader(
    date: String,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 0f else -90f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formatDateForHeader(dateString = date), // Преобразуем "2025-10-31" в "Сегодня" или "31 октября"
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Свернуть/Развернуть",
            tint = Color.Gray,
            modifier = Modifier.rotate(degrees = rotationAngle)
        )
    }
}

private fun formatDateForHeader(dateString: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = parser.parse(dateString) ?: return dateString

    val today = Calendar.getInstance()
    val yesterday = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    val itemCalendar = Calendar.getInstance().apply { time = date }

    return when {
        today.get(Calendar.YEAR) == itemCalendar.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == itemCalendar.get(Calendar.DAY_OF_YEAR) -> "Сегодня"

        yesterday.get(Calendar.YEAR) == itemCalendar.get(Calendar.YEAR) &&
                yesterday.get(Calendar.DAY_OF_YEAR) == itemCalendar.get(Calendar.DAY_OF_YEAR) -> "Вчера"

        else -> SimpleDateFormat("d MMMM yyyy", Locale("ru")).format(date)
    }
}

@Composable
private fun HistoryItemRow(
    item: CalculationHistoryItem,
    onAction: (CalculatorEvent) -> Unit,
    isNoteEnabled: Boolean = true
) {

    var labelText by remember(key1 = item.id) { mutableStateOf(value = item.label ?: "") }

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

//        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Button(
                onClick = { onAction(CalculatorEvent.RecallCalculation(item.expression)) },
                modifier = Modifier.weight(weight = 1f), // Занимает 1 долю (половину)
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(color = 0xFFDEAA45) // Синий цвет
                ),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(text = "Изменить", color = Color.White, fontSize = 13.sp)
            }

            Spacer(
                modifier = Modifier.width(width = 8.dp)
            )

            Button(
                onClick = { onAction(CalculatorEvent.DeleteHistoryItem(item.id)) },
                modifier = Modifier.weight(weight = 1f), // Занимает 1 долю (половину)
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(color = 0xFFEE4848) // Синий цвет
                ),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(text = "Удалить", color = Color.White, fontSize = 13.sp)
            }

        }

        if (isNoteEnabled) {

            // --- Поле для ввода метки ---
            OutlinedTextField(
                value = labelText,
                onValueChange = { newText ->
                    labelText = newText
                    // Отправляем событие для сохранения в ViewModel
                    onAction(CalculatorEvent.UpdateHistoryLabel(item.id, label = newText))
                },
                placeholder = { Text("Добавить заметку...", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Orange.copy(alpha = 0.5f),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    cursorColor = Orange,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
                shape = RoundedCornerShape(size = 12.dp)
            )
        }

//        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = item.getFormattedTime(),
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier
        )

    }
}
