import requests

API_KEY = "ВАШ_API_КЛЮЧ"  # <-- вставьте сюда ваш ключ от CurrencyApi.net

def convert_currency(amount: float, from_currency: str, to_currency: str) -> float:
    """
    Конвертация валют через CurrencyApi.net.
    
    :param amount: Сумма для конвертации
    :param from_currency: Исходная валюта (например "USD")
    :param to_currency: Целевая валюта (например "RUB")
    :return: Результат конвертации (float)
    """
    url = "https://api.currencyapi.net/api/v1/convert"
    params = {
        "key": API_KEY,
        "from": from_currency.upper(),
        "to": to_currency.upper(),
        "amount": amount
    }

    try:
        response = requests.get(url, params=params, timeout=10)
        response.raise_for_status()  # проверка на ошибки HTTP
        data = response.json()

        # Проверка, что API вернул результат
        if "result" in data:
            return float(data["result"])
        else:
            raise ValueError(f"Ошибка API: {data}")

    except requests.RequestException as e:
        raise ConnectionError(f"Ошибка запроса к CurrencyApi.net: {e}")