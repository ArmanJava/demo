CREATE TABLE token(
        id BIGSERIAL PRIMARY KEY NOT NULL,
        token text,
        user_id  BIGINT REFERENCES users (id) ON DELETE SET NULL,
        is_active boolean
);
