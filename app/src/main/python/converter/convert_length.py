# Базовая единица — метр (m)

CONVERSION_TO_METER = {

    # SI
    "nanometer": 1e-9,
    "micrometer": 1e-6,
    "millimeter": 1e-3,
    "centimeter": 1e-2,
    "decimeter": 1e-1,
    "meter": 1.0,
    "kilometer": 1_000.0,

    # Астрономические
    "astronomical_unit": 149_597_870_700.0,
    "light_year": 9.4607304725808e15,
    "parsec": 3.08567758149137e16,

    # Имперские
    "inch": 0.0254,
    "foot": 0.3048,
    "yard": 0.9144,
    "mile": 1609.344,

    # Морские
    "nautical_mile": 1852.0,

    # Типографские
    "point": 0.0003527777778,  # 1/72 inch
    "pica": 0.0042333333333,   # 12 points
}


def convert_length(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц длины.

    Пример единиц:
    - meter, kilometer
    - inch, foot, mile
    - astronomical_unit, light_year
    - nautical_mile
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_METER:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_METER:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в метры
    value_in_meters = value * CONVERSION_TO_METER[from_unit]

    # Перевод в целевую единицу
    result = value_in_meters / CONVERSION_TO_METER[to_unit]

    return result