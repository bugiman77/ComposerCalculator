package com.bugiman.domain.usecase.calculation

class CalculationBuildBracketRigthUseCase {
    private val operators = setOf('+', '-', '*', '/', '%', '.')

    operator fun invoke(current: String, bracketRigth: String): String {
        val countBracketLeft = current.count { it == '(' }
        val countBracketRight = current.count { it == ')' }
        val lastChar = current.last()
        if (countBracketLeft > countBracketRight) {
            if (lastChar in operators) {

            }
        }
        return current
    }

}