# import requests


def get_exchange_rate(amount: int|str, from_currency: str, to_currency: str) -> dict[str, int | str]:
    """
    Конвертирует сумму из одной валюты в другую.
    Коды валют должны быть в формате ISO (например, USD, EUR, RUB).
    """
    url = f"https://api.frankfurter.app/latest?amount={amount}&from={from_currency}&to={to_currency}"

    try:
        response = requests.get(url)
        response.raise_for_status()  # Проверка на ошибки HTTP
        data = response.json()
        print(data)

        # Извлекаем результат из ответа
        result = data['rates'][to_currency]
        return {
            "status": "success",
            "amount": amount,
            "from": from_currency,
            "to": to_currency,
            "result": result
        }
    except requests.exceptions.RequestException as e:
        return {
            "status": "error",
            "message": f"Ошибка сети: {str(e)}"
        }
    except KeyError:
        return {
            "status": "error",
            "message": "Неверный код валюты"
        }