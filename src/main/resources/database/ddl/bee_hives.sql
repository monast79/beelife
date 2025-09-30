-- DROP TABLE IF EXISTS beehives;

CREATE TYPE hive_type AS ENUM
    ('langstroth', 'dadant', 'lounger-dadant', 'lounger-rut');

CREATE TYPE frame_type AS ENUM
    ('eight', 'ten', 'twelve', 'fourteen', 'sixteen', 'twenty', 'twenty-four');

CREATE TABLE IF NOT EXISTS beehives
(
    id bigint NOT NULL,
    name text NOT NULL,
    description text,
    apiary_id bigint NOT NULL,
    type hive_type NOT NULL,
    frame frame_type NOT NULL,
    CONSTRAINT beehives_pk PRIMARY KEY (id),
    CONSTRAINT apiary_fk FOREIGN KEY (apiary_id)  REFERENCES apiary (id)
    );

CREATE UNIQUE INDEX IF NOT EXISTS hive_name_uk
    ON beehives (name, apiary_id);

CREATE INDEX IF NOT EXISTS apiaries_hive_idx
    ON apiaries (type);

CREATE INDEX IF NOT EXISTS apiaries_frame_idx
    ON apiaries (frame);

CREATE INDEX IF NOT EXISTS apiaries_apiary_id_idx
    ON apiaries (apiary_id);

CREATE SEQUENCE IF NOT EXISTS beehive_seq_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY beehives.id;