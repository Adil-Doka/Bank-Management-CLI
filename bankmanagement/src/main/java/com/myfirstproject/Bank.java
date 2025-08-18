package com.myfirstproject;

import java.util.HashMap;
import java.util.InputMismatchException;
// import java.util.List;
import java.util.Scanner;

public class Bank {

    Scanner sc;
    UserInputHandler handle;

    Bank(Scanner sc) {
        this.sc = sc;
        this.handle = new UserInputHandler(sc);
    }

    public static void main(String[] args) {

        Bank bank = new Bank(new Scanner(System.in));

        HashMap<String, Accounts> accontList = new HashMap<>();
        // JsonJackson json = new JsonJackson(accontList);
        int TotalUsers = accontList.size();
        byte option = 0;

        while (true) {
            System.out.println(
                    "1.Create an account\n2.check Balance\n3.withDraw Money\n4.Deposit Money\n5.Print Log\n0.Exit");
            System.out.print("Please select an Option: ");

            if (bank.sc.hasNextByte()) {
                option = bank.sc.nextByte();

                switch (option) {

                    case 1 -> {
                        try {

                            accontList.put(
                                    Integer.toString(1000 + TotalUsers),
                                    (new Accounts(
                                            bank.handle.CheckName(),
                                            bank.handle.checkNumber(),
                                            Integer.toString(1000 + TotalUsers),
                                            bank.handle.checkPin())));

                        } catch (InValidInputException e) {
                            System.out.println(e);
                        }

                        System.out.println("Your Account is Created Successfully\nThanks for Joining Us");
                        System.out.println("Your Account Number is: " + (1000 + TotalUsers));
                        // json.writeNewAccountData(accontList.get(TotalUsers));
                        TotalUsers++;

                    }
                    case 2 -> {
                        System.out.println("Enter Your bank account Number");
                        String acc = bank.sc.next();
                        if (accontList.containsKey(acc))
                            bank.checkBalance(accontList.get(acc));
                        else
                            System.out.println("Account didnt exist");

                    }
                    case 3 -> {
                        System.out.println("Enter Your bank account Number");
                        String acc = bank.sc.next();
                        if (accontList.containsKey(acc))
                            bank.withDraw(accontList.get(acc));
                        else
                            System.out.println("Account didnt exist");
                    }
                    case 4 -> {
                        System.out.println("Enter Your bank account Number");
                        String acc = bank.sc.next();

                        // (accontList.containsKey(acc)) ? Deposit(accontList.get(acc)) :
                        // System.out.println("Account didnt exist") ;
                        if (accontList.containsKey(acc))
                            bank.Deposit(accontList.get(acc));
                        else
                            System.out.println("Account didnt exist");

                    }
                    // case 5 -> {

                    // try {
                    // json.PrintLogs(accontList.get(searchAccount(TotalUsers,
                    // accontList)).getAccountNumber());
                    // } catch (NumberNotValidException e) {
                    // System.out.println(e);
                    // }
                    // }
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

    // static int searchAccount(int user, List<Accounts> acc) throws
    // NumberNotValidException {
    // byte Attempt = 0;

    // while (Attempt < 3) {
    // try {
    // System.out.print("Enter Your Bank Account Number: ");
    // int accNo = sc.nextInt();
    // if (String.valueOf(accNo).length() != 4) {
    // System.out.println("Please Enter a valid 4 digit Account Number");
    // continue;
    // }

    // for (byte i = 0; i < user; i++) {
    // if (acc.get(i).getAccountNumber() == accNo) {
    // return i;
    // }
    // }
    // throw new NumberNotValidException("Account Number Doesn't match");
    // } catch (InputMismatchException e) {
    // System.out.println("Please Enter a valid number\n");
    // } catch (NumberNotValidException a) {
    // System.out.println(a);
    // System.out.println("You have " + (3 - Attempt) + " attempts left\n");
    // Attempt++;
    // }
    // }
    // throw new NumberNotValidException("maximum trying limit
    // exit.\n..........................");
    // }

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
            if (amount >= 0)
                throw new InputMismatchException();
            if (amount > acc.getBalance())
                throw new InputMismatchException("Insufficient Balance");

        } catch (InputMismatchException e) {
            System.out.println("Please Enter a Valid Amount");
            sc.nextLine(); // Clear the invalid input
            return;
        }

        acc.setBalance(acc.getBalance() - amount);
        ;
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
            if (amount >= 0)
                throw new InputMismatchException();
        } catch (InputMismatchException e) {
            System.out.println("Please Enter a Valid Amount");
            sc.nextLine(); // Clear the invalid input
            return;
        }

        acc.setBalance(amount + acc.getBalance());
        System.out.println("Amount Deposit Successfully");
        System.out.println("Your Updated Balance is: " + acc.getBalance());
        // json.writeLogs(amount, acc.getAccountNumber());
    }

}
