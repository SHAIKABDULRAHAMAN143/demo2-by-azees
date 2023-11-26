package miniatm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Base class for accounts
class Account {
    private double balance;

    public Account(String accountNumber, double balance) {
        this.balance = balance;
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

// SavingsAccount is a specialized type of Account
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        System.out.println("Interest applied. New balance: $" + getBalance());
    }
}

class ATM {
    private Map<String, Account> accounts;
    private Scanner scanner;

    public ATM() {
        this.accounts = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void createAccount(String accountNumber, double initialBalance) {
        if (!accounts.containsKey(accountNumber)) {
            Account account = new Account(accountNumber, initialBalance);
            accounts.put(accountNumber, account);
            System.out.println("Account created successfully. Account number: " + accountNumber);
        } else {
            System.out.println("Account already exists.");
        }
    }

    public void createSavingsAccount(String accountNumber, double initialBalance, double interestRate) {
        if (!accounts.containsKey(accountNumber)) {
            SavingsAccount savingsAccount = new SavingsAccount(accountNumber, initialBalance, interestRate);
            accounts.put(accountNumber, savingsAccount);
            System.out.println("Savings account created successfully. Account number: " + accountNumber);
        } else {
            System.out.println("Account already exists.");
        }
    }

    public void displayBalance(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println("Account balance for " + accountNumber + ": $" + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    public void performTransaction(String accountNumber) {
        System.out.println("Choose an action:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");

        int choice = scanner.nextInt();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        Account account = accounts.get(accountNumber);
        if (account != null) {
            switch (choice) {
                case 1:
                    account.deposit(amount);
                    break;
                case 2:
                    account.withdraw(amount);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void closeScanner() {
        scanner.close();
    }
}

public class AtmClass {
    public static void main(String[] args) {
        ATM atm = new ATM();

        atm.createAccount("123456", 1000);
        atm.createSavingsAccount("789012", 500, 0.05);

        atm.displayBalance("123456");
        atm.displayBalance("789012");

        atm.performTransaction("123456");
        atm.performTransaction("789012");

        atm.closeScanner();
    }
}
