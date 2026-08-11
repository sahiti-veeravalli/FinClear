# Ledger

The ledger is designed around double-entry accounting.

For every journal:
`sum(debits) == sum(credits)`.

Posted entries should not be edited or deleted. Corrections are represented by compensating entries.

The payment service creates a balanced journal record in the same database transaction as the payment and balance update.
