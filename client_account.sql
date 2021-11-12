DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS client;



-- clients table ---- 
CREATE TABLE client (
	client_id SERIAL PRIMARY KEY,
	client_first_name VARCHAR(255) NOT NULL,
	client_last_name VARCHAR(255) NOT NULL,
	client_street VARCHAR(255) NOT NULL, 
	client_pin_code TEXT NOT NULL,
	client_phone_number TEXT NOT NULL
);

INSERT INTO client (client_first_name, client_last_name,
client_street, client_pin_code, client_phone_number)
VALUES 
('Antonio', 'Conte', '1900 North London', 7282, 6191998009),
('Thomas', 'Tuchel', '1220 West London', 1906, 9391998729),
('Patrick', 'Vierra', '3120 East London st', 6610, 5155273746),
('Pep', 'Guardiola', '5672 West Manchester rd', 1111, 9337193380),
('Mikel', 'Arteta', '1234 North London', 2323, 5672986475),
('Ole', 'Solksjaer', '9484 West Manchester', 5954, 3245465768),
('Arsene', 'Wenger', '1234 North London', 3232, 4191998009)

SELECT *
FROM client 

SELECT * FROM client WHERE client_id = 2

SELECT * FROM client WHERE client_id = 1


UPDATE client
SET client_first_name = 'Antonio'
WHERE client_id = 1;

UPDATE client 
SET client_first_name = 'Xabi', client_last_name =  'Alonso', client_street = '1220 West Frankuft', client_pin_code = 1232, client_phone_number = 52638930283
WHERE  client_id = 2;

-- account_info table -- 
CREATE TABLE account (
	account_id SERIAL PRIMARY KEY, -- to identify the account of the customer
	account_status VARCHAR NOT NULL, -- active, suspended, or inactive
	account_number NUMERIC NOT NULL,
	account_total_balance NUMERIC NOT NULL,
	account_type VARCHAR NOT NULL, 
	client_id INTEGER NOT NULL
);

DELETE FROM account WHERE client_id = 1

INSERT INTO account (account_status, account_number, account_total_balance, account_type, client_id)
VALUES 
('active', 1234567, 7462, 'checkings', 1),
('suspended', 567890, 194, 'savings', 1),
('inactive', 1234567890, 2322, 'others', 2),
('active', 1234567890, 9000, 'checkings', 1),
('inactive', 12890, 100, 'savings', 2)

SELECT * FROM account 

SELECT * FROM account WHERE client_id = 2

SELECT * FROM account WHERE client_id = 2 AND account_total_balance >= 200 AND account_total_balance <= 4000

UPDATE Account 
	SET account_status = 'active', account_total_balance = '23234234', account_type = 'checkings'
	WHERE account_id = 2 AND client_id = 2;

SELECT * FROM account 
WHERE client_id  = 1;

UPDATE account SET account_status = 'active', account_number = '1234567', account_total_balance = '27326327', account_type = 'checkings'
		       WHERE client_id = '1' AND account_id = '1';

SELECT * FROM account
