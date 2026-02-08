package com.bugiman.composercalculator.view.components.settings.SettingsBlock

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bugiman.composercalculator.ui.theme.iOSGray
import com.bugiman.composercalculator.ui.theme.iOSGreen
import com.bugiman.composercalculator.view.components.calculation.SettingsGroup
import com.bugiman.composercalculator.view.components.calculation.SettingsRow
import com.bugiman.composercalculator.view.components.general.settings.SettingsSelectionRow
import com.bugiman.composercalculator.viewmodel.CalculatorViewModel
import com.bugiman.composercalculator.viewmodel.SettingsViewModel

@Composable
fun HistoryComputing(
    modifier: Modifier = Modifier,
    viewModelSettings: SettingsViewModel,
    viewModelCalculation: CalculatorViewModel
) {

    val isSwipeEnabled = viewModelSettings.isSwipeEnabled.collectAsState()
    val isNoteEnabled = viewModelSettings.isNoteEnabled.collectAsState()
    val showHistoryButton = viewModelSettings.showHistoryButton.collectAsState()
    val currentLayout = viewModelSettings.historyHeaderLayout.collectAsState()
    val isTitleNote = viewModelSettings.isTitleNote.collectAsState()
    val isClearHistoryOnClose = viewModelSettings.isClearHistoryOnClose.collectAsState()
    val isShowHistoryLastCalculation =
        viewModelSettings.isShowHistoryLastCalculation.collectAsState()

    SettingsGroup(title = "История вычислений") {
        SettingsRow(
            title = "История вычислений",
            subtitle = "Отображать кнопку истории вычислений в левом верхнем углу главного экрана",
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Switch(
                checked = showHistoryButton.value,
                onCheckedChange = { viewModelSettings.onShowHistoryChange(show = it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iOSGreen,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = iOSGray,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        if (showHistoryButton.value) {

            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsSelectionRow(
                title = "Кнопка закрытия",
                subtitle = "Выберите, с какой стороны будет находиться кнопка закрытия в истории",
                option1Text = "Слева",
                option2Text = "Справа",
                selectedOption = currentLayout.value,
                onClick1 = { viewModelSettings.toggleHistoryHeaderLayout(layout = 0) },
                onClick2 = { viewModelSettings.toggleHistoryHeaderLayout(layout = 1) }
            )

            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Свайп для удаления",
                subtitle = "Для удаления элемента истории можно включить использование свайпа",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = isSwipeEnabled.value,
                    onCheckedChange = { viewModelSettings.onSaveSwipeDeleteItem(isEnabled = it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Заметки",
                subtitle = "Пометка к вычисленному выражению",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = isNoteEnabled.value,
                    onCheckedChange = { viewModelSettings.onSaveNoteItem(isEnabled = it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            if (isNoteEnabled.value) {
                HorizontalDivider(color = Color(color = 0xFF3A3A3C))

                SettingsRow(
                    title = "Заглавная для заметки",
                    subtitle = "Начинать заметку с заглавной буквы",
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Switch(
                        checked = isTitleNote.value,
                        onCheckedChange = { viewModelSettings.toggleIsTitleNote(enabled = it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = iOSGreen,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = iOSGray,
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }

            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Удалять историю",
                subtitle = "При включении переключателя, история будет удаляться при закрытии приложения",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = isClearHistoryOnClose.value,
                    onCheckedChange = { viewModelSettings.toggleIsClearHistoryOnClose(enabled = it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

            HorizontalDivider(color = Color(color = 0xFF3A3A3C))

            SettingsRow(
                title = "Последнее вычисление",
                subtitle = "Отображать последнее вычисление при запуске приложения",
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Switch(
                    checked = isShowHistoryLastCalculation.value,
                    onCheckedChange = {
                        viewModelSettings.toggleIsShowHistoryLastCalculation(
                            enabled = it,
                            expression = viewModelCalculation.expression.value
                        )
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = iOSGreen,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = iOSGray,
                        uncheckedBorderColor = Color.Transparent
                    )
                )
            }

        }
    }

}
