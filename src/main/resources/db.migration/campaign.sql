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