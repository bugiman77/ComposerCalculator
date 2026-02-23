# Базовая единица — джоуль (J)

CONVERSION_TO_JOULE = {

    # СИ
    "joule": 1.0,
    "kilojoule": 1_000.0,
    "megajoule": 1_000_000.0,
    "gigajoule": 1_000_000_000.0,

    # Калории
    "calorie": 4.184,                 # малая калория (cal)
    "kilocalorie": 4184.0,            # пищевая калория (kcal)
    "megacalorie": 4_184_000.0,

    # Электроэнергия
    "watt_hour": 3600.0,
    "kilowatt_hour": 3_600_000.0,
    "megawatt_hour": 3_600_000_000.0,

    # Электронвольты
    "electronvolt": 1.602176634e-19,
    "kiloelectronvolt": 1.602176634e-16,
    "megaelectronvolt": 1.602176634e-13,
    "gigaelectronvolt": 1.602176634e-10,

    # Британские и механические
    "british_thermal_unit": 1055.05585,
    "therm": 105_505_585.257348,
    "foot_pound": 1.3558179483314004,
}


def convert(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц энергии.

    Пример единиц:
    - joule, kilojoule
    - calorie, kilocalorie
    - watt_hour, kilowatt_hour
    - electronvolt, megaelectronvolt
    - british_thermal_unit
    - therm
    - foot_pound
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_JOULE:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_JOULE:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в джоули
    value_in_joules = value * CONVERSION_TO_JOULE[from_unit]

    # Перевод в целевую единицу
    result = value_in_joules / CONVERSION_TO_JOULE[to_unit]

    return result