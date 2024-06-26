package smarthome.domain.sensor.externalservices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimHardwareTest {
    /**
     * This test ensures the getValue method returns the sample value successfully.
     */
    @Test
    void successfullyReturnsSample() {
        //Arrange
        SimHardware sim = new SimHardware();
        String expected = "Sample";
        //Act
        String result = sim.getValue();
        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test ensures the getValue method returns the sample value successfully.
     */
    @Test
    void successfullyReturnsSampleWithDates() {
        //Arrange
        SimHardware sim = new SimHardware();
        String expected = "Sample";
        //Act
        String result = sim.getValue("15-12-2020 14:15:45", "16-12-2020 14:15:45");
        //Assert
        assertEquals(expected, result);
    }
}