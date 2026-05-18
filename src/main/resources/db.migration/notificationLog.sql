CREATE TABLE notification_logs (
    id BIGSERIAL PRIMARY KEY,
    recipient_id BIGINT,
    provider VARCHAR(100),
    status VARCHAR(50),
    error_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_recipient
        FOREIGN KEY (recipient_id)
        REFERENCES recipients(id)
);