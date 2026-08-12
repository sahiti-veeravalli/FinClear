CREATE TABLE exception_cases (
 id BINARY(16) NOT NULL PRIMARY KEY,
 title VARCHAR(160) NOT NULL,
 category VARCHAR(40) NOT NULL,
 severity VARCHAR(20) NOT NULL,
 amount DECIMAL(19,4) NOT NULL,
 currency VARCHAR(3) NOT NULL,
 status VARCHAR(20) NOT NULL,
 owner VARCHAR(190) NOT NULL,
 evidence VARCHAR(700) NOT NULL,
 created_at TIMESTAMP(6) NOT NULL,
 updated_at TIMESTAMP(6) NOT NULL,
 INDEX ix_exception_status(status,created_at)
);
