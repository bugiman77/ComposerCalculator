CONVERSION_TO_SQ_METER = {
    # Метрическая система
    "square_meter": 1.0,
    "square_kilometer": 1_000_000.0,
    "square_centimeter": 0.0001,
    "square_millimeter": 0.000001,
    "hectare": 10_000.0,
    "are": 100.0,

    # Имперская система
    "square_inch": 0.00064516,
    "square_foot": 0.09290304,
    "square_yard": 0.83612736,
    "acre": 4046.8564224,
    "square_mile": 2_589_988.110336,
}


def convert_area(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация площади между различными единицами измерения.

    Доступные единицы:
    - square_meter
    - square_kilometer
    - square_centimeter
    - square_millimeter
    - hectare
    - are
    - square_inch
    - square_foot
    - square_yard
    - acre
    - square_mile
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_SQ_METER:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_SQ_METER:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в квадратные метры
    value_in_sq_meters = value * CONVERSION_TO_SQ_METER[from_unit]

    # Перевод из квадратных метров в целевую единицу
    result = value_in_sq_meters / CONVERSION_TO_SQ_METER[to_unit]

    return result