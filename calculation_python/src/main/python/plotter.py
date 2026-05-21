import matplotlib.pyplot as plt
import numpy as np
import io
import base64
from sympy import sympify, lambdify, symbols

def generate_graph_base64(expression_str, x_min=-10, x_max=10):
    try:
        # 1. Подготовка данных
        x = symbols('x')
        expr = sympify(expression_str)

        # Превращаем выражение SymPy в быструю функцию NumPy
        f = lambdify(x, expr, modules=['numpy'])

        x_vals = np.linspace(x_min, x_max, 500)
        y_vals = f(x_vals)

        # 2. Построение графика
        plt.figure(figsize=(6, 4))
        plt.plot(x_vals, y_vals, label=f"y = {expression_str}", color='blue', linewidth=2)
        plt.axhline(0, color='black', linewidth=0.5) # Ось X
        plt.axvline(0, color='black', linewidth=0.5) # Ось Y
        plt.grid(True, linestyle='--', alpha=0.7)
        plt.legend()

        # 3. Сохранение в буфер (вместо файла)
        buf = io.BytesIO()
        plt.savefig(buf, format='png', dpi=100)
        plt.close() # Важно закрыть фигуру для экономии памяти
        buf.seek(0)

        # 4. Кодирование в Base64 для передачи в Android
        img_base64 = base64.b64encode(buf.read()).decode('utf-8')
        return {"status": "success", "image": img_base64}

    except Exception as e:
        return {"status": "error", "message": str(e)}