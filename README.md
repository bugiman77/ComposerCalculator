<h1 align="center">Composer Calculator</h1>

![calculator.png](forREADME/calculator.png)

Инженерный калькулятор, в котором совмещено нативная Android-разработка и мощь 
Python-библиотек. Основная идея была в том, чтобы не писать велосипед для парсинга сложных 
математических выражений на Kotlin, а использовать возможности библиотек Python.

```mermaid
---
Block Diagram
---
graph TB
    subgraph Android["Android Native Layer"]
        UI["User Interface<br/>(Kotlin/Jetpack Compose)"]
        InputHandler["Input Handler<br/>(UI Events)"]
        OutputDisplay["Output Display<br/>(Results & History)"]
    end

    subgraph Processing["Processing Layer"]
        ExpressionValidator["Expression Validator"]
        Calculator["Calculator Engine"]
    end

    subgraph Python["Python Backend"]
        PythonParser["Python Expression Parser<br/>(sympy/numpy)"]
        MathLibs["Mathematical Libraries<br/>(Advanced Operations)"]
    end

    subgraph Storage["Data Storage"]
        History["Calculation History<br/>(Local Database)"]
        Cache["Cache Layer"]
    end

    UI -->|User Input| InputHandler
    InputHandler -->|Expression| ExpressionValidator
    ExpressionValidator -->|Valid Expression| Calculator
    Calculator -->|Parse Request| PythonParser
    PythonParser -->|Use Libraries| MathLibs
    MathLibs -->|Result| Calculator
    Calculator -->|Display Result| OutputDisplay
    OutputDisplay -->|Update| UI
    Calculator -->|Save| History
    History -->|Load| InputHandler
    Calculator -.->|Cache| Cache

    style Android fill:#3ddc84
    style Processing fill:#bb86fc
    style Python fill:#ff6b6b
    style Storage fill:#03dac6
```
