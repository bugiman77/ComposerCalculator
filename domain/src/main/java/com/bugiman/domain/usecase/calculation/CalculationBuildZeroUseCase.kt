package com.bugiman.domain.usecase.calculation

class CalculationBuildZeroUseCase {
    private val operators = setOf('+', '-', '*', '/', '%', '(')

    operator fun invoke(current: String): String {
        // Если строка пустая — просто ставим 0
        if (current.isEmpty()) return "0"

        val lastChar = current.last()

        // Если последняя была закрывающая скобка: ")0" -> ")*0"
        if (lastChar == ')') return "$current*0"

        // Ищем последнее число в выражении
        val lastNumber = current.split(*operators.toCharArray()).last()

        // Если последнее число — это просто "0", то второй ноль не добавляем ("00" -> "0")
        if (lastNumber == "0") return current

        return current + "0"
    }
}