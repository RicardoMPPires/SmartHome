package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumidityValueTest {

    /**
     * Test case to verify that HumidityValue throws an IllegalArgumentException when provided with invalid readings.
     *
     * <p>
     * This test verifies that the HumidityValue class's constructor throws an IllegalArgumentException when provided with invalid readings. Invalid readings include null, empty string, non-numeric strings, and values outside the valid range of 0 to 100.
     * </p>
     *
     * <p>
     * The test arranges the scenario by defining invalid readings, including null, empty string, a non-numeric string, and values outside the valid range.
     * </p>
     *
     * <p>
     * The test then acts by invoking the HumidityValue constructor with each invalid reading and asserts that an IllegalArgumentException is thrown with the expected error message for each case.
     * </p>
     */
    @Test
    void whenReadingIsNull_throwsIllegalArgumentException(){
        //Arrange
        String expected = "Invalid reading";
        String reading1 = " ";
        String reading2 = "this will fail";
        String reading3 = "101";
        String reading4 = "-1";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new HumidityValue(null));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new HumidityValue(reading1));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new HumidityValue(reading2));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new HumidityValue(reading3));
        String result4 = exception4.getMessage();

        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> new HumidityValue(reading4));
        String result5 = exception5.getMessage();

        //Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
        assertEquals(expected,result4);
        assertEquals(expected,result5);
    }


    /**
     * Test case to verify that HumidityValue returns the correct value when provided with readings at the lower and upper bounds.
     *
     * <p>
     * This test verifies the behavior of the HumidityValue class's constructor when provided with readings at the lower and upper bounds of the valid range (0 to 100). It expects that invoking the constructor with readings of "0" and "100" will result in HumidityValue objects being created with values of 0 and 100, respectively.
     * </p>
     *
     * <p>
     * The test sets up the scenario by providing readings at the lower and upper bounds of the valid range and creates HumidityValue objects with these readings.
     * </p>
     *
     * <p>
     * The test then verifies that the created HumidityValue objects return the expected values using assertions.
     * </p>
     */
    @Test
    void whenReadingOnLowerAndUpperBound_thenValueIsReturned() {
        //Arrange
        String reading1 = "0";
        String reading2 = "100";
        int expected1 = 0;
        int expected2 = 100;

        //Act
        HumidityValue value1 = new HumidityValue(reading1);
        HumidityValue value2 = new HumidityValue(reading2);

        //Assert
        assertEquals(expected1,value1.getValue());
        assertEquals(expected2,value2.getValue());
    }
}