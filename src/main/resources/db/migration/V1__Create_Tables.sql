CREATE TABLE hibernate_sequence
(
    next_val    BIGINT
);

CREATE TABLE `role`
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NOT NULL unique ,
    CONSTRAINT pk_role PRIMARY KEY (id),
    CONSTRAINT role_name_index unique (name)
);

CREATE TABLE user
(
    id         BIGINT       NOT NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    telephone  VARCHAR(255) NULL,
    enable    BIT(1)       NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT email_index unique (email),
    CONSTRAINT telephone_index unique (telephone)
);

CREATE TABLE user_roles
(
    id      BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (id),
    CONSTRAINT fk_user_roles_on_user
    FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_on_role
    FOREIGN KEY (role_id) REFERENCES `role` (id) ON UPDATE CASCADE ON DELETE CASCADE
);
