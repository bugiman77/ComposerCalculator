package com.bugiman.composercalculator.view.base

/*
class ViewModelFactory(
    private val soundManager: SoundManager,
    private val vibrationManager: VibrationManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Если создаем CalculatorViewModel
            modelClass.isAssignableFrom(CalculatorViewModel::class.java) -> {
                CalculatorViewModel(soundManager, vibrationManager) as T
            }
            // Если создаем SettingsViewModel (если ей тоже нужны менеджеры)
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}*/
