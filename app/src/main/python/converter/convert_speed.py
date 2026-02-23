# Базовая единица — метр в секунду (m/s)

SPEED_OF_SOUND = 340.29  # м/с (при 15°C, 1 атм)

CONVERSION_TO_MPS = {

    # СИ
    "meter_per_second": 1.0,
    "kilometer_per_second": 1000.0,
    "kilometer_per_hour": 1000.0 / 3600.0,
    "meter_per_minute": 1.0 / 60.0,

    # Имперские
    "mile_per_hour": 1609.344 / 3600.0,
    "foot_per_second": 0.3048,

    # Морские
    "knot": 1852.0 / 3600.0,

    # Число Маха (приближённо)
    "mach": SPEED_OF_SOUND,
}


def convert(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц скорости.

    Пример единиц:
    - meter_per_second
    - kilometer_per_hour
    - mile_per_hour
    - knot
    - mach
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_MPS:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_MPS:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в м/с
    value_in_mps = value * CONVERSION_TO_MPS[from_unit]

    # Перевод в целевую единицу
    result = value_in_mps / CONVERSION_TO_MPS[to_unit]

    return result