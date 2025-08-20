
package com.myfirstproject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank {

    JDBCconectivity DB = new JDBCconectivity();

    Scanner sc;
    UserInputHandler handle;

    Bank(Scanner sc) {
        this.sc = sc;
        this.handle = new UserInputHandler(sc);
    }

    public static void main(String[] args) {

        Bank bank = new Bank(new Scanner(System.in));

        ArrayList<String> totalUserAccNo = new ArrayList<>();
        bank.DB.getAllAccountno(totalUserAccNo);

        byte option = 0;

        while (true) {
            System.out.println(
                    "1.Create an account\n2.check Balance\n3.withDraw Money\n4.Deposit Money\n5.Print Log\n0.Exit");
            System.out.print("Please select an Option: ");

            if (bank.sc.hasNextByte()) {
                option = bank.sc.nextByte();

                switch (option) {

                    case 1 -> {
                        String acc = "0";
                        try {

                            acc = bank.DB.newUser(bank.handle.CheckName(),
                                    bank.handle.checkNumber(),
                                    bank.handle.checkPin());

                        } catch (InValidInputException e) {
                            System.out.println(e);
                        }

                        if (acc.equals("0")) {
                            System.out.println("Something went wrong");
                        } else {
                            System.out.println("Your Account is Created Successfully\nThanks for Joining Us");
                            System.out.println("Your Account Number is: " + acc);
                            totalUserAccNo.add(acc);
                        }

                    }
                    case 2 -> {
                        System.out.println("Enter Your bank account Number");
                        String acc = bank.sc.next();
                        if (totalUserAccNo.contains(acc)) {
                            try {
                                bank.checkBalance(bank.DB.getAccount(acc));
                            } catch (NullPointerException e) {
                                System.out.println("Some shit happen " + e);
                            }
                        } else
                            System.out.println("Account didnt exist");

                    }
                    case 3 -> {
                        System.out.println("Enter Your bank account Number");
                        String acc = bank.sc.next();
                        if (totalUserAccNo.contains(acc)) {
                            try {
                                bank.withDraw(bank.DB.getAccount(acc));
                            } catch (NullPointerException e) {
                                System.out.println("Some shit happen " + e);
                            }
                        } else
                            System.out.println("Account didnt exist");
                    }
                    case 4 -> {
                        System.out.println("Enter Your bank account Number");
                        String acc = bank.sc.next();
                        if (totalUserAccNo.contains(acc)) {
                            try {
                                bank.Deposit(bank.DB.getAccount(acc));
                            } catch (NullPointerException e) {
                                System.out.println("Some shit happen " + e);
                            }
                        } else
                            System.out.println("Account didnt exist");

                    }
                    case 5 -> {
                        System.out.println("Enter your account number");
                        String acc = bank.sc.next();
                        if (totalUserAccNo.contains(acc)) {
                            bank.printLogs(bank.DB.getAccount(acc));
                        } else
                            System.out.print("account not found....");
                    }

                    case 0 -> {
                        // json.SyncAccountDataToJson();
                        bank.sc.close();
                        return;
                    }

                    default ->
                        System.out.println("Please Enter a valid option:");
                }
            } else {
                System.out.println("Enter an Number between 0 and 6");
                bank.sc.nextLine();
            }
        }
    }

    // just will check this new added methods
    void checkBalance(Accounts acc) {
        // checking the pin
        if (!handle.validatePin(acc.getPin())) {
            System.out.println("Invalid Pin entered too many times");
            return;
        }
        System.out.println("Your Balance is: " + acc.getBalance());
    }

    // static void withDraw(Accounts acc, JsonJackson json) {
    void withDraw(Accounts acc) {
        int amount = 0;
        // check pin
        if (!handle.validatePin(acc.getPin())) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }
        System.out.println("Enter the Amount You want to Withdraw");
        try {
            amount = sc.nextShort();
            if (amount <= 0)
                throw new InputMismatchException("Please Enter a Valid Amount");
            if (amount > acc.getBalance())
                throw new InputMismatchException("Insufficient Balance");

        } catch (InputMismatchException e) {
            System.out.println(e);
            sc.nextLine(); // Clear the invalid input
            return;
        }

        DB.updateBalance(acc.getAccountNumber(), acc.getBalance() - amount);
        acc = DB.getAccount(acc.getAccountNumber());

        System.out.println("Amount Withdrawn Successfully");
        System.out.println("Your Remaining Balance is: " + acc.getBalance());
        // json.writeLogs(-amount, acc.getAccountNumber());

    }

    // static void Deposit(Accounts acc, JsonJackson json) {
    void Deposit(Accounts acc) {
        int amount = 0;
        if (!handle.validatePin(acc.getPin())) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }
        System.out.println("Enter the Amount You want to Deposit");

        try {
            amount = sc.nextShort();
            if (amount <= 0)
                throw new InputMismatchException();
        } catch (InputMismatchException e) {
            System.out.println("Please Enter a Valid Amount");
            sc.nextLine(); // Clear the invalid input
            return;
        }

        DB.updateBalance(acc.getAccountNumber(), (amount + acc.getBalance()));
        acc = DB.getAccount(acc.getAccountNumber());

        System.out.println("Amount Deposit Successfully");
        System.out.println("Your Updated Balance is: " + acc.getBalance());
        // json.writeLogs(amount, acc.getAccountNumber());
    }

    void printLogs(Accounts acc) {
        ArrayList<String> logs = new ArrayList<>();
        DB.getLogs(logs, acc.getAccountNumber());

        if (logs.isEmpty()) {
            System.out.println("Nothing to Print");
            return;
        }
        if (!handle.validatePin(acc.getPin())) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }

        System.out.println("Transaction Histroy for " + acc.getAccountNumber());
        System.out.println("Date And Time             amount");
        for (String log : logs) {
            System.out.println(log);
        }
    }
}