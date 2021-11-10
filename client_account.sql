-- clients_type table--- 

DROP TABLE IF EXISTS client_account

CREATE TABLE client_account(
	client_id INTEGER, -- foreign key 
	account_id VARCHAR(255), -- foreign key
	
	CONSTRAINT fk_client FOREIGN KEY(client_id)
		REFERENCES client(client_id)
);

INSERT INTO client_account (client_id, account_id)
VALUES
(1,'12334343pl'),
(2,'35263234pl'),
(3,'12326344pl'),
(4,'94023038pl'),
(5,'94839448pl')

SELECT * FROM client_account


-- clients table ---- 

DROP TABLE IF EXISTS client;

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

SELECT * FROM client WHERE client_id = 1

UPDATE client
SET client_first_name = 'Antonio'
WHERE client_id = 1;


-- account_type table --

DROP TABLE IF EXISTS account_type;

CREATE TABLE account_type (
	account_type_id SERIAL PRIMARY KEY,
	account_type_description VARCHAR(255)
);

INSERT INTO account_type (account_type_description)
VALUES 
('Savings'),
('Current'),
('Others')

SELECT *
FROM account_type


-- account_info table --

DROP TABLE IF EXISTS account; 

CREATE TABLE account (
	account_id SERIAL PRIMARY KEY, -- to identify the account of the customer
	account_status VARCHAR NOT NULL, -- active, suspended, or inactive
	account_total_balance MONEY NOT NULL,
	client_id INTEGER NOT NULL,
	account_type_id INTEGER, -- foreign key 
	
	CONSTRAINT fk_account_type_id FOREIGN KEY(account_type_id)
		REFERENCES account_type(account_type_id)

);

INSERT INTO account (account_status, account_total_balance, client_id, account_type_id)
VALUES 
('active', 382009384, 1, 1),
('suspended', 10, 2, 1),
('inactive', 592000020, 2, 2),
('active', 10000, 1, 1),
('inactive', 8232, 1, 2)

SELECT * FROM account 


