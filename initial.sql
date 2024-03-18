CREATE TABLE SAGA_ORDER (
    id              SERIAL PRIMARY KEY,
    product_id      varchar(16),
    amount          bigint,
    user_id         varchar(16),
    status          varchar(16)
);
CREATE TABLE SAGA_ORDER_RECORD (
    id              SERIAL PRIMARY KEY,
    order_id        bigint,
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status          varchar(16)
);
CREATE TABLE SAGA_PRODUCT_SALE_RECORD (
    id              SERIAL PRIMARY KEY,
    product_id      varchar(16),
    amount          bigint,
    order_id        bigint,
    status          varchar(16),
    create_time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);