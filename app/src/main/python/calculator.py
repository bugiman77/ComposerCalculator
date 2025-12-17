from simpleeval import simple_eval


def evaluate_expression(expression_string: str):
    try:
        return simple_eval(expression_string)
    except Exception as e:
        return f"Ошибка при оценке выражения: {e}"
