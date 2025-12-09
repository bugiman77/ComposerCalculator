def evaluate_expression(expression_string):
    """
    Принимает строку выражения и вычисляет ее с помощью eval.
    В реальном приложении eval() использовать опасно,
    но для заглушки подойдет.
    """
    try:
        # Используем simpleeval для безопасной оценки выражения
        result = simpleeval(expr)
        return result
    except Exception as e:
        return f"Ошибка при оценке выражения: {e}"
