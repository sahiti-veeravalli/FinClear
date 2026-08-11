# FinClear — Payment Processing, Ledger, Clearing & Settlement Platform

FinClear is a locally runnable fintech backend project demonstrating payment processing,
double-entry accounting, idempotency, concurrency protection, refunds, settlement,
reconciliation, audit trails and an institutional operations dashboard.

## Architecture

```text
React + TypeScript
        |
        v
Spring Boot REST API
        |
  +-----+--------------------+
  |                          |
MySQL 8                    Redis
  |                          |
  +-----------+--------------+
              |
            Kafka
              |
      Outbox / Events
              |
      Settlement / Recon
```

The implementation is intentionally a **modular monolith** first. The domain boundaries
are explicit so they can later become independently deployed services.

## Main capabilities

- JWT authentication and role-based authorization
- Customer and account management
- Payment lifecycle
- PostgreSQL-style fixed precision is NOT used: MySQL 8 DECIMAL is used for money
- Double-entry ledger with balanced journal validation
- Idempotency keys
- Pessimistic account locking for concurrent debits
- Refunds
- Transactional outbox
- Kafka events + consumer retry/DLQ configuration
- Settlement batches
- Reconciliation engine
- Immutable-style audit records
- Redis idempotency/rate-limit support
- Actuator metrics/health endpoints
- React operations console

## Run

### 1. Infrastructure
```bash
docker compose up -d mysql redis kafka kafka-ui
```

### 2. Backend
```bash
cd backend
mvn spring-boot:run
```

API: http://localhost:8080  
Swagger: http://localhost:8080/swagger-ui.html

### 3. Frontend
```bash
cd frontend
npm install
npm run dev
```

UI: http://localhost:5173

### Demo credentials
- admin@finclear.local / Admin@12345
- operator@finclear.local / Operator@12345

For portfolio use, change these immediately.

## Testing

```bash
cd backend
mvn test
```

The core financial tests include idempotency, insufficient funds, ledger balancing,
refund limits and concurrent debit protection.

## Important design rule

The database is authoritative for financial state. Redis is never the source of truth
for balances. Kafka is used for asynchronous propagation and operational workflows;
the critical debit + ledger write is committed transactionally in MySQL.

## Project structure

```text
FinClear/
├── backend/
├── frontend/
├── database/
├── infra/
├── docs/
└── .github/
```
