DROP DATABASE IF EXISTS secure_banking_system;

CREATE DATABASE secure_banking_system;

USE secure_banking_system;

CREATE TABLE `secure_banking_system`.`user` (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(60) NOT NULL,
  status INT NOT NULL,
  incorrect_attempts INT DEFAULT 0,
  created_date DATETIME DEFAULT NOW(),
  modified_date DATETIME DEFAULT NOW(),
  user_type ENUM('tier1', 'tier2', 'admin', 'customer', 'merchant')
);

CREATE TABLE `secure_banking_system`.`user_details` (
  user_id INT NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  tier VARCHAR(10),
  address1 VARCHAR(255) NOT NULL,
  address2 VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  province VARCHAR(255) NOT NULL,
  zip INT NOT NULL,
  date_of_birth DATETIME NOT NULL,
  ssn VARCHAR(15) NOT NULL UNIQUE,
  question_1 VARCHAR(255) NOT NULL,
  question_2 VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(user_id) ON DELETE CASCADE
);

CREATE TABLE `secure_banking_system`.`account` (
  account_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  account_number VARCHAR(255) NOT NULL,
  account_type ENUM('savings', 'checking', 'credit') NOT NULL,
  current_balance DECIMAL(10, 5) DEFAULT 0.0,
  created_date DATETIME NOT NULL DEFAULT NOW(),
  approval_status BOOLEAN NOT NULL,
  interest DECIMAL(10, 5) DEFAULT 0.0,
  approval_date DATETIME,
  approver INT,
  FOREIGN KEY (approver) REFERENCES `secure_banking_system`.`user`(user_id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(user_id) ON DELETE CASCADE
);

CREATE TABLE `secure_banking_system`.`transaction` (
  transaction_id INT PRIMARY KEY AUTO_INCREMENT,
  transaction_type ENUM('cc', 'debit', 'transfer') NOT NULL,
  approval_status BOOLEAN NOT NULL,
  amount DECIMAL(10, 5),
  is_critical_transaction BOOLEAN NOT NULL,
  requested_date DATETIME NOT NULL DEFAULT NOW(),
  decision_date DATETIME NOT NULL DEFAULT NOW(),
  from_account INT NOT NULL,
  to_account INT NOT NULL,
  approver INT NOT NULL, /* final approval which should trigger transfer. Tier 2 can directly approve critical transactions no problem. */
  level_1_approval BOOLEAN DEFAULT NULL, /* Customer approval */
  level_2_approval BOOLEAN DEFAULT NULL, /* Tier 2 employee approval */
  FOREIGN KEY (from_account) REFERENCES `secure_banking_system`.`account`(account_id),
  FOREIGN KEY (to_account) REFERENCES `secure_banking_system`.`account`(account_id),
  FOREIGN KEY (approver) REFERENCES `secure_banking_system`.`user`(user_id)
);

CREATE TABLE `secure_banking_system`.`appointment` (
  appointment_id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_user_id INT NOT NULL,
  assigned_to_user_id INT NOT NULL,
  created_date DATETIME NOT NULL DEFAULT NOW(),
  appointment_status VARCHAR(25) NOT NULL,
  FOREIGN KEY (appointment_user_id) REFERENCES `secure_banking_system`.`user`(user_id),
  FOREIGN KEY (assigned_to_user_id) REFERENCES `secure_banking_system`.`user`(user_id)
);

CREATE TABLE `secure_banking_system`.`request` (
  request_id INT PRIMARY KEY AUTO_INCREMENT,
  requested_by INT NOT NULL,
  type_of_request INT NOT NULL, /* request to modify account, request to ??? */
  request_assigned_to INT DEFAULT NULL,
  type_of_account VARCHAR(25) NOT NULL,
  approved BOOLEAN DEFAULT NULL, /* Can be approved by merchant or bank employee depending on type of request */
  FOREIGN KEY (request_assigned_to) REFERENCES `secure_banking_system`.`user`(user_id),
  FOREIGN KEY (requested_by) REFERENCES `secure_banking_system`.`user`(user_id)
);

CREATE TABLE `secure_banking_system`.`login_history` (
  user_id INT NOT NULL,
  logged_in DATETIME NOT NULL DEFAULT NOW(),
  logged_out DATETIME DEFAULT NULL,
  ip_address VARCHAR(25) NOT NULL,
  device_type VARCHAR(25) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(user_id)
);

CREATE VIEW `secure_banking_system`.`customer` AS SELECT * FROM `secure_banking_system`.`user` WHERE user_type = 'customer';
CREATE VIEW `secure_banking_system`.`merchant` AS SELECT * FROM `secure_banking_system`.`user` WHERE user_type = 'merchant';
CREATE VIEW `secure_banking_system`.`tier1` AS SELECT * FROM `secure_banking_system`.`user` WHERE user_type = 'tier1';
CREATE VIEW `secure_banking_system`.`tier2` AS SELECT * FROM `secure_banking_system`.`user` WHERE user_type = 'tier2';
CREATE VIEW `secure_banking_system`.`admin` AS SELECT * FROM `secure_banking_system`.`user` WHERE user_type = 'admin';


CREATE VIEW `secure_banking_system`.`savings_account` AS SELECT * FROM `secure_banking_system`.`account` WHERE account_type = 'savings';
CREATE VIEW `secure_banking_system`.`checking_account` AS SELECT * FROM `secure_banking_system`.`account` WHERE account_type = 'checking';
CREATE VIEW `secure_banking_system`.`credit_account` AS SELECT * FROM `secure_banking_system`.`account` WHERE account_type = 'credit';

CREATE VIEW `secure_banking_system`.`cheque_transaction` AS SELECT * FROM `secure_banking_system`.`transaction` WHERE transaction_type = 'cc';
CREATE VIEW `secure_banking_system`.`transfer_transaction` AS SELECT * FROM `secure_banking_system`.`transaction` WHERE transaction_type = 'transfer';
CREATE VIEW `secure_banking_system`.`debit_transaction` AS SELECT * FROM `secure_banking_system`.`transaction` WHERE transaction_type = 'debit';

CREATE VIEW `secure_banking_system`.`critical_transaction` AS SELECT * FROM `secure_banking_system`.`transaction` WHERE is_critical_transaction = TRUE;
CREATE VIEW `secure_banking_system`.`non_critical_transaction` AS SELECT * FROM `secure_banking_system`.`transaction` WHERE is_critical_transaction = FALSE;