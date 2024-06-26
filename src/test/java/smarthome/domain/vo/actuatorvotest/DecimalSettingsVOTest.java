package smarthome.domain.vo.actuatorvotest;

import smarthome.domain.vo.actuatorvo.DecimalSettingsVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecimalSettingsVOTest {
    /**
     * Verifies that a DecimalSettingsVO cannot be created with a null lower limit.
     */
    @Test
    void givenNullLowerLimit_WhenVOIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        String upperLimit = "7.8";
        String precision = "0.1";
        String expected = "Invalid actuator settings";
        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(null, upperLimit, precision));
        String result = exception.getMessage();
        //Arrange
        assertEquals(expected, result);
    }

    /**
     * Verifies that a DecimalSettingsVO cannot be created with a null upper limit.
     */
    @Test
    void givenNullUpperLimit_WhenVOIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        String lowerLimit = "6.5";
        String precision = "0.1";
        String expected = "Invalid actuator settings";
        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit, null, precision));
        String result = exception.getMessage();
        //Arrange
        assertEquals(expected, result);
    }

    /**
     * Verifies that a DecimalSettingsVO cannot be created with a null precision.
     */
    @Test
    void givenNullPrecision_WhenVOIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        String lowerLimit = "6.5";
        String upperLimit = "7.8";
        String expected = "Invalid actuator settings";
        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit, upperLimit, null));
        String result = exception.getMessage();
        //Arrange
        assertEquals(expected, result);
    }

    /**
     * Tests the behavior of the DecimalSettingsVO class when instantiated with invalid settings.
     * <p>
     * This test verifies that an IllegalArgumentException is thrown when attempting to instantiate a DecimalSettingsVO
     * object with invalid settings. Eight different scenarios are tested, each representing an invalid combination of
     * lower limit, upper limit, and precision:
     * <ul>
     *     <li>Lower limit greater than upper limit.</li>
     *     <li>Lower limit equal to upper limit.</li>
     *     <li>Different limits with precision.</li>
     *     <li>Precision set to 0.</li>
     *     <li>Precision set to a negative value.</li>
     *     <li>Precision set to a value greater than 1.</li>
     *     <li>Precision set to 1.</li>
     *     <li>Difference between limits greater than precision.</li>
     * </ul>
     * For each scenario, the test checks that an IllegalArgumentException is thrown with the message "Invalid actuator
     * settings."
     * </p>
     */
    @Test
    void givenGivenInvalidSettings_WhenVOIsInstantiated_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        String lowerLimit1 = "8.5"; String upperLimit1 = "7.8"; String precision1 = "0.1"; // Lower Limit > Upper Limit
        String lowerLimit2 = "8.5"; String upperLimit2 = "8.5"; String precision2 = "0.1"; // Lower Limit = Upper Limit
        String lowerLimit3 = "6.55"; String upperLimit3 = "7.8"; String precision3 = "0.1"; // Limits != precisions
        String lowerLimit4 = "6.55"; String upperLimit4 = "7.83"; String precision4 = "0"; // Precision = 0
        String lowerLimit5 = "6.55"; String upperLimit5 = "7.83"; String precision5 = "-0.02"; // Precision < 0
        String lowerLimit6 = "6.55"; String upperLimit6 = "7.83"; String precision6 = "1.54"; // Precision > 1
        String lowerLimit7 = "6.55"; String upperLimit7 = "7.83"; String precision7 = "1"; // Precision = 1
        String lowerLimit8 = "6.555"; String upperLimit8 = "7.835"; String precision8 = "0.01"; // Dif Limits > precision

        String expected = "Invalid actuator settings";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit1, upperLimit1, precision1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit2, upperLimit2, precision2));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit3, upperLimit3, precision3));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit4, upperLimit4, precision4));
        String result4 = exception4.getMessage();

        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit5, upperLimit5, precision5));
        String result5 = exception5.getMessage();

        Exception exception6 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit6, upperLimit6, precision6));
        String result6 = exception6.getMessage();

        Exception exception7 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit7, upperLimit7, precision7));
        String result7 = exception7.getMessage();

        Exception exception8 = assertThrows(IllegalArgumentException.class, () -> new DecimalSettingsVO(lowerLimit8, upperLimit8, precision8));
        String result8 = exception8.getMessage();

        //Arrange
        assertEquals(expected, result1); // Lower Limit > Upper Limit
        assertEquals(expected, result2); // Lower Limit = Upper Limit
        assertEquals(expected, result3); // Limits != precisions
        assertEquals(expected, result4); // Precision = 0
        assertEquals(expected, result5); // Precision < 0
        assertEquals(expected, result6); // Precision > 1
        assertEquals(expected, result7); // Precision = 1
        assertEquals(expected, result8); // Dif Limits > precision
    }

    /**
     * Verifies that is possible to create a DecimalSettingsVO where the precision of the set lower and upper limits
     * is lower than the defined precision.
     */
    @Test
    void givenLimitsWithLowerDefinedPrecisionThanActualPrecision_VOIsCreated_WhenGetValue_ThenShouldReturnSettingsArray(){
        //Arrange
        String lowerLimit = "6.5";
        String upperLimit = "7.5";
        String precision = "0.001";
        Double[] expected = {6.5, 7.5, 0.001};
        DecimalSettingsVO settingsVO = new DecimalSettingsVO(lowerLimit, upperLimit, precision);
        //Act
        Double[] result = settingsVO.getValue();
        //Arrange
        assertArrayEquals(expected, result);
    }

    /**
     * Verifies that a DecimalSettingsVO is created when upper and lower limits are valid and their precision matches the
     * defined precision.
     */
    @Test
    void givenLimitsRespectingDefinedPrecision_VOIsCreated_WhenGetValue_ThenShouldReturnSettingsArray(){
        //Arrange
        String lowerLimit = "6.555";
        String upperLimit = "7.835";
        String precision = "0.555";
        Double[] expected = {6.555, 7.835, 0.555};
        DecimalSettingsVO settingsVO = new DecimalSettingsVO(lowerLimit, upperLimit, precision);
        //Act
        Double[] result = settingsVO.getValue();
        //Arrange
        assertArrayEquals(expected, result);
    }

}