package com.myfirstproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UserInputHandlerTest {

    private UserInputHandler handler;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out; // Save the original System.out
        System.setOut(new PrintStream(outContent)); // Capture System.out
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // Restore System.out
        outContent.reset(); // Clear captured output
    }

    @Test
    void testCheckNameValid() throws InValidInputException {

        String input = "JohnDoe\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        String result = handler.CheckName();

        assertEquals("JohnDoe", result, "Valid name should be returned");

    }

    @Test
    void testCheckNameInvalidThrowsException() {

        String input = "John123\nJohn@Doe\n123\nnot012\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        InValidInputException exception = assertThrows(
                InValidInputException.class,
                () -> handler.CheckName(), "Should throw exception after 3 invalid name attempts");
        assertEquals("Invalid Name Entered Too Many Times", exception.getMessage());
    }

    @Test
    void testCheckNumberValid() throws InValidInputException {
        String input = "1234567890\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        String result = handler.checkNumber();

        assertEquals("1234567890", result, "Valid mobile number should be returned");
    }

    @Test
    void testCheckNumberInvalidThrowsException() {

        String input = "abc\n123\n123456\n00\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        InValidInputException exception = assertThrows(
                InValidInputException.class,
                () -> handler.checkNumber(),
                "Should throw exception after 3 invalid mobile number attempts");
        assertEquals("Invalid Mobile Number Entered Too Many Times", exception.getMessage());
    }

    @Test
    void testCheckPinValid() throws InValidInputException {
        String input = "1234\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        String result = handler.checkPin();

        assertEquals("1234", result, "Valid PIN should be returned");
    }

    @Test
    void testCheckPinInvalidThrowsException() {
        String input = "abc\n1q2\n123\nas12\n"; // 3 bad + 1 extra
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        InValidInputException exception = assertThrows(
                InValidInputException.class,
                () -> handler.checkPin(),
                "Should throw exception after 3 invalid PIN attempts");
        assertEquals("Invalid pin entered too many times", exception.getMessage());
    }

    @Test
    void testValidatePinValid() {
        String input = "1234\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        boolean result = handler.validatePin("1234");

        assertTrue(result, "Valid PIN should return true");
    }

    @Test
    void testValidatePinInvalid() {
        String input = "1111\n2222\n3333\n"; // 3 attempts
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        handler = new UserInputHandler(new Scanner(in));

        boolean result = handler.validatePin("1234");

        assertFalse(result, "Invalid PIN after 3 attempts should return false");
        String output = outContent.toString();
        assertTrue(output.contains("Invalid Pin entered too many times"),
                "Should display final invalid PIN message");
        assertTrue(output.contains("2 attempts left") ||
                output.contains("1 attempts left") ||
                output.contains("0 attempts left"),
                "Should display attempt countdown");
    }

    @Test
    void testIsInteger() {
        handler = new UserInputHandler();

        assertTrue(handler.isInteger("123"), "Should return true for numeric string");
        assertFalse(handler.isInteger("abc"), "Should return false for non-numeric string");
        assertFalse(handler.isInteger("12a3"), "Should return false for mixed string");
    }

    @Test
    void testIsOnlyAlphabets() {
        handler = new UserInputHandler();

        assertTrue(handler.isOnlyAlphabets("JohnDoe"), "Should return true for alphabetic string");
        assertFalse(handler.isOnlyAlphabets("John123"), "Should return false for alphanumeric string");
        assertFalse(handler.isOnlyAlphabets("123"), "Should return false for numeric string");
    }
}
