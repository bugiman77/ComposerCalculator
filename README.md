# 🧮 Composer Calculator

### Современный калькулятор на Jetpack Compose с интеграцией Python

![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-blue.svg)
![Compose](https://img.shields.io/badge/JetpackCompose-2024.09.00-green.svg)
![Python](https://img.shields.io/badge/Python-Chaquopy-yellow.svg)
![Android](https://img.shields.io/badge/Android-12.0+-darkgreen.svg)

## 📱 Интерфейс

[//]: # ()
[//]: # (|                                           Главный экран                                            |                                                Настройки                                                 |                                                 История                                                  |                                                 О приложении                                                 |)

[//]: # (|:--------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------:|)

[//]: # (| <img alt="Screen main" src="screenshots/Screenshot_20260108_213042.png" title="Main" width="200"/> | <img alt="Screen setting" src="screenshots/Screenshot_20260108_212758.png" title="Setting" width="200"/> | <img alt="Screen history" src="screenshots/Screenshot_20260108_213020.png" title="History" width="200"/> | <img alt="Screen about app" src="screenshots/Screenshot_20260109_122307.png" title="About app" width="200"/> |)

<table>
  <tbody>
  <tr>
    <td align="center" rowspan="3">
        <img alt="Screen setting" src="screenshots/Screenshot_20260108_212758.png" title="Setting" width="200"/>
    </td>
    <td></td>
    <td align="center" rowspan="2">
        <img alt="Screen main" src="screenshots/Screenshot_20260109_221800.png" title="Main" width="120"/>
    </td>
  </tr>
  <tr>
    <td align="center" rowspan="3">
     <img alt="Screen main" src="screenshots/Screenshot_20260108_213042.png" title="Main" width="300"/>
    </td>
  </tr>
  <tr>
    <td align="center" rowspan="3">
        <img alt="Screen history" src="screenshots/Screenshot_20260108_213020.png" title="History" width="200"/>
    </td>
  </tr>
  <tr>
    <td align="center" rowspan="2">
        <img alt="Screen history" src="screenshots/Screenshot_20260109_122307.png" title="History" width="120"/>
    </td>
  </tr>
  <tr>
    <td></td>
  </tr>
  </tbody>
</table>

## ✨ Особенности

* **Точные вычисления:** Использование Python скриптов для обхода проблем с плавающей точкой (
  `0.1 + 0.2 = 0.3`).
* **Умный ввод:** Автоматическая подстановка точек после нуля и корректная обработка знаков.
* **Динамический UI:** Адаптивный размер шрифта и горизонтальная прокрутка в стиле iOS 18.
* **Темы:** Поддержка встроенных и пользовательских тем с хранением в Room.
* **История:** Сохранение всех вычислений с возможностью добавления заметок.

## 🛠 Технологии

- **UI:** Jetpack Compose (Declarative UI)
- **Архитектура:** MVVM + Clean Architecture (Use Cases)
- **База данных:** Room (хранение истории и состояний)
- **Логика вычислений:** Python (через библиотеку Chaquopy)
- **Фоновые задачи:** Coroutines & Flow