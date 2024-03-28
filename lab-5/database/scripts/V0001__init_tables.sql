BEGIN;

CREATE TABLE messages
(
    id         UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id    UUID                           NOT NULL,
    text       TEXT                           NOT NULL,
    created_at DATE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE users
(
    id        UUID PRIMARY KEY,
    name      VARCHAR(50) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT false
);

ALTER TABLE messages
    ADD CONSTRAINT fk_message_to_user
        FOREIGN KEY (user_id)
            REFERENCES users (id);

CREATE INDEX index_name_to_users ON users (name);

COMMIT;
