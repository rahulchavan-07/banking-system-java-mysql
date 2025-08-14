# Banking Management System

A simple console-based Banking Management System built using **Java** and **MySQL (JDBC)**.  
This system allows user registration, login, account management, and basic banking transactions.

## Features

- **User Registration & Login**
  - Create a new user account with name, email, and password
  - Secure login with email and password validation

- **Bank Account Management**
  - Open a new bank account with an initial deposit and security PIN
  - Automatically generate a unique account number
  - Prevent multiple accounts for the same user

- **Transactions**
  - **Credit Money** – Deposit funds into an account after PIN verification
  - **Debit Money** – Withdraw funds from an account if sufficient balance exists
  - **Transfer Money** – Send money between accounts with balance check and atomic transactions
  - **Check Balance** – View account balance after PIN authentication

- **Data Storage**
  - Uses MySQL database for storing user, account, and transaction details
  - JDBC for database connectivity and SQL queries

## Database
The database schema is available in the `database.sql` file included in this repository.
