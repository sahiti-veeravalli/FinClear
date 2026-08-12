# FinClear

> **The financial truth layer for high-growth businesses.**
>
> FinClear turns fragmented payment, settlement, and ledger data into one explainable operational record — so a finance team can answer: **where is our money, what broke, and what should we do next?**

![Status](https://img.shields.io/badge/status-working%20prototype-F6DBC0?style=flat-square&labelColor=502D55)
![Frontend](https://img.shields.io/badge/frontend-React%20%2B%20TypeScript-935073?style=flat-square)
![Backend](https://img.shields.io/badge/backend-Spring%20Boot-935073?style=flat-square)
![Ledger](https://img.shields.io/badge/ledger-double--entry-935073?style=flat-square)

---

## The 30-second version

Modern finance teams do not have a *single* view of money.

An invoice sits in one tool. A payment attempt is in a gateway. A bank settlement arrives later. The accounting entry is elsewhere. When the numbers do not match, someone opens spreadsheets, searches Slack or WhatsApp, and tries to reconstruct the truth.

**FinClear is the command center for that mess.**

It connects the lifecycle of money into one auditable chain:

```text
Invoice / Payable
        ↓
Payment instruction
        ↓
Gateway / bank event
        ↓
Settlement
        ↓
Double-entry ledger
        ↓
Reconciliation + exceptions
        ↓
Clear next action for an operator
```

The key product promise is simple:

> Every number in FinClear can be traced to a source event, a ledger entry, and an accountable decision.

---

## What makes FinClear different

FinClear is not trying to be another payment gateway, expense manager, or dashboard full of vanity charts. It is a **CashOps intelligence layer** for businesses that already move money through multiple systems.

| Instead of… | FinClear does… |
| --- | --- |
| “Settlement failed” | Shows the exact payment chain, the missing event, the amount at risk, and the owner. |
| A spreadsheet reconciliation | Automatically matches payment, settlement, invoice, refund, and ledger records — then queues only exceptions. |
| A fraud alert with no context | Explains the signals, linked entities, financial impact, and recommended control. |
| A stale cash report | Shows expected versus actual cash, upcoming settlements, and money that needs attention. |
| An opaque payment record | Maintains an immutable audit history and balanced double-entry record. |

### The signature experience: “Explain this money”

An operator should be able to type or click:

```text
Why is ₹4,82,000 still unsettled?
```

FinClear should return a decision-ready answer:

```text
₹4,82,000 is held across 17 successful payment attempts.

• 14 are awaiting the scheduled T+1 gateway settlement
• 2 have a beneficiary verification mismatch
• 1 was refunded after capture

Expected next action: review two verification exceptions.
Financial exposure: low · confidence: high
```

That is the product standard: **evidence, context, and an action — not just an alert.**

---

## Who it is for

FinClear is designed for Indian B2B finance and fintech operations teams:

- CFOs and finance controllers who need a trusted cash position
- Payment operations teams resolving settlement and payout failures
- Reconciliation analysts closing books faster
- Risk and compliance teams investigating suspicious movement
- Founders who need financial controls before scale creates chaos

Initial product focus: UPI/payment gateway collections, vendor payouts, settlement reconciliation, and a control-grade ledger.

---

## Product modules

```text
┌─────────────────────────────────────────────────────────────────┐
│                         FINCLEAR COMMAND CENTER                  │
├───────────────┬──────────────────┬──────────────────────────────┤
│ Money         │ Control          │ Intelligence                 │
│               │                  │                              │
│ • Accounts    │ • Roles          │ • Exception inbox            │
│ • Payments    │ • Approvals      │ • Risk signals               │
│ • Refunds     │ • Audit trail    │ • Explain-this-money         │
│ • Ledger      │ • Idempotency    │ • Expected vs actual cash    │
│ • Settlements │ • Policy rules   │ • Reconciliation confidence  │
└───────────────┴──────────────────┴──────────────────────────────┘
```

### What is working in this repository today

- React operations console with protected sign-in
- Spring Boot API with JWT authentication and role-based access
- Demo accounts, in-memory database profile, and live API-backed UI
- Payment creation with idempotency keys and balance validation
- Pessimistic account locking to protect concurrent debits
- Double-entry journal validation, refunds, audit events, and transactional outbox domain model
- Automatic dashboard refresh of payments and balances

### What we will build next

- Persistent hosted database and organization/workspace model
- Real payment-gateway, bank, and accounting sandbox connectors
- Settlement ingestion and reconciliation exception workflow
- Maker-checker approvals and policy engine
- Evidence-first risk graph and explainable anomaly detection
- Realtime collaborative resolution, comments, and case ownership
- Production-quality onboarding, visual system, and responsive UX

The roadmap is intentionally separated from the working prototype: FinClear will never pretend a simulated integration is live money movement.

---

## Architecture

```text
                         React + TypeScript
                     Operations command center
                                  │
                                  ▼
                      Spring Boot REST API + JWT
                                  │
              ┌───────────────────┼────────────────────┐
              ▼                   ▼                    ▼
       Financial domain      Control plane        Event pipeline
       • accounts            • RBAC               • outbox events
       • payments            • audit log          • Kafka (optional)
       • refunds             • idempotency        • retry / DLQ
       • journal entries     • rate limits
              │
              ▼
       MySQL 8 (production) / H2 (local demo)
```

### The non-negotiable financial rule

**The database is the source of truth for money.**

Redis can accelerate idempotency and rate limits. Kafka can distribute events. Neither is allowed to become the authoritative balance or ledger.

---

## Run the working prototype

### Prerequisites

- Node.js 20+
- Java 17+
- Maven 3.9+

### Fastest local start — demo mode

Open two terminals from the repository root.

```bash
# Terminal 1 — API with an in-memory H2 database
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

```bash
# Terminal 2 — web app
cd frontend
npm install
npm run dev
```

Open [http://127.0.0.1:5173](http://127.0.0.1:5173).

Use the seeded admin account:

```text
Email:    admin@finclear.local
Password: Admin@12345
```

Then create a payment. The API creates a real demo payment record, debits the demo account balance, creates a ledger journal entry, writes an audit event, and refreshes the UI.

### Local API links

| Service | URL |
| --- | --- |
| API | http://127.0.0.1:8080 |
| Swagger / OpenAPI UI | http://127.0.0.1:8080/swagger-ui.html |
| Health check | http://127.0.0.1:8080/actuator/health |

### Full infrastructure mode

For MySQL, Redis, Kafka, and Kafka UI:

```bash
docker compose up -d mysql redis kafka kafka-ui
```

Then run the backend without the `demo` profile.

---

## Repository map

```text
FinClear/
├── frontend/        React + TypeScript operations console
├── backend/         Spring Boot API and financial domain
├── database/        SQL schema and seed data
├── docs/            Architecture, ledger, reconciliation, and runbook docs
├── docker-compose.yml
└── README.md
```

---

## Financial safety built in

FinClear treats financial correctness as a product feature:

- **Idempotency keys** prevent duplicate payment creation on retry.
- **Pessimistic locking** prevents two concurrent debits from spending the same balance.
- **Fixed-precision decimal amounts** are used for money — never floating-point arithmetic.
- **Double-entry ledger validation** ensures every journal is balanced.
- **Audit records** preserve who did what and when.
- **Outbox events** keep data changes and event publication consistent.

Run the core financial test suite:

```bash
cd backend
mvn test
```

---

## Design principles

1. **Truth before polish** — a beautiful number is useless if it cannot be explained.
2. **Operators need decisions, not data dumps** — surface the exception, impact, evidence, and next action.
3. **AI must be accountable** — any recommendation must link back to deterministic facts and source records.
4. **Every money movement is traceable** — source event → state change → ledger → audit trail.
5. **Never fake production** — sandbox, demo, and live financial connections are clearly labeled.

---

## Project status

FinClear is an actively evolving working prototype. The current local demo uses seeded data and an H2 in-memory database; it is designed for product exploration and engineering demonstration, **not for real-money processing**.

Before connecting real bank or customer data, we will add secure secret management, formal organization isolation, consent flows, production database migrations, compliance review, monitoring, and integration-specific controls.

---

## The ambition

The goal is not to make finance teams stare at a dashboard.

The goal is to make every payment, settlement, and ledger question feel answerable in seconds.

**FinClear makes money operations explainable.**
