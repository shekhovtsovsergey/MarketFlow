CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255)
);

CREATE TABLE products (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 user_id BIGINT,
                                 account_number VARCHAR(255),
                                 balance DECIMAL(10, 2),
                                 product_type VARCHAR(255),
                                 FOREIGN KEY (user_id) REFERENCES users(id)
);