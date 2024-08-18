INSERT INTO users (username) VALUES
                                 ('user1'),
                                 ('user2'),
                                 ('user3');

INSERT INTO products (user_id, account_number, balance, product_type) VALUES
                                                                          (1, '1234567890', 1000.00, 'Account'),
                                                                          (1, '9876543210', 500.00, 'Card'),
                                                                          (2, '1111111111', 2000.00, 'Account'),
                                                                          (3, '2222222222', 1500.00, 'Card');

SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC';
SELECT * FROM users;
SELECT * FROM products;