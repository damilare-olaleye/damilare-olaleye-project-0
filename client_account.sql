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
	client_dob DATE NOT NULL,
	client_active_date DATE DEFAULT CURRENT_TIMESTAMP,
	client_street VARCHAR(255) NOT NULL, 
	client_pin_code TEXT NOT NULL,
	client_email_id VARCHAR(255),
	client_phone_number TEXT NOT NULL
);

INSERT INTO client (client_first_name, client_last_name, client_dob, client_active_date,
client_street, client_pin_code, client_email_id, client_phone_number)
VALUES 
('Antonio', 'Conte', '07/31/1969', '04/09/2021', '1900 North London', 7282, 'conte.antonio@tottenhamfc.com', 6191998009),
('Thomas', 'Tuchel', '01/02/1986', '01/03/2020', '1220 West London', 1906, 'thomas.tuchel@chelseafc.com', 9391998729),
('Patrick', 'Vierra', '05/04/1980', '11/06/2002', '3120 East London st', 6610, 'patrick.verria@crystalpalacefc.com', 5155273746),
('Pep', 'Guardiola', '09/12/1974', '01/01/2001', '5672 West Manchester rd', 1111, 'pep.g@mancityfc.com', 9337193380),
('Mikel', 'Arteta', '11/11/1986', '06/06/2006', '1234 North London', 2323, 'mikel.arteta@arsenalfc.com', 5672986475),
('Ole', 'Solksjaer', '03/05/1983', '05/09/2005', '9484 West Manchester', 5954, 'ole.solksjaer@manchesterunitedfc.com', 3245465768),
('Arsene', 'Wenger', '03/12/1890', '05/06/2004', '1234 North London', 3232, 'arsene.wenger@ilovearsenalfc.com', 4191998009)

SELECT *
FROM client 


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
	account_activation_date DATE DEFAULT CURRENT_TIMESTAMP, 
	account_total_balance MONEY NOT NULL,
	client_id INTEGER NOT NULL,
	account_type_id INTEGER, -- foreign key 
	
	CONSTRAINT fk_account_type_id FOREIGN KEY(account_type_id)
		REFERENCES account_type(account_type_id)

);

INSERT INTO account (account_status, account_activation_date, account_total_balance, client_id, account_type_id)
VALUES 
('active', '03/10/2001', 382009384, 1, 1),
('suspended', '12/12/2007', 10, 2, 1),
('inactive', '02/08/2009', 592000020, 2, 2),
('active', '06/05/2002', 10000, 1, 1),
('inactive', '03/10/2022', 8232, 1, 2)

SELECT * FROM account 


