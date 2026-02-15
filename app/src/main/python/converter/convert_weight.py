# Базовая единица — килограмм (kg)

CONVERSION_TO_KILOGRAM = {

    # СИ
    "kilogram": 1.0,
    "gram": 1e-3,
    "milligram": 1e-6,
    "microgram": 1e-9,
    "tonne": 1000.0,        # метрическая тонна
    "kilotonne": 1e6,
    "megagram": 1000.0,     # синоним тонн
    "carat": 0.0002,        # метрический карат

    # Имперская / США
    "pound": 0.45359237,
    "ounce": 0.028349523125,
    "stone": 6.35029318,
    "short_ton": 907.18474,   # США
    "long_ton": 1016.0469088, # Великобритания

    # Астрономические / редкие
    "atomic_mass_unit": 1.66053906660e-27,  # u
}


def convert_weight(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц массы (веса).

    Пример единиц:
    - kilogram, gram, milligram
    - pound, ounce, stone
    - tonne, short_ton, long_ton
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_KILOGRAM:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_KILOGRAM:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в килограммы
    value_in_kg = value * CONVERSION_TO_KILOGRAM[from_unit]

    # Перевод в целевую единицу
    result = value_in_kg / CONVERSION_TO_KILOGRAM[to_unit]

    return result