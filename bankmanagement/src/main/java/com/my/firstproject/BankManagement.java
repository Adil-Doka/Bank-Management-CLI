package com.my.firstproject;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankManagement {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        BankAccount[] acc = new BankAccount[100];
        byte users = 0;
        byte user;
        byte option;

        while (true) {
            System.out.println("1.Create an account\n2.check Balance\n3.withDraw Money\n4.Deposit Money\n5.Print Log");
            System.out.print("Please select an Option: ");

            if (sc.hasNextByte()) {
                option = sc.nextByte();

                switch (option) {

                    case 1 -> {
                        try {
                            acc[users] = new BankAccount(users);
                            users++;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Something went Wrong Please contact the authorityies........");
                        }

                    }
                    case 2 -> {
                        try {
                            user = searchAccount(users, acc);
                            acc[user].checkBalance();

                        } catch (AccNoDoesntExistException e) {
                            System.out.println(e);
                        }
                    }
                    case 3 -> {
                        try {
                            user = searchAccount(users, acc);
                            acc[user].withDraw();

                        } catch (AccNoDoesntExistException e) {
                            System.out.println(e);
                        }
                    }
                    case 4 -> {

                        try {
                            user = searchAccount(users, acc);
                            acc[user].Deposit();

                        } catch (AccNoDoesntExistException e) {
                            System.out.println(e);
                        }

                    }
                    case 5 -> {
                        try {
                            user = searchAccount(users, acc);
                            System.out.println(acc[user]);
                            acc[user].PrintLog();
                        } catch (AccNoDoesntExistException ex) {
                            System.out.println(ex);
                        }
                    }

                    default ->
                        System.out.println("Please Enter a valid option:");
                }
            } else {
                System.out.println("Enter an Number between 1 and 6");
                sc.nextLine();
            }
        }
    }

    static byte searchAccount(byte user, BankAccount[] acc) throws AccNoDoesntExistException {
        byte count = 0;

        while (count < 3) {
            try {
                System.out.print("Enter Your Bank Account Number: ");
                int accNo = sc.nextInt();

                for (byte i = 0; i < user; i++) {
                    if (acc[i].getAccountNumber() == accNo) {
                        return i;
                    }
                }
                    throw new AccNoDoesntExistException("Account Number Doesn't match");
            } catch (InputMismatchException e) {
                System.out.println("Please Enter a valid number\n");
            } catch (AccNoDoesntExistException a) {
                System.out.println(a);
            }
            count++;
        }
        throw new AccNoDoesntExistException("maximum trying limit exit.\n..........................");
    }

}
