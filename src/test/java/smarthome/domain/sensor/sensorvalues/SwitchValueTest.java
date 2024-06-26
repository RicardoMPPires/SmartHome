package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwitchValueTest {

    /**
     * Verifies if the constructor throws an IllegalArgumentException when provided with an invalid switch value.
     * <p>
     * This test checks if the constructor throws an IllegalArgumentException when provided with null, an empty string, or a value
     * other than "On" or "Off".
     * </p>
     */
    @Test
    void constructor_throwsInstantiationExceptionIfReadingInvalid() {
        //Arrange
        String reading2 = " ";
        String reading3 = "this will fail because it is not On or Off";
        String expected = "Invalid switch value";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new SwitchValue(null));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new SwitchValue(reading2));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new SwitchValue(reading3));
        String result3 = exception3.getMessage();

        //Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
    }

    /**
     * Verifies that the getValue method accurately returns the switch value provided during initialization.
     * <p>
     * This test ensures that the getValue method returns the correct switch value when given valid input.
     * </p>
     */
    @Test
    void whenGivenValidReading_getValueReturnsAccurately() {
        //Arrange
        String reading1 = "On";
        String reading2 = "Off";
        String expected1 = "On";
        String expected2 = "Off";

        //Act
        SwitchValue value1 = new SwitchValue(reading1);
        SwitchValue value2 = new SwitchValue(reading2);

        //Assert
        assertEquals(expected1, value1.getValue());
        assertEquals(expected2, value2.getValue());
    }
}