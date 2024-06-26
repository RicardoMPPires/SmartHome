package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureValueTest {

    /**
     * Test if the constructor throws an exception when the reading is invalid.
     */
    @Test
    void whenReadingIsInvalid_thenThrowsIllegalArgumentException(){
        //Arrange
        String reading2 = " ";
        String reading3 = "this will fail";
        String expected = "Invalid reading";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new TemperatureValue(null));
        String result1 = exception1.getMessage();

        //Act
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new TemperatureValue(reading2));
        String result2 = exception2.getMessage();

        //Act
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new TemperatureValue(reading3));
        String result3 = exception3.getMessage();

        //Assert
        assertEquals(expected,result1);
        assertEquals(expected,result2);
        assertEquals(expected,result3);
    }


    /**
     * Tests if the constructor returns the correct value when the reading is positive.
     */
    @Test
    void whenReadingIsValid_thenItIsReturned() {
        //Arrange
        String doubleValue = "3.0";
        Double expected = 3.0;

        //Act
        TemperatureValue value = new TemperatureValue(doubleValue);

        //Assert
        assertEquals(expected,value.getValue());
    }
}