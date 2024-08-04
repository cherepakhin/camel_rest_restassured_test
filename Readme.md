## Интеграционные RestAssured тесты для проекта camel_rest

Основной проект [https://github.com/cherepakhin/camel_rest](https://github.com/cherepakhin/camel_rest)

Переменные окружения:

__CAMEL_SIMPLE_APP_IP__ - IP адрес основного проектаю Значение по умолчанию 127.0.0.1:8980.

Проведение тестирования:

````shell
./gradlew test
````

Просмотр Allure отчета от тестировании:

````shell
./gradlew alluServe
````

![allure report](doc/allure_report.png)