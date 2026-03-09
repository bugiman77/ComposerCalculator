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

fun SettingModel.toProto(currentProto: SettingsProto): SettingsProto {
    return currentProto.toBuilder()
        .setIsDarkTheme(this.isDarkTheme)
        .setIsSystemTheme(this.isSystemTheme)
        .setIsShowHistoryButton(this.isShowHistoryButton)
        .setIsSwipeEnabled(this.isSwipeEnabled)
        .setIsNoteEnabled(this.isNoteEnabled)
        .setIsShowIconButton(this.isShowIconButton)
        .setIsPlaySound(this.isPlaySound)
        .setIsPlayVibration(this.isPlayVibration)
        .setIsKeepScreenOn(this.isKeepScreenOn)
        .setIsShowPlaceholderInput(this.isShowPlaceholderInput)
        .setIsTitleNote(this.isTitleNote)
        .setIsClearHistoryOnClose(this.isClearHistoryOnClose)
        .setIsShowHistoryLastCalculation(this.isShowHistoryLastCalculation)
        .setHeaderLayoutValue(this.historyHeaderLayout)
        .build()
}