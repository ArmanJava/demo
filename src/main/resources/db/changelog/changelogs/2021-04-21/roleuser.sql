CREATE TABLE users(
        id BIGSERIAL PRIMARY KEY NOT NULL,
        name text,
        lastname text,
        password text,
        email text UNIQUE
);
CREATE TABLE role (
     id BIGSERIAL PRIMARY KEY NOT NULL,
     code INTEGER UNIQUE,
      name text

);

CREATE TABLE link_user_role(
    user_id  BIGINT REFERENCES users (id) ON DELETE SET NULL,
    role_id  BIGINT REFERENCES role (id) ON DELETE SET NULL,
    CONSTRAINT user_role_unique PRIMARY KEY(user_id,role_id)
);


