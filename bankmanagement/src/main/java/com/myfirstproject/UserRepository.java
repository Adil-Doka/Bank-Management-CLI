package com.myfirstproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository {

    private final Connection connection;
    private PreparedStatement statementNewUser;
    private PreparedStatement statementGetUserData;
    private PreparedStatement statementUpdateBalance;
    private PreparedStatement statementGetLogs;

    private static final String QUERY_NEW_USER = "INSERT INTO user_data(name,mobile_number,pin) VALUES(?, ?, ?)";
    private static final String QUERY_GET_USER_DATA = "SELECT * FROM user_data WHERE account_number = ?";
    private static final String QUERY_UPDATE_BALANCE = "UPDATE user_data SET balance = ? where account_number = ? ";
    private static final String QUERY_GET_LOGS = "SELECT * FROM account_logs WHERE account_number = ?";

    public UserRepository() {
        this(DatabaseConnection.getConnection());
    }

    public UserRepository(Connection connection) {
        if (connection == null) {
            throw new RuntimeException("Database connection cannot be null");
        }
        this.connection = connection;
        initializeStatements();
    }

    private void initializeStatements() {
        if (connection == null)
            return;
        try {
            this.statementNewUser = connection.prepareStatement(QUERY_NEW_USER, Statement.RETURN_GENERATED_KEYS);
            this.statementGetUserData = connection.prepareStatement(QUERY_GET_USER_DATA);
            this.statementUpdateBalance = connection.prepareStatement(QUERY_UPDATE_BALANCE);
            this.statementGetLogs = connection.prepareStatement(QUERY_GET_LOGS);
        } catch (SQLException e) {
            System.out.println("Error initializing User statements: " + e.getMessage());
        }
    }

    public String newUser(String name, String mobileNumber, String pin) {
        int accountNumber = 0;
        try {
            statementNewUser.setString(1, name);
            statementNewUser.setString(2, mobileNumber);
            statementNewUser.setString(3, pin);

            int rowAffected = statementNewUser.executeUpdate();
            if (rowAffected > 0) {
                try (ResultSet rs = statementNewUser.getGeneratedKeys()) {
                    if (rs.next()) {
                        accountNumber = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating new user: " + e.getMessage());
        }
        return Integer.toString(accountNumber);
    }

    public Accounts getAccount(String acc) {
        try {
            statementGetUserData.setInt(1, Integer.parseInt(acc));
            try (ResultSet result = statementGetUserData.executeQuery()) {
                if (result.next()) {
                    return new Accounts(
                            result.getString("name"),
                            result.getString("mobile_number"),
                            Integer.toString(result.getInt("account_number")),
                            result.getString("pin"),
                            result.getInt("balance"));
                } else {
                    System.out.println("Account not found");
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error fetching user account: " + e.getMessage());
        }
        return null;
    }

    public void updateBalance(String accno, double balance) {
        try {
            statementUpdateBalance.setDouble(1, balance);
            statementUpdateBalance.setInt(2, Integer.parseInt(accno));

            int rowAffected = statementUpdateBalance.executeUpdate();
            if (rowAffected <= 0) {
                System.out.println("Something went wrong updating balance");
            }
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

    public void getLogs(java.util.ArrayList<String> logs, String accno) {
        try {
            statementGetLogs.setInt(1, Integer.parseInt(accno));
            try (ResultSet result = statementGetLogs.executeQuery()) {
                while (result.next()) {
                    logs.add(
                            result.getTimestamp("date_and_time") + "      " +
                                    result.getInt("amount"));
                }
            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Error fetching logs: " + e.getMessage());
        }
    }
}
