from simpleeval import simple_eval


def evaluate_expression(expression_string: str):
    def expressionProcessing(expression_string: str) -> str:
        return (expression_string
                .replace("^", "**")
                )

    try:
        processedExpression = expressionProcessing(expression_string=expression_string)
        return simple_eval(processedExpression)
    except Exception as e:
        return f"Ошибка при оценке выражения: {e}"