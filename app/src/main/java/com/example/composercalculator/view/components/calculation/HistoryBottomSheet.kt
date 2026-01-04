package com.example.composercalculator.view.components.calculation

import android.content.ClipData
import android.icu.util.Calendar
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composercalculator.data.local.db.entity.History
import com.example.composercalculator.model.CalculatorEvent
import com.example.composercalculator.ui.theme.Orange
import com.example.composercalculator.viewmodel.CalculatorViewModel
import com.example.composercalculator.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    calculatorViewModel: CalculatorViewModel,
    settingsViewModel: SettingsViewModel,
    onAction: (CalculatorEvent) -> Unit = {},
    sheetState: SheetState,
    onDismiss: () -> Unit
) {

    val historyItems by calculatorViewModel.history.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

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
            TextButton(
                onClick = {
                    scope.launch {
                        calculatorViewModel.deleteHistoryItemAll()
                    }
                }
            ) {
                Text(text = "Очистить", color = Color.Red, fontSize = 16.sp)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxHeight(fraction = 0.6f), // Увеличим высоту
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp) // Расстояние между группами
        ) {

            if (historyItems.isEmpty()) {
                item {
                    Text(
                        text = "История вычислений пуста",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                // Отображаем список записей
                items(
                    items = historyItems,
                    key = { it.id } // Используем ID из Room для оптимизации
                ) { item ->
                    HistoryItemRow(
                        item = item,
                        isNoteEnabled = settingsViewModel.isNoteEnabled.collectAsState().value,
                        calculatorViewModel = calculatorViewModel,
                    )
                }
            }

        }
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
    item: History,
    isNoteEnabled: Boolean,
    calculatorViewModel: CalculatorViewModel,
) {

    var labelText by remember(key1 = item.id) { mutableStateOf(value = item.note) }
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current

    Column(
        modifier = Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.End
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {

            Text(
                text = item.expression,
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                text = "=${item.result}",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(height = 8.dp))

        Text(
            text = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                .withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(item.timestamp)),
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(height = 8.dp))

        if (isNoteEnabled) {

            OutlinedTextField(
                value = labelText,
                onValueChange = { newText ->
                    labelText = newText
                    scope.launch {
                        calculatorViewModel.onInputNote(itemHistory = item, newNote = newText)
                    }
                },
                placeholder = { Text(text = "Добавить заметку...", color = Color.Gray) },
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

            Spacer(
                modifier = Modifier.height(height = 8.dp)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Button(
                onClick = {
                    calculatorViewModel.editingExpression(itemHistory = item)
                },
                modifier = Modifier.weight(weight = 1f),
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(color = 0xFFDEAA45)
                ),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(text = "Изменить", color = Color.White, fontSize = 13.sp)
            }

            Spacer(
                modifier = Modifier.width(width = 8.dp)
            )

            Button(
                onClick = { calculatorViewModel.deleteHistoryItem(itemHistory = item) },
                modifier = Modifier.weight(weight = 1f),
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(color = 0xFFEE4848)
                ),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(text = "Удалить", color = Color.White, fontSize = 13.sp)
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Button(
                onClick = {
                    scope.launch {
                        val clipEntry =
                            ClipEntry(ClipData.newPlainText("Expression", item.expression))
                        clipboard.setClipEntry(clipEntry)
                    }
                },
                modifier = Modifier.weight(weight = 1f), // Занимает 1 долю (половину)
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(color = 0xFF757575)
                ),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(text = "Скопировать выражение", color = Color.White, fontSize = 13.sp)
            }

            Spacer(
                modifier = Modifier.width(width = 8.dp)
            )

            Button(
                onClick = {
                    scope.launch {
                        val clipEntry = ClipEntry(ClipData.newPlainText("Result", item.result))
                        clipboard.setClipEntry(clipEntry)
                    }
                },
                modifier = Modifier.weight(weight = 1f), // Занимает 1 долю (половину)
                shape = RoundedCornerShape(size = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(color = 0xFF616161)
                ),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(text = "Скопировать результат", color = Color.White, fontSize = 13.sp)
            }

        }

    }
}
