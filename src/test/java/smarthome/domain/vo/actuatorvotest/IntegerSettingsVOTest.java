package smarthome.domain.vo.actuatorvotest;

import smarthome.domain.vo.actuatorvo.IntegerSettingsVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerSettingsVOTest {

    /**
     * Tests the behavior of the IntegerSettingsVO constructor when provided with invalid limits.
     * <p>
     * This test verifies that an IllegalArgumentException is thrown when attempting to create an IntegerSettingsVO object
     * with invalid limits. Two different scenarios are tested:
     * <ul>
     *     <li>Lower limit greater than upper limit.</li>
     *     <li>Lower limit equal to upper limit.</li>
     * </ul>
     * For each scenario, the test checks that an IllegalArgumentException is thrown with the message
     * "Upper limit can't be less than or equal to lower limit."
     * </p>
     */
    @Test
    void ifLimitsAreInvalid_whenConstructorCalled_thenReturnsException(){
        //Arrange
        String lowerLimit1 = "10"; String upperLimit1 = "0"; // Lower Limit > Upper Limit
        String lowerLimit2 = "10"; String upperLimit2 = "10"; // Lower Limit = Upper Limit

        String expected = "Upper limit can't be less than or equal to lower limit.";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(lowerLimit1, upperLimit1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(lowerLimit2, upperLimit2));
        String result2 = exception2.getMessage();

        //Assert
        assertEquals(expected, result1);
        assertEquals(expected, result2);
    }

    /**
     * Test if the lower limit is null.
     * When the constructor is called with a null lower limit, an exception is thrown.
     */
    @Test
    void ifLowerLimitIsNull_whenConstructorCalled_thenReturnsIllegalArgumentException() {
        //Arrange
        String upperLimit = "10";
        String expected = "Invalid actuator settings";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(null, upperLimit));
        String result = exception.getMessage();
        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test if the upper limit is null.
     * When the constructor is called with a null upper limit, an exception is thrown.
     */
    @Test
    void ifUpperLimitNull_whenConstructorCalled_thenReturnsIllegalArgumentException() {
        //Arrange
        String lowerLimit = "10";
        String expected = "Invalid actuator settings";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(lowerLimit, null));
        String result = exception.getMessage();
        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test if both limits are null.
     * When the constructor is called with both limits null, an exception is thrown.
     */
    @Test
    void ifBothLimitsNull_whenConstructorCalled_thenReturnsIllegalArgumentException() {
        //Arrange
        String expected = "Invalid actuator settings";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(null, null));
        String result = exception.getMessage();
        //Assert
        assertEquals(expected, result);
    }

    /**
     * Tests the behavior of the IntegerSettingsVO constructor when provided with unparseable limits.
     * <p>
     * This test verifies that an IllegalArgumentException is thrown when attempting to create an IntegerSettingsVO object
     * with unparseable limits. Three different scenarios are tested:
     * <ul>
     *     <li>Lower limit is unparseable.</li>
     *     <li>Upper limit is unparseable.</li>
     *     <li>Both lower and upper limits are unparseable.</li>
     * </ul>
     * For each scenario, the test checks that an IllegalArgumentException is thrown with the message
     * "Invalid actuator settings."
     * </p>
     */
    @Test
    void ifUnparseableLimits_whenConstructorCalled_thenReturnsIllegalArgumentException() {
        //Arrange
        String lowerLimit1 = "teste"; String upperLimit1 = "10";
        String lowerLimit2 = "10"; String upperLimit2 = "teste";
        String lowerLimit3 = "teste"; String upperLimit3 = "outro teste";

        String expected = "Invalid actuator settings";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(lowerLimit1, upperLimit1));
        String result1 = exception1.getMessage();
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(lowerLimit2, upperLimit2));
        String result2 = exception2.getMessage();
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new IntegerSettingsVO(lowerLimit3, upperLimit3));
        String result3 = exception3.getMessage();

        //Assert
        assertEquals(expected, result1);
        assertEquals(expected, result2);
        assertEquals(expected, result3);
    }

    /**
     * Tests if the object is created if the parameters are valid.
     */
    @Test
    void whenParametersAreValid_thenObjectIsCreated(){
        //Arrange
        String lowerLimit1 = "0"; String upperLimit1 = "10";
        String lowerLimit2 = "-10"; String upperLimit2 = "10";

        //Act
        IntegerSettingsVO integerConfigurationVO1 = new IntegerSettingsVO(lowerLimit1, upperLimit1);

        IntegerSettingsVO integerConfigurationVO2 = new IntegerSettingsVO(lowerLimit2, upperLimit2);

        //Assert
        assertNotNull(integerConfigurationVO1);
        assertNotNull(integerConfigurationVO2);
    }

    /**
     * Tests if when the getValue method is called, the array is created and the lower limits are saved in index 0.
     */
    @Test
    void whenGetValueIsCalled_thenLowerLimitIsSavedInTheArrayFirstPosition() {
        //Arrange
        String lowerLimit = "0";
        String upperLimit = "10";
        IntegerSettingsVO integerConfigurationVO = new IntegerSettingsVO(lowerLimit,upperLimit);
        Integer[] array = integerConfigurationVO.getValue();
        int expected = 0;

        //Act
        int result = array[0];

        //Assert
        assertEquals(expected,result);
    }

    /**
     * Tests if when the getValue method is called, the array is created and the upper limits are saved in index 1.
     */
    @Test
    void whenGetValueIsCalled_thenUpperLimitIsSavedInTheArraySecondPosition() {
        //Arrange
        String lowerLimit = "0";
        String upperLimit = "10";
        IntegerSettingsVO integerConfigurationVO = new IntegerSettingsVO(lowerLimit,upperLimit);
        Integer[] array = integerConfigurationVO.getValue();
        int expected = 10;

        //Act
        int result = array[1];

        //Assert
        assertEquals(expected,result);
    }
}