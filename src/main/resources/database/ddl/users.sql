-- DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id bigint NOT NULL,
    username text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    password text NOT NULL,
    email text NOT NULL,
    lock_date date,
    lock_description text,
    enabled boolean NOT NULL DEFAULT false,
    CONSTRAINT users_pk PRIMARY KEY (id)
);


CREATE UNIQUE INDEX IF NOT EXISTS users_email_uk
    ON users (email);


CREATE UNIQUE INDEX IF NOT EXISTS users_login_uk
    ON users (login);

CREATE SEQUENCE IF NOT EXISTS users_seq_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY users.id;