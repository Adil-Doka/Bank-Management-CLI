package com.myfirstproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        // setting up the mock behavior for preparing statements
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        
        userRepository = new UserRepository(mockConnection);
    }

    @Test
    void testNewUser() throws SQLException {
        // simulating a new user creation
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1001); // fake account number

        String accNo = userRepository.newUser("Adil", "1234567890", "1234");
        
        assertEquals("1001", accNo);
        verify(mockPreparedStatement).setString(1, "Adil");
    }

    @Test
    void testGetAccount() throws SQLException {
        // simulating fetching an account
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        
        // setting up fake data from DB
        when(mockResultSet.getString("name")).thenReturn("Doka");
        when(mockResultSet.getString("mobile_number")).thenReturn("0987654321");
        when(mockResultSet.getInt("account_number")).thenReturn(2002);
        when(mockResultSet.getString("pin")).thenReturn("4321");
        when(mockResultSet.getInt("balance")).thenReturn(5000);

        Accounts acc = userRepository.getAccount("2002");

        assertNotNull(acc);
        assertEquals("Doka", acc.getName());
        assertEquals(5000, acc.getBalance());
    }

    @Test
    void testUpdateBalance() throws SQLException {
        // testing balance update
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        userRepository.updateBalance("1001", 1000.0);

        verify(mockPreparedStatement).setDouble(1, 1000.0);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetLogs() throws SQLException {
        java.util.ArrayList<String> logs = new java.util.ArrayList<>();

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, false); // return true once, then false

        // mocking the timestamp and amount
        java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
        when(mockResultSet.getTimestamp("date_and_time")).thenReturn(now);
        when(mockResultSet.getInt("amount")).thenReturn(500);

        userRepository.getLogs(logs, "101");

        // making sure logs were added
        assert (!logs.isEmpty());
        // verify we actually queried the DB
        verify(mockPreparedStatement).setInt(1, 101);
    }

    @Test
    void testNewUser_Failure() throws SQLException {
        // simulate DB error
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("DB Error"));

        String accNo = userRepository.newUser("Adil", "1234567890", "1234");
        
        assertEquals("0", accNo, "Should return 0 on failure");
    }

    @Test
    void testGetAccount_NotFound() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No results

        Accounts acc = userRepository.getAccount("9999");

        assertEquals(null, acc, "Should return null if account not found");
    }

    @Test
    void testGetAccount_InvalidFormat() {
        // trying to pass a non-integer string
        Accounts acc = userRepository.getAccount("invalid-id");
        assertEquals(null, acc, "Should handle NumberFormatException gracefully");
    }

    @Test
    void testUpdateBalance_Failure() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows updated

        // This method is void and prints to console, so we just verify it doesn't throw exception
        userRepository.updateBalance("1001", 1000.0);
        
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testGetLogs_Empty() throws SQLException {
        java.util.ArrayList<String> logs = new java.util.ArrayList<>();
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false); // No logs

        userRepository.getLogs(logs, "101");

        assert (logs.isEmpty());
    }

    @Test
    void testGetLogs_DbError() throws SQLException {
        java.util.ArrayList<String> logs = new java.util.ArrayList<>();
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Connection failed"));

        userRepository.getLogs(logs, "101");

        assert (logs.isEmpty());
    }
}
