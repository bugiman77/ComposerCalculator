package com.bugiman.composercalculator.view.components.calculation

import android.content.ClipData
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bugiman.composercalculator.presentation.calculation.CalculatorViewModel
import com.bugiman.composercalculator.presentation.history.HistoryViewModel
import com.bugiman.composercalculator.ui.theme.Orange
import com.bugiman.domain.models.history.HistoryModel
import com.bugiman.domain.models.settings.SettingModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.collections.isNotEmpty

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryBottomSheet(
    calculatorViewModel: CalculatorViewModel,
    settingModel: SettingModel,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onCloseClick: () -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        containerColor = Color(color = 0xFF1C1C1E),
    ) {

        if (settingModel.historyHeaderLayout == 0) {
            HistoryHeaderContentCloseClear(
                onCloseClick = onCloseClick
            )
        } else {
            HistoryHeaderContentClearClose(
                onCloseClick = onCloseClick
            )
        }

        HistorySheetContent(
            settingModel = SettingModel,
        )
    }
}

@Composable
private fun HistoryHeaderContentCloseClear(
    onCloseClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val historyItems by calculatorViewModel.history.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()

        OutlinedButton(
            modifier = Modifier
                .size(size = 45.dp)
                .testTag(tag = "sheet_close"),
            onClick = onCloseClick,
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Закрыть",
                tint = Color.White
            )
        }

        if (historyItems.isNotEmpty()) {
            OutlinedButton(
                modifier = Modifier
                    .size(size = 45.dp)
                    .testTag(tag = "sheet_close"),
                onClick = {
                    scope.launch {
                        /*calculatorViewModel.deleteAll()*/
                    }
                },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(1.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Удалить все пункты истории вычислений",
                    tint = Color(color = 0xFFC74E4E)
                )
            }
        }
    }
}

@Composable
private fun HistoryHeaderContentClearClose(
    onCloseClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val historyItems by calculatorViewModel.history.collectAsStateWithLifecycle()
        val scope = rememberCoroutineScope()

        if (historyItems.isNotEmpty()) {
            OutlinedButton(
                modifier = Modifier
                    .size(size = 45.dp)
                    .testTag(tag = "sheet_close"),
                onClick = {
                    scope.launch {
                        /*calculatorViewModel.deleteAll()*/
                    }
                },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(1.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Удалить все пункты истории вычислений",
                    tint = Color(color = 0xFFEE4848)
                )
            }
        } else {
            Spacer(modifier = Modifier)
        }

        OutlinedButton(
            modifier = Modifier
                .size(size = 45.dp)
                .testTag(tag = "sheet_close"),
            onClick = onCloseClick,
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Закрыть",
                tint = Color.White
            )
        }

    }
}

@Composable
private fun HistorySheetContent(
    settingModel: SettingModel,
) {

    val historyItems = emptyList<String>() /*calculatorViewModel.history.collectAsStateWithLifecycle()*/

    LazyColumn(
        modifier = Modifier.fillMaxHeight(fraction = 0.92f),
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
            items(
                items = historyItems,
                key = { it.id }
            ) { item ->
                HistoryItemRow(
                    settingModel = settingModel,
                    item = item,
                )
            }
        }

    }
}

@Composable
private fun HistoryItemRow(
    historyModel: HistoryModel,
    settingModel: SettingModel,
    onDeleteItemClick: () -> Unit,
    onNoteUpdate: (String) -> Unit,
    onEditExpression: (String) -> Unit,
) {

    var labelText by remember(key1 = historyModel.id) { mutableStateOf(value = historyModel.note) }
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current

    val isSwipe = settingModel.isSwipeEnabled
    val isNote = settingModel.isNoteEnabled
    val isTitleNote = settingModel.isTitleNote

    // Состояние свайпа
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    // Действие при свайпе слева направо (например, копирование результата)
                    scope.launch {
                        val clipEntry =
                            ClipEntry(
                                ClipData.newPlainText(
                                    "Result",
                                    historyModel.result
                                )
                            )
                        clipboard.setClipEntry(clipEntry)
                    }
                    false // Возвращаем карточку на место
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    // Действие при свайпе справа налево (например, удаление)
                    onDeleteItemClick()
                    true
                }

                else -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // Слева направо
        enableDismissFromEndToStart = isSwipe, // Справа налево
        backgroundContent = {
            // Фоновый слой (иконки/цвета при свайпе)
            val direction = dismissState.dismissDirection
            val color = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Color(color = 0xFFDEAA45)
                SwipeToDismissBoxValue.EndToStart -> Color(color = 0xFFEE4848)
                else -> Color.Transparent
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = if (direction == SwipeToDismissBoxValue.StartToEnd)
                    Alignment.CenterStart else Alignment.CenterEnd
            ) {
                val icon = if (direction == SwipeToDismissBoxValue.StartToEnd)
                    Icons.Default.Delete else Icons.Default.Delete
                Icon(imageVector = icon, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(color = 0xFF1C1C1E),
            shape = RoundedCornerShape(size = 12.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = historyModel.expression,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    Text(
                        text = historyModel.result,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(height = 8.dp))

                Text(
                    text = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.ofEpochMilli(historyModel.timestamp)),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                )

                Spacer(modifier = Modifier.height(height = 8.dp))

                if (isNote) {

                    OutlinedTextField(
                        value = labelText,
                        onValueChange = { newText ->
                            labelText = newText
                            onNoteUpdate(newText)
                        },
                        placeholder = { Text(text = "Заметка", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        singleLine = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF2C2C2E),
                            unfocusedContainerColor = Color.Transparent,
                            focusedBorderColor = Orange.copy(alpha = 0.5f),
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = Orange,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                        ),
                        shape = RoundedCornerShape(size = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = if (isTitleNote) KeyboardCapitalization.Sentences
                            else KeyboardCapitalization.None // С заглавной буквы в начале предложения
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(height = 8.dp)
                    )

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    Button(
                        onClick = { },
                        modifier = Modifier.weight(weight = 1f),
                        shape = RoundedCornerShape(size = 20.dp),
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
                        onClick = { onDeleteItemClick() },
                        modifier = Modifier.weight(weight = 1f),
                        shape = RoundedCornerShape(size = 20.dp),
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
                                    ClipEntry(
                                        ClipData.newPlainText(
                                            "Expression",
                                            historyModel.expression
                                        )
                                    )
                                clipboard.setClipEntry(clipEntry)
                            }
                        },
                        modifier = Modifier.weight(weight = 1f), // Занимает 1 долю (половину)
                        shape = RoundedCornerShape(size = 20.dp),
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
                                val clipEntry =
                                    ClipEntry(
                                        ClipData.newPlainText(
                                            "Result",
                                            historyModel.result
                                        )
                                    )
                                clipboard.setClipEntry(clipEntry)
                            }
                        },
                        modifier = Modifier.weight(weight = 1f), // Занимает 1 долю (половину)
                        shape = RoundedCornerShape(size = 20.dp),
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
    }

    Spacer(Modifier.height(height = 4.dp))

    HorizontalDivider()

    Spacer(Modifier.height(height = 4.dp))

}
