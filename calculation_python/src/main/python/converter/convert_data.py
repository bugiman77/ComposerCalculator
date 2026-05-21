# Базовая единица — бит (bit)

BITS_IN_BYTE = 8

CONVERSION_TO_BIT = {
    # Биты (SI 1000)
    "bit": 1,
    "kilobit": 1_000,
    "megabit": 1_000_000,
    "gigabit": 1_000_000_000,
    "terabit": 1_000_000_000_000,
    "petabit": 1_000_000_000_000_000,
    "exabit": 1_000_000_000_000_000_000,

    # Биты (IEC 1024)
    "kibibit": 1024,
    "mebibit": 1024**2,
    "gibibit": 1024**3,
    "tebibit": 1024**4,
    "pebibit": 1024**5,
    "exbibit": 1024**6,

    # Байты (SI 1000)
    "byte": BITS_IN_BYTE,
    "kilobyte": 1_000 * BITS_IN_BYTE,
    "megabyte": 1_000_000 * BITS_IN_BYTE,
    "gigabyte": 1_000_000_000 * BITS_IN_BYTE,
    "terabyte": 1_000_000_000_000 * BITS_IN_BYTE,
    "petabyte": 1_000_000_000_000_000 * BITS_IN_BYTE,
    "exabyte": 1_000_000_000_000_000_000 * BITS_IN_BYTE,

    # Байты (IEC 1024)
    "kibibyte": 1024 * BITS_IN_BYTE,
    "mebibyte": 1024**2 * BITS_IN_BYTE,
    "gibibyte": 1024**3 * BITS_IN_BYTE,
    "tebibyte": 1024**4 * BITS_IN_BYTE,
    "pebibyte": 1024**5 * BITS_IN_BYTE,
    "exbibyte": 1024**6 * BITS_IN_BYTE,
}


def convert(value: float, from_unit: str, to_unit: str) -> float:
    """
    Конвертация единиц измерения данных.

    Пример единиц:
    - bit, byte
    - kilobit, megabit, gigabit ...
    - kibibit, mebibit ...
    - kilobyte, megabyte ...
    - kibibyte, mebibyte ...
    """

    from_unit = from_unit.lower()
    to_unit = to_unit.lower()

    if from_unit not in CONVERSION_TO_BIT:
        raise ValueError(f"Неподдерживаемая единица: {from_unit}")
    if to_unit not in CONVERSION_TO_BIT:
        raise ValueError(f"Неподдерживаемая единица: {to_unit}")

    # Перевод в биты
    value_in_bits = value * CONVERSION_TO_BIT[from_unit]

    # Перевод в целевую единицу
    result = value_in_bits / CONVERSION_TO_BIT[to_unit]

    return result