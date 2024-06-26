package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindValueTest {

    /**
     * Tests the constructor of the WindValue class for handling invalid readings.
     * <p>
     * This test verifies that the constructor of the WindValue class throws an IllegalArgumentException
     * when provided with invalid readings. Invalid readings include those with incorrect formats or null values.
     * It tests various scenarios such as readings without direction, zero readings, readings with invalid formats,
     * and negative readings. Each scenario is tested individually by attempting to construct a WindValue object
     * with the invalid reading and checking if an IllegalArgumentException is thrown with the expected error message.
     * </p>
     */

    @Test
    void givenInvalidReading_ConstructorThrowsIllegalArgumentException() {
        // Arrange
        String expected = "Invalid reading";
        String reading1 = "22N";
        String reading2 = "0N";
        String reading3 = "3K:N";
        String reading4 = "33:J";
        String reading5 = "-58:J";
        String reading6 = "0:N";

        // Act + Assert
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new WindValue(reading1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new WindValue(reading2));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new WindValue(reading3));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new WindValue(reading4));
        String result4 = exception4.getMessage();

        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> new WindValue(reading5));
        String result5 = exception5.getMessage();

        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> new WindValue(null));
        String result6 = exception6.getMessage();

        Exception exception7 = assertThrows(IllegalArgumentException.class, () -> new WindValue(reading6));
        String result7 = exception7.getMessage();

        //Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
        assertEquals(expected,result4);
        assertEquals(expected,result5);
        assertEquals(expected,result6);
        assertEquals(expected,result7);
    }

    /**
     * Tests the {@code getValue} method of the WindValue class.
     * <p>
     * This test verifies that the {@code getValue} method of the WindValue class returns an array with reading values
     * correctly. It first constructs a WindValue object with a specific reading. Then, it calls the getValue method
     * and checks if the returned value matches the expected reading.
     * </p>
     */
    @Test
    void getValue_ShouldReturnArrayWithReadingValues() {
        // Arrange
        String reading = "58:N";
        WindValue windValue = new WindValue(reading);

        // Act
        String result = windValue.getValue();

        // Assert
        assertEquals(reading,result);
    }

}
