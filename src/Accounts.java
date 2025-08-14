package BankingManagementSystem;

import java.sql.*;
import java.util.*;
import java.util.Scanner;

public class Accounts {

    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner2) {
        this.connection = connection;
        this.scanner = scanner2;
    }

    public boolean account_exist(String email) {
        String query = "SELECT account_number FROM Accounts WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private long generateAccountNum() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT account_number FROM Accounts ORDER BY account_number DESC LIMIT 1");
            if (resultSet.next()) {
                long lastAccountNumber = resultSet.getLong("account_number");
                return lastAccountNumber + 1;
            } else {
                return 10000100;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10000100;
    }

    public long open_account(String email) {
        if (!account_exist(email)) {
            String openAccountQuery = "INSERT INTO Accounts(account_number, full_name, email, balance, security_pin) VALUES (?, ?, ?, ?, ?)";
            scanner.nextLine();
            System.out.println("Enter full name:");
            String full_name = scanner.nextLine();
            System.out.println("Enter initial amount:");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter security pin:");
            String security_pin = scanner.nextLine();

            try {
                long account_number = generateAccountNum();
                PreparedStatement preparedStatement = connection.prepareStatement(openAccountQuery);
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, full_name);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, security_pin);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return account_number;
                } else {
                    throw new RuntimeException("Failed to create account.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Account already exists.");
    }

    public long getAccount_number(String email) {
        String query = "SELECT account_number FROM Accounts WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("account_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Account number does not exist.");
    }
}
