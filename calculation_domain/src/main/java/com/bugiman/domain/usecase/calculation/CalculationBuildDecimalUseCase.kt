package com.bugiman.domain.usecase.calculation

class CalculationBuildDecimalUseCase {
    private val operators = setOf('+', '-', '*', '/', '%', '(')

    operator fun invoke(current: String): String {
        if (current.isEmpty()) return "0."

        val lastChar = current.last()
        // TODO если после скобки ставим точку, то заменяем с "...)." ==> "...)*0."
        if (lastChar == ')') return current // После скобки точку нельзя

        // Проверяем последнее число в строке на наличие точки
        val lastNumber = current.split(*operators.toCharArray()).last()
        if (lastNumber.contains('.')) return current

        // Если последний символ оператор — добавляем "0."
        if (lastChar in operators) return current + "0."

        return "$current."
    }
}