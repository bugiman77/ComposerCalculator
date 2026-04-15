package com.bugiman.domain.usecase.calculation

class CalculationBuildBracketUseCase {
    operator fun invoke(current: String, bracket: String): String {
        if (current.isEmpty() && bracket == "(") return "("
        if (current.isEmpty()) return ""

        val lastChar = current.last()

        if (bracket == "(") {
            // Если перед "(" число или ")", добавляем "*"
            return if (lastChar.isDigit() || lastChar == ')') "$current*(" else "$current("
        }

        if (bracket == ")") {
            // Закрывать скобку можно только если есть открытые
            val openCount = current.count { it == '(' }
            val closeCount = current.count { it == ')' }
            if (openCount <= closeCount || lastChar == '(') return current
            return "$current)"
        }

        return current
    }
}