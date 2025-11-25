package model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountActions extends UserInputHandler {

    JDBCconectivity DB;
    ArrayList<String> totalUserAccNo = new ArrayList<>();
    Scanner sc;
    Accounts account = new Accounts();

    AccountActions(Scanner sc, JDBCconectivity DB) {
        super(sc);
        this.sc = sc;
        this.DB = DB;

        DB.getAllAccountno(totalUserAccNo);

    }

    void newAccount() {
        String acc = "0";
        try {

            acc = DB.newUser(CheckName(),
                    checkNumber(),
                    checkPin());

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

    boolean login() {

        System.out.println("Enter Your bank account Number");
        System.out.println("our Enter \"create\" to create a new account");

        String accNO = sc.next();

        if ("create".equalsIgnoreCase(accNO)) {
            newAccount();
            return false;
        }

        if (totalUserAccNo.contains(accNO)) {

            this.account = DB.getAccount(accNO);
            if (validatePin(account.getPin())) {
                return true;
            }
            System.out.println("Invalid Pin entered too many times");

        } else
            System.out.println("Account didnt exist");

        return false;
    }

    void checkBalance() {

        System.out.println("Your Balance is: " + account.getBalance());



    }

    void withDraw() {

        int amount = 0;
        System.out.println("Enter the Amount You want to Withdraw");
        amount = sc.nextShort();
        try {
            amount = sc.nextShort();
            if (amount <= 0)
                throw new InputMismatchException("Please Enter a Valid Amount");
            if (amount > account.getBalance())
                throw new InputMismatchException("Insufficient Balance");

        } catch (InputMismatchException e) {
            System.out.println(e);
            sc.nextLine(); // Clear the invalid input
            return;
        }

        DB.updateBalance(account.getAccountNumber(), account.getBalance() - amount);
        account = DB.getAccount(account.getAccountNumber());

        System.out.println("Amount Withdrawn Successfully");
        System.out.println("Your Remaining Balance is: " + account.getBalance());

    }

    void Deposit() {
        int amount = 0;

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

        DB.updateBalance(account.getAccountNumber(), (amount + account.getBalance()));
        account = DB.getAccount(account.getAccountNumber());

        System.out.println("Amount Deposit Successfully");
        System.out.println("Your Updated Balance is: " + account.getBalance());

  
    }

    void printLogs() {
        ArrayList<String> logs = new ArrayList<>();

        DB.getLogs(logs, account.getAccountNumber());

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
