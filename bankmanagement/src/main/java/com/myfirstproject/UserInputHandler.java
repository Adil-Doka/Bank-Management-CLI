package com.myfirstproject;

import java.util.InputMismatchException;
import java.util.Scanner;

class UserInputHandler {

    Scanner sc = new Scanner(System.in);

    String CheckName() {
        String name;
        System.out.println("Enter you Name");
        name = sc.nextLine();
        while (!isOnlyAlphabets(name)) {
            System.out.println("Enter a Valid Name");
            name = sc.nextLine();
        }
        return name;
    }

    String checkNumber() {
        String num;
        System.out.println("Enter You'r Mobile Number");
        num = sc.next();
        while (!isInteger(num)) {
            System.out.println("Enter a Valid mobile Number");
            num = sc.next();
        }
        while (num.length() != 10) {
            System.out.println("Enter a 10 Digit Mobile Number");
            num = sc.next();
        }
        return num;
    }

    public boolean isInteger(String str) {
        return str.matches("\\d+");
    }

    public boolean isOnlyAlphabets(String str) {
        return str.matches("[a-zA-Z]+");
    }

    short checkPin() {
        // System.out.println("Create new PIN:");
        short pin;
        System.out.println("Enter the 4 digit Pin Below");
        while (true) {

            try {

                pin = sc.nextShort();
                if (pin > 999 & pin < 9999) {
                    return pin;
                } else {
                    throw new NumberNotValidException("Enter the correct 4 Digit Pin");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a Valid 4 DIgit Pin");
            } catch (NumberNotValidException ex) {
                System.out.println(ex);
            }

        }
    }

    boolean validatePin(short pin) {
        int attempt = 4;
        for (int i = 1; i < 5; i++) {
            if (pin == checkPin()) {
                return true;
            } else {
                System.out.println("Invalid Pin");
                System.out.println("Enter Your Pin Again (" +attempt+ "attempt left)");
                attempt--;
            }
        }
        return false;
    }

}
