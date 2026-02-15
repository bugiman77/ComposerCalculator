# Базовая единица — ньютон (N)

STANDARD_GRAVITY = 9.80665  # м/с²

CONVERSION_TO_NEWTON = {

    # СИ
    "newton": 1.0,
    "kilonewton": 1_000.0,
    "meganewton": 1_000_000.0,
    "millinewton": 0.001,

    # CGS
    "dyne": 1e-5,  # 1 дина = 10⁻⁵ Н

    # Гравитационные (при стандартной g)
    "kilogram_force": STANDARD_GRAVITY,
    "gram_force": STANDARD_GRAVITY / 1000.0,
    "tonne_force": STANDARD_GRAVITY * 1000.0,

    # Имперские
    "pound_force": 4.4482216152605,
    "ounce_force": 0.27801385095378125,
    "kip": 4448.2216152605,        # 1000 pound-force
    "poundal": 0.138254954376,
}


def convert_force(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц силы.

    Пример единиц:
    - newton, kilonewton
    - dyne
    - kilogram_force
    - pound_force
    - kip
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_NEWTON:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_NEWTON:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в ньютоны
    value_in_newtons = value * CONVERSION_TO_NEWTON[from_unit]

    # Перевод в целевую единицу
    result = value_in_newtons / CONVERSION_TO_NEWTON[to_unit]

    return result