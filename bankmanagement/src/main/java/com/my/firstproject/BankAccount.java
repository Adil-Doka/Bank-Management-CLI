package com.my.firstproject;

import java.util.InputMismatchException;
import java.util.Scanner;

class BankAccount {

    static Scanner sc = new Scanner(System.in);
    static Check c = new Check(sc);

    int amount;
    private final String name;
    @SuppressWarnings("unused")
    private final String mobileNumber;
    final private int accountNumber;
    @SuppressWarnings("FieldMayBeFinal")
    private short pin;
    private int Balance;
    private final int transLog[][];// will change from array to another datatype/DS soon
    byte transCount = 0;

    void updateTrans(int amount) {

        try {
            transLog[transCount][0] = amount;
            transLog[transCount][1] = Balance;
            transCount++;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Your Transaction Log is full Please contact the authorities");
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    BankAccount(byte accNo) {

        System.out.println("Enter You'r Name");
        this.name = c.CheckName();

        System.out.println("Enter You'r Mobile Number");
        this.mobileNumber = c.checkNumber();

        System.out.println("Create new PIN:");
        this.pin = c.checkPin();

        this.accountNumber = 1000 + accNo;

        System.out.println("Your Account is Created Successfully\nThanks for Joining Us");
        System.out.println("Your Account Number is: " + accountNumber);

        this.Balance = 0;
        this.transLog = new int[100][2];
    }

    void checkBalance() {
        if (!c.validatePin(this.pin)) {
            System.out.println("Invalid Pin entered too many times");
        }
        System.out.println("Your Balance is: " + Balance);
    }

    void withDraw() {
        if (!c.validatePin(this.pin)) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }
        System.out.println("Enter the Amount You want to Withdraw");
        try {
            amount = sc.nextShort();
        } catch (InputMismatchException e) {
            System.out.println("Please Enter a Valid Amount");
        }

        if (amount > Balance) {
            System.out.println("Insufficient Balance");
            return;

        }
        Balance -= amount;
        System.out.println("Amount Withdrawn Successfully");
        System.out.println("Your Remaining Balance is: " + Balance);
        updateTrans(-amount);

    }

    void Deposit() {
        if (!c.validatePin(this.pin)) {
            System.out.println("Wrong Otp Entered Too many times.......");
            return;
        }
        System.out.println("Enter the Amount You want to Deposit");

        try {
            amount = sc.nextShort();
        } catch (InputMismatchException e) {
            System.out.println("Please Enter a Valid Amount");
        }

        Balance += amount;
        System.out.println("Amount Deposit Successfully");
        System.out.println("Your Updated Balance is: " + Balance);
        updateTrans(amount);

    }

    @Override
    public String toString() {
        return (this.name + "\n" + this.accountNumber + "\n" + this.Balance);
    }

    void PrintLog() {
        if (transLog.length == 0) {
            System.out.println("No Transaction to Show");
            return;
        }
        System.out.println("Amount\tRemaining Balance");
        for (int row = 0; row < 100; row++) {
            System.out.println(transLog[row][0] + "  " + transLog[row][1]);
        }
    }
}