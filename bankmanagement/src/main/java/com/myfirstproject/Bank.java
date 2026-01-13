package com.myfirstproject;

import java.util.Scanner;

public class Bank {

    public static void main(String[] args) {

        AccountActions accAction = new AccountActions();
        byte option = 0;

        Scanner sc = new Scanner(System.in);

        boolean isLoggedIn;

        while (true) {
            isLoggedIn = accAction.login();
            while (isLoggedIn) {

                System.out.println(
                        "\n1.check Balance\n2.withDraw Money\n3.Deposit Money\n4.Print Log\n0.LogOut");
                System.out.print("Please select an Option: ");

                if (sc.hasNextByte()) {
                    option = sc.nextByte();

                    switch (option) {

                        case 1 -> accAction.checkBalance();

                        case 2 -> accAction.withdraw();

                        case 3 -> accAction.deposit();

                        case 4 -> accAction.printLogs();

                        case 0 -> isLoggedIn = false;

                        default ->
                            System.out.println("Please Enter a valid option:");
                    }
                } else {
                    System.out.println("Enter an Number between 0 and 6");
                    sc.nextLine();
                }
            }
        }
    }

}