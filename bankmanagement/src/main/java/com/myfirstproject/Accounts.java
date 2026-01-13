package com.myfirstproject;


class Accounts {

    private String accountNumber;
    private String name;
    private String mobileNumber;
    private String pin;
    private double balance;

    // Constructor
    public Accounts(String name, String mobileNumber, String accountNumber, String pin,double balance) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
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

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getPin() {
        return this.pin;
    }

    public double getBalance() {
        return this.balance;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(double balance) {
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
