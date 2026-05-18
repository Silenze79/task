CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE campaigns (
    id BIGSERIAL PRIMARY KEY,
    campaign_id UUID UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    channel VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tenants (
    tenant_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    campaign_id UUID REFERENCES campaigns(campaign_id),
    idempotency_key varchar(255) UNIQUE,
    retry_count INT DEFAULT 0
);
INSERT INTO campaigns (
    campaign_id,
    name,
    status,
    channel,
    created_at
)
VALUES (
    gen_random_uuid(),
    'Promo Campaign',
    'PENDING',
    'EMAIL',
    NOW()
);

INSERT INTO tenants (
    name,
    email,
    phone,
    status,
    campaign_id
)
VALUES
(
    'Marcus Ma',
    'marcus@gmail.com',
    '0123456789',
    'PENDING',
    '49373b72-2be0-4736-bca6-85b20544302f'
),
(
    'John Tan',
    'john@gmail.com',
    '0171112222',
    'PENDING',
    '49373b72-2be0-4736-bca6-85b20544302f'
),
(
    'Sarah Lim',
    'sarah@gmail.com',
    '0188889999',
    'PENDING',
    '49373b72-2be0-4736-bca6-85b20544302f'
);