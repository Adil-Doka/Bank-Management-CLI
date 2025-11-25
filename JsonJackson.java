public package com.myfirstproject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JsonJackson {

    private static final String ACCOUNT_DATA_PATH = "bankmanagement/Json_Files/AccountData.jsonl";
    private static final String LOGS_DATA_PATH = "bankmanagement/Json_Files/AccountLogs.jsonl";

    private final File accountData = new File(ACCOUNT_DATA_PATH);
    private final File accountLogs = new File(LOGS_DATA_PATH);

    private final List<Accounts> account;
    private final ObjectMapper mapper = new ObjectMapper();

    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

    public JsonJackson(List<Accounts> account) {
        this.account = account;
        File dir = new File("bankmanagement/Json_Files");
        dir.mkdirs();

        if (!accountData.exists()) {
            try {
                System.out.println("No Data Found\nCreating New File");
                accountData.createNewFile();
            } catch (IOException e) {
                System.out.println("Error Creating File: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(accountData))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Accounts acc = mapper.readValue(line, Accounts.class);
                account.add(acc);
            }
        } catch (IOException e) {
            System.out.println("Error Reading File: " + e.getMessage());
        }
    }

    public void writeNewAccountData(Accounts account) {
        try (FileWriter writer = new FileWriter(accountData, true)) {
            writer.write(mapper.writeValueAsString(account));
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error Writing Account: " + e.getMessage());
        }
    }

    public void syncAccountDataToJson() {
        accountData.delete();
        try (FileWriter writer = new FileWriter(accountData, true)) {
            for (Accounts acc : account) {
                writer.write(mapper.writeValueAsString(acc));
                writer.write("\n");
            }
            System.out.println("Data Synced to Json File");
        } catch (IOException e) {
            System.out.println("Error Syncing Data: " + e.getMessage());
        }
    }

    public void writeLogs(int amount, int accountNo) {
        try {
            if (!accountLogs.exists()) accountLogs.createNewFile();

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            LogEntry log = new LogEntry();
            log.setAccountNumber(accountNo);
            log.setDate(date.format(formatDate));
            log.setTime(time.format(formatTime));
            log.setAmount(amount);
            log.setBalance(account.get(accountNo - 1000).getBalance());

            try (FileWriter writer = new FileWriter(accountLogs, true)) {
                writer.write(mapper.writeValueAsString(log));
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error Writing Logs: " + e.getMessage());
        }
    }

    public void printLogs(int accountNo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountLogs))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry log = mapper.readValue(line, LogEntry.class);
                if (log.getAccountNumber() == accountNo) {
                    System.out.printf("\nDate: %s\nTime: %s\nAmount: %d\nBalance: %d\n",
                            log.getDate(), log.getTime(), log.getAmount(), log.getBalance());
                }
            }
        } catch (IOException e) {
            System.out.println("Error Reading Logs: " + e.getMessage());
        }
    }
}

class LogEntry {
    @JsonProperty("accountNumber")
    private int accountNumber;
    @JsonProperty("date")
    private String date;
    @JsonProperty("time")
    private String time;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("balance")
    private int balance;

    public int getAccountNumber() { return accountNumber; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getAmount() { return amount; }
    public int getBalance() { return balance; }

    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setAmount(int amount) { this.amount = amount; }
    public void setBalance(int balance) { this.balance = balance; }
} 
