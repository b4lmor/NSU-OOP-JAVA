BEGIN;

CREATE TABLE messages
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    text       TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users
(
    id             UUID PRIMARY KEY NOT NULL,
    name           VARCHAR(50)      NOT NULL,
    is_active      BOOLEAN   DEFAULT false,
    last_active_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE messages
    ADD CONSTRAINT fk_message_to_user
        FOREIGN KEY (user_id)
            REFERENCES users (id);

CREATE INDEX index_name_to_users ON users (name);

COMMIT;
