package miniatm;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String password;
    private double balance;

    public Account(String username, String password, double initialBalance) {
        this.password = password;
        this.balance = initialBalance;
    }

    public boolean authenticate(String enteredPassword) {
        return password.equals(enteredPassword);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + ". New balance: $" + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class ATM {
    private Map<String, Account> accounts;
    private Scanner scanner;

    public ATM() {
        this.accounts = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void createAccount(String username, String password, double initialBalance) {
        if (!accounts.containsKey(username)) {
            Account account = new Account(username, password, initialBalance);
            accounts.put(username, account);
            System.out.println("Account created successfully. Username: " + username);
        } else {
            System.out.println("Username already exists. Choose a different username.");
        }
    }

    public void authenticateAndPerformTransaction(String username, String password) {
        Account account = accounts.get(username);
        if (account != null && account.authenticate(password)) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawalAmount = scanner.nextDouble();
                    account.withdraw(withdrawalAmount);
                    break;

                case 3:
                    System.out.println("Account balance: $" + account.getBalance());
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void displayMenu() {
        System.out.println("Choose an action:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Display Balance");
        System.out.print("Enter choice: ");
    }

    public void closeScanner() {
        scanner.close();
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();

        atm.createAccount("user123", "password123", 1000);
        atm.createAccount("john_doe", "secret123", 500);

        System.out.print("Enter username: ");
        String username = new Scanner(System.in).nextLine();

        System.out.print("Enter password: ");
        String password = new Scanner(System.in).nextLine();

        atm.authenticateAndPerformTransaction(username, password);

        atm.closeScanner();
    }
}
