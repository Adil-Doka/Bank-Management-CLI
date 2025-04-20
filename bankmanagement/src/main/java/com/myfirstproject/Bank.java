package com.myfirstproject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Bank {

    static UserInputHandler handle = new UserInputHandler();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        List<Accounts> accontList = new ArrayList<>();
        JsonJackson json = new JsonJackson(accontList);
        int TotalUsers = accontList.size();
        byte option = 0;

        while (true) {
            System.out.println(
                    "1.Create an account\n2.check Balance\n3.withDraw Money\n4.Deposit Money\n5.Print Log\n0.Exit");
            System.out.print("Please select an Option: ");

            if (sc.hasNextByte()) {
                option = sc.nextByte();

                switch (option) {

                    case 1 -> {
                        accontList.add(new Accounts(handle.CheckName(), handle.checkNumber(), 1000 + TotalUsers,
                                handle.checkPin()));
                        System.out.println("Your Account is Created Successfully\nThanks for Joining Us");
                        System.out.println("Your Account Number is: " + 1000 + TotalUsers);
                        json.writeNewAccountData(accontList.get(TotalUsers));
                        TotalUsers++;

                    }
                    case 2 -> {
                        try {
                            checkBalance(accontList.get(searchAccount(TotalUsers, accontList)));
                        } catch (NumberNotValidException e) {
                            System.out.println(e);
                        }
                    }
                    case 3 -> {
                        try {
                            withDraw(accontList.get(searchAccount(TotalUsers, accontList)), json);
                        } catch (NumberNotValidException e) {
                            System.out.println(e);
                        }
                    }
                    case 4 -> {

                        try {
                            Deposit(accontList.get(searchAccount(TotalUsers, accontList)), json);
                        } catch (NumberNotValidException e) {
                            System.out.println(e);
                        }

                    }
                    case 5 -> {
                        try {
                            json.PrintLogs(accontList.get(searchAccount(TotalUsers, accontList)).getAccountNumber());
                        } catch (NumberNotValidException e) {
                            System.out.println(e);
                        }
                    }
                    case 0 -> {
                        json.SyncAccountDataToJson();
                        sc.close();
                        return;
                    }

                    default ->
                        System.out.println("Please Enter a valid option:");
                }
            } else {
                System.out.println("Enter an Number between 0 and 6");
                sc.nextLine();
            }
        }
    }

    static int searchAccount(int user, List<Accounts> acc) throws NumberNotValidException {
        byte Attempt = 0;

        while (Attempt < 3) {
            try {
                System.out.print("Enter Your Bank Account Number: ");
                int accNo = sc.nextInt();
                if (String.valueOf(accNo).length() != 4) {
                    System.out.println("Please Enter a valid 4 digit Account Number");
                    continue;
                }

                for (byte i = 0; i < user; i++) {
                    if (acc.get(i).getAccountNumber() == accNo) {
                        return i;
                    }
                }
                throw new NumberNotValidException("Account Number Doesn't match");
            } catch (InputMismatchException e) {
                System.out.println("Please Enter a valid number\n");
            } catch (NumberNotValidException a) {
                System.out.println(a);
                System.out.println("You have " + (3 - Attempt) + " attempts left\n");
                Attempt++;
            }
        }
        throw new NumberNotValidException("maximum trying limit exit.\n..........................");
    }

    // just will check this new added methods
    static void checkBalance(Accounts acc) {
        // checking the pin
        if (!handle.validatePin(acc.getPin())) {
            System.out.println("Invalid Pin entered too many times");
        }
        System.out.println("Your Balance is: " + acc.getBalance());
    }

    static void withDraw(Accounts acc, JsonJackson json) {
        int amount = 0;
        // check pin
        if (!handle.validatePin(acc.getPin())) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }
        System.out.println("Enter the Amount You want to Withdraw");
        try {
            amount = sc.nextShort();
        } catch (InputMismatchException e) {
            System.out.println("Please Enter a Valid Amount");
            sc.nextLine(); // Clear the invalid input
        }

        if (amount > acc.getBalance()) {
            System.out.println("Insufficient Balance");
            return;

        }
        acc.setBalance(acc.getBalance() - amount);
        ;
        System.out.println("Amount Withdrawn Successfully");
        System.out.println("Your Remaining Balance is: " + acc.getBalance());
        json.writeLogs(-amount, acc.getAccountNumber());

    }

    static void Deposit(Accounts acc, JsonJackson json) {
        int amount = 0;
        if (!handle.validatePin(acc.getPin())) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }
        System.out.println("Enter the Amount You want to Deposit");

        try {
            amount = sc.nextShort();
        } catch (InputMismatchException e) {
            System.out.println("Please Enter a Valid Amount");
            sc.nextLine(); // Clear the invalid input
        }

        acc.setBalance(amount + acc.getBalance());
        System.out.println("Amount Deposit Successfully");
        System.out.println("Your Updated Balance is: " + acc.getBalance());
        json.writeLogs(amount, acc.getAccountNumber());
    }

}
