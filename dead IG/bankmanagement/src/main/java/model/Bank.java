
package model;

import java.util.Scanner;

public class Bank {

    static JDBCconectivity DB;
    static Scanner sc;

    Bank(Scanner sc, JDBCconectivity DB) {
        Bank.sc = sc;
        Bank.DB = DB;
    }

    static {
        Bank.sc = new Scanner(System.in);
        Bank.DB = new JDBCconectivity();
    }

    public static void main(String[] args) {

        AccountActions accAction = new AccountActions(sc, DB);
        byte option = 0;

        boolean isLogedIN;

        while (true) {
            isLogedIN = accAction.login();
            while (isLogedIN) {

                System.out.println(
                        "\n1.check Balance\n2.withDraw Money\n3.Deposit Money\n4.Print Log\n0.LogOut");
                System.out.print("Please select an Option: ");

                if (sc.hasNextByte()) {
                    option = sc.nextByte();

                    switch (option) {

                        case 1 -> accAction.checkBalance();

                        case 2 -> accAction.withDraw();

                        case 3 -> accAction.Deposit();

                        case 4 -> accAction.printLogs();

                        case 0 -> isLogedIN = false;

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