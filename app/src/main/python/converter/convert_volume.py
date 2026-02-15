# Базовая единица — кубический метр (m³)

CONVERSION_TO_CUBIC_METER = {

    # СИ
    "cubic_meter": 1.0,
    "liter": 0.001,
    "milliliter": 1e-6,
    "cubic_centimeter": 1e-6,
    "cubic_millimeter": 1e-9,
    "cubic_kilometer": 1e9,
    "cubic_decimeter": 0.001,  # 1 L = 1 dm³

    # Имперская / США
    "cubic_inch": 1.6387064e-5,
    "cubic_foot": 0.028316846592,
    "cubic_yard": 0.764554857984,
    "teaspoon_us": 4.92892159375e-6,
    "tablespoon_us": 1.478676478125e-5,
    "fluid_ounce_us": 2.95735295625e-5,
    "cup_us": 0.0002365882365,
    "pint_us": 0.000473176473,
    "quart_us": 0.000946352946,
    "gallon_us": 0.003785411784,

    "teaspoon_uk": 5.91939e-6,
    "tablespoon_uk": 1.77869e-5,
    "fluid_ounce_uk": 2.84131e-5,
    "pint_uk": 0.00056826125,
    "quart_uk": 0.0011365225,
    "gallon_uk": 0.00454609,

    # Баррели
    "oil_barrel_us": 0.158987294928,  # нефть
    "beer_barrel_us": 0.117347765,
}


def convert_volume(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц объёма.

    Пример единиц:
    - cubic_meter, liter, milliliter
    - cubic_inch, cubic_foot, gallon_us
    - pint_us, quart_us, oil_barrel_us
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_CUBIC_METER:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_CUBIC_METER:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в кубические метры
    value_in_cubic_meters = value * CONVERSION_TO_CUBIC_METER[from_unit]

    # Перевод в целевую единицу
    result = value_in_cubic_meters / CONVERSION_TO_CUBIC_METER[to_unit]

    return result