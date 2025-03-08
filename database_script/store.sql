CREATE DATABASE stock_management_db;

CREATE TABLE products(
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    product_unit_price DECIMAL(6,2) NOT NULL,
    quantity INT DEFAULT 0,
    imported_date DATE DEFAULT CURRENT_DATE
);

INSERT INTO products (product_name, product_unit_price, quantity)
VALUES
('iphone 15 ultra pro', 1499.99, 10),
('iphone 14 ultra pro', 1099.99, 10),
('iphone 13 ultra pro', 899.99, 10),
('iphone 12 ultra pro', 499.99, 10),
('samsung z fold 2', 799.99, 15),
('samsung s8', 299.99, 5),
('Redmi note 8', 499.99, 9),
('One Plus 8T', 399.99, 9),
('One Plus 7', 299.99, 9),
('iphone 11 ultra pro', 199.99, 10);
