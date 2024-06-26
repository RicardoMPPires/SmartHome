package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SunTimeValueTest {

    /**
     * Given a null ZonedDateTime object, the constructor throws IllegalArgumentException
     * This test utilized the .now() method to identify the time test is taking place. Which invalidates a potential test
     * that used toString();
     */
    @Test
    void givenNullZonedDateTime_ConstructorThrowsIllegalArgument(){
        // Arrange
        String expected = "Invalid sun time value";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SunTimeValue(null));
        String result = exception.getMessage();
        // Assert
        assertEquals(expected,result);
    }

    /**
     * Given correct zoned date time object, the constructor is able to create a SunTimeValue object,
     * which, when called with getValue, returns the same zonedDateTime object.
     * This test utilized the .now() method to identify the time test is taking place. Which invalidates a potential test
     * that used toString();
     */
    @Test
    void givenCorrectZonedDateTime_getValueReturnsTheObject(){
        // Arrange
        ZonedDateTime expected = ZonedDateTime.now();
        SunTimeValue sunTimeValue = new SunTimeValue(expected);
        // Act
        ZonedDateTime result = sunTimeValue.getValue();
        // Assert
        assertEquals(expected,result);
    }
}
