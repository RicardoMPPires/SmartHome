package smarthome.mapper.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTypeDTOTest {

    /**
     * Test the constructor of SensorTypeDTO with valid type
     * Expecting SensorTypeDTO object
     * Assert the type of the object is the same as the input
     */
    @Test
    void createSensorTypeDTO_WhenGetType_ThenShouldReturnTypeAsString() {
        String type = "Temperature";
        String unit = "C";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(type,unit);
        assertEquals(type, sensorTypeDTO.getSensorTypeID());
    }
    /**
     * Test the constructor of SensorTypeDTO with valid unit
     * Expecting SensorTypeDTO object
     * Assert the unit of the object is the same as the input
     */
    @Test
    void createSensorTypeDTO_WhenGetUnit_ThenShouldReturnUnitAsString() {
        String type = "Temperature";
        String unit = "C";
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(type,unit);
        assertEquals(unit, sensorTypeDTO.getUnit());
    }
}