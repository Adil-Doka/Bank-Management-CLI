package com.myfirstproject;

import java.util.Scanner;

class UserInputHandler {

    Scanner sc;

    UserInputHandler(Scanner sc) {
        this.sc = sc;
    }

    UserInputHandler() {
        // Default constructor
        sc = new Scanner(System.in);
    }

    String CheckName() throws InValidInputException {
        String name;
        byte attempt = 0;
        System.out.println("Enter your Name");
        name = sc.next();
        while (!isOnlyAlphabets(name)) {
            attempt++;
            System.out.println("Enter a Valid Name");
            name = sc.next();
            if (attempt >= 3) // giving 4 attemts.
                throw new InValidInputException("Invalid Name Entered Too Many Times");
        }
        return name;
    }

    String checkNumber() throws InValidInputException {
        String num;
        byte attempt = 0;

        System.out.println("Enter You'r Mobile Number");
        num = sc.next();
        while (!isInteger(num) || num.length() != 10) {
            attempt++;
            System.out.println("Enter a Valid 10 Digit mobile Number");
            num = sc.next();
            if (attempt >= 3) // giving 4 attemts.
                throw new InValidInputException("Invalid Mobile Number Entered Too Many Times");
        }

        return num;
    }

    public boolean isInteger(String str) {
        return str.matches("\\d+");
    }

    public boolean isOnlyAlphabets(String str) {
        return str.matches("[a-zA-Z]+");
    }

    String checkPin() throws InValidInputException {

        String pin;
        byte attempt = 0;

        System.out.println("Enter the 4 digit Pin Below\n");

        pin = sc.next();

        while ((!isInteger(pin)) || (!(pin.length() == 4))) {

            attempt++;
            System.out.println("Invalid pin\nEnter the correct 4 Digit Pin");
            pin = sc.next();
            if (attempt == 3) {
                throw new InValidInputException("Invalid pin entered too many times");
            }
        }

        return pin;

    }

    boolean validatePin(String Pin) {

        byte attempt = 0;

        System.out.println("Enter your 4 digit Pin Below\n");

        while (attempt < 3) {
            String pin = sc.next();
            if (Pin.equals(pin))   return true;

            System.out.println("Invalid Pin");
            System.out.println("Enter Your Pin Again (" + (2 - attempt) + " attempts left)");
            attempt++;
        }

        System.out.println("Invalid Pin entered too many times");
        return false;
    }

}
