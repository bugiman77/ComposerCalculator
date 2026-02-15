# Константы
KM_PER_MILE = 1.609344
LITER_PER_GALLON_US = 3.785411784
LITER_PER_GALLON_UK = 4.54609


def to_l_per_100km(value: float, unit: str) -> float:
    unit = unit.lower()

    if unit == "liter_per_100km":
        return value

    elif unit == "kilometer_per_liter":
        return 100.0 / value

    elif unit == "mile_per_gallon_us":
        # mpg → km/L → L/100km
        km_per_l = (value * KM_PER_MILE) / LITER_PER_GALLON_US
        return 100.0 / km_per_l

    elif unit == "mile_per_gallon_uk":
        km_per_l = (value * KM_PER_MILE) / LITER_PER_GALLON_UK
        return 100.0 / km_per_l

    else:
        raise ValueError(f"Неподдерживаемая единица: {unit}")


def from_l_per_100km(value: float, unit: str) -> float:
    unit = unit.lower()

    if unit == "liter_per_100km":
        return value

    elif unit == "kilometer_per_liter":
        return 100.0 / value

    elif unit == "mile_per_gallon_us":
        km_per_l = 100.0 / value
        return (km_per_l * LITER_PER_GALLON_US) / KM_PER_MILE

    elif unit == "mile_per_gallon_uk":
        km_per_l = 100.0 / value
        return (km_per_l * LITER_PER_GALLON_UK) / KM_PER_MILE

    else:
        raise ValueError(f"Неподдерживаемая единица: {unit}")


def convert_fuel_consumption(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация расхода топлива.

    Доступные единицы:
    - liter_per_100km
    - kilometer_per_liter
    - mile_per_gallon_us
    - mile_per_gallon_uk
    """

    value_l_per_100km = to_l_per_100km(value, from_unit)
    result = from_l_per_100km(value_l_per_100km, to_unit)

    return result