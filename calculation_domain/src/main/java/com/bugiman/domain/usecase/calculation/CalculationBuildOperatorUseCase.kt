package com.bugiman.domain.usecase.calculation

class CalculationBuildOperatorUseCase {
    private val operators = setOf('+', '-', '*', '/', '%', '.')

    operator fun invoke(current: String, operator: String): String {
        if (current.isEmpty()) return "" // Оператор не может быть первым (кроме минуса, если нужно)

        val lastChar = current.last()

        // Если последний символ точка — игнорируем оператор
        //TODO если последний символ точка, то заменяем его на оператор
//        if (lastChar == '.') return current

        // Замена оператора: "5+" + "-" -> "5-"
        if (lastChar in operators) {
            return current.dropLast(1) + operator
        }

        // Нельзя ставить оператор сразу после открывающей скобки
        //TODO если после открывающей скобки ставится оператор, то скобку нужно закрыть
        if (lastChar == '(') return current

        return current + operator
    }
}