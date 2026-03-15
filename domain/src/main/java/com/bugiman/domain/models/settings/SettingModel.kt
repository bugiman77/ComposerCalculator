package com.bugiman.domain.models.settings

data class SettingModel(
    var isDarkTheme: Boolean = true,
    var isSystemTheme: Boolean = false,
    var isShowHistoryButton: Boolean = true,
    var isSystemFontSize: Boolean = true,
    var isSwipeEnabled: Boolean = true,
    var isNoteEnabled: Boolean = true,
    var isShowIconButton: Boolean = true,
    var isPlaySound: Boolean = false,
    var isPlayVibration: Boolean = false,
    var isKeepScreenOn: Boolean = false,
    var isShowPlaceholderInput: Boolean = true,
    var isTitleNote: Boolean = true,
    var isClearHistoryOnClose: Boolean = false,
    var isShowHistoryLastCalculation: Boolean = true,
    var historyHeaderLayout: Int = 0,
)