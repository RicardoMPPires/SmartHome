package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnergyConsumptionValueTest {

    /**
     * Test case to verify that attempting to instantiate an EnergyConsumptionValue object with null or empty reading throws an IllegalArgumentException.
     *
     * <p>
     * This test verifies the behavior of the EnergyConsumptionValue class constructor when provided with null or empty reading strings. It ensures that an IllegalArgumentException is thrown in both cases.
     * </p>
     *
     * <p>
     * The test instantiates an EnergyConsumptionValue object using null and an empty string as readings, respectively. It expects that both attempts will throw an IllegalArgumentException with the message "Invalid reading".
     * </p>
     *
     * <p>
     * The test uses the assertThrows method to verify that the constructor indeed throws an IllegalArgumentException when invoked with invalid readings. It captures the exception messages and compares them with the expected message.
     * </p>
     */
    @Test
    void whenReadingIsNull_thenThrowsInstantiationException(){
        //Arrange
        String expected = "Invalid reading";
        String reading = " ";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new EnergyConsumptionValue(null));
        String result1 = exception1.getMessage();
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new EnergyConsumptionValue(reading));
        String result2 = exception2.getMessage();

        //Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
    }

    /**
     * Tests that if reading is in invalid format, then an InstantiationException is thrown
     */
    @Test
    void whenReadingIsInInvalidFormat_thenThrowsInstantiationException(){
        //Arrange
        String reading = "this will fail";
        String expected = "Invalid reading";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new EnergyConsumptionValue(reading));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * Tests that if reading is valid, then it is successfully returned
     */
    @Test
    void whenReadingIsValid_thenItIsReturned() {
        //Arrange
        String reading = "1";
        int expected = 1;

        //Act
        EnergyConsumptionValue energyConsumptionValue = new EnergyConsumptionValue(reading);
        int result = energyConsumptionValue.getValue();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * Test case to verify the behavior of the `EnergyConsumptionValue` constructor
     * when a valid reading is provided at the lower border (0). It ensures that the constructor
     * correctly creates an instance with the provided reading, which should be 0.
     */
    @Test
    void whenReadingIsValid_thenItIsReturned_BorderCaseLower(){
        //Arrange
        String reading = "0";
        int expected = 0;

        //Act
        EnergyConsumptionValue energyConsumptionValue = new EnergyConsumptionValue(reading);
        int result = energyConsumptionValue.getValue();

        //Assert
        assertEquals(expected,result);
    }

    /**
     * Tests that if reading is valid, then it is successfully returned as String
     */
    @Test
    void whenReadingIsValid_thenItIsReturnedAsString(){
        //Arrange
        String reading = "1";
        String expected = "1";

        //Act
        EnergyConsumptionValue energyConsumptionValue = new EnergyConsumptionValue(reading);
        String result = energyConsumptionValue.getValueAsString();

        //Assert
        assertEquals(expected,result);
    }
}