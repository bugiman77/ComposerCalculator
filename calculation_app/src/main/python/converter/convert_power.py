# Базовая единица — ватт (W)

CONVERSION_TO_WATT = {

    # СИ
    "watt": 1.0,
    "milliwatt": 0.001,
    "kilowatt": 1_000.0,
    "megawatt": 1_000_000.0,
    "gigawatt": 1_000_000_000.0,

    # Лошадиные силы
    "horsepower_mechanical": 745.69987158227022,  # США
    "horsepower_metric": 735.49875,               # PS
    "horsepower_electric": 746.0,

    # Электрическая мощность
    "volt_ampere": 1.0,  # при cosφ = 1

    # Тепловая мощность
    "btu_per_hour": 0.29307107,

    # CGS
    "erg_per_second": 1e-7,
}


def convert(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц мощности.

    Пример единиц:
    - watt, kilowatt
    - horsepower_mechanical
    - btu_per_hour
    - volt_ampere
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_WATT:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_WATT:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в ватты
    value_in_watts = value * CONVERSION_TO_WATT[from_unit]

    # Перевод в целевую единицу
    result = value_in_watts / CONVERSION_TO_WATT[to_unit]

    return result