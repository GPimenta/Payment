INSERT INTO payments (
    id, 
    created_at, 
    amount, 
    currency, 
    debtor_account_number, 
    creditor_account_number
) VALUES (
    '123e4567-e89b-12d3-a456-426614174000', 
    NOW(), 
    '100.50', 
    'USD', 
    'DE1234567890', 
    'US0987654321'
);