package com.bugiman.data.mapper

import com.bugiman.data.proto.SettingsProto
import com.bugiman.domain.models.settings.SettingModel

fun SettingsProto.toDomain(): SettingModel {
    return SettingModel(
        isDarkTheme = isDarkTheme,
        isSystemTheme = isSystemTheme,
        isShowHistoryButton = isShowHistoryButton,
        isSwipeEnabled = isSwipeEnabled,
        isNoteEnabled = isNoteEnabled,
        isShowIconButton = isShowIconButton,
        isPlaySound = isPlaySound,
        isPlayVibration = isPlayVibration,
        isKeepScreenOn = isKeepScreenOn,
        isShowPlaceholderInput = isShowPlaceholderInput,
        historyHeaderLayout = headerLayoutValue,
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