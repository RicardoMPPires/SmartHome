package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DewPointValueTest {
    @Test
    void constructor_throwsInstantiationExceptionIfReadingNull() {
        //Arrange
        String reading1 = " ";
        String expected = "Invalid DewPoint reading";
        String reading2 = "this will fail";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new DewPointValue(reading1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new DewPointValue(null));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new DewPointValue(reading2));
        String result3 = exception3.getMessage();

        //Assert
        assertEquals(expected, result1);
        assertEquals(expected, result2);
        assertEquals(expected, result3);
    }

    @Test
    void getValue_returnsSuccessfullyWhenReadingIsValid() {
        //Arrange
        String reading = "5.0";
        double expected = 5.0;
        //Act
        DewPointValue value = new DewPointValue(reading);
        //Assert
        assertEquals(expected, value.getValue());
    }
    @Test
    void constructor_throwsInstantiationExceptionIfReadingUnableToParse(){
        //Arrange
        String reading = "this will fail";
        String expected = "Invalid DewPoint reading";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DewPointValue(reading));
        String result = exception.getMessage();
        //Assert
        assertEquals(expected,result);
    }
}

