-- DROP TABLE IF EXISTS apiaries;

CREATE TYPE apiary_type AS ENUM
    ('stationary', 'mobile');

CREATE TABLE IF NOT EXISTS apiaries
(
    id bigint NOT NULL,
    name text NOT NULL,
    description text,
    location geography,
    type apiary_type NOT NULL,
    CONSTRAINT apiaries_pk PRIMARY KEY (id),
    CONSTRAINT apiary_name_uk UNIQUE (name)
    );

CREATE INDEX IF NOT EXISTS apiaries_type_idx
    ON apiaries (type);

CREATE SEQUENCE IF NOT EXISTS apiary_seq_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY apiaries.id;
