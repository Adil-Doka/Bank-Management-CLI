package com.myfirstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InValidInputExceptionTest {

    @Test
    void testInValidInputExceptionMessage() {
        // Arrange
        String message = "Test Exception Message";
        
        // Act
        InValidInputException exception = new InValidInputException(message);
        
        // Assert
        assertEquals(message, exception.getMessage(), "Exception message should match");
        assertEquals(message, exception.toString(), "toString should return message");
    }
}