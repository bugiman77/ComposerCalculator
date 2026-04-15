def convert(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц температуры.

    Доступные единицы:
    - celsius
    - kelvin
    - fahrenheit
    - rankine
    - delisle
    - newton
    - reaumur
    - romer
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    # Сначала переводим в Цельсий
    if from_unit == "celsius":
        c = value
    elif from_unit == "kelvin":
        c = value - 273.15
    elif from_unit == "fahrenheit":
        c = (value - 32) * 5.0 / 9.0
    elif from_unit == "rankine":
        c = (value - 491.67) * 5.0 / 9.0
    elif from_unit == "delisle":
        c = 100 - value * 2.0 / 3.0
    elif from_unit == "newton":
        c = value * 100.0 / 33.0
    elif from_unit == "reaumur":
        c = value * 5.0 / 4.0
    elif from_unit == "romer":
        c = (value - 7.5) * 40.0 / 21.0
    else:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")

    # Затем переводим из Цельсия в целевую единицу
    if to_unit == "celsius":
        return c
    elif to_unit == "kelvin":
        return c + 273.15
    elif to_unit == "fahrenheit":
        return c * 9.0 / 5.0 + 32
    elif to_unit == "rankine":
        return (c + 273.15) * 9.0 / 5.0
    elif to_unit == "delisle":
        return (100 - c) * 3.0 / 2.0
    elif to_unit == "newton":
        return c * 33.0 / 100.0
    elif to_unit == "reaumur":
        return c * 4.0 / 5.0
    elif to_unit == "romer":
        return c * 21.0 / 40.0 + 7.5
    else:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")