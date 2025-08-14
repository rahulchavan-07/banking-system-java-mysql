CREATE DATABASE banking_system;
USE banking_system;

-- User table
CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

-- Accounts table
CREATE TABLE Accounts (
    account_number BIGINT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    balance DECIMAL(15,2) DEFAULT 0.00,
    security_pin VARCHAR(10) NOT NULL,
    FOREIGN KEY (email) REFERENCES User(email) ON DELETE CASCADE
);
