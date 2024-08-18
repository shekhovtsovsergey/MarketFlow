CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          quantity INT NOT NULL,
                          price DECIMAL(10, 2) NOT NULL
);