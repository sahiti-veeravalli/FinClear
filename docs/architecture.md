# Architecture

FinClear starts as a modular monolith. The financial write path remains transactionally consistent in MySQL.

## Critical payment path

```mermaid
sequenceDiagram
actor C as Client
participant A as API
participant P as PaymentService
participant D as MySQL
participant O as Outbox
C->>A: POST /payments + Idempotency-Key
A->>P: validate/authenticate
P->>D: lock account
P->>D: debit + payment + journal + outbox
D-->>P: commit
P-->>A: payment result
A-->>C: response
O->>Kafka: publish PaymentSucceeded
```

Redis/Kafka are not the financial source of truth.
