INSERT INTO clients (first_name, last_name, email, phone)
VALUES ('John', 'Doe', 'johndoe@gmail.com', '1234567890'),
       ('Jane', 'Smith', 'janesmith@gmail.com', '0987654321'),
       ('Michael', 'Johnson', 'michaeljohnson@gmail.com', '5468790321');

INSERT INTO accounts (number, client_id, balance, status)
VALUES ('40817810000000000001', 1, 1000.00, 'Active'),
       ('987654321', 2, 500.00, 'Active'),
       ('546879032', 3, 2000.00, 'Inactive');

INSERT INTO Payments (amount, status,debit_account,credit_account)
VALUES (100.00, 'Completed','40817810000000000001','30301'),
       (100.00, 'Pending','40817810000000000001','30301'),
       (100.00, 'Completed','40817810000000000001','30301');

select * from clients;
select * from accounts;
select * from payments;