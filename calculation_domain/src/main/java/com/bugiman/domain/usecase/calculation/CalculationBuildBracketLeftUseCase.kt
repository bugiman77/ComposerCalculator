package com.bugiman.domain.usecase.calculation

class CalculationBuildBracketLeftUseCase {

    operator fun invoke(current: String, bracketLeft: String): String {
        val lastChar = current.last()

        if (lastChar.isDigit()) return "$current*$bracketLeft"
        if (lastChar.toString() == bracketLeft) return current + bracketLeft
        if (lastChar == ')') return "$current*$bracketLeft"
        if (lastChar == '.') return current.dropLast(1) + bracketLeft

        return current + bracketLeft
    }

}