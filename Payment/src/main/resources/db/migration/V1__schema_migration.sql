CREATE TABLE clients (
                         id BIGSERIAL PRIMARY KEY,
                         first_name VARCHAR(50),
                         last_name VARCHAR(50),
                         email VARCHAR(100),
                         phone VARCHAR(20)
);

CREATE TABLE accounts (
                          id BIGSERIAL PRIMARY KEY,
                          number VARCHAR(50),
                          client_id BIGSERIAL,
                          balance DECIMAL(10, 2),
                          status VARCHAR(50),
                          FOREIGN KEY (client_id) REFERENCES clients(id)
);


CREATE TABLE Payments (
                          id BIGSERIAL PRIMARY KEY,
                          amount DECIMAL(10,2) NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          debit_account VARCHAR(20) NOT NULL,
                          credit_account VARCHAR(20) NOT NULL
);


