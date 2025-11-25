package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class JDBCconectivity {

    private static final String url = "jdbc:mysql://localhost:3306/Bank_Management";
    private static final String userName = "root";
    private static final String password = "Root@123";

    private Connection connection;

    String query_NewUser = """
            INSERT INTO User_Data(name,mobileNumber,pin)
            VALUES(?, ?, ?)
            """;
    String query_getUserData = """
            SELECT * FROM User_Data WHERE account_number = ?
            """;
    String query_UpdateBalance = "UPDATE User_Data SET balance = ? where account_number = ? ";
    String query_getLogs = """
            SELECT * FROM Account_Logs WHERE account_number = ?
            """;

    PreparedStatement statementNewUser;
    PreparedStatement statementGetUserData;
    PreparedStatement query_updateBalance;
    Statement getuseracc;
    PreparedStatement stmt_getLogs;

    JDBCconectivity() {
        // getting the connnection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("Connected Succesfully");

        // preparing statement
        try {
            this.statementNewUser = connection.prepareStatement(query_NewUser, Statement.RETURN_GENERATED_KEYS);
            // give primar key after insertion
            this.statementGetUserData = connection.prepareStatement(query_getUserData);
            query_updateBalance = connection.prepareStatement(query_UpdateBalance);
            stmt_getLogs = connection.prepareStatement(query_getLogs);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    String newUser(String name, String mobileNumber, String pin) {
        int account_number = 0;
        try {
            statementNewUser.setString(1, name);
            statementNewUser.setString(2, mobileNumber);
            statementNewUser.setString(3, pin);

            int rowAffected = statementNewUser.executeUpdate();
            if (rowAffected > 0) {
                ResultSet rs = statementNewUser.getGeneratedKeys();
                if (rs.next())
                    account_number = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return Integer.toString(account_number);
    }

    Accounts getAccount(String acc) {

        try {
            statementGetUserData.setInt(1, Integer.parseInt(acc));
            ResultSet result = statementGetUserData.executeQuery();

            if (result.next()) {
                return new Accounts(
                        (result.getString("name")),
                        (result.getString("mobileNumber")),
                        (Integer.toString(result.getInt("account_number"))),
                        (result.getString("pin")),
                        (result.getInt("balance")));
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;

    }

    void getAllAccountno(ArrayList<String> accno) {

        String query = "SELECT account_number FROM User_Data";

        try {
            getuseracc = connection.createStatement();
            ResultSet result = getuseracc.executeQuery(query);
            while (result.next()) {
                accno.add(Integer.toString(result.getInt("account_number")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    void updateBalance(String accno, int balance) {
        try {

            query_updateBalance.setInt(1, balance);
            query_updateBalance.setInt(2, Integer.parseInt(accno));

            int rowAffected = query_updateBalance.executeUpdate();
            if (rowAffected <= 0) {
                System.out.println("Something went wrong");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    void getLogs(ArrayList<String> logs, String accno) {

        try {
            stmt_getLogs.setInt(1, Integer.parseInt(accno));

            ResultSet result = stmt_getLogs.executeQuery();
            while (result.next()) {
                logs.add(
                        String.valueOf(result.getTimestamp("DateAndTime")) + "      "
                                + (Integer.toString(result.getInt("amount")))

                );
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
