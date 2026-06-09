package com.bugiman.composercalculator.view.components.calculation

import android.content.ClipData
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    onCloseClick: () -> Unit,
    onDeleteItem: (HistoryModel) -> Unit,
    onUpdateNote: (HistoryModel) -> Unit,
    onEditExpression: (HistoryModel) -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        containerColor = Color(color = 0xFF1C1C1E),
    ) {

        if (settingModel.historyHeaderLayout == 0) {
            HistoryHeaderContentCloseClear(
                calculatorViewModel = calculatorViewModel,
                onCloseClick = onCloseClick
            )
        } else {
            HistoryHeaderContentClearClose(
                calculatorViewModel = calculatorViewModel,
                onCloseClick = onCloseClick
            )
        }

        HistorySheetContent(
            calculatorViewModel = calculatorViewModel,
            settingModel = settingModel,
            onDeleteItem = onDeleteItem,
            onUpdateNote = onUpdateNote,
            onEditExpression = onEditExpression
        )
    }
}

@Composable
private fun HistoryHeaderContentCloseClear(
    calculatorViewModel: CalculatorViewModel,
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
                    .testTag(tag = "sheet_delete"),
                onClick = {
                    scope.launch {
                        calculatorViewModel.deleteAll()
                    }
                },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(1.dp, Color(color = 0xFFEE4848)),
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
    calculatorViewModel: CalculatorViewModel,
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
                    .testTag(tag = "sheet_delete"),
                onClick = {
                    scope.launch {
                        calculatorViewModel.deleteAll()
                    }
                },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                border = BorderStroke(1.dp, Color(color = 0xFFEE4848)),
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
    calculatorViewModel: CalculatorViewModel,
    settingModel: SettingModel,
    onDeleteItem: (HistoryModel) -> Unit,
    onUpdateNote: (HistoryModel) -> Unit,
    onEditExpression: (HistoryModel) -> Unit,
) {

    val historyItems by calculatorViewModel.history.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier.fillMaxHeight(fraction = 0.92f),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
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
                    historyModel = item,
                    settingModel = settingModel,
                    onDeleteItemClick = {
                        onDeleteItem(item)
                    },
                    onNoteUpdate = { newNote ->
                        onUpdateNote(item.copy(note = newNote))
                    },
                    onEditExpression = {
                        onEditExpression(item)
                    }
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryItemRow(
    historyModel: HistoryModel,
    settingModel: SettingModel,
    onDeleteItemClick: () -> Unit,
    onNoteUpdate: (String) -> Unit,
    onEditExpression: () -> Unit,
) {

    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    var showNoteSheet by remember {
        mutableStateOf(false)
    }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->

            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDeleteItemClick()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = settingModel.isSwipeEnabled,

        backgroundContent = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFEE4848))
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    ) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFF1C1C1E)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 14.dp,
                        vertical = 10.dp
                    )
            ) {

                Text(
                    text = historyModel.expression,
                    color = Color(0xFF7A869A),
                    fontSize = 13.sp,
                    maxLines = 1
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = historyModel.result,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )

                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    HistoryActionButton(
                        icon = Icons.Default.ContentCopy,
                        colorIcon = Color(0xFF7A869A),
                        onClick = {
                            scope.launch {

                                clipboard.setClipEntry(
                                    ClipEntry(
                                        ClipData.newPlainText(
                                            "Expression",
                                            historyModel.expression
                                        )
                                    )
                                )
                            }
                        }
                    )

                    Spacer(Modifier.width(8.dp))

                    HistoryActionButton(
                        icon = Icons.Default.Edit,
                        onClick = onEditExpression
                    )

                    Spacer(Modifier.width(8.dp))

                    HistoryActionButton(
                        icon = Icons.Default.Note,
                        onClick = {
                            showNoteSheet = true
                        }
                    )

                    Spacer(Modifier.weight(1f))

                    HistoryActionButton(
                        icon = Icons.Default.ContentCopy,
                        onClick = {
                            scope.launch {

                                clipboard.setClipEntry(
                                    ClipEntry(
                                        ClipData.newPlainText(
                                            "Result",
                                            historyModel.result
                                        )
                                    )
                                )
                            }
                        }
                    )

                    Spacer(Modifier.width(8.dp))

                    HistoryActionButton(
                        icon = Icons.Default.Delete,
                        onClick = onDeleteItemClick,
                        colorIcon = Color(0xFFF36464)
                    )
                }

                if (historyModel.note.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Surface(
                        color = Color(0xFF2C2C2E),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.Note,
                                contentDescription = null,
                                tint = Color(0xFFDEAA45),
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = historyModel.note,
                                color = Color.White,
                                fontSize = 13.sp,
                                maxLines = 2
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = DateTimeFormatter
                        .ofPattern("dd.MM.yyyy HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(
                            Instant.ofEpochMilli(
                                historyModel.timestamp
                            )
                        ),
                    color = Color.Gray,
                    fontSize = 11.sp
                )

            }
        }
    }

    Spacer(
        modifier = Modifier.height(6.dp)
    )

    if (showNoteSheet) {

        NoteSheet(
            initialText = historyModel.note,
            isTitleNote = settingModel.isTitleNote,
            onDismiss = {
                showNoteSheet = false
            },
            onNoteUpdate = onNoteUpdate
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteSheet(
    initialText: String,
    isTitleNote: Boolean,
    onDismiss: () -> Unit,
    onNoteUpdate: (String) -> Unit
) {
    var note by remember {
        mutableStateOf(initialText)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1C1C1E)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Заметка",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = note,
                onValueChange = {
                    note = it
                    onNoteUpdate(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                placeholder = {
                    Text(
                        "Введите заметку...",
                        color = Color.Gray
                    )
                },
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2C2E),
                    unfocusedContainerColor = Color(0xFF2C2C2E),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization =
                        if (isTitleNote)
                            KeyboardCapitalization.Sentences
                        else
                            KeyboardCapitalization.None
                )
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HistoryActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorIcon: Color = Color.White
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier.size(34.dp),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = Color(0xFF2C2C2E)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorIcon,
            modifier = Modifier.size(18.dp)
        )
    }
}