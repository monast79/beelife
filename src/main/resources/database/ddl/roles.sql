DROP TABLE IF EXISTS roles;

CREATE TABLE IF NOT EXISTS roles
(

    privilege_id bigint,
    CONSTRAINT roles_pk PRIMARY KEY (id),
    CONSTRAINT roles_privilege_fk FOREIGN KEY (privilege_id) REFERENCES privileges (id),
    ) INHERITS (privileges);


CREATE INDEX IF NOT EXISTS roles_privilege_idx
    ON roles (privilege_id);

CREATE UNIQUE INDEX IF NOT EXISTS roles_name_uk
    ON roles (name);

CREATE SEQUENCE IF NOT EXISTS roles_seq_id
    INCREMENT 1
    START 1
    MINVALUE 1
    OWNED BY roles.id;