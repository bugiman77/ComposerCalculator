<h3 align="center">
    <i>
        Диаграмма процесса обработки вычислений
    </i>
</h3>

```mermaid
flowchart TD
    Start([User Opens App]) --> ShowUI["Display Calculator UI"]
    ShowUI --> WaitInput["Wait for User Input"]
    WaitInput --> InputType{Input Type?}
    
    InputType -->|Number/Operator| ValidateInput["Validate Expression"]
    InputType -->|Function Button| SelectFunc["Select Math Function"]
    InputType -->|Clear| ClearHistory["Clear Current Input"]
    
    ValidateInput --> IsValid{Valid<br/>Expression?}
    IsValid -->|No| ShowError["Show Error Message"]
    ShowError --> WaitInput
    
    IsValid -->|Yes| SendPython["Send to Python<br/>Parser"]
    SelectFunc --> SendPython
    
    SendPython --> ParseExpr["Parse Expression<br/>with sympy/numpy"]
    ParseExpr --> Calculate["Execute Calculation"]
    Calculate --> GetResult["Get Result"]
    
    GetResult --> DisplayResult["Display Result<br/>on Screen"]
    DisplayResult --> SaveHistory["Save to History<br/>Database"]
    SaveHistory --> WaitNext{Next<br/>Action?}
    
    WaitNext -->|Continue Calculating| WaitInput
    WaitNext -->|View History| ShowHistory["Load History"]
    ShowHistory --> WaitInput
    WaitNext -->|Exit| End([Close App])
    
    ClearHistory --> WaitInput
```

- **Ввод и валидация**: Приложение разделяет входящие данные на типы (числа, функции или команды управления). Перед расчетом происходит проверка корректности выражения, что предотвращает ошибки синтаксиса.
- **Математический движок**: Валидное выражение передается в парсер (использующий библиотеки sympy/numpy), который отвечает за точность вычислений любой сложности.
- **Реактивность и хранение**: После получения результата интерфейс мгновенно обновляется (благодаря State-менеджменту в Jetpack Compose), а данные автоматически сохраняются в локальную базу данных истории.
- **Управление состоянием**: Пользователь может бесшовно переключаться между новыми вычислениями, просмотром архива операций или очисткой текущего ввода.


<h3 align="center">
    <i>
        Use Case Diagram
    </i>
</h3>

```mermaid
flowchart LR
    User((User))
    
    subgraph Calculator["Calculator Application"]
        UC1([Perform Calculation])
        UC2([View Calculation History])
        UC3([Manage History])
        UC4([Convert Units])
        UC5([Configure Settings])
        UC6([Provide Haptic Feedback])
        UC7([Manage Notes])
    end
    
    User --> UC1
    User --> UC2
    User --> UC3
    User --> UC4
    User --> UC5
    User --> UC6
    User --> UC7
    
    UC1 -.->|saves to| UC2
    UC3 -->|retrieves from| UC2
    UC3 -->|includes operations| UC7
    UC5 -->|controls| UC6
```

- **Ядро вычислений и конвертации**: Пользователь имеет доступ к двум основным вычислительным движкам — стандартному калькулятору и модулю конвертации единиц измерения.
- **Интегрированная система учета**: История вычислений тесно связана с модулем управления заметками. Это позволяет пользователю не просто хранить сухие цифры, но и добавлять к ним контекстные комментарии.
- **Персонализация и UX**: Через настройки реализовано управление тактильным откликом, что обеспечивает более глубокое взаимодействие с интерфейсом на Android.
- **Управление данными**: Модуль работы с историей позволяет как извлекать старые записи для повторных вычислений, так и выполнять полную очистку данных.
