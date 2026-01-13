package com.myfirstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InValidInputExceptionTest {

    @Test
    void testExceptionMessage() {
        // Checking if my custom exception stores the message
        String msg = "Something is wrong!";
        InValidInputException ex = new InValidInputException(msg);

        assertEquals(msg, ex.getMessage());
    }
}