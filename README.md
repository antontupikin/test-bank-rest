# Wallet REST Service

Тестовое приложение на **Java 17 + Spring Boot 3 + PostgreSQL + Liquibase**.

***Данные для тестирования:***
- email: user@example.com
- пароль: user
- walletId: 123e4567-e89b-12d3-a456-426614174000

Реализует операции с кошельком:
- `POST /api/v1/wallet` — пополнение/списание;
- `GET /api/v1/wallet/{id}` — получение текущего состояния кошелька.
---

## Соответствие заданию

- ✅ Стек: Java 8–17 (в проекте настроен **Java 17**), Spring 3, PostgreSQL.
- ✅ REST API для `DEPOSIT/WITHDRAW` и получения баланса.
- ✅ Liquibase-миграции.
- ✅ Docker + Docker Compose для поднятия приложения и БД.
- ✅ Валидация и унифицированные ошибки для:
    - несуществующего кошелька,
    - невалидного JSON,
    - недостатка средств.
- ✅ Учет конкурентной нагрузки (операции обновления баланса выполняются атомарно на SQL-уровне, есть обработка lock/deadlock с ответом 429).

---

## API

### 1) Изменить баланс

`POST /api/v1/wallet`

```json
{
  "walletId": "123e4567-e89b-12d3-a456-426614174000",
  "operationType": "DEPOSIT",
  "amount": 1000.00
}
```

`operationType`:
- `DEPOSIT`
- `WITHDRAW`

Успех: `200 OK` + объект кошелька.

### 2) Получить кошелек

`GET /api/v1/wallet/{WALLET_UUID}`

Успех: `200 OK` + объект кошелька.

---

## Формат ошибок

Тело ошибок:

```json
{
  "message": "..."
}
```

Типовые статусы:
- `400 Bad Request` — невалидный JSON / ошибки валидации;
- `403 Forbidden` — недостаточно средств;
- `404 Not Found` — кошелек не найден;
- `429 Too Many Requests` — временные конкурентные конфликты БД (lock/deadlock);
- `500 Internal Server Error` — неизвестная ошибка.

---

## Данные, необходимые для работы (из миграции)

После старта Liquibase создаёт базовые записи:

### Пользователь
- `id`: `1`
- `name`: `User`
- `phone`: `+20000000000`
- `email`: `user@example.com`
- `password` (bcrypt): (в проекте не предусмотрена регистрация, поэтому пароль задается сразу в зашифрованном виде)
    - `'$2a$10$JTJhv1n0ERzJ4BtL1YV9W.tdJ9hPPuhrXBnykBAQ2YK7J6pr6Wcde'`
- `role`: `USER`

### Кошелёк
- `id`: `123e4567-e89b-12d3-a456-426614174000`
- `account_number`: `ACC00000001`
- `user_id`: `1`
- `balance`: `1000.50`
- `currency`: `RUB`
- `is_active`: `true`

---

## Конфигурация без пересборки контейнеров

Проект поддерживает настройку через переменные окружения и `docker-compose`.

### Основное
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SECURITY_JWT_SECRET_KEY`
- `CRYPTO_PASSWORD`

### Параметры БД (docker-compose)
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

`app` сервис подключает `.env` через `env_file`, поэтому параметры можно менять без пересборки образа.

---

## Запуск через Docker Compose

### 1. `.env` файл добавлен в репозиторий (что противоречит общей практике)
### 2. Запуск

```bash
docker compose up --build
```

Сервисы:
- API: `http://localhost:8080/swagger-ui/index.html`
- PostgreSQL: `localhost:5432`

---

## Документация

- Swagger UI: `http://localhost:8080/swagger-ui.html`