package com.myfirstproject;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
// import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonJackson {

    final String AccountDataPath = "bankmanagement/Json_Files/AccountData.jsonl";
    final String LogsDataPath = "bankmanagement/Json_Files/AccountLogs.jsonl";

    File dir = new File("bankmanagement/Json_Files");
    File accountData = new File(AccountDataPath);
    File accountlogs = new File(LogsDataPath);

    List<Accounts> account;
    ObjectMapper mapper = new ObjectMapper();

    LocalDate date;
    LocalTime time;
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

    // loads Accounts Data from json file and maps to obj.
    public JsonJackson(List<Accounts> account) {

        this.account = account;
        Accounts acc = new Accounts();
        dir.mkdir();

        if (!this.accountData.exists()) {
            System.out.println("No Data Found\nCreating New File");
            try {
                this.accountData.createNewFile();
            } catch (IOException e) {
                System.out.println("Error Creating File");
            }
            return;
        }

        // reading the data from json and mapping to obj.
        try (BufferedReader reader = new BufferedReader(new FileReader(this.AccountDataPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                acc = mapper.readValue(line, Accounts.class);
                account.add(acc);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println("Error Creating File");
        }

    }

    void writeNewAccountData(Accounts account) {
        try (FileWriter fwd = new FileWriter(this.AccountDataPath, true)) {

            fwd.write(mapper.writeValueAsString(account));
            fwd.write("\n");// add new line

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    void SyncAccountDataToJson() {
        this.accountData.delete();
        try (FileWriter fwd = new FileWriter(this.AccountDataPath, true)) {

            for (Accounts account : account) {
                fwd.write(mapper.writeValueAsString(account));
                fwd.write("\n");
            }
            fwd.flush();
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println("SomeThing Went Wrong");
        } finally {
            System.out.println("Data Synced to Json File");
        }

    }

    void writeLogs(int amount, int accountNo) {
        if (!accountlogs.exists()) {
            System.out.println("Log data file missing");
            try {
                accountlogs.createNewFile();
            } catch (IOException e) {
                System.out.println("Error Creating File");
            } catch (Exception e) {
                System.out.println("SomeThing Went Wrong");
            }
            return;
        }

        date = LocalDate.now();
        time = LocalTime.now();

        LogEntry trans = new LogEntry(accountNo, date.format(formatDate), time.format(formatTime), amount,
                account.get(accountNo - 1000).getBalance());
        // String str =
        // "{\"accountNumber\":"+accountNo+",\"date\":\""+date.format(formatDate)+"\",\"time\":\""+time.format(formatTime)+"\",\"amount\":"+amount+",\"balance\":"+account.get(accountNo-1000).getBalance()+"}";

        try (FileWriter fwd = new FileWriter(this.LogsDataPath, true)) {

            fwd.write(mapper.writeValueAsString(trans));
            fwd.write("\n");// add new line

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    void PrintLogs(int accountNo) {
        LogEntry Transaction = new LogEntry();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.LogsDataPath))) {
            String line;
            int count = 0;

            System.out.println("\nAccount Number: " + accountNo);
            while ((line = reader.readLine()) != null) {
                Transaction = mapper.readValue(line, LogEntry.class);

                if (Transaction.getAccountNumber() == accountNo) {
                    System.out.println("\nDate: " + Transaction.getDate() + "\nTime: " + Transaction.getTime()
                            + "\nAmount: " + Transaction.getAmount() + "\nBalance: " + Transaction.getBalance() + "\n");
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("No Transaction Found");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class LogEntry {
    private int accountNumber;
    private String date;
    private String time;
    private int amount;
    private int balance;

    public LogEntry() {
        // Default constructor for json mapping
    }

    // Constructor
    public LogEntry(int accountNumber, String date, String time, int amount, int balance) {
        this.accountNumber = accountNumber;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.balance = balance;
    }

    // Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }

    // Setters
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
