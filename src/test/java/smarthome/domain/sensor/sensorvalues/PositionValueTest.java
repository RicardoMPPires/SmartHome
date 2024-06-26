package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PositionValueTest {

    /**
     * Verifies that the PositionValue constructor throws an IllegalArgumentException if the reading is below 0 or above
     * 100.
     *
     * <p>
     * This test method ensures that the PositionValue constructor throws an IllegalArgumentException if the provided
     * reading is below 0 or above 100. It covers both scenarios by passing readings of -1 and 101, respectively, to the
     * constructor and verifies that the expected exception message is thrown.
     * </p>
     */
    @Test
    void constructor_throwsInstantiationExceptionIfReadingIsBelow0() {
        //Arrange
        String reading1 = "-1";
        String reading2 = "101";
        String expected = "Invalid Position Value, has to be between 0 and 100";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new PositionValue(reading1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new PositionValue(reading2));
        String result2 = exception2.getMessage();

        //Assert
        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }

    /**
     * Verifies that the getValue method returns successfully when the reading is within the range of 0 and 100.
     *
     * <p>
     * This test method ensures that the getValue method of the PositionValue class returns the expected value when the
     * reading is within the range of 0 and 100. It creates PositionValue instances with readings of 20, 0, and 100 and
     * verifies that the getValue method returns the expected values of 20, 0, and 100, respectively. 0 and 100 being
     * border cases.
     * </p>
     */
    @Test
    void getValue_returnsSuccessfullyWhenReadingIsWithin0And100() {
        //Arrange
        String reading1 = "20";
        String reading2 = "0";
        String reading3 = "100";
        int expected1 = 20;
        int expected2 = 0;
        int expected3 = 100;
        //Act
        PositionValue value1 = new PositionValue(reading1);
        PositionValue value2 = new PositionValue(reading2);
        PositionValue value3 = new PositionValue(reading3);

        //Assert
        assertEquals(expected1, value1.getValue());
        assertEquals(expected2, value2.getValue());
        assertEquals(expected3, value3.getValue());
    }

    /**
     * Verifies that the PositionValue constructor throws an IllegalArgumentException when given a non-integer input.
     * This test ensures that inputs which cannot be parsed into an integer (such as alphabetic strings) trigger the
     * appropriate exception. This is crucial for maintaining the integrity of the PositionValue class, which
     * expects an integer value within a specified range (0 to 100). The test passes a string that is clearly not
     * an integer and checks if the IllegalArgumentException is thrown with the expected message.
     *
     * @throws IllegalArgumentException If the input cannot be parsed to an integer, validating that the class
     * correctly handles invalid types of inputs not just out-of-range numbers.
     */
    @Test
    void constructor_throwsIllegalArgumentExceptionForNonIntegerInput() {
        // Arrange
        String nonIntegerInput = "abc";
        String expected = "Invalid Position Value, has to be between 0 and 100";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new PositionValue(nonIntegerInput));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }
}
