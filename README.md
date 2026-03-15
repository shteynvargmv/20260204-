
# InvestMate — Информационная платформа для анализа рынка ценных бумаг и валют

## 🚀  Функциональные возможности
📊 *Получение данных из T‑Bank Invest API в реальном времени*

🔍 *Гибкая фильтрация инструментов*

📌 *Избранное: добавление инструментов в личный список*

🔎 *Поиск по названию и тикеру*

📄 *Пагинация и сортировка*

👤 *Регистрация и аутентификация (JWT + Spring Security)*

📱 *Адаптивный интерфейс на Bootstrap*

## 🧱 Технологический стек  
- **Java 21**
- **Spring Boot 3.1.5**
- **Spring MVC**
- **Spring Data JPA**
- **Spring Security**
- **REST API**
- **JWT 0.11.5**
- **MSSQL JDBC 12.4.2.jre11**
- **Thymeleaf**
- **HTML5/CSS3**
- **JavaScript**
- **Bootstrap 5.3.0**
- **GitHub**

## 🛠️ Установка и запуск
1. Клонирование репозитория
```
   git clone https://github.com/shteynvargmv/20260204-.git
   cd 20260204-
```
2. Настройка базы данных
```
Создайте базу данных в MSSQL
Укажите параметры подключения в application.properties:
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=Java521;trustServerCertificate=true
spring.datasource.username=UserJava521
spring.datasource.password=UserJava521

Создайте пользователя в ролью ADMIN
Выполните пункт меню Дополнительно-Обновление
```

3. Настройка API‑ключа T‑Bank
```
В application.properties добавьте:
properties
tbank.api.base.url=https://invest-public-api.tbank.ru/rest
tbank.api.token=ВАШ_ТОКЕН
```
5. Доступ к приложению
```
   После запуска перейдите по адресу:
   👉 http://top/invest/home
```

## 📁 Структура проекта  
```
src/main/java/com/example/demo/  
├── config/     // Конфигурации  
├── controller/ // Контроллеры  
├── entity/     // JPA‑сущности  
├── repository/ // Репозитории Spring Data  
├── service/    // Бизнес‑логика  
├── jwt/        // JWT‑фильтры и утилиты  
├── utils/      // RestTemplate, кэш  
├── dto/        // Data Transfer Objects  
└── model/      // Модели
```
## 🔐 Безопасность
*Аутентификация на основе JWT*

*Защита маршрутов через Spring Security*

*Хеширование паролей (BCrypt)*

## 📌 Примеры запросов
Получение списка акций с сортировкой
```
GET /invest/catalog/share?page=1&sort=name_asc
```
Получение списка всех инвестиционных инструментов
```
GET /invest/catalog/all
```

## 🧑‍🎓 Дипломная работа
### Тема:
«Информационная платформа для анализа рынка ценных бумаг и валют»

### Автор:
Штейнварг Марина Владимировна
Группа Java521

### Контакты
Если у вас есть вопросы или предложения, пишите:  
📧 parfen-marina@yandex.ru