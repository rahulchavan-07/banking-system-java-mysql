package BankingManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class AccountManager {

    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner sc) {
        this.connection = connection;
        this.scanner = sc;
    }

    public void credit_money(long account_number) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security pin:");
        String security_pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String credit_query = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                PreparedStatement creditStatement = connection.prepareStatement(credit_query);
                creditStatement.setDouble(1, amount);
                creditStatement.setLong(2, account_number);

                int rows = creditStatement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Rs. " + amount + " credited successfully.");
                    connection.commit();
                } else {
                    System.out.println("Transaction failed.");
                    connection.rollback();
                }
            } else {
                System.out.println("Invalid security pin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void debit_money(long account_number) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security pin:");
        String security_pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double current_balance = resultSet.getDouble("balance");
                if (amount <= current_balance) {
                    String debit_query = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                    PreparedStatement debitStatement = connection.prepareStatement(debit_query);
                    debitStatement.setDouble(1, amount);
                    debitStatement.setLong(2, account_number);

                    int rows = debitStatement.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Rs. " + amount + " debited successfully.");
                        connection.commit();
                    } else {
                        System.out.println("Transaction failed.");
                        connection.rollback();
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Invalid security pin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void getBalance(long account_number) {
        scanner.nextLine();
        System.out.println("Enter security pin:");
        String security_pin = scanner.nextLine();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?");
            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                System.out.println("Your Available Balance: Rs. " + balance);
            } else {
                System.out.println("Invalid pin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transfer_money(long sender_account_num) throws SQLException {
        scanner.nextLine();
        System.out.println("Enter receiver account number:");
        long receiver_account_num = scanner.nextLong();
        System.out.println("Enter amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter security pin:");
        String security_pin = scanner.nextLine();

        try {
            connection.setAutoCommit(false);

            PreparedStatement senderCheck = connection.prepareStatement("SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?");
            senderCheck.setLong(1, sender_account_num);
            senderCheck.setString(2, security_pin);
            ResultSet resultSet = senderCheck.executeQuery();

            if (resultSet.next()) {
                double current_balance = resultSet.getDouble("balance");
                if (amount <= current_balance) {
                    String debit_query = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                    String credit_query = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";

                    PreparedStatement debitStatement = connection.prepareStatement(debit_query);
                    PreparedStatement creditStatement = connection.prepareStatement(credit_query);

                    debitStatement.setDouble(1, amount);
                    debitStatement.setLong(2, sender_account_num);

                    creditStatement.setDouble(1, amount);
                    creditStatement.setLong(2, receiver_account_num);

                    int rows1 = debitStatement.executeUpdate();
                    int rows2 = creditStatement.executeUpdate();

                    if (rows1 > 0 && rows2 > 0) {
                        System.out.println("Rs. " + amount + " transferred successfully.");
                        connection.commit();
                    } else {
                        System.out.println("Transaction failed.");
                        connection.rollback();
                    }
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Invalid security pin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
