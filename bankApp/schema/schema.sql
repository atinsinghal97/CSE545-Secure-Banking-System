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
  is_external_user BOOLEAN NOT NULL
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
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(user_id) ON DELETE CASCADE
);

CREATE TABLE `secure_banking_system`.`account` (
  account_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  account_type VARCHAR(255) NOT NULL,
  current_amount DECIMAL(10, 5) DEFAULT 0.0,
  created_date DATETIME NOT NULL DEFAULT NOW(),
  status INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES `secure_banking_system`.`user`(user_id) ON DELETE CASCADE
);

CREATE TABLE `secure_banking_system`.`transaction` (
  transaction_id INT PRIMARY KEY AUTO_INCREMENT,
  transaction_type INT NOT NULL,
  transaction_status INT NOT NULL,
  transaction_amount DECIMAL(10, 5),
  is_critical_transaction BOOLEAN NOT NULL,
  transaction_created_date DATETIME NOT NULL DEFAULT NOW(),
  transaction_updated_date DATETIME NOT NULL DEFAULT NOW(),
  from_account INT NOT NULL,
  to_account INT NOT NULL,
  transaction_approved_by INT NOT NULL,
  FOREIGN KEY (from_account) REFERENCES `secure_banking_system`.`account`(account_id),
  FOREIGN KEY (to_account) REFERENCES `secure_banking_system`.`account`(account_id),
  FOREIGN KEY (transaction_approved_by) REFERENCES `secure_banking_system`.`user`(user_id)
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
  type_of_request INT NOT NULL,
  request_assigned_to INT DEFAULT NULL,
  type_of_account VARCHAR(25) NOT NULL,
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