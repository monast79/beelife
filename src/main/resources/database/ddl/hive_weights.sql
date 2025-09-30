-- DROP TABLE IF EXISTS hive_weights;

CREATE TABLE IF NOT EXISTS hive_weights
(
    id bigint NOT NULL,
    measure date NOT NULL,
    hive_fk bigint NOT NULL,
    weight numeric(5,2) NOT NULL,
    CONSTRAINT hive_weights_pk PRIMARY KEY (id),
    CONSTRAINT hive_id FOREIGN KEY (hive_id) REFERENCES beehive (id)
    );

CREATE INDEX IF NOT EXISTS privileges_hive_idx
    ON privileges (hive_fk);

CREATE SEQUENCE IF NOT EXISTS hive_weight_seq_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY hive_weights.id;
