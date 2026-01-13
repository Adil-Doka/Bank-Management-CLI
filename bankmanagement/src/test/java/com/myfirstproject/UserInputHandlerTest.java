package com.myfirstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class UserInputHandlerTest {

    @Test
    void testValidName() throws InValidInputException {
        // Checking if it accepts a normal name
        String data = "Adil\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Scanner sc = new Scanner(System.in);
        UserInputHandler handler = new UserInputHandler(sc);

        assertEquals("Adil", handler.CheckName());
    }

    @Test
    void testIsInteger() {
        UserInputHandler handler = new UserInputHandler();
        // Should be true for numbers
        assertTrue(handler.isInteger("1234"));
        // Should be false for letters
        assertFalse(handler.isInteger("abcd"));
    }
}
