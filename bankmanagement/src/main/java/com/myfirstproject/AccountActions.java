package com.myfirstproject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountActions extends UserInputHandler {

    UserRepository userRepo = new UserRepository();

    Scanner sc = new Scanner(System.in);

    Accounts account;

    boolean login() {

        System.out.println("""
                Enter Your bank account Number
                or Enter \"create\" to create a new account
                  """);

        String accNO = sc.next();

        if ("create".equalsIgnoreCase(accNO)) {
            newAccount();
            return false;
        }

        this.account = userRepo.getAccount(accNO);

        return (account != null && validatePin(account.getPin())) ? true : false;

    }

    void newAccount() {

        String acc;

        try {
            acc = userRepo.newUser(CheckName(),
                    checkNumber(),
                    checkPin());

            System.out.println("""
                    Your Account is Created Successfully\nThanks for Joining Us
                    Your Account Number is: """ + acc);

        } catch (InValidInputException e) {
            System.out.println(e);
        }

    }

    void checkBalance() {
        System.out.println("Your Balance is: " + account.getBalance());
    }

    void withdraw() {

        double amount = 0.0;

        System.out.println("Enter the Amount You want to Withdraw");

        try {
            amount = sc.nextDouble();
            if (amount <= 0)
                throw new InValidInputException("Please Enter a Valid Amount");
            if (amount > account.getBalance())
                throw new InValidInputException("Insufficient Balance");

        } catch (InValidInputException e) {
            System.out.println(e);
            sc.nextLine(); // Clear the invalid input
            return;
        } catch (InputMismatchException e) {
            System.out.println(e);
            sc.nextLine(); // Clear the invalid input
            return;
        }

        userRepo.updateBalance(account.getAccountNumber(), account.getBalance() - amount);
        account = userRepo.getAccount(account.getAccountNumber());

        System.out.println("Amount Withdrawn Successfully");
        System.out.println("Your Remaining Balance is: " + account.getBalance());

    }

    void deposit() {
        double amount = 0.0;

        System.out.println("Enter the Amount You want to Deposit");

        try {
            amount = sc.nextDouble();
            if (amount <= 0)
                throw new InValidInputException("Please Enter a Valid Amount");
        } catch (InValidInputException e) {
            System.out.println(e);
            sc.nextLine(); // Clear the invalid input
            return;
        } catch (InputMismatchException e) {
            System.out.println(e);
        }

        userRepo.updateBalance(account.getAccountNumber(), (amount + account.getBalance()));
        account = userRepo.getAccount(account.getAccountNumber());

        System.out.println("Amount Deposit Successfully");
        System.out.println("Your Updated Balance is: " + account.getBalance());

    }

    void printLogs() {
        ArrayList<String> logs = new ArrayList<>();

        userRepo.getLogs(logs, account.getAccountNumber());

        if (logs.isEmpty()) {
            System.out.println("Nothing to Print");
            return;
        }
        if (!validatePin(account.getPin())) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }

        System.out.println("Transaction Histroy for " + account.getAccountNumber());
        System.out.println("Date And Time             amount");
        for (String log : logs) {
            System.out.println(log);
        }

    }
}
