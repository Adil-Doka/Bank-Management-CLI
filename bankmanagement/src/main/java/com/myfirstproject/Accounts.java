package com.myfirstproject;


class Accounts {

    private String name;
    private String mobileNumber;
    private int accountNumber;
    private short pin;
    private int balance;

    // Constructor
    public Accounts(String name, String mobileNumber, int accountNumber, short pin) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = 0;
    }
    Accounts(){
        
    }



    // Getters
    public String getName() {
        return this.name;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public short getPin() {
        return this.pin;
    }

    public int getBalance() {
        return this.balance;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPin(short pin) {
        this.pin = pin;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    // toString Method
    @Override
    public String toString() {
        return "Accounts Details: \n"
                + "name: " + name 
                + ", mobileNumber: " + mobileNumber 
                + ", accountNumber: " + accountNumber
                + ", pin: " + pin
                + ", balance: " + balance
                + "\n";
    }
}
