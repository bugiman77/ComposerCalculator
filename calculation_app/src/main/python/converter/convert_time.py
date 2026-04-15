# Базовая единица — секунда (s)
SECONDS_IN = {
    # СИ
    "second": 1.0,
    "millisecond": 1e-3,
    "microsecond": 1e-6,
    "nanosecond": 1e-9,

    # Минуты, часы, сутки
    "minute": 60.0,
    "hour": 3600.0,
    "day": 86400.0,
    "week": 604800.0,

    # Годы
    "julian_year": 365.25 * 86400.0,  # 365.25 дней
    "tropical_year": 365.24219 * 86400.0,
    "calendar_year": 365.0 * 86400.0,
    "leap_year": 366.0 * 86400.0,
    "month": 30.436875 * 86400.0,     # средний месяц
}


def convert(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц времени.

    Пример единиц:
    - second, millisecond, microsecond
    - minute, hour, day, week
    - month, julian_year, tropical_year
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in SECONDS_IN:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in SECONDS_IN:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в секунды
    value_in_seconds = value * SECONDS_IN[from_unit]

    # Перевод в целевую единицу
    result = value_in_seconds / SECONDS_IN[to_unit]

    return result