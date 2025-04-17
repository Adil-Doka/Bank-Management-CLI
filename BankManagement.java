import java.util.Scanner;

public class BankManagement {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        BankAccount[] acc = new BankAccount[100];
        byte users = 0;
        byte user;
        short amount;

        IO i = new IO();

        byte option;
        i.show("Please select an Option");

        while (true) {
            i.show("1.Create an account\n2.check Balance\n3.withDraw Money\n4.deposit Money");

            if (sc.hasNextByte()) {
                option = sc.nextByte();

                switch (option) {
                    case 1 -> {
                        acc[users] = new BankAccount(users);
                        users++;
                    }
                    case 2 -> {
                        user = searchAccount(users, acc);
                        if (user == -1) {
                            i.show("Account Number Didn't Exist");
                        } else {
                            acc[user].checkBalance();

                        }
                    }
                    case 3 -> {
                        user = searchAccount(users, acc);
                        if (user == -1) {
                            i.show("Account Number Didn't Exist");
                        } else {
                            acc[user].withDraw();
                        }
                    }
                    case 4 -> {
                        user = searchAccount(users, acc);
                        if (user == -1) {
                            i.show("Account Number Didn't Exist");
                        } else {
                            acc[user].withDraw();
                        }
                    }

                    default ->
                        i.show("Please Enter a valid option:");
                }
            } else {
                System.out.println("Enter an Number between 1 and 6");
                sc.nextLine();
            }
        }
    }

    static byte searchAccount(byte user, BankAccount[] acc) {
        byte op;

        do {
            System.out.println("Enter Your Bank Account Number:");
            int accNo = sc.nextInt();

            for (byte i = 0; i < user; i++) {
                if (acc[i].getAccountNumber() == accNo) {
                    return i;
                }
            }
            System.out.println("Account Number Didn't Exist");
            System.out.println("Enter 1 to continue");
            System.out.println("or press 0 to Exit");
            op = sc.nextByte();
        } while (op != 0);
        return -1;
    }

}

class BankAccount {

    static Scanner sc = new Scanner(System.in);
    static IO i = new IO();
    static Check c = new Check(sc, i);

    int amount;
    private String name;
    private String mobileNumber;
    final private int accountNumber;
    private short pin;
    private int Balance;
    private int transLog[][];
    byte transCount = 0;

    void updateTrans(int amount) {

        transLog[transCount][1] = amount;
        transLog[transCount][2] = Balance;
        transCount++;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    BankAccount(byte accNo) {

        i.show("Enter You'r Name");
        this.name = c.CheckName();

        i.show("Enter You'r mobile Number");
        this.mobileNumber = c.checkNumber();

        i.show("Create new PIN:");
        this.pin = c.checkPin();

        this.accountNumber = 1000 + accNo;

        i.show("Your Account is Created successfully\nThanks for Joining Us");
        i.show("Your Account Number is: " + accountNumber);

        this.Balance = 0;
        this.transLog = new int[100][2];
    }

    void checkBalance() {
        if (c.validatePin(this.pin)) {
            i.show("Your Balance is: " + Balance);
        } else {
            i.show("Invalid Pin entered too many times");
        }
    }

    void withDraw() {
        if (!c.validatePin(this.pin)) {
            i.show("Wrong Otp Entered Too many times.......");
            return;
        }
        i.show("Enter the Amount You want to Withdraw");
        amount = sc.nextShort();
        if (amount > Balance) {
            i.show("Insufficient Balance");
            return;

        }
        Balance -= amount;
        i.show("Amount Withdrawn Successfully");
        i.show("Your Remaining Balance is: " + Balance);
        updateTrans(-amount);

    }

    void Deposit() {
        if (!c.validatePin(this.pin)) {
            i.show("Wrong Otp Entered Too many times.......");
            return;
        }
        i.show("Enter the Amount You want to Deposit");
        amount = sc.nextShort();

        Balance += amount;
        i.show("Amount Deposit Successfully");
        i.show("Your Updated Balance is: " + Balance);
        updateTrans(amount);

    }
}

class Check {

    static Scanner sc;
    static IO i;

    Check(Scanner sc, IO i) {
        this.sc = sc;
        this.i = i;
    }

    String CheckName() {
        String name;
        name = sc.nextLine();
        while (!isOnlyAlphabets(name)) {
            i.show("Enter a Valid Name");
            name = sc.nextLine();
        }
        return name;
    }

    String checkNumber() {
        String num;
        num = sc.next();
        while (!isInteger(num)) {
            i.show("Enter a Valid mobile Number");
            num = sc.next();
        }
        while (num.length() != 10) {
            i.show("Enter a 10 Digit Mobile Number");
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
        i.show("Enter the Pin Below");
        while (true) {
            if (sc.hasNextShort()) {
                pin = sc.nextShort();
                if (pin > 999) {
                    return pin;
                } else {
                    i.show("Enter Your 4 Digit Pin");
                    sc.nextLine();
                }
            } else {
                i.show("Enter a Valid 4 DIgit Pin");
            }
        }
    }

    boolean validatePin(short pin) {
        for (int i = 0; i < 5; i++) {
            if (pin == checkPin()) {
                return true;
            } else {
                this.i.show("Invalid Pin");
                this.i.show("Enter Your Pin Again");
            }
        }
        return false;
    }

}

class IO {

    void show(String str) {
        System.out.println(str);
    }
}
