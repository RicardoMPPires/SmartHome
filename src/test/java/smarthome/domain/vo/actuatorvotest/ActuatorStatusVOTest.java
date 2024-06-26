package smarthome.domain.vo.actuatorvotest;

import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActuatorStatusVOTest {

    /**
     * Unit test for the ActuatorStatusVO constructor.
     * <p>
     * This test verifies that when an invalid argument (null or empty string) is provided
     * to the constructor of ActuatorStatusVO, it throws an IllegalArgumentException with the expected message.
     * </p>
     */
    @Test
    void whenGivenInvalidArgument_constructorThrowsIllegalArgumentException() {
        // Arrange
        String status = "";
        String expected = "Invalid parameter";
        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, ()
                -> new ActuatorStatusVO(null));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> new ActuatorStatusVO(status));
        String result2 = exception2.getMessage();

        // Assert
        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }

    /**
     * Tests the getValue method of the ActuatorStatusVO class.
     * <p>
     * This test verifies that when a valid argument is provided to the constructor
     * of ActuatorStatusVO, the getValue method returns the correct encapsulated value.
     * </p>
     */
    @Test
    void whenGivenValidArgument_getValueReturnsEncapsulatedValue() {
        // Arrange
        String expected = "It works!";
        ActuatorStatusVO status = new ActuatorStatusVO(expected);

        // Act
        String result = status.getValue();

        // Assert
        assertEquals(expected, result);
    }
}
