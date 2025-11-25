// package com.myfirstproject;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class AccountsTest {

//     @Test
//     void testAccountsConstructorAndGetters() {
//         // Arrange
//         String name = "John Doe";
//         String mobileNumber = "1234567890";
//         String accountNumber = "1001";
//         String pin = "1234";

//         // Act
//         Accounts account = new Accounts(name, mobileNumber, accountNumber, pin);

//         // Assert
//         assertEquals(name, account.getName(), "Name should match constructor input");
//         assertEquals(mobileNumber, account.getMobileNumber(), "Mobile number should match constructor input");
//         assertEquals(accountNumber, account.getAccountNumber(), "Account number should match constructor input");
//         assertEquals(pin, account.getPin(), "PIN should match constructor input");
//         assertEquals(0, account.getBalance(), "Initial balance should be 0");
//     }

//     @Test
//     void testSetters() {
//         // Arrange
//         Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
//         String newName = "Jane Doe";
//         String newMobile = "0987654321";
//         String newAccountNumber = "1002";
//         String newPin = "5678";
//         int newBalance = 1000;

//         // Act
//         account.setName(newName);
//         account.setMobileNumber(newMobile);
//         account.setAccountNumber(newAccountNumber);
//         account.setPin(newPin);
//         account.setBalance(newBalance);

//         // Assert
//         assertEquals(newName, account.getName(), "Name should be updated");
//         assertEquals(newMobile, account.getMobileNumber(), "Mobile number should be updated");
//         assertEquals(newAccountNumber, account.getAccountNumber(), "Account number should be updated");
//         assertEquals(newPin, account.getPin(), "PIN should be updated");
//         assertEquals(newBalance, account.getBalance(), "Balance should be updated");
//     }

//     @Test
//     void testToString() {
//         // Arrange
//         Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");

//         // Act
//         String result = account.toString();

//         // Assert
//         assertTrue(result.contains("John Doe"), "toString should contain name");
//         assertTrue(result.contains("1234567890"), "toString should contain mobile number");
//         assertTrue(result.contains("1001"), "toString should contain account number");
//         assertTrue(result.contains("1234"), "toString should contain PIN");
//         assertTrue(result.contains("0"), "toString should contain balance");
//     }
// }