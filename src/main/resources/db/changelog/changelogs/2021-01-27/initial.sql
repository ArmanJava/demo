CREATE TABLE customer(
        id BIGSERIAL PRIMARY KEY NOT NULL,
        name text,
        lastname text
);
CREATE TABLE orders (
     id BIGSERIAL PRIMARY KEY NOT NULL,
     total text,
     customer_id  BIGINT REFERENCES customer (id) ON DELETE SET NULL

);
CREATE TABLE item (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    description text,
    order_id  BIGINT REFERENCES orders (id) ON DELETE SET NULL
);
CREATE TABLE link_customer_item(
    customer_id  BIGINT REFERENCES customer (id) ON DELETE SET NULL,
    order_id  BIGINT REFERENCES orders (id) ON DELETE SET NULL,
    CONSTRAINT customer_item_unique PRIMARY KEY(customer_id,order_id)
);


