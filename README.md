# 🧮 Composer Calculator & Converter

![Kotlin]([https://img.shields.io](https://img.shields.io/badge/kotlin-2.0.21-blue.svg))
![Compose]([https://img.shields.io](https://img.shields.io/badge/JetpackCompose-2.0.21-green.svg))
![Python](https://img.shields.io/badge/Python-Chaquopy-yellow.svg)

Современный Android-калькулятор с расширенным функционалом конвертации валют. Проект разработан как демонстрация глубокого понимания **Clean Architecture** и интеграции многоязычных сред (Kotlin + Python).

---

## 🌟 Ключевые особенности

*   **Smart Engine:** Математические вычисления производятся на Python через **Chaquopy** (использование библиотек SymPy/Numpy для высокой точности).
*   **Currency Converter:** Актуальные курсы валют (включая RUB) через **Retrofit 2** и внешние API.
*   **Modern UI:** Полностью на **Jetpack Compose** с поддержкой динамических тем и адаптивной верстки.
*   **Persistence:** История вычислений на **Room**, настройки приложения через **Proto DataStore** (типизированное хранилище).
*   **Feedback:** Настраиваемый тактильный (Vibration) и звуковой отклик.

---

## 🏗 Архитектурный стек (Clean Architecture)

Проект разделен на три независимых модуля для обеспечения максимальной тестируемости и гибкости:

1.  **`:domain`** (Pure Kotlin): Бизнес-логика, модели данных, интерфейсы репозиториев и Use Cases. Не имеет зависимостей от Android SDK.
2.  **`:data`** (Android Library): Реализация репозиториев, работа с сетью (Retrofit), БД (Room), сериализация (Protobuf).
3.  **`:app`** (Android App): UI слой (Compose), ViewModels, DI (Manual Dependency Injection) и точка входа Python.

---

## 🛠 Технологии

*   **UI:** Jetpack Compose (Material 3)
*   **Navigation:** Voyager (Multi-stack navigation)
*   **Networking:** Retrofit 2 + GSON
*   **Database:** Room Persistence
*   **Storage:** Proto DataStore
*   **Scripting:** Chaquopy (Python 3.11)
*   **Concurrency:** Kotlin Coroutines & Flow
*   **Build:** Gradle Kotlin DSL + Version Catalog (`libs.versions.toml`)

---

## 📸 Скриншоты

| Calculator | Settings | Converter |
| :---: | :---: | :---: |
| <img src="" width="200" /> | <img src="" width="200" /> | <img src="" width="200" /> |

---
