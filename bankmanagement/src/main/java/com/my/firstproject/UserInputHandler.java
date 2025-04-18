package com.my.firstproject;

import java.util.InputMismatchException;
import java.util.Scanner;

class UserInputHandler {

    static Scanner sc;

    @SuppressWarnings("static-access")
    UserInputHandler(Scanner sc) {
        this.sc = sc;
    }

    String CheckName() {
        String name;
        name = sc.nextLine();
        while (!isOnlyAlphabets(name)) {
            System.out.println("Enter a Valid Name");
            name = sc.nextLine();
        }
        return name;
    }

    String checkNumber() {
        String num;
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
        short pin;
        System.out.println("Enter the Pin Below");
        while (true) {

            try {

                pin = sc.nextShort();
                if (pin > 999) {
                    return pin;
                } else {
                    throw new AccNoDoesntExistException("Enter Your 4 Digit Pin");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a Valid 4 DIgit Pin");
            } catch (AccNoDoesntExistException ex) {
                System.out.println(ex);
            }

        }
    }

    boolean validatePin(short pin) {
        for (int i = 0; i < 5; i++) {
            if (pin == checkPin()) {
                return true;
            } else {
                System.out.println("Invalid Pin");
                System.out.println("Enter Your Pin Again");
            }
        }
        return false;
    }

}

