package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerConsumptionValueTest {

    /**
     * Tests the constructor of the PowerConsumptionValue class when the power consumption value is negative or invalid.
     *
     * <p>This test verifies that the constructor of the PowerConsumptionValue class throws an IllegalArgumentException
     * when attempting to create an instance with a negative or invalid power consumption value.</p>
     *
     * <p>The System Under Test (SUT) includes the constructor of the PowerConsumptionValue class.</p>
     */
    @Test
    void constructor_throwsIllegalArgumentExceptionIfPowerConsumptionValueIsNegative() {
        //Arrange
        String reading1 = "-1";
        String reading2 = "I will fail";
        String reading3 = " ";
        String expected = "Invalid power consumption value";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionValue(reading1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionValue(reading2));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionValue(reading3));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionValue(null));
        String result4 = exception4.getMessage();

        //Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
        assertEquals(expected,result4);
    }

    /**
     * Tests the behavior of the getValue method when the power consumption value is positive.
     *
     * <p>This test verifies that the getValue method of the PowerConsumptionValue class returns the expected value
     * when the power consumption reading is positive.</p>
     *
     * <p>The System Under Test (SUT) includes the PowerConsumptionValue class and its getValue method.</p>
     */
    @Test
    void getValue_returnsSuccessfullyWhenPowerConsumptionValueIsPositive() {
        //Arrange
        String reading1 = "0";
        String reading2 = "1";
        int expected1 = 0;
        int expected2 = 1;
        PowerConsumptionValue value1 = new PowerConsumptionValue(reading1);
        PowerConsumptionValue value2 = new PowerConsumptionValue(reading2);

        //Act
        int result1 = value1.getValue();
        int result2 = value2.getValue();

        //Assert
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }
}