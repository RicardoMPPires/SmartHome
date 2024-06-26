package smarthome.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.mapper.dto.ActuatorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ActuatorMapper test class
 */

class ActuatorMapperTest {
    /**
     * Test to verify that an exception is thrown when the ActuatorDTO object is null.
     * Should throw an IllegalArgumentException with the message "Invalid DTO, ActuatorDTO cannot be null" since it fails to create an ActuatorNameVO object.
     */
    @Test
    void givenNullActuatorDTO_WhenCreateActuatorNameVO_ThenThrowException() {
//        Arrange
        String expectedMessage = "ActuatorDTO cannot be null.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.createActuatorNameVO(null));
//        Assert
        String resultingMessage = exception.getMessage();
        assertEquals(expectedMessage, resultingMessage);
    }

    /**
     * Test to verify that an exception is thrown when the ActuatorDTO object is null.
     * Should throw an IllegalArgumentException with the message "Invalid DTO, ActuatorDTO cannot be null" since it fails to create an ActuatorTypeIDVO object.
     */

    @Test
    void givenNullActuatorDTO_WhenCreateActuatorTypeIDVO_ThenThrowException() {
//        Arrange
        String expectedMessage = "ActuatorDTO cannot be null.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.createActuatorTypeIDVO(null));
//        Assert
        String resultingMessage = exception.getMessage();
        assertEquals(expectedMessage, resultingMessage);
    }

    /**
     * Test to verify that an exception is thrown when the ActuatorDTO object is null.
     * Should throw an IllegalArgumentException with the message "Invalid DTO, ActuatorDTO cannot be null" since
     * it fails to create a DeviceIDVO object.
     */
    @Test
    void givenNullActuatorDTO_WhenCreateDeviceIDVO_ThenThrowException() {
//        Arrange
        String expectedMessage = "ActuatorDTO cannot be null.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.createDeviceIDVO(null));
//        Assert
        String resultingMessage = exception.getMessage();
        assertEquals(expectedMessage, resultingMessage);
    }

    @Test
    void givenNullActuatorDTO_whenCreateActuatorStatusVO_thenThrowsException() {
        // Arrange
        String expectedMessage = "ActuatorDTO cannot be null.";
        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> ActuatorMapper.createActuatorStatusVO(null));
        // Assert
        String resultingMessage = exception.getMessage();
        assertEquals(expectedMessage, resultingMessage);
    }

    /**
     * This test verifies that an exception is thrown if the actuatorDTO exists but the deviceId is null.
     * It should throw an Illegal Argument Exception with the message "Device ID cannot be null" since it fails to
     * create the DeviceIDVO object.
     */
    @Test
    void givenActuatorDTOWithNullDeviceID_whenCreateDeviceIDVOMethodCall_thenThrowsException() {
        //Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = null;
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();

        String expected = "Device ID cannot be null";

        //Act

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.createDeviceIDVO(actuatorDTO));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that an ActuatorNameVO object is created correctly.
     */

    @Test
    void givenActuatorDTO_WhenCreateActuatorNameVO_ThenReturnActuatorNameVO() {
//        Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = UUID.randomUUID().toString();
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();
//        Act
        ActuatorNameVO actuatorNameVO = ActuatorMapper.createActuatorNameVO(actuatorDTO);
//        Assert
        assertEquals(expectedActuatorName, actuatorNameVO.getValue());
    }

    /**
     * Test to verify that an ActuatorTypeIDVO object is created correctly.
     */

    @Test
    void givenActuatorDTO_WhenCreateActuatorTypeIDVO_ThenReturnActuatorTypeIDVO() {
//        Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "frt3-567p-32za";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();
//        Act
        ActuatorTypeIDVO actuatorTypeIDVO = ActuatorMapper.createActuatorTypeIDVO(actuatorDTO);
//        Assert
        assertEquals(actuatorType, actuatorTypeIDVO.getID());
    }

    /**
     * Test case to verify the behavior of the createActuatorStatusVO method when given a valid ActuatorDTO.
     * <p>
     * Given a valid ActuatorDTO with a status,
     * when the createActuatorStatusVO method is called with the ActuatorDTO,
     * then it should return an ActuatorStatusVO with the same status,
     * and the value obtained from the ActuatorStatusVO should match the expected status.
     */
    @Test
    void givenValidActuatorDTO_createActuatorStatusVOReturnsActuatorStatusVO(){
        // Arrange
        String expected = "I will pass";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder().status(expected).build();
        ActuatorStatusVO statusVO = ActuatorMapper.createActuatorStatusVO(actuatorDTO);
        // Act
        String result = statusVO.getValue();
        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test to verify that a DeviceIDVO object is created correctly.
     */

    @Test
    void givenActuatorDTO_WhenCreateDeviceIDVO_ThenReturnDeviceIDVO() {
//        Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "123e4567-e89b-12d3-a456-111111111111";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();
//        Act
        DeviceIDVO deviceIDVO = ActuatorMapper.createDeviceIDVO(actuatorDTO);
//        Assert
        assertEquals(deviceID, deviceIDVO.getID());
    }

    /**
     * Test to verify that an exception is thrown when the ActuatorDTO object is null.
     * Should throw an IllegalArgumentException with the message "ActuatorDTO cannot be null" since it fails to create a Settings object.
     */

    @Test
    void givenNullActuatorDTO_WhenCreateSettingsVO_ThenReturnNullObject() {
//        Arrange
        String expectedMessage = "ActuatorDTO cannot be null.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.createSettingsVO(null));
//        Assert
        String resultingMessage = exception.getMessage();
        assertEquals(expectedMessage, resultingMessage);
    }

    /**
     * Test to verify that an IntegerSettingsVO object is created correctly when the ActuatorDTO object has limits.
     * It creates an IntegerSettingsVO object with the lower and upper limits. Then uses a getter to retrieve the values.
     * The expected values are the lower and upper limits in a string format.
     */

    @Test
    void givenActuatorDTOWithLimits_WhenCreateSettingsVO_ThenReturnIntegerSettingsVO() {
//        Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "frt3-567p-32za";
        String lowerLimit = "10";
        String upperLimit = "30";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .build();
//        Act
        Settings integerSettingsVO = ActuatorMapper.createSettingsVO(actuatorDTO);
//        Assert
        Object[] expectedValues = integerSettingsVO.getValue();
        assertEquals(lowerLimit, expectedValues[0].toString());
        assertEquals(upperLimit, expectedValues[1].toString());
    }

    /**
     * Test to verify that a DecimalSettingsVO object is created correctly when the ActuatorDTO object has limits and precision.
     * It creates a DecimalSettingsVO object with the lower and upper limits and precision. Then uses a getter to retrieve the values.
     * The expected values are the lower limit, upper limit and precision in a string format.
     */

    @Test
    void givenActuatorDTOWithLimitsAndPrecision_WhenCreateSettingsVO_ThenReturnDecimalSettingsVO() {
//        Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "frt3-567p-32za";
        String lowerLimit = "10.3";
        String upperLimit = "30.2";
        String precision = "0.1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .lowerLimit(lowerLimit)
                .upperLimit(upperLimit)
                .precision(precision)
                .build();
//        Act
        Settings decimalSettingsVO = ActuatorMapper.createSettingsVO(actuatorDTO);
//        Assert
        Object[] expectedValues = decimalSettingsVO.getValue();
        assertEquals(lowerLimit, expectedValues[0].toString());
        assertEquals(upperLimit, expectedValues[1].toString());
        assertEquals(precision, expectedValues[2].toString());
    }

    /**
     * Test to verify that a null object is returned when the ActuatorDTO object has null limits.
     */

    @Test
    void givenActuatorDTONullLimits_WhenCreateSettingsVO_ThenReturnNullObject() {
//        Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "frt3-567p-32za";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .build();
//        Act
        Settings settingsVO = ActuatorMapper.createSettingsVO(actuatorDTO);
//        Assert
        assertNull(settingsVO);
    }

    /**
     * This test verifies that if the createSettingsVO method receives an ActuatorDTO object with invalid limits
     * (not a number) it returns null.
     */
    @Test
    void givenInvalidActuatorDTO_WhenCreateSettingsVO_ThenReturnNull() {
        // Arrange
        String expectedActuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "frt3-567p-32za";
        String invalidLowerLimit = "invalid";
        String invalidUpperLimit = "invalid";
        String invalidPrecision = "invalid";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                .actuatorName(expectedActuatorName)
                .actuatorTypeID(actuatorType)
                .deviceID(deviceID)
                .lowerLimit(invalidLowerLimit)
                .upperLimit(invalidUpperLimit)
                .precision(invalidPrecision)
                .build();
        // Act
        Settings result = ActuatorMapper.createSettingsVO(actuatorDTO);

        // Assert
        assertNull(result);
    }

    /**
     * Verifies when a valid DTO is received (not null), ActuatorNameVO is created, and when getValue() is called on it,
     * the expected actuator name in a string format is retrieved.
     * ActuatorMapper Class has two collaborators in this scenario, ActuatorDTO and ActuatorNameVO classes. To isolate
     * the class under test (ActuatorMapper), a double of the ActuatorDTO class is made as well as a MockedConstruction of the
     * ActuatorNameVO Class, in order to condition its behaviour when a ActuatorNameVO is created.
     * It also verifies the number of times the ActuatorNameVO constructor is called.
     */

    @Test
    void givenActuatorDTOMock_WhenCreateActuatorNameVO_ThenReturnActuatorNameVO() {
//        Arrange
        String expectedActuatorName = "Smart Thermostat";
        int expectedListSize = 1;
        ActuatorDTO actuatorDTODouble = mock(ActuatorDTO.class);
        when(actuatorDTODouble.getActuatorName()).thenReturn(expectedActuatorName);

        try (MockedConstruction<ActuatorNameVO> mockedActuatorNameVO = mockConstruction(ActuatorNameVO.class, (mock, context)
                -> when(mock.getValue()).thenReturn("Smart Thermostat"))) {
//            Act
            ActuatorNameVO actuatorNameVO = ActuatorMapper.createActuatorNameVO(actuatorDTODouble);
//            Assert
            List<ActuatorNameVO> listOfMockedActuatorNameVO = mockedActuatorNameVO.constructed();
            assertEquals(expectedListSize, listOfMockedActuatorNameVO.size());
            assertEquals(expectedActuatorName, actuatorNameVO.getValue());
        }
    }

    /**
     * Verifies when a valid DTO is received (not null), ActuatorTypeIDVO is created, and when getID() is called on it,
     * the expected actuator type in a string format is retrieved.
     * ActuatorMapper Class has two collaborators in this scenario, ActuatorDTO and ActuatorTypeIDVO classes. To isolate
     * the class under test (ActuatorMapper), a double of the ActuatorDTO class is made as well as a MockedConstruction of the
     * ActuatorTypeIDVO Class, in order to condition its behaviour when a ActuatorTypeIDVO is created.
     * It also verifies the number of times the ActuatorTypeIDVO constructor is called.
     */

    @Test
    void givenActuatorDTOMock_WhenCreateActuatorTypeIDVO_ThenReturnActuatorTypeIDVO() {
//        Arrange
        String actuatorType = "Thermostat";
        int expectedListSize = 1;
        ActuatorDTO actuatorDTODouble = mock(ActuatorDTO.class);
        when(actuatorDTODouble.getActuatorTypeID()).thenReturn(actuatorType);

        try (MockedConstruction<ActuatorTypeIDVO> mockedActuatorTypeIDVO = mockConstruction(ActuatorTypeIDVO.class, (mock, context)
                -> when(mock.getID()).thenReturn("Thermostat"))) {
//            Act
            ActuatorTypeIDVO actuatorTypeIDVO = ActuatorMapper.createActuatorTypeIDVO(actuatorDTODouble);
//            Assert
            List<ActuatorTypeIDVO> listOfMockedActuatorTypeIDVO = mockedActuatorTypeIDVO.constructed();
            assertEquals(expectedListSize, listOfMockedActuatorTypeIDVO.size());
            assertEquals(actuatorType, actuatorTypeIDVO.getID());
        }
    }

    /**
     * Verifies when a valid DTO is received (not null), DeviceIDVO is created, and when getID() is called on it,
     * the expected device ID in a string format is retrieved.
     * ActuatorMapper Class has two collaborators in this scenario, ActuatorDTO and DeviceIDVO classes. To isolate
     * the class under test (ActuatorMapper), a double of the ActuatorDTO class is made as well as a MockedConstruction of the
     * DeviceIDVO Class, in order to condition its behaviour when a DeviceIDVO is created.
     * It also verifies the number of times the DeviceIDVO constructor is called.
     */

    @Test
    void givenActuatorDTOMock_WhenCreateDeviceIDVO_ThenReturnDeviceIDVO() {
//        Arrange
        String deviceID = "123e4567-e89b-12d3-a456-111111111111";
        int expectedListSize = 1;
        ActuatorDTO actuatorDTODouble = mock(ActuatorDTO.class);
        when(actuatorDTODouble.getDeviceID()).thenReturn(deviceID);

        try (MockedConstruction<DeviceIDVO> mockedDeviceIDVO = mockConstruction(DeviceIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn("123e4567-e89b-12d3-a456-111111111111"))) {
//            Act
            DeviceIDVO deviceIDVO = ActuatorMapper.createDeviceIDVO(actuatorDTODouble);
//            Assert
            List<DeviceIDVO> listOfMockedDeviceIDVO = mockedDeviceIDVO.constructed();
            assertEquals(expectedListSize, listOfMockedDeviceIDVO.size());
            assertEquals(deviceID, deviceIDVO.getID());
        }
    }

    /**
     * Verifies when a valid DTO is received (not null), IntegerSettingsVO is created, and when getValue() is called on it,
     * the expected lower and upper limits in a string format are retrieved.
     * ActuatorMapper Class has two collaborators in this scenario, ActuatorDTO and IntegerSettingsVO classes. To isolate
     * the class under test (ActuatorMapper), a double of the ActuatorDTO class is made as well as a MockedConstruction of the
     * IntegerSettingsVO Class, in order to condition its behaviour when a IntegerSettingsVO is created.
     * It also verifies the number of times the IntegerSettingsVO constructor is called.
     */

    @Test
    void givenActuatorDTOMock_WhenCreateSettingsVOWithLimits_ThenReturnIntegerSettingsVO() {
//        Arrange
        String lowerLimit = "10";
        String upperLimit = "30";
        int expectedListSize = 1;
        ActuatorDTO actuatorDTODouble = mock(ActuatorDTO.class);
        when(actuatorDTODouble.getLowerLimit()).thenReturn(lowerLimit);
        when(actuatorDTODouble.getUpperLimit()).thenReturn(upperLimit);

        try (MockedConstruction<IntegerSettingsVO> mockedIntegerSettingsVO = mockConstruction(IntegerSettingsVO.class, (mock, context) ->
                when(mock.getValue()).thenReturn(new Integer[]{10, 30}))) {
//            Act
            Settings integerSettingsVO = ActuatorMapper.createSettingsVO(actuatorDTODouble);
//            Assert
            List<IntegerSettingsVO> listOfMockedIntegerSettingsVO = mockedIntegerSettingsVO.constructed();
            Object[] expectedValues = integerSettingsVO.getValue();
            assertEquals(expectedListSize, listOfMockedIntegerSettingsVO.size());
            assertEquals(lowerLimit, expectedValues[0].toString());
            assertEquals(upperLimit, expectedValues[1].toString());
        }
    }

    /**
     * Verifies when a valid DTO is received (not null), DecimalSettingsVO is created, and when getValue() is called on it,
     * the expected lower limit, upper limit and precision in a string format are retrieved.
     * ActuatorMapper Class has two collaborators in this scenario, ActuatorDTO and DecimalSettingsVO classes. To isolate
     * the class under test (ActuatorMapper), a double of the ActuatorDTO class is made as well as a MockedConstruction of the
     * DecimalSettingsVO Class, in order to condition its behaviour when a DecimalSettingsVO is created.
     * It also verifies the number of times the DecimalSettingsVO constructor is called.
     */

    @Test
    void givenActuatorDTOMock_WhenCreateSettingsVOWithLimitsAndPrecision_ThenReturnDecimalSettingsVO() {
//        Arrange
        String lowerLimit = "10.3";
        String upperLimit = "30.2";
        String precision = "0.1";
        int expectedListSize = 1;
        ActuatorDTO actuatorDTODouble = mock(ActuatorDTO.class);
        when(actuatorDTODouble.getLowerLimit()).thenReturn(lowerLimit);
        when(actuatorDTODouble.getUpperLimit()).thenReturn(upperLimit);
        when(actuatorDTODouble.getPrecision()).thenReturn(precision);

        try (MockedConstruction<DecimalSettingsVO> mockedDecimalSettingsVO = mockConstruction(DecimalSettingsVO.class, (mock, context) ->
                when(mock.getValue()).thenReturn(new Double[]{10.3, 30.2, 0.1}))) {
//            Act
            Settings decimalSettingsVO = ActuatorMapper.createSettingsVO(actuatorDTODouble);
//            Assert
            List<DecimalSettingsVO> listOfMockedDecimalSettingsVO = mockedDecimalSettingsVO.constructed();
            Object[] expectedValues = decimalSettingsVO.getValue();
            assertEquals(expectedListSize, listOfMockedDecimalSettingsVO.size());
            assertEquals(lowerLimit, expectedValues[0].toString());
            assertEquals(upperLimit, expectedValues[1].toString());
            assertEquals(precision, expectedValues[2].toString());
        }
    }

    /**
     * This test verifies that if the domainToDTO method receives a null Actuator object, it throws an
     * IllegalArgumentException.
     */
    @Test
    void givenNullActuator_whenDomainToDtoMethodCalled_thenThrowsException() {
        // Arrange
        Actuator actuator = null;
        String expected = "Invalid actuator, DTO cannot be created";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.domainToDTO(actuator));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }



    /**
     * This test verifies that the domainToDTO method returns an ActuatorDTO object with the correct values when
     * the Actuator object has no settings. All the attributes of the Actuator object are mocked and the expected
     * behaviour set. Then the ActuatorDTO object is created with the actuator double.
     * Finally, the values of the ActuatorDTO object are compared with the expected values.
     */
    @Test
    void givenActuatorWithNoSettings_whenDomainToDtoMethodCalled_thenReturnActuatorDTO() {
        // Arrange
        String actuatorID = "123e4567-e89b-12d3-a456-426614174000";
        String actuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "123e4567-e89b-12d3-a456-426614174001";
        String status = "I am valid";

        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        when(actuatorIDVO.getID()).thenReturn(actuatorID);

        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        when(actuatorNameVO.getValue()).thenReturn(actuatorName);

        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeIDVO.getID()).thenReturn(actuatorType);

        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        when(deviceIDVO.getID()).thenReturn(deviceID);

        ActuatorStatusVO actuatorStatusVO = mock(ActuatorStatusVO.class);
        when(actuatorStatusVO.getValue()).thenReturn(status);

        Actuator actuator = mock(Actuator.class);
        when(actuator.getId()).thenReturn(actuatorIDVO);
        when(actuator.getActuatorName()).thenReturn(actuatorNameVO);
        when(actuator.getActuatorTypeID()).thenReturn(actuatorTypeIDVO);
        when(actuator.getDeviceID()).thenReturn(deviceIDVO);
        when(actuator.getActuatorStatus()).thenReturn(actuatorStatusVO);


        // Act
        ActuatorDTO actuatorDTO = ActuatorMapper.domainToDTO(actuator);

        //Assert
        assertEquals(actuatorID, actuatorDTO.getActuatorId());
        assertEquals(actuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
    }

    /**
     * This test verifies that the domainToDTO method returns an ActuatorDTO object with the correct values when
     * the Actuator object has settings but no precision. All the attributes of the Actuator object are mocked and the
     * expected behaviour set. Then the ActuatorDTO object is created with the actuator double.
     * Finally, the values of the ActuatorDTO object are compared with the expected values.
     */
    @Test
    void givenActuatorWithSettingsButNoPrecision_whenDomainToDtoMethodCalled_thenReturnActuatorDTO() {
        // Arrange
        String actuatorID = "123e4567-e89b-12d3-a456-426614174000";
        String actuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "123e4567-e89b-12d3-a456-426614174001";
        String status = "I am valid";

        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        when(actuatorIDVO.getID()).thenReturn(actuatorID);

        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        when(actuatorNameVO.getValue()).thenReturn(actuatorName);

        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeIDVO.getID()).thenReturn(actuatorType);

        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        when(deviceIDVO.getID()).thenReturn(deviceID);

        ActuatorStatusVO actuatorStatusVO = mock(ActuatorStatusVO.class);
        when(actuatorStatusVO.getValue()).thenReturn(status);

        Integer lowerLimit = 10;
        Integer upperLimit = 30;
        Integer[] values = {lowerLimit, upperLimit};

        Settings settings = mock(Settings.class);
        when(settings.getValue()).thenReturn(values);

        Actuator actuator = mock(Actuator.class);
        when(actuator.getId()).thenReturn(actuatorIDVO);
        when(actuator.getActuatorName()).thenReturn(actuatorNameVO);
        when(actuator.getActuatorTypeID()).thenReturn(actuatorTypeIDVO);
        when(actuator.getDeviceID()).thenReturn(deviceIDVO);
        when(actuator.getLowerLimit()).thenReturn("10");
        when(actuator.getUpperLimit()).thenReturn("30");
        when(actuator.getPrecision()).thenReturn(null);
        when(actuator.getActuatorStatus()).thenReturn(actuatorStatusVO);


        // Act
        ActuatorDTO actuatorDTO = ActuatorMapper.domainToDTO(actuator);

        //Assert
        assertEquals(actuatorID, actuatorDTO.getActuatorId());
        assertEquals(actuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
        assertEquals(lowerLimit.toString(), actuatorDTO.getLowerLimit());
        assertEquals(upperLimit.toString(), actuatorDTO.getUpperLimit());
    }

    /**
     * This test verifies that the domainToDTO method returns an ActuatorDTO object with the correct values when
     * the Actuator object has settings and precision. All the attributes of the Actuator object are mocked and the
     * expected behaviour set. Then the ActuatorDTO object is created with the actuator double.
     * Finally, the values of the ActuatorDTO object are compared with the expected values.
     */
    @Test
    void givenActuatorWithSettings_whenDomainToDtoMethodCalled_thenReturnActuatorDTO() {
        // Arrange
        String actuatorID = "123e4567-e89b-12d3-a456-426614174000";
        String actuatorName = "Smart Thermostat";
        String actuatorType = "Thermostat";
        String deviceID = "123e4567-e89b-12d3-a456-426614174001";
        String status = "I am valid";

        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        when(actuatorIDVO.getID()).thenReturn(actuatorID);

        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        when(actuatorNameVO.getValue()).thenReturn(actuatorName);

        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeIDVO.getID()).thenReturn(actuatorType);

        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        when(deviceIDVO.getID()).thenReturn(deviceID);

        ActuatorStatusVO actuatorStatusVO = mock(ActuatorStatusVO.class);
        when(actuatorStatusVO.getValue()).thenReturn(status);

        Double lowerLimit = 10.0;
        Double upperLimit = 30.0;
        Double precision = 0.1;
        Double[] values = {lowerLimit, upperLimit, precision};

        Settings settings = mock(Settings.class);
        when(settings.getValue()).thenReturn(values);


        Actuator actuator = mock(Actuator.class);
        when(actuator.getId()).thenReturn(actuatorIDVO);
        when(actuator.getActuatorName()).thenReturn(actuatorNameVO);
        when(actuator.getActuatorTypeID()).thenReturn(actuatorTypeIDVO);
        when(actuator.getDeviceID()).thenReturn(deviceIDVO);
        when(actuator.getLowerLimit()).thenReturn("10.0");
        when(actuator.getUpperLimit()).thenReturn("30.0");
        when(actuator.getPrecision()).thenReturn("0.1");
        when(actuator.getActuatorStatus()).thenReturn(actuatorStatusVO);


        // Act
        ActuatorDTO actuatorDTO = ActuatorMapper.domainToDTO(actuator);

        //Assert
        assertEquals(actuatorID, actuatorDTO.getActuatorId());
        assertEquals(actuatorName, actuatorDTO.getActuatorName());
        assertEquals(actuatorType, actuatorDTO.getActuatorTypeID());
        assertEquals(deviceID, actuatorDTO.getDeviceID());
        assertEquals(lowerLimit.toString(), actuatorDTO.getLowerLimit());
        assertEquals(upperLimit.toString(), actuatorDTO.getUpperLimit());
        assertEquals(precision.toString(), actuatorDTO.getPrecision());
    }

    /**
     * This test verifies that if the createActuatorIDVO method receives a null actuatorId, it throws an
     * IllegalArgumentException.
     * First it sets tha actuatorID string to null and defines the expected message.
     * Then it creates an exception by calling the createActuatorIDVO method with the null actuatorID string, and
     * defines a result string with the message retrieved from the exception.
     * Finally, it compares the expected message with the result message.
     */
    @Test
    void givenNullActuatorId_whenCreateActuatorIDVOMethodCalled_thenReturnIllegalArgumentExpression() {
        // Arrange
        String actuatorId = null;
        String expected = "ActuatorID cannot be null";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.createActuatorIDVO(actuatorId));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that if the createActuatorIDVO method receives an invalid actuatorId, it throws an
     * IllegalArgumentException.
     * First it sets tha actuatorID string to "invalid" and defines the expected message.
     * Then it creates an exception by calling the createActuatorIDVO method with the invalid actuatorID string, and
     * defines a result string with the message retrieved from the exception.
     * Finally, it compares the expected message with the result message.
     */
    @Test
    void givenInvalidActuatorId_whenCreateActuatorIDVOMethodCalled_theReturnIllegalArgumentExpression() {
        //Arrange
        String actuatorId = "invalid";
        String expected = "Invalid UUID string: invalid";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                ActuatorMapper.createActuatorIDVO(actuatorId));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that the createActuatorIDVO method returns an ActuatorIDVO object with the correct ID when
     * a valid actuatorId is received. The actuatorId string is set to a valid UUID string and the expected size of
     * the list of mocked construction is set to one.
     * Then, the construction is mocked and the behaviour of the ActuatorIDVO object is set to return a string equal to
     * the actuatorId's string.
     * After this, the createActuatorIDVO method is called with the actuatorId string and the ID of the
     * ActuatorIDVO object retrieved and compared with the expected ID, as well as the size of the list of mocked
     * constructions.
     */
    @Test
    void givenActuatorId_whenCreateActuatorIDVOMethodCalled_thenReturnActuatorIDVO() {
        // Arrange
        String actuatorId = "123e4567-e89b-12d3-a456-426614174002";
        int listSize = 1;

        try (MockedConstruction<ActuatorIDVO> mockedActuatuorIDVO = mockConstruction(ActuatorIDVO.class, (mock, context) ->
                when(mock.getID()).thenReturn("123e4567-e89b-12d3-a456-426614174002"))) {

            // Act
            ActuatorIDVO actuatorIDVO = ActuatorMapper.createActuatorIDVO(actuatorId);
            List<ActuatorIDVO> listOfMockedActuatorIDVO = mockedActuatuorIDVO.constructed();

            // Assert

            assertEquals(listSize, listOfMockedActuatorIDVO.size());
            assertEquals(actuatorId, actuatorIDVO.getID());
        }
    }

    /**
     * Test to verify that, when calling the domainToDTO method, the actuatorDTO list is returned.
     * First, the actuatorNameVO, deviceIDVO, actuatorIDVO and actuatorTypeIDVO are mocked.
     * Then, the actuatorDouble is mocked and the intended behavior is mimicked.
     * This process is repeated for the actuatorDouble2.
     * Afterward, a list of actuators is created, the mocked actuators added to the list and the expected size defined.
     * Finally, the domainToDTO method is called and the VOs of both doubles compared with the expected mocks.
     */
    @Test
    void whenDomainToDTOIsCalled_ThenReturnActuatorDTOList() {
        //Arrange
        ActuatorIDVO actuatorIDVODouble = mock(ActuatorIDVO.class);
        ActuatorTypeIDVO actuatorTypeIDVODouble = mock(ActuatorTypeIDVO.class);
        ActuatorNameVO actuatorNameVODouble = mock(ActuatorNameVO.class);
        DeviceIDVO deviceIDVODouble = mock(DeviceIDVO.class);
        ActuatorStatusVO actuatorStatusVODouble = mock(ActuatorStatusVO.class);

        Actuator actuatorDouble = mock(Actuator.class);
        when(actuatorDouble.getId()).thenReturn(actuatorIDVODouble);
        when(actuatorDouble.getActuatorTypeID()).thenReturn(actuatorTypeIDVODouble);
        when(actuatorDouble.getActuatorName()).thenReturn(actuatorNameVODouble);
        when(actuatorDouble.getDeviceID()).thenReturn(deviceIDVODouble);
        when(actuatorDouble.getActuatorStatus()).thenReturn(actuatorStatusVODouble);

        ActuatorIDVO actuatorIDVODouble2 = mock(ActuatorIDVO.class);
        ActuatorTypeIDVO actuatorTypeIDVODouble2 = mock(ActuatorTypeIDVO.class);
        ActuatorNameVO actuatorNameVODouble2 = mock(ActuatorNameVO.class);
        DeviceIDVO deviceIDVODouble2 = mock(DeviceIDVO.class);
        ActuatorStatusVO actuatorStatusVODouble2 = mock(ActuatorStatusVO.class);

        Actuator actuatorDouble2 = mock(Actuator.class);
        when(actuatorDouble2.getId()).thenReturn(actuatorIDVODouble2);
        when(actuatorDouble2.getActuatorTypeID()).thenReturn(actuatorTypeIDVODouble2);
        when(actuatorDouble2.getActuatorName()).thenReturn(actuatorNameVODouble2);
        when(actuatorDouble2.getDeviceID()).thenReturn(deviceIDVODouble2);
        when(actuatorDouble2.getActuatorStatus()).thenReturn(actuatorStatusVODouble2);

        List<Actuator> actuatorList = new ArrayList<>();
        actuatorList.add(actuatorDouble);
        actuatorList.add(actuatorDouble2);

        int expectedListSize = 2;

        //Act
        List<ActuatorDTO> result = ActuatorMapper.domainToDTO(actuatorList);

        //Assert
        assertEquals(expectedListSize, result.size());
        assertEquals(actuatorNameVODouble.getValue(), result.get(0).getActuatorName());
        assertEquals(actuatorTypeIDVODouble.getID(), result.get(0).getActuatorTypeID());
        assertEquals(deviceIDVODouble2.getID(), result.get(1).getDeviceID());
    }

    /**
     * Test to verify that, when calling the CreateDeviceIDVOFromString method, with an empty string, an
     * IllegalArgumentException is thrown.
     */
    @Test
    void whenCreateDeviceIDVOFromStringWithEmptyString_ThenThrowIllegalArgumentException() {
        // Arrange
        String deviceID = "";
        String expectedMessage = "DeviceID cannot be null";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ActuatorMapper.createDeviceIDVOFromString(deviceID));
        String resultingMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, resultingMessage);
    }
}