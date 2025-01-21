CREATE TABLE transactions (
    internal_id UUID PRIMARY KEY,
    transaction_type VARCHAR(50) NOT NULL,
    account_number VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    country VARCHAR(50) NOT NULL,
    date TIMESTAMP NOT NULL,
    process_date TIMESTAMP NOT NULL
);
