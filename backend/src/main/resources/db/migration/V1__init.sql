CREATE TABLE users (
 id BINARY(16) NOT NULL PRIMARY KEY,
 email VARCHAR(190) NOT NULL UNIQUE,
 password_hash VARCHAR(255) NOT NULL,
 role VARCHAR(30) NOT NULL,
 enabled BOOLEAN NOT NULL,
 created_at TIMESTAMP(6) NOT NULL
);
CREATE TABLE accounts (
 id BINARY(16) NOT NULL PRIMARY KEY,
 user_id BINARY(16) NOT NULL,
 account_number VARCHAR(40) NOT NULL UNIQUE,
 available_balance DECIMAL(19,4) NOT NULL,
 currency VARCHAR(3) NOT NULL,
 status VARCHAR(20) NOT NULL,
 version BIGINT NOT NULL DEFAULT 0,
 created_at TIMESTAMP(6) NOT NULL,
 CONSTRAINT fk_accounts_user FOREIGN KEY(user_id) REFERENCES users(id),
 INDEX ix_accounts_user(user_id)
);
CREATE TABLE payments (
 id BINARY(16) NOT NULL PRIMARY KEY,
 payer_account_id BINARY(16) NOT NULL,
 merchant VARCHAR(190) NOT NULL,
 amount DECIMAL(19,4) NOT NULL,
 currency VARCHAR(3) NOT NULL,
 status VARCHAR(30) NOT NULL,
 idempotency_key VARCHAR(100) NOT NULL UNIQUE,
 created_at TIMESTAMP(6) NOT NULL,
 updated_at TIMESTAMP(6) NOT NULL,
 CONSTRAINT fk_payments_account FOREIGN KEY(payer_account_id) REFERENCES accounts(id),
 INDEX ix_payments_created(created_at),
 INDEX ix_payments_status(status)
);
CREATE TABLE ledger_accounts (
 id BINARY(16) NOT NULL PRIMARY KEY,
 code VARCHAR(80) NOT NULL UNIQUE,
 name VARCHAR(120) NOT NULL,
 balance DECIMAL(19,4) NOT NULL
);
CREATE TABLE journal_entries (
 id BINARY(16) NOT NULL PRIMARY KEY,
 reference VARCHAR(80) NOT NULL UNIQUE,
 debit_total DECIMAL(19,4) NOT NULL,
 credit_total DECIMAL(19,4) NOT NULL,
 status VARCHAR(20) NOT NULL,
 created_at TIMESTAMP(6) NOT NULL
);
CREATE TABLE refunds (
 id BINARY(16) NOT NULL PRIMARY KEY,
 payment_id BINARY(16) NOT NULL,
 amount DECIMAL(19,4) NOT NULL,
 status VARCHAR(20) NOT NULL,
 created_at TIMESTAMP(6) NOT NULL,
 CONSTRAINT fk_refunds_payment FOREIGN KEY(payment_id) REFERENCES payments(id)
);
CREATE TABLE outbox_events (
 id BINARY(16) NOT NULL PRIMARY KEY,
 event_type VARCHAR(100) NOT NULL,
 payload TEXT NOT NULL,
 created_at TIMESTAMP(6) NOT NULL,
 published BOOLEAN NOT NULL DEFAULT FALSE,
 INDEX ix_outbox_published(published,created_at)
);
CREATE TABLE audit_logs (
 id BINARY(16) NOT NULL PRIMARY KEY,
 actor VARCHAR(190),
 action VARCHAR(80) NOT NULL,
 entity_type VARCHAR(80),
 entity_id VARCHAR(80),
 created_at TIMESTAMP(6) NOT NULL,
 metadata TEXT,
 INDEX ix_audit_created(created_at)
);
