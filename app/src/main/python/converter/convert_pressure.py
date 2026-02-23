# Базовая единица — паскаль (Pa)

STANDARD_GRAVITY = 9.80665

CONVERSION_TO_PASCAL = {

    # СИ
    "pascal": 1.0,
    "kilopascal": 1_000.0,
    "megapascal": 1_000_000.0,
    "gigapascal": 1_000_000_000.0,

    # Бар
    "bar": 100_000.0,
    "millibar": 100.0,

    # Атмосферы
    "atmosphere": 101_325.0,
    "technical_atmosphere": 98_066.5,  # кгс/см²

    # Торр и ртуть
    "torr": 133.322368,
    "millimeter_of_mercury": 133.322368,
    "inch_of_mercury": 3386.389,

    # Водяной столб
    "millimeter_of_water": 9.80665,
    "inch_of_water": 249.08891,

    # Имперские
    "psi": 6894.757293168,
    "ksi": 6_894_757.293168,
}


def convert(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц давления.

    Пример единиц:
    - pascal, kilopascal
    - bar
    - atmosphere
    - psi
    - torr
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_PASCAL:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_PASCAL:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в паскали
    value_in_pascal = value * CONVERSION_TO_PASCAL[from_unit]

    # Перевод в целевую единицу
    result = value_in_pascal / CONVERSION_TO_PASCAL[to_unit]

    return result