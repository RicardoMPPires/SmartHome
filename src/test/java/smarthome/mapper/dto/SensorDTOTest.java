package smarthome.mapper.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class SensorDTOTest {

    /**
     * Test case for creating a SensorDTO and getting the sensor ID.
     * The test creates a SensorDTO with a specific sensor ID and then checks if the returned sensor ID matches the input.
     */
    @Test
    void createSensorDTO_WhenGetSensorID_ThenShouldReturnSensorIDAsString() {
        //Arrange
        String sensorID = UUID.randomUUID().toString();
        String sensorName = "Temperature";
        String deviceID = UUID.randomUUID().toString();
        String sensorTypeID = "TemperatureSensor";

        //Act
        SensorDTO sensorDTO = new SensorDTO(sensorID, sensorName, deviceID, sensorTypeID);

        //Assert
        assertEquals(sensorID, sensorDTO.getSensorID());
    }

    /**
     * Test case for creating a SensorDTO and getting the sensor name.
     * The test creates a SensorDTO with a specific sensor name and then checks if the returned sensor name matches the input.
     */
    @Test
    void createSensorDTO_WhenGetSensorName_ThenShouldReturnSensorNameAsString() {
        //Arrange
        String sensorID = UUID.randomUUID().toString();
        String sensorName = "Temperature";
        String deviceID = UUID.randomUUID().toString();
        String sensorTypeID = "TemperatureSensor";

        //Act
        SensorDTO sensorDTO = new SensorDTO(sensorID, sensorName, deviceID, sensorTypeID);

        //Assert
        assertEquals(sensorName, sensorDTO.getSensorName());
    }

    /**
     * Test case for creating a SensorDTO and getting the device ID.
     * The test creates a SensorDTO with a specific device ID and then checks if the returned device ID matches the input.
     */
    @Test
    void createSensorDTO_WhenGetDeviceID_ThenShouldReturnDeviceIDAsString() {
        //Arrange
        String sensorID = UUID.randomUUID().toString();
        String sensorName = "Temperature";
        String deviceID = UUID.randomUUID().toString();
        String sensorTypeID = "TemperatureSensor";

        //Act
        SensorDTO sensorDTO = new SensorDTO(sensorID, sensorName, deviceID, sensorTypeID);

        //Assert
        assertEquals(deviceID, sensorDTO.getDeviceID());
    }

    /**
     * Test case for creating a SensorDTO and getting the sensor type ID.
     * The test creates a SensorDTO with a specific sensor type ID and then checks if the returned sensor type ID matches the input.
     */
    @Test
    void createSensorDTO_WhenGetSensorTypeID_ThenShouldReturnSensorTypeIDAsString() {
        //Arrange
        String sensorID = UUID.randomUUID().toString();
        String sensorName = "Temperature";
        String deviceID = UUID.randomUUID().toString();
        String sensorTypeID = "TemperatureSensor";

        //Act
        SensorDTO sensorDTO = new SensorDTO(sensorID, sensorName, deviceID, sensorTypeID);

        //Assert
        assertEquals(sensorTypeID, sensorDTO.getSensorTypeID());
    }

    /**
     * Test case for the no-args constructor of the SensorDTO class.
     * This test verifies that a new SensorDTO object created with the no-args constructor has all its fields set to null.
     */
    @Test
    void noArgsConstructor_ShouldCreateEmptyObject() {
        //Act
        SensorDTO sensorDTO = new SensorDTO();

        //Assert
        assertNull(sensorDTO.getSensorID());
        assertNull(sensorDTO.getSensorName());
        assertNull(sensorDTO.getDeviceID());
        assertNull(sensorDTO.getSensorTypeID());
    }

    /**
     * Test case for the equals() and hashCode() methods of the SensorDTO class.
     * This test verifies that two SensorDTO objects with the same data are considered equal and have the same hash code.
     */
    @Test
    void equalsAndHashCode_ShouldConsiderTwoObjectsWithSameDataAsEqual() {
        //Arrange
        String sensorID = "1";
        String sensorName = "Temperature";
        String deviceID = "1";
        String sensorTypeID = "TemperatureSensor";

        //Act
        SensorDTO sensorDTO1 = SensorDTO.builder()
                .sensorID(sensorID)
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        SensorDTO sensorDTO2 = SensorDTO.builder()
                .sensorID(sensorID)
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        //Assert
        assertEquals(sensorDTO1, sensorDTO2);
        assertEquals(sensorDTO1.hashCode(), sensorDTO2.hashCode());
    }

    /**
     * Test case for the builder() method of the SensorDTO class.
     * This test verifies that the builder creates a SensorDTO object with the given data.
     */
    @Test
    void builder_ShouldCreateObjectWithGivenData() {
        //Arrange
        String sensorID = "1";
        String sensorName = "Temperature";
        String deviceID = "1";
        String sensorTypeID = "TemperatureSensor";

        //Act
        SensorDTO sensorDTO = SensorDTO.builder()
                .sensorID(sensorID)
                .sensorName(sensorName)
                .deviceID(deviceID)
                .sensorTypeID(sensorTypeID)
                .build();

        //Assert
        assertEquals(sensorID, sensorDTO.getSensorID());
        assertEquals(sensorName, sensorDTO.getSensorName());
        assertEquals(deviceID, sensorDTO.getDeviceID());
        assertEquals(sensorTypeID, sensorDTO.getSensorTypeID());
    }
}