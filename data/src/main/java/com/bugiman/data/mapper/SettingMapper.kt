package com.bugiman.data.mapper

import com.bugiman.data.local.entity.SettingEntity
import com.bugiman.domain.models.SettingModel

fun SettingEntity.toDomain(): SettingModel {
    return SettingModel(
        id = id,
        isDarkTheme = isDarkTheme,
        isSystemTheme = isSystemTheme,
        showHistoryButton = showHistoryButton,
        systemFontSize = systemFontSize,
        displayFontSize = displayFontSize,
        decimalFormat = decimalFormat,
        isSaveHistoryData = isSaveHistoryData,
        isSaveSettingsData = isSaveSettingsData,
        isSwipeEnabled = isSwipeEnabled,
        isNoteEnabled = isNoteEnabled,
        showIconButton = showIconButton,
        playSound = playSound,
        playVibration = playVibration,
        bottomSpacer = bottomSpacer,
        isAnimationAll = isAnimationAll,
        keepScreenOn = keepScreenOn,
        showPlaceholderInput = showPlaceholderInput,
        historyHeaderLayout = historyHeaderLayout,
        isTitleNote = isTitleNote,
        isClearHistoryOnClose = isClearHistoryOnClose,
        isShowHistoryLastCalculation = isShowHistoryLastCalculation,
    )
}

fun SettingModel.toEntity(): SettingEntity {
    return SettingEntity(
        id = id,
        isDarkTheme = isDarkTheme,
        isSystemTheme = isSystemTheme,
        showHistoryButton = showHistoryButton,
        systemFontSize = systemFontSize,
        displayFontSize = displayFontSize,
        decimalFormat = decimalFormat,
        isSaveHistoryData = isSaveHistoryData,
        isSaveSettingsData = isSaveSettingsData,
        isSwipeEnabled = isSwipeEnabled,
        isNoteEnabled = isNoteEnabled,
        showIconButton = showIconButton,
        playSound = playSound,
        playVibration = playVibration,
        bottomSpacer = bottomSpacer,
        isAnimationAll = isAnimationAll,
        keepScreenOn = keepScreenOn,
        showPlaceholderInput = showPlaceholderInput,
        historyHeaderLayout = historyHeaderLayout,
        isTitleNote = isTitleNote,
        isClearHistoryOnClose = isClearHistoryOnClose,
        isShowHistoryLastCalculation = isShowHistoryLastCalculation,
    )
}