# import math

# Базовая единица — радиан
CONVERSION_TO_RAD = {
    "radian": 1.0,
    "degree": math.pi / 180.0,
    "arcminute": math.pi / (180.0 * 60.0),
    "arcsecond": math.pi / (180.0 * 3_600.0),
    "milliarcsecond": math.pi / (180.0 * 3600.0 * 1_000.0),
    "microarcsecond": math.pi / (180.0 * 3600.0 * 1_000_000.0),
    "milliradian": 1e-3,
    "microradian": 1e-6,
}

def convert_angle(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация угла между различными единицами измерения.

    Доступные единицы:
    - radian
    - degree
    - arcminute
    - arcsecond
    - milliarcsecond
    - microarcsecond
    - milliradian
    - microradian
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_RAD:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_RAD:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в радианы
    value_in_radians = value * CONVERSION_TO_RAD[from_unit]

    # Перевод из радиан в целевую единицу
    result = value_in_radians / CONVERSION_TO_RAD[to_unit]

    return result