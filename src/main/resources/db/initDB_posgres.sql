DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS models;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS price_requests;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE GLOBAL_SEQ START 100000;

CREATE TABLE USERS
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE USER_ROLES
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,

  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE CLIENTS
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                NOT NULL,
  last_name        VARCHAR                NOT NULL,
  phone_number     INTEGER                NOT NULL,

  CONSTRAINT phone_number_idx UNIQUE (phone_number)
);

CREATE TABLE ORDERS
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  client_id        INTEGER                 NOT NULL,
  add_date_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  prepayment       INTEGER                 NOT NULL,
  amount           INTEGER                 NOT NULL,
  residue          INTEGER                 NOT NULL,
  ready            BOOLEAN DEFAULT TRUE    NOT NULL,

  CONSTRAINT client_id_add_date_time_idx UNIQUE (client_id, add_date_time),
  FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
);

CREATE TABLE PRICE_REQUESTS
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  user_id          INTEGER                 NOT NULL,
  add_date_time    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  order_id         INTEGER,
  client_id        INTEGER                 NOT NULL,
  ready            BOOLEAN DEFAULT TRUE    NOT NULL,

  CONSTRAINT user_id_add_date_time_idx UNIQUE (user_id, add_date_time),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
  FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
);

CREATE TABLE PRODUCTS
(
  id                      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  price_request_id        INTEGER                 NOT NULL,
  add_date                DATE DEFAULT CURRENT_DATE      NOT NULL,
  price                   INTEGER,

  CONSTRAINT add_date_idx UNIQUE (add_date),
  FOREIGN KEY (price_request_id) REFERENCES price_requests (id) ON DELETE CASCADE
);

CREATE TABLE TYPES
(
  id                      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  product_id              INTEGER                 NOT NULL,
  name                    VARCHAR,

  CONSTRAINT type_name_idx UNIQUE (name),
  FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

CREATE TABLE MODELS
(
  id                      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  product_id              INTEGER                 NOT NULL,
  name                    VARCHAR,

  CONSTRAINT model_name_idx UNIQUE (name),
  FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);