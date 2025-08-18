<<<<<<< HEAD:bankmanagement/src/main/java/com/my/firstproject/UserInputHandler.java
package com.my.firstproject;

import java.util.InputMismatchException;
import java.util.Scanner;

class Check {

    static Scanner sc;

    @SuppressWarnings("static-access")
    Check(Scanner sc) {
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

=======
package com.myfirstproject;

import java.util.Scanner;

class UserInputHandler {

    Scanner sc;

    UserInputHandler() {
        sc = new Scanner(System.in);
    }

    UserInputHandler(Scanner sc) {
        this.sc = sc;
    }

    String CheckName() throws InValidInputException {
        String name;
        byte attempt = 0;
        System.out.println("Enter your Name");
        name = sc.next();
        while (!isOnlyAlphabets(name)) {
            attempt++;
            System.out.println("Enter a Valid Name");
            name = sc.nextLine();
            if (attempt == 3) // giving 4 attemts.
                throw new InValidInputException("Invalid Name Entered Too Many Times");
        }
        return name;
    }

    String checkNumber() throws InValidInputException {
        String num;
        byte attempt = 0;

        System.out.println("Enter You'r Mobile Number");
        num = sc.next();
        while (!isInteger(num)) {
            attempt++;
            System.out.println("Enter a Valid mobile Number");
            num = sc.next();
            if (attempt == 3) // giving 4 attemts.
                throw new InValidInputException("Invalid Mobile Number Entered Too Many Times");
        }
        while (num.length() != 10) {
            attempt++;
            System.out.println("Enter a 10 Digit Mobile Number");
            num = sc.next();
            if (attempt == 3) // giving 4 attemts in total.
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
        // System.out.println("Create new PIN:");
        String pin;
        byte attempt = 0;

        System.out.println("Enter the 4 digit Pin Below");

        pin = sc.next();

        while ((!isInteger(pin)) ||
                (!(pin.length() == 4))) {
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

        int attempt = 0;
        System.out.println("Enter your 4 digit Pin Below");

        while (attempt < 3) {
            String P = sc.next();
            if (Pin.equals(P)) {
                return true;
            } else {
                System.out.println("Invalid Pin");
            }
            System.out.println("Enter Your Pin Again (" + (2 - attempt) + " attempts left)");
            attempt++;
        }
        System.out.println("Invalid Pin entered too many times");
        return false;
    }

}
>>>>>>> fcd9ca6 (revisiting.):bankmanagement/src/main/java/com/myfirstproject/UserInputHandler.java
