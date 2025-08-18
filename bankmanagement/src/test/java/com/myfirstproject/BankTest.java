package com.myfirstproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class BankTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        outContent.reset();
    }

    private Bank prepareBankWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        return new Bank(new Scanner(in));
    }

    @Test
    void testCreateAccount() throws InValidInputException {
        Bank bank = prepareBankWithInput("JohnDoe\n1234567890\n1234\n");

        HashMap<String, Accounts> accountList = new HashMap<>();
        int initialSize = accountList.size();

        accountList.put("1000",
                new Accounts(bank.handle.CheckName(),
                        bank.handle.checkNumber(),
                        "1000",
                        bank.handle.checkPin()));

        assertEquals(initialSize + 1, accountList.size());
        assertTrue(accountList.containsKey("1000"));
        assertEquals("JohnDoe", accountList.get("1000").getName());
    }

    @Test
    void testCheckBalanceValidPin() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        Bank bank = prepareBankWithInput("1234\n");

        bank.checkBalance(account);

        assertTrue(outContent.toString().contains("Your Balance is: 0"));
    }

    @Test
    void testCheckBalanceInvalidPin() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        Bank bank = prepareBankWithInput("1111\n2222\n3333\n");

        bank.checkBalance(account);

        assertTrue(outContent.toString().contains("Invalid Pin entered too many times"));
    }

    @Test
    void testDepositValidPinAndAmount() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        Bank bank = prepareBankWithInput("1234\n500\n");

        bank.Deposit(account);

        assertEquals(500, account.getBalance());
        assertTrue(outContent.toString().contains("Amount Deposit Successfully"));
        assertTrue(outContent.toString().contains("Your Updated Balance is: 500"));
    }

    @Test
    void testDepositInvalidPin() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        Bank bank = prepareBankWithInput("1111\n2222\n3333\n");

        bank.Deposit(account);

        assertEquals(0, account.getBalance());
        assertTrue(outContent.toString().contains("Wrong Otp Entered Too many times"));
    }

    @Test
    void testDepositInvalidAmount() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        Bank bank = prepareBankWithInput("1234\nabc\n0\n");
        bank.Deposit(account);

        assertEquals(0, account.getBalance());
        assertTrue(outContent.toString().contains("Please Enter a Valid Amount"));
    }

    @Test
    void testWithdrawValidPinAndAmount() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        account.setBalance(1000);
        Bank bank = prepareBankWithInput("1234\n500\n");
        bank.withDraw(account);

        assertEquals(500, account.getBalance());
        assertTrue(outContent.toString().contains("Amount Withdrawn Successfully"));
        assertTrue(outContent.toString().contains("Your Remaining Balance is: 500"));
    }

    @Test
    void testWithdrawInsufficientBalance() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        account.setBalance(100);
        Bank bank = prepareBankWithInput("1234\n500\n");
        bank.withDraw(account);

        assertEquals(100, account.getBalance());
        assertTrue(outContent.toString().contains("Insufficient Balance"));
    }

    @Test
    void testWithdrawInvalidPin() {
        Accounts account = new Accounts("John Doe", "1234567890", "1001", "1234");
        account.setBalance(500);
        Bank bank = prepareBankWithInput("1111\n2222\n3333\n");
        bank.withDraw(account);

        assertEquals(500, account.getBalance());
        assertTrue(outContent.toString().contains("Wrong Otp Entered Too many times"));
    }
}
