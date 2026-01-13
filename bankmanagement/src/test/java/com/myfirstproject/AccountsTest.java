package com.myfirstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountsTest {

    @Test
    void testAccountDetails() {
        // Just checking if my constructor works
        Accounts myAcc = new Accounts("Adil", "1234567890", "1001", "1234", 100);

        assertEquals("Adil", myAcc.getName());
        assertEquals("1234567890", myAcc.getMobileNumber());
        assertEquals(100, myAcc.getBalance());
    }

    @Test
    void testSetters() {
        // Checking if I can update name and balance
        Accounts acc = new Accounts();
        acc.setName("Doka");
        acc.setBalance(500);

        assertEquals("Doka", acc.getName());
        assertEquals(500, acc.getBalance());
    }
}