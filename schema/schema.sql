DROP DATABASE IF EXISTS secure_banking_system;

CREATE DATABASE secure_banking_system;

USE secure_banking_system;

CREATE TABLE `secure_banking_system`.`user` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL,
  status INT NOT NULL,
  incorrect_attempts INT DEFAULT 0,
  created_date DATETIME DEFAULT NOW(),
  modified_date DATETIME DEFAULT NOW(),
  role VARCHAR(100)
);

CREATE TABLE `secure_banking_system`.`user_details` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  address1 VARCHAR(255) NOT NULL,
  address2 VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  province VARCHAR(255) NOT NULL,
  zip INT NOT NULL,
  date_of_birth DATETIME NOT NULL,
  ssn VARCHAR(15) NOT NULL UNIQUE,
  question_1 VARCHAR(255) NOT NULL,
  question_2 VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(id) ON DELETE CASCADE
);

CREATE TABLE `secure_banking_system`.`account` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  account_number VARCHAR(255) NOT NULL UNIQUE,
  account_type VARCHAR(100) NOT NULL,
  current_balance DECIMAL(30, 5) DEFAULT 0.0,
  created_date DATETIME NOT NULL DEFAULT NOW(),
  status BOOLEAN NOT NULL,
  interest DECIMAL(10, 5) DEFAULT 0.0,
  approval_date DATETIME,
  default_flag INT,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(id)
);

CREATE TABLE `secure_banking_system`.`transaction` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  approval_status BOOLEAN NOT NULL,
  customer_approval INT NOT NULL DEFAULT 0,
  amount DECIMAL(10, 5),
  is_critical_transaction BOOLEAN NOT NULL,
  requested_date DATETIME NOT NULL DEFAULT NOW(),
  decision_date DATETIME DEFAULT NULL,
  from_account VARCHAR(255),
  to_account VARCHAR(255),
  transaction_type VARCHAR(100) NOT NULL,
  request_assigned_to INT DEFAULT NULL,
  approval_level_required VARCHAR(100) NOT NULL,
  level_1_approval BOOLEAN DEFAULT NULL,
  level_2_approval BOOLEAN DEFAULT NULL,
  approved BOOLEAN DEFAULT NULL, /* Can be approved by merchant or bank employee depending on type of request */
  FOREIGN KEY (request_assigned_to) REFERENCES `secure_banking_system`.`user`(id),
  FOREIGN KEY (from_account) REFERENCES `secure_banking_system`.`account`(account_number),
  FOREIGN KEY (to_account) REFERENCES `secure_banking_system`.`account`(account_number)
);

CREATE TABLE `secure_banking_system`.`appointment` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_user_id INT NOT NULL,
  assigned_to_user_id INT NOT NULL,
  created_date DATETIME NOT NULL DEFAULT NOW(),
  appointment_status VARCHAR(255) NOT NULL,
  FOREIGN KEY (appointment_user_id) REFERENCES `secure_banking_system`.`user`(id),
  FOREIGN KEY (assigned_to_user_id) REFERENCES `secure_banking_system`.`user`(id)
);

CREATE TABLE `secure_banking_system`.`login_history` (
  id INT PRIMARY KEY AUTO_INCREMENT, 
  username VARCHAR(255) NOT NULL,
  ip_address VARCHAR(25) NOT NULL,
  logged_in DATETIME NOT NULL DEFAULT NOW(),
  FOREIGN KEY (username) REFERENCES `secure_banking_system`.`user`(username)
);

/*
 * MODE is the mode of communication
 * 0 - SMS
 * 1 - EMail
 * */
CREATE TABLE `secure_banking_system`.`otp` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  otp_key VARCHAR(255) UNIQUE,
  initator INT NOT NULL,
  creation_date DATETIME NOT NULL DEFAULT NOW(),
  expiry_date DATETIME NOT NULL,
  mode INT NOT NULL,
  completed BOOLEAN NOT NULL DEFAULT FALSE,
  ip_address VARCHAR(255) NOT NULL,
  FOREIGN KEY (initator) REFERENCES `secure_banking_system`.`user`(id)
);


CREATE TABLE `secure_banking_system`.`cashierscheck` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  from_account_number VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255),
  transaction_status VARCHAR(100),
  deposit_amount DECIMAL(30, 5) NOT NULL
);

DELIMITER $$

CREATE PROCEDURE `secure_banking_system`.`create_user_transaction` (
	IN transfer_type ENUM('cc', 'debit', 'transfer'),
	IN from_username VARCHAR(255),
	IN from_account INT,
	IN to_account INT,
	IN amount DECIMAL,
	OUT status INT)
BEGIN
	DECLARE total_amount_transferred_today DECIMAL(30, 5) DEFAULT 0;
	DECLARE user_count INT;
	DECLARE trigger_user_id INT;

	SELECT IFNULL(SUM(amount), 0) 
		INTO total_amount_transferred_today 
		FROM `secure_banking_system`.`transaction` AS t 
		WHERE t.from_account = from_account
			AND DATE(t.requested_date) = CURDATE();

	SELECT COUNT(*) INTO user_count
		FROM `secure_banking_system`.`account` AS a JOIN `secure_banking_system`.`user` AS u ON a.user_id = u.id
		WHERE (a.id = from_account AND u.username = from_username AND u.status = 1) OR (a.id = to_account AND u.status = 1);

	SELECT id INTO trigger_user_id
		FROM `secure_banking_system`.`user`
		WHERE username = from_username;

	IF user_count != 2 THEN
		SET status = 3;
	ELSEIF (total_amount_transferred_today + amount > 1000.0) THEN
		INSERT INTO `secure_banking_system`.`transaction` (transaction_type, approval_status, amount, is_critical_transaction, from_account, to_account)
			VALUES(transfer_type, FALSE, amount, TRUE, from_account, to_account);

		INSERT INTO `secure_banking_system`.`request` (requested_by, request_id, type_of_request, approval_level_required)
		  VALUES(trigger_user_id, LAST_INSERT_ID(), "transaction", "tier2");
		SET status = 1;
	ELSEIF (SELECT IFNULL(current_balance, 0) FROM `secure_banking_system`.`account` WHERE id = from_account) < amount THEN
		SET status = 2;
	ELSE
		INSERT INTO `secure_banking_system`.`transaction` (transaction_type, approval_status, amount, is_critical_transaction, from_account, to_account)
			VALUES(transfer_type, FALSE, amount, FALSE, from_account, to_account);
		INSERT INTO `secure_banking_system`.`request` (requested_by, request_id, type_of_request, approval_level_required)
		  VALUES(trigger_user_id, LAST_INSERT_ID(), "transaction", "tier1");
		SET status = 0;
	END IF;
END$$

DELIMITER ;
