# import sympy as sp

def process_expression(user_input: str, mode: str) -> dict[str, str]:
    """
    user_input: Строка вида "(x + 1)**2 + 2*x - (x**2 + 1)"
    mode: "simplify" (упростить) или "expand" (раскрыть скобки)
    """
    try:
        # 1. Парсим строку в выражение SymPy
        # sympify автоматически распознает переменные (x, y, a и т.д.)
        expr = sp.sympify(user_input)

        # 2. Выбираем операцию
        if mode == "simplify":
            result = sp.simplify(expr)
        elif mode == "expand":
            result = sp.expand(expr)
        else:
            result = expr

        # 3. Возвращаем результат в виде читаемой строки
        return {
            "status": "success",
            "result": str(result),
            "latex": sp.latex(result)  # Понадобится для красивого отображения
        }

    except Exception as e:
        return {
            "status": "error",
            "message": f"Ошибка в выражении: {str(e)}"
        }