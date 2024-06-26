package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the AveragePowerConsumptionValue value object
 */

class AveragePowerConsumptionValueTest {

    /**
     * Test to validate the reading of the average power consumption value
     * It should throw an exception if the reading is not a number
     */

    @Test
    void givenValueIsNotANumber_whenAveragePowerConsumptionValueConstructorCalled_thenThrowException() {
//        Arrange
        String reading = "Not a number";
        String expected = "Invalid reading";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new AveragePowerConsumptionValue(reading));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * Test to validate the reading of the average power consumption value
     * It should throw an exception if the reading is negative
     */

    @Test
    void givenNegativeValue_whenAveragePowerConsumptionValueConstructorCalled_thenThrowException() {
//        Arrange
        String reading = "-1";
        String expected = "Invalid reading";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new AveragePowerConsumptionValue(reading));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     * Test to validate the reading of the average power consumption value
     * It should return the value if the reading is valid
     */

    @Test
    void givenValidValue_whenAveragePowerConsumptionValueConstructorCalled_thenReturnSuccessfully() {
//        Arrange
        String reading = "100";
        AveragePowerConsumptionValue averagePowerConsumptionValue = new AveragePowerConsumptionValue(reading);
        int expected = 100;
//        Act
        int result = averagePowerConsumptionValue.getValue();
//        Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify the behavior of the `AveragePowerConsumptionValue` constructor
     * when a valid value is provided at the lower border (0). It ensures that the constructor
     * correctly creates an instance with the provided value, which should be 0.
     */
    @Test
    void givenValidValue_whenAveragePowerConsumptionValueConstructorCalled_thenReturnSuccessfully_BorderCaseLower() {
//        Arrange
        String reading = "0";
        AveragePowerConsumptionValue averagePowerConsumptionValue = new AveragePowerConsumptionValue(reading);
        int expected = 0;
//        Act
        int result = averagePowerConsumptionValue.getValue();
//        Assert
        assertEquals(expected, result);
    }
}
