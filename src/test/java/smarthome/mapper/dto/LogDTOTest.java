package smarthome.mapper.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LogDTOTest {

    /**
     * Tests the scenario when the constructor is called, verifying that the LogDTO object is created and its attributes are accessible.
     * This test method verifies that a LogDTO object is created with the specified attributes when the constructor is called.
     * It arranges the necessary input parameters including log ID, time, reading, sensor ID, device ID, and sensor type ID.
     * Then, it constructs a LogDTO object using these parameters.
     * After that, it retrieves each attribute of the LogDTO object using getter methods.
     * Finally, it asserts that the retrieved attribute values match the expected values based on the input parameters.
     */
    @Test
    void whenConstructorCalled_objectIsCreated_attributesAreAccessible(){
        // Arrange
        String logID = UUID.randomUUID().toString();
        String time = LocalDateTime.now().minusHours(2).toString();
        String reading = "60";
        String sensorID = UUID.randomUUID().toString();
        String deviceID = UUID.randomUUID().toString();
        String sensorTypeID = UUID.randomUUID().toString();
        LogDTO logDTO = new LogDTO(logID,time,reading,sensorID,deviceID,sensorTypeID);

        // Act
        String resultLogID = logDTO.getLogID();
        String resultTime = logDTO.getTime();
        String resultReading = logDTO.getReading();
        String resultSensorID = logDTO.getSensorID();
        String resultDeviceID = logDTO.getDeviceID();
        String resultSensorTypeID = logDTO.getSensorTypeID();

        // Assert
        assertEquals(logID,resultLogID);
        assertEquals(time,resultTime);
        assertEquals(reading,resultReading);
        assertEquals(sensorID,resultSensorID);
        assertEquals(deviceID,resultDeviceID);
        assertEquals(sensorTypeID,resultSensorTypeID);
    }

    /**
     * Tests the scenario when the constructor is called with all parameters set to null, verifying that the LogDTO object is created with all attributes set to null.
     * This test method verifies that a LogDTO object is created with all attributes set to null when the constructor is called with all parameters set to null.
     * It arranges the scenario by creating a LogDTO object with all parameters set to null.
     * Then, it retrieves each attribute of the LogDTO object using getter methods.
     * Finally, it asserts that all retrieved attribute values are null.
     */
    @Test
    void whenConstructorCalledWithFullyNullParameters_objectIsCreated(){
        // Arrange
        LogDTO logDTO = new LogDTO(null,null,null,null,null,null);

        // Act
        String resultLogID = logDTO.getLogID();
        String resultTime = logDTO.getTime();
        String resultReading = logDTO.getReading();
        String resultSensorID = logDTO.getSensorID();
        String resultDeviceID = logDTO.getDeviceID();
        String resultSensorTypeID = logDTO.getSensorTypeID();

        // Assert
        assertNull(resultLogID);
        assertNull(resultTime);
        assertNull(resultReading);
        assertNull(resultSensorID);
        assertNull(resultDeviceID);
        assertNull(resultSensorTypeID);
    }
}
