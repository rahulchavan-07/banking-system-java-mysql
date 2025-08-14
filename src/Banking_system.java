package BankingManagementSystem;
import java.sql.*;
import java.util.Scanner;

public class Banking_system {

    private static final String url = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "Rahul@7072";

    public static void main(String[] args) {

        // Use try-with-resources for auto-closing resources
        try (Scanner scanner = new Scanner(System.in);
             Connection connection = DriverManager.getConnection(url, username, password)) {

            // Create User, Accounts, and AccountManager instances
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            while (true) {
                System.out.println("** Welcome to Sangli Bank **");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your choice:");

                int choice1 = scanner.nextInt();

                switch (choice1) {
                    case 1:
                        user.register();
                        break;

                    case 2:
                        String email = user.login();
                        if (email != null) {
                            System.out.println("User successfully logged in!");

                            if (!accounts.account_exist(email)) {
                                System.out.println("1. Open Bank Account");
                                System.out.println("2. Exit");
                                if (scanner.nextInt() == 1) {
                                    long newAcc = accounts.open_account(email);
                                    System.out.println("Account created! Your account number is: " + newAcc);
                                } else {
                                    break;
                                }
                            }

                            long accNum = accounts.getAccount_number(email);
                            int choice2 = 0;
                            while (choice2 != 5) {
                                System.out.println("\n1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Logout");
                                System.out.println("Enter your choice:");
                                choice2 = scanner.nextInt();

                                switch (choice2) {
                                    case 1:
                                        accountManager.debit_money(accNum);
                                        break;
                                    case 2:
                                        accountManager.credit_money(accNum);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(accNum);
                                        break;
                                    case 4:
                                        accountManager.getBalance(accNum);
                                        break;
                                    case 5:
                                        System.out.println("Logged out successfully.");
                                        break;
                                    default:
                                        System.out.println("Enter a valid choice.");
                                }
                            }
                        } else {
                            System.out.println("Login failed. Check credentials.");
                        }
                        break;

                    case 3:
                        System.out.println("Thanks for using Sangli Bank!");
                        return;

                    default:
                        System.out.println("Enter a valid option.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
