package smarthome.mapper.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * ActuatorDTO test class
 */

class ActuatorDTOTest {

    /**
     * Test to verify that the ActuatorDTO object is created correctlywhen no arguments are passed.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithNoArgsConstructor_ThenReturnActuatorDTO() {
        //Arrange

        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO();

        //Assert
        assertNull(actuatorDTO.getActuatorId());
        assertNull(actuatorDTO.getActuatorName());
        assertNull(actuatorDTO.getActuatorTypeID());
        assertNull(actuatorDTO.getDeviceID());
        assertNull(actuatorDTO.getLowerLimit());
        assertNull(actuatorDTO.getUpperLimit());
        assertNull(actuatorDTO.getPrecision());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using the builder pattern.
     * In this case, the actuator ID is not passed as a parameter, testing the DTO needed to add an actuator.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithoutActuatorIDUsingBuilder_ThenReturnActuatorDTO() {
        //Arrange
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();

        //Act
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();

        //Assert
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using constructor.
     * In this case, the actuator ID is not passed as a parameter, testing the DTO needed to add an actuator.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithoutActuatorID_ThenReturnActuatorDTO() {
        //Arrange
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();

        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO(null, expectedActuatorName, actuatorType, deviceID,
                null, null, null, null);

        //Assert
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using the builder pattern.
     * In this case, the actuator ID is passed as a parameter, testing the DTO needed to get an actuator from
     * database.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithoutSettingsUsingBuilder_ThenReturnActuatorDTO() {
        //Arrange
        String actuatorId = UUID.randomUUID().toString();
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();

        //Act
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorId(actuatorId)
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();

        //Assert
        assertEquals(actuatorId, actuatorDTO.getActuatorId());
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using constructor.
     * In this case, the actuator ID is passed as a parameter, testing the DTO needed to get an actuator from
     * database.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithoutSettings_ThenReturnActuatorDTO() {
        //Arrange
        String actuatorId = UUID.randomUUID().toString();
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();

        //Act
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorId(actuatorId)
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();

        //Assert
        assertEquals(actuatorId, actuatorDTO.getActuatorId());
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using the builder pattern.
     * In this case, the actuator ID is passed as a parameter, testing the DTO needed to get an actuator from
     * database with settings but no precision.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithoutPrecisionUsingBuilder_ThenReturnActuatorDTO() {
        //Arrange
        String actuatorId = UUID.randomUUID().toString();
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();
        String lowerLimit = "1";
        String upperLimit = "12";
        String status = "I am valid";

        //Act
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorId(actuatorId)
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .status(status)
                .build();

        //Assert
        assertEquals(actuatorId, actuatorDTO.getActuatorId());
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
        assertEquals(lowerLimit, actuatorDTO.getLowerLimit());
        assertEquals(upperLimit, actuatorDTO.getUpperLimit());
        assertEquals(status, actuatorDTO.getStatus());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using constructor.
     * In this case, the actuator ID is passed as a parameter, testing the DTO needed to get an actuator from
     * database with settings but no precision.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithoutPrecision_ThenReturnActuatorDTO() {
        //Arrange
        String actuatorId = UUID.randomUUID().toString();
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();
        String lowerLimit = "1";
        String upperLimit = "12";
        String status = "I am valid";

        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId, expectedActuatorName, actuatorType, deviceID, lowerLimit, upperLimit, null, status);

        //Assert
        assertEquals(actuatorId, actuatorDTO.getActuatorId());
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
        assertEquals(lowerLimit, actuatorDTO.getLowerLimit());
        assertEquals(upperLimit, actuatorDTO.getUpperLimit());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using the builder pattern.
     * In this case, the actuator ID is passed as a parameter, testing the DTO needed to get an actuator from
     * database with settings and precision.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithAllParametersUsingBuilder_ThenReturnActuatorDTO() {
        //Arrange
        String actuatorId = UUID.randomUUID().toString();
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();
        String lowerLimit = "1.0";
        String upperLimit = "12.0";
        String precision = "2.0";
        String status = "I am valid";

        //Act
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorId(actuatorId)
                                            .actuatorName(expectedActuatorName)
                                            .actuatorTypeID(actuatorType)
                                            .deviceID(deviceID)
                                            .lowerLimit(lowerLimit)
                                            .upperLimit(upperLimit)
                                            .precision(precision)
                                            .status(status)
                                            .build();

        //Assert
        assertEquals(actuatorId, actuatorDTO.getActuatorId());
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
        assertEquals(lowerLimit, actuatorDTO.getLowerLimit());
        assertEquals(upperLimit, actuatorDTO.getUpperLimit());
        assertEquals(precision, actuatorDTO.getPrecision());
        assertEquals(status, actuatorDTO.getStatus());
    }

    /**
     * Test to verify that the ActuatorDTO object is created correctly when using constructor.
     * In this case, the actuator ID is passed as a parameter, testing the DTO needed to get an actuator from
     * database with settings and precision.
     */
    @Test
    void givenCorrectParameters_WhenCreateActuatorDTOWithAllParameters_ThenReturnActuatorDTO() {
        //Arrange
        String actuatorId = UUID.randomUUID().toString();
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();
        String lowerLimit = "1.0";
        String upperLimit = "12.0";
        String precision = "2.0";
        String status = "I am valid";

        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId, expectedActuatorName, actuatorType, deviceID, lowerLimit, upperLimit, precision, status);

        //Assert
        assertEquals(actuatorId, actuatorDTO.getActuatorId());
        assertEquals(expectedActuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
        assertEquals(lowerLimit, actuatorDTO.getLowerLimit());
        assertEquals(upperLimit, actuatorDTO.getUpperLimit());
        assertEquals(precision, actuatorDTO.getPrecision());
    }

    /**
     * Test to verify that the equas and hashcode methods are working as expected.
     */
    @Test
    void equalsAndHashcode_shouldConsiderTwoObjectsWithSameDataAndHashcode() {
        //Arrange
        String actuatorId = UUID.randomUUID().toString();
        String expectedActuatorName = "Switch Actuator 2";
        String actuatorType = "SwitchActuator";
        String deviceID = UUID.randomUUID().toString();
        String lowerLimit = "1.0";
        String upperLimit = "12.0";
        String precision = "2.0";

        //Act
        ActuatorDTO actuatorDTO1 = ActuatorDTO.builder()
                .actuatorId(actuatorId)
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        ActuatorDTO actuatorDTO2 = ActuatorDTO.builder()
                .actuatorId(actuatorId)
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();

        //Assert
        assertEquals(actuatorDTO1, actuatorDTO2);
        assertEquals(actuatorDTO1.hashCode(), actuatorDTO2.hashCode());
    }
}
