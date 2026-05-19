package com.bugiman.domain.usecase.calculation

class CalculationRemoveLastCharUseCase {
    operator fun invoke(current: String): String {
        if (current.isEmpty()) return ""

        //TODO удаляем посимвольно после ввода
        if (current.endsWith("(-")) {
            return current.dropLast(2)
        }

        return current.dropLast(1)
    }
}