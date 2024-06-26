package smarthome.domain.sensor.sensorvalues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolarIrradianceValueTest {

    /**
     * Verifies that when the reading provided to the {@code getValue} method is equal to zero,
     * the method returns zero. Also, it ensures that when the reading is not zero,
     * the method returns the expected value.
     */
    @Test
    void givenAValidReading_whenGetValueIsInvoked_AnIntegerValueShouldBeReturned(){
        //Arrange
        String reading1 = "0";
        String reading2 = "999";

        int expected1 = 0;
        int expected2 = 999;
        SolarIrradianceValue value1 = new SolarIrradianceValue(reading1);
        SolarIrradianceValue value2 = new SolarIrradianceValue(reading2);

        //Act
        int result1 = value1.getValue();
        int result2 = value2.getValue();

        //Assert
        assertEquals(expected1,result1);
        assertEquals(expected2,result2);
    }


    /**
     * Verifies that when an invalid reading is provided to the constructor of {@code SolarIrradianceValue},
     * an {@code IllegalArgumentException} is thrown. An invalid reading is defined as any non-positive value,
     * a string containing non-numeric characters, or a null value.
     */
    @Test
    void givenAnInvalidReading_ThenShouldThrowIllegalArgumentException(){
        //Arrange
        String reading1 = "-700";
        String reading2 = " ";
        String reading3 = "250.6";
        String reading4 = "Result = 700";
        String expected = "Invalid reading";

        //Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceValue(reading1));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceValue(reading2));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceValue(reading3));
        String result3 = exception3.getMessage();

        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceValue(reading4));
        String result4 = exception4.getMessage();

        Exception exception5 = assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceValue(null));
        String result5 = exception5.getMessage();

        //Assert
        assertEquals(expected, result1);
        assertEquals(expected, result2);
        assertEquals(expected, result3);
        assertEquals(expected, result4);
        assertEquals(expected, result5);
    }
}