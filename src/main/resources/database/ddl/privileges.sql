
DROP SEQUENCE privileges_seq_id;

DROP TABLE IF EXISTS privileges;

CREATE TABLE IF NOT EXISTS privileges
(
    id bigint NOT NULL,
    name text NOT NULL,
    enabled boolean NOT NULL DEFAULT true,
    CONSTRAINT privileges_pk PRIMARY KEY (id)
    );

CREATE UNIQUE INDEX IF NOT EXISTS privileges_name_uk
    ON privileges (name);

CREATE SEQUENCE IF NOT EXISTS privileges_seq_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY privileges.id;