CREATE TABLE IF NOT EXISTS user_privileges
(
    user_id bigint,
    privilege_id bigint,
    CONSTRAINT user_privs_pk PRIMARY KEY (user_id, privilege_id),
    CONSTRAINT user_privs_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_privs_priv_fk FOREIGN KEY (privilege_id) REFERENCES privileges (id)
    );