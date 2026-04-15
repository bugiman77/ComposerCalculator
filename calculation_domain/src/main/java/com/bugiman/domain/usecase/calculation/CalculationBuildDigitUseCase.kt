package com.bugiman.domain.usecase.calculation

class CalculationBuildDigitUseCase {
    operator fun invoke(current: String, digit: String): String {
        if (current.isEmpty()) return digit

        val lastChar = current.last()

        // Авто-умножение: ")5" -> ")*5"
        if (lastChar == ')') return "$current*$digit"

        // Запрет лишних нулей: если в текущем числе уже есть "0", превращаем в "0.5" или игнорируем
        val lastNumber = current.split('+', '-', '*', '/', '%', '(').last()
        if (lastNumber == "0") return current.dropLast(1) + digit

        return current + digit
    }
}