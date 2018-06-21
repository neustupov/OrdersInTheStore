DELETE FROM models;
DELETE FROM brands;
DELETE FROM types;
DELETE FROM products;
DELETE FROM price_requests;
DELETE FROM orders;
DELETE FROM clients;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (name, email, password) VALUES
  ('Seller', 'seller@yandex.ru', '{noop}seller'),
  ('Manager', 'manager@yandex.ru', '{noop}manager'),
  ('Admin', 'admin@yandex.ru','{noop}admin');

INSERT INTO USER_ROLES (role, user_id) VALUES
  ('ROLE_SELLER', 100000),
  ('ROLE_MANAGER', 100001),
  ('ROLE_ADMIN', 100002);

INSERT INTO CLIENTS (name, last_name, phone_number) VALUES
  ('Andrey', 'Ivanov', 9214215265),
  ('Fedor', 'Pavlov', 9114525874),
  ('Dmitry', 'Nikolaev', 9532147854);

INSERT INTO ORDERS (client_id, add_date_time, prepayment, amount, ready) VALUES
  (100003, '2018-01-10 10:00:00', 1000, 10000, true),
  (100004, '2018-01-11 10:00:00', 500, 5000, true),
  (100005, '2018-01-12 10:00:00', 0, 7000, true),
  (100003, '2018-01-13 10:00:00', 100, 8000, false),
  (100004, '2018-01-14 10:00:00', 200, 5000, false),
  (100005, '2018-01-15 10:00:00', 1000, 4000, false);

INSERT INTO PRICE_REQUESTS (user_id, add_date_time, order_id, client_id, ready) VALUES
  (100000, '2018-01-07 10:00:00', 100006, 100003, true),
  (100000, '2018-01-08 10:00:00', 100007, 100004, true),
  (100000, '2018-01-09 10:00:00', 100008, 100005, true),
  (100000, '2018-01-10 10:00:00', null, 100005, false);

INSERT INTO TYPES (name) VALUES
  ('TV'),
  ('Washing Machine'),
  ('Mouse');

INSERT INTO MODELS (name) VALUES
  ('40F6101'),
  ('10B81'),
  ('21D210'),
  ('Maxima');

INSERT INTO BRANDS (name) VALUES
  ('Samsung'),
  ('LG'),
  ('Akai'),
  ('Razer');

INSERT INTO PRODUCTS (price_request_id, add_date, price, type_id, model_id, brand_id) VALUES
  (100012, '2018-01-07', 10000, 100016, 100019, 100023),
  (100013, '2018-01-08', 5000, 100017, 100020, 100024),
  (100014, '2018-01-09', 3000, 100016, 100021, 100025),
  (100014, '2018-01-09', 4000, 100018, 100022, 100026);


