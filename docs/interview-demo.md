# FinClear interview demo guide

FinClear is a **CashOps command center**. It helps a finance operations team see payment activity, track unresolved settlement issues, and safely create a controlled payment record.

This is a local demo system only. It never connects to a real bank or moves real money.

## Start it before the interview

Open two terminals from the project root.

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=demo
```

```bash
cd frontend
npm run dev
```

Open `http://127.0.0.1:5173`.

| Demo user | Password | What it demonstrates |
| --- | --- | --- |
| admin@finclear.local | Admin@12345 | Admin cash-operations account |
| operator@finclear.local | Operator@12345 | Separate operations account |

If a reviewer asks about the API, open `http://127.0.0.1:8080/swagger-ui.html`. If they ask whether the service is up, open `http://127.0.0.1:8080/actuator/health`.

## A 3-minute live walkthrough

Say this:

> “FinClear solves a basic finance-operations problem: a team needs one explainable record of where money is, what failed, and what action is needed. I built the frontend in React and TypeScript, and the backend in Spring Boot with a relational financial model.”

1. **Sign in.** Explain that the frontend calls a Spring Boot REST API and receives a JWT. Protected API endpoints require that token.
2. **Command center.** Point out available cash, processed volume, open exceptions, and money at risk. These values come from the API, not hard-coded React data.
3. **Exception inbox.** Open it and resolve one case. Explain that this models a reconciliation workflow: each case has severity, evidence, financial exposure, and ownership.
4. **Create a payment.** Create a small payment, such as `₹1,250` to `Interview Demo Supplier`. Show that available cash decreases and the payment appears in live activity.
5. **Explain the safety controls.** The API requires an idempotency key, locks the account row during debit, rejects insufficient balance, uses `BigDecimal` for money, and creates a journal entry, audit record, and outbox event in one transaction.
6. **Show proof.** Run `mvn test`. The integration test logs in, creates the same payment twice with one idempotency key, confirms only one debit occurs, and checks that one journal, audit, and outbox record are created.

Finish with:

> “In production I would replace the demo payment input with gateway or bank sandbox webhooks, enforce organization isolation, and use the outbox event for reliable downstream settlement and reconciliation processing. I deliberately label this as a demo because it is not real-money software.”

## Skill-to-feature map

| Skill | Where it is visible in FinClear | One sentence to say |
| --- | --- | --- |
| Java and OOP | Spring domain entities, services, repositories, controllers | “I separated the financial domain, business rules, persistence, and HTTP layer.” |
| DSA / correctness | Idempotency lookup and locking for concurrent debits | “A retry cannot create a duplicate payment, and locking protects the balance during a debit.” |
| DBMS and SQL | MySQL schema, Flyway migrations, H2 demo profile | “The database is the source of truth for balances and ledger records.” |
| Spring Boot / REST | JWT-secured `/api/v1` REST endpoints and Swagger | “The React client uses authenticated REST APIs with validation and structured errors.” |
| React / TypeScript | Operations console, login state, live refresh, payment modal | “TypeScript models the API data and React renders the live CashOps workflow.” |
| Docker / Linux / CI/CD | Dockerfiles, Compose configuration, GitHub Actions workflow | “The project can run locally as containers and the CI workflow runs backend tests and frontend builds.” |
| Observability | Spring Boot Actuator health endpoint | “I exposed a health endpoint as the starting point for operational monitoring.” |
| Agile | Small vertical slices: authentication → accounts → payment → audit | “I built an end-to-end thin slice first, then added controls and verification around it.” |

## Questions you may be asked

### Why use `BigDecimal` instead of `double`?

`double` cannot exactly represent many decimal values. For financial amounts, precision errors are unacceptable, so the backend uses fixed-precision `BigDecimal` columns and values.

### What is idempotency?

Networks retry requests. A client sends an `Idempotency-Key` with a payment; if the same key is repeated, FinClear returns the already-created payment instead of debiting twice.

### Why use a database lock?

Two payment requests can read the same balance at the same time. The pessimistic write lock makes one request wait while the other updates the account, preventing double-spend races.

### What is the outbox pattern?

The payment and an event describing it are written in the same database transaction. A background publisher can later send that event to Kafka reliably without losing it if the service crashes between database write and publish.

### What would you improve next?

“I would add a payment-gateway sandbox connector and settlement-file ingestion, then match source payments to settlements and automatically create exception cases. I would also add Prometheus metrics and Grafana dashboards around payment success rates, balance operations, and unresolved exposure.”

## Honest limitations

- This is a portfolio demo, not a production financial platform.
- JWT configuration and demo credentials must be replaced with secrets management and user onboarding before deployment.
- The current journal entry validates balanced totals but is a simplified representation, not a complete accounting chart-of-accounts implementation.
- Prometheus and Grafana are planned observability integrations; Actuator health is available today.
