from simpleeval import simple_eval
from decimal import Decimal


def evaluate_expression(expression_string: str):
    try:
        expr = expression_string.replace("^", "**")
        result = simple_eval(expr)

        if isinstance(result, (float, int)):
            final_res = Decimal(str(round(result, 12))).normalize()
            return format(final_res, 'f')

        return str(result)
    except Exception:
        return "Ошибка"