package smarthome.service;

import org.springframework.boot.test.mock.mockito.MockBean;
import smarthome.domain.actuator.*;
import smarthome.domain.actuator.externalservices.ActuatorExternalService;
import smarthome.domain.actuator.externalservices.SimHardwareAct;
import smarthome.domain.device.Device;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.domain.vo.actuatorvo.Settings;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.ActuatorTypeRepository;
import smarthome.persistence.DeviceRepository;
import org.junit.jupiter.api.Test;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActuatorServiceImplTest {

    @MockBean
    ActuatorExternalService externalService;
    /**
     * The following tests verify that if any of the parameters are null, ActuatorService instantiation should throw an
     * Illegal Argument Exception with the  message "Invalid Parameters".
     * Regarding test syntax it uses a reusable assertion method assertThrowsIllegalArgumentExceptionWithNullParameter()
     * for the similar nature of the following tests.
     * @param deviceRepository Device Repository
     * @param actuatorTypeRepository Actuator type repository
     * @param factoryActuator actuator factory
     * @param memActuatorRepository actuator repository
     */
    private void assertThrowsIllegalArgumentExceptionWithNullParameter(
            DeviceRepository deviceRepository,
            ActuatorTypeRepository actuatorTypeRepository,
            ActuatorFactory factoryActuator,
            ActuatorRepository memActuatorRepository) {

        //Arrange
        String expected = "Parameters cannot be null";

        //Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> new ActuatorServiceImpl(deviceRepository,actuatorTypeRepository,
                                            factoryActuator, memActuatorRepository));
        String result = exception.getMessage();
        assertEquals(expected, result);
    }

    /**
     *The following test verifies that if DeviceRepository parameter is null then ActuatorService instantiation should
     * throw an Illegal Argument Exception with the message "Invalid Parameters".
     */
    @Test
    void whenNullDeviceRepository_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        //Assert
        assertThrowsIllegalArgumentExceptionWithNullParameter(null,actuatorTypeRepository,
                                                                actuatorFactory,actuatorRepository);
    }
//
    /**
     *The following test verifies that if ActuatorTypeRepository parameter is null then ActuatorService instantiation
     * should throw an Illegal Argument Exception with the message "Invalid Parameters".
     */
    @Test
    void whenNullActuatorTypeRepository_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        //Assert
        assertThrowsIllegalArgumentExceptionWithNullParameter(deviceRepository,null,
                                                               actuatorFactory,actuatorRepository);
    }

    /**
     *The following test verifies that if ActuatorFactory parameter is null then ActuatorService instantiation should
     * throw an Illegal Argument Exception with the message "Invalid Parameters".
     */
    @Test
    void whenNullActuatorFactory_thenThrowsIllegalArgumentException() {
        //Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        //Assert
        assertThrowsIllegalArgumentExceptionWithNullParameter(deviceRepository,actuatorTypeRepository,
                                                                null,actuatorRepository);
    }

    /**
     *The following test verifies that if ActuatorRepository parameter is null then ActuatorService instantiation
     * should throw an Illegal Argument Exception with the message "Invalid Parameters".
     */
    @Test
    void whenNullActuatorRepository_thenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);

        //Assert
        assertThrowsIllegalArgumentExceptionWithNullParameter(deviceRepository,actuatorTypeRepository,
                                                            actuatorFactory,null);
    }

    /**
     * Test to verify that addActuator method throws IllegalArgumentException when receiving null parameters.
     *
     * <p>
     * This test verifies that the {@code addActuator} method of the {@code ActuatorServiceImpl} class
     * correctly throws an {@code IllegalArgumentException} when any of the parameters passed to it are null.
     * </p>
     *
     * @throws IllegalArgumentException if any of the parameters passed to the {@code addActuator} method are null.
     */
    @Test
    void whenReceivingNullParameters_addActuatorThrowsIllegalArgumentException(){
        // Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);

        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository,actuatorTypeRepository,
                                                                actuatorFactory,actuatorRepository);

        String expected = "Parameters cannot be null";

        // Act
        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                service.addActuator(null,actuatorTypeIDVO,deviceIDVO,settings));
        String result1 = exception1.getMessage();

        Exception exception2 = assertThrows(IllegalArgumentException.class, ()
                -> service.addActuator(actuatorNameVO,null,deviceIDVO,settings));
        String result2 = exception2.getMessage();

        Exception exception3 = assertThrows(IllegalArgumentException.class, ()
                -> service.addActuator(actuatorNameVO,actuatorTypeIDVO,null,settings));
        String result3 = exception3.getMessage();

        // Assert
        assertEquals(expected,result1); // null actuatorName
        assertEquals(expected,result2); // null actuatorTypeID
        assertEquals(expected,result3); // null deviceID
    }

    /**
     * Test to verify that addActuator method throws IllegalArgumentException when given a DeviceIDVO for an inactive
     * device.
     * <p>
     * This test verifies that the {@code addActuator} method of the {@code ActuatorServiceImpl} class
     * correctly throws an {@code IllegalArgumentException} when attempting to add an actuator to an inactive device.
     * </p>
     * @throws IllegalArgumentException if the device associated with the provided DeviceIDVO is not active.
     */
    @Test
    void givenDeviceIDVOForAnInactiveDevice_addActuatorThrowsIllegalArgumentException() {
        // Arrange
        // Mock repositories and factory
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        // Mock value objects and settings
        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);
        Device device = mock(Device.class);

        // Mock behavior of DeviceRepository and Device
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(false);

        // Create ActuatorServiceImpl instance with mocked dependencies
        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Expected error message
        String expected = "Device is not active";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> service.addActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that addActuator method throws IllegalArgumentException when given a DeviceIDVO for a non-existing
     * device.
     * <p>
     * This test verifies that the {@code addActuator} method of the {@code ActuatorServiceImpl} class
     * correctly throws an {@code IllegalArgumentException} when attempting to add an actuator to a device that does not
     * exist.
     * </p>
     * @throws IllegalArgumentException if the device associated with the provided DeviceIDVO does not exist.
     */
    @Test
    void givenDeviceIDVOForADeviceThatDoesNotExist_addActuatorThrowsIllegalArgumentException() {
        // Arrange
        // Mock repositories and factory
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        // Mock value objects and settings
        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);

        // Mock behavior of DeviceRepository
        when(deviceRepository.findById(deviceIDVO)).thenReturn(null);

        // Create ActuatorServiceImpl instance with mocked dependencies
        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Expected error message
        String expected = "Device is not active";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> service.addActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that addActuator method throws IllegalArgumentException when given an ActuatorTypeIDVO for a
     * non-existent actuator type.
     * <p>
     * This test verifies that the {@code addActuator} method of the {@code ActuatorServiceImpl} class
     * correctly throws an {@code IllegalArgumentException} when attempting to add an actuator with a non-existent
     * actuator type.
     * </p>
     * @throws IllegalArgumentException if the ActuatorTypeIDVO provided does not correspond to an existing actuator
     * type.
     */
    @Test
    void givenActuatorTypeIDVOForANonExistentActuatorType_addActuatorThrowsIllegalArgumentException() {
        // Arrange
        // Mock repositories and factory
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        // Mock value objects and settings
        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);
        Device device = mock(Device.class);

        // Mock behavior of DeviceRepository and ActuatorTypeRepository
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(true);
        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(false);

        // Create ActuatorServiceImpl instance with mocked dependencies
        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Expected error message
        String expected = "Actuator type is not present";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> service.addActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that addActuator method propagates IllegalArgumentException thrown by ActuatorFactory.
     * <p>
     * This test verifies that the {@code addActuator} method of the {@code ActuatorServiceImpl} class
     * correctly propagates an {@code IllegalArgumentException} thrown by the ActuatorFactory.
     * </p>
     * @throws IllegalArgumentException if the ActuatorFactory throws an IllegalArgumentException when attempting to
     * create an actuator.
     */
    @Test
    void givenInvalidVOs_ActuatorFactoryThrowsIllegalArgumentException_addActuatorPropagatesSaidException() {
        // Arrange
        // Mock repositories and factory
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        // Mock value objects and settings
        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);
        Device device = mock(Device.class);

        // Mock behavior of DeviceRepository, ActuatorTypeRepository, and ActuatorFactory
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(true);
        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(actuatorFactory.createActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings))
                .thenThrow(new IllegalArgumentException("Invalid actuator parameters"));

        // Create ActuatorServiceImpl instance with mocked dependencies
        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Expected error message
        String expected = "Invalid actuator parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> service.addActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }



    /**
     * Test to verify that addActuator method returns an empty Optional when ActuatorFactory returns null.
     * <p>
     * This test verifies that the {@code addActuator} method of the {@code ActuatorServiceImpl} class
     * returns an empty {@code Optional} when the ActuatorFactory returns null.
     * </p>
     * @see java.util.Optional
     */
    @Test
    void whenActuatorFactoryReturnsNull_addActuatorReturnsEmptyOptional() {
        // Arrange
        // Mock repositories and factory
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        // Mock value objects and settings
        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);
        Device device = mock(Device.class);

        // Mock behavior of DeviceRepository, ActuatorTypeRepository, ActuatorFactory, and ActuatorRepository
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(true);
        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(actuatorFactory.createActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings)).thenReturn(null);
        when(actuatorRepository.save(null)).thenReturn(false);

        // Create ActuatorServiceImpl instance with mocked dependencies
        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        Optional<Actuator> expected = Optional.empty();

        // Act
        Optional<Actuator> result = service.addActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings);

        // Assert
        assertEquals(expected, result);
    }


    /**
     * Test to verify that addActuator method returns an Optional containing an Actuator object when given correct
     * parameters and no issues arise.
     * <p>
     * This test verifies that the {@code addActuator} method of the {@code ActuatorServiceImpl} class
     * returns an {@code Optional} containing an Actuator object when given correct parameters and no issues arise
     * during the process.
     * </p>
     * @see java.util.Optional
     */
    @Test
    void whenGivenCorrectParametersAndNoIssuesArise_addActuatorReturnsOptionalWithAnActuatorObject() {
        // Arrange
        // Mock repositories and factory
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        // Mock value objects and settings
        ActuatorNameVO actuatorNameVO = mock(ActuatorNameVO.class);
        ActuatorTypeIDVO actuatorTypeIDVO = mock(ActuatorTypeIDVO.class);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Settings settings = mock(Settings.class);
        Device device = mock(Device.class);
        Actuator actuator = mock(Actuator.class);

        // Mock behavior of DeviceRepository, ActuatorTypeRepository, ActuatorFactory, and ActuatorRepository
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(true);
        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);
        when(actuatorFactory.createActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings))
                .thenReturn(actuator);
        when(actuatorRepository.save(actuator)).thenReturn(true);

        // Create ActuatorServiceImpl instance with mocked dependencies
        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        Optional<Actuator> expected = Optional.of(actuator);

        // Act
        Optional<Actuator> result = service.addActuator(actuatorNameVO, actuatorTypeIDVO, deviceIDVO, settings);

        // Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that if the ActuatorIDVO is null, the getActuatorById method should throw an
     * IllegalArgumentException.
     * First it creates mocks of the DeviceRepository, ActuatorTypeRepository, ActuatorFactory and ActuatorRepository.
     * Then, it creates an ActuatorServiceImpl instance with the mocked dependencies.
     * Next, it creates an expected string with the error message.
     * It then creates an exception variable that is assigned the result of the getActuatorById method called with a
     * null argument and retrieves the message of the exception to a string variable.
     * Finally, it asserts that the expected string is equal to the result of the exception message.
     */
    @Test
    void givenNullActuatorIDVO_whenGetActuatorByIDCalled_thenReturnsIllegalArgumentException() {
        //Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        String expected = "ActuatorIDVO cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                actuatorService.getActuatorById(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that if the ActuatorIDVO is not present in the ActuatorRepository it returns an empty
     * Optional.
     * It creates mocks of the DeviceRepository, ActuatorTypeRepository, ActuatorFactory and ActuatorRepository.
     * Next, it creates an ActuatorIDVO mock and sets the behavior of the ActuatorRepository to return false when
     * the isPresent method is called with the ActuatorIDVO as an argument.
     * Then, it creates an ActuatorServiceImpl instance with the mocked dependencies.
     * After that, it creates an Optional variable and assigns the result of the getActuatorById method called with the
     * ActuatorIDVO as an argument.
     * Finally, it asserts that the Optional is empty.
     */
    @Test
    void givenNonExistentActuatorIDVO_whenGetActuatorByIDCalled_thenReturnsEmptyOptional() {
        //Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(false);

        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        //Act
        Optional<Actuator> result = actuatorService.getActuatorById(actuatorIDVO);

        //Assert
        assertTrue(result.isEmpty());
    }

    /**
     * This test verifies that if the ActuatorIDVO is present in the ActuatorRepository it returns an Optional with the
     * Actuator.
     * It creates mocks of the DeviceRepository, ActuatorTypeRepository, ActuatorFactory and ActuatorRepository.
     * Next, it creates an ActuatorIDVO mock and an Actuator mock.
     * Then, it sets the behavior of the ActuatorRepository to return true when the isPresent method is called with the
     * ActuatorIDVO as an argument, and to return the actuator double when the findById method is called with the
     * ActuatorIDVO as an argument.
     * After that, it creates an ActuatorServiceImpl instance with the mocked dependencies.
     * It then creates an Optional variable and assigns the result of the getActuatorById method called with the
     * ActuatorIDVO as an argument.
     * Finally, it asserts that the Optional is present and that is equal to the actuator double.
     */
    @Test
    void givenExistentActuatorIDVO_whenGetActuatorByIDCalled_thenReturnsActuatorOptional() {
        //Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        Actuator actuatorDouble = mock(Actuator.class);

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuatorDouble);

        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        //Act
        Optional<Actuator> result = actuatorService.getActuatorById(actuatorIDVO);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(actuatorDouble, result.get());
    }

    /**
     * This test verifies that if the ActuatorIDVO is valid and is of the type RollerBlind it executes the command
     * to close the RollerBlind and returns true.
     * First, it creates a mockBean of the ActuatorExternalService. Then it creates mocks of the ActuatorIDVO, Actuator,
     * DeviceRepository, ActuatorTypeRepository, ActuatorFactory and ActuatorRepository.
     * Then, it sets the behavior of the ActuatorRepository to return the actuator double when the
     * findById method is called with the ActuatorIDVO as an argument.
     * Next, it sets the behavior of the actuator double to return a new ActuatorTypeIDVO of type "RollerBlindActuator".
     * Then, it sets the behavior of the actuator double to return true when the executeCommand method is called with
     * the ActuatorExternalService and 0 as arguments.
     * After that, it creates an ActuatorServiceImpl instance with the mocked dependencies. It then calls the
     * closeRollerBlind method with the ActuatorIDVO as an argument and assigns the result to a boolean variable.
     * Finally, it asserts that the result is true.
     */
    @Test
    void whenCloseRollerBlindWithValidActuator_ThenReturnsTrue() {
        //Arrange
        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        RollerBlindActuator actuator = mock(RollerBlindActuator.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);
        when(actuator.getActuatorTypeID()).thenReturn(new ActuatorTypeIDVO("RollerBlindActuator"));
        when(actuator.executeCommand(this.externalService, "0")).thenReturn("0");
        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository, actuatorFactory, actuatorRepository);
        //Act
        boolean result = actuatorService.closeRollerBlind(actuatorIDVO);
        //Assert
        assertTrue(result);
    }


    @Test
    void whenCloseRollerBlindWithValidActuator_executeCommandFails_thenReturnsFalse() {
        //Arrange
        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        RollerBlindActuator actuator = mock(RollerBlindActuator.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);
        when(actuator.getActuatorTypeID()).thenReturn(new ActuatorTypeIDVO("RollerBlindActuator"));
        when(actuator.executeCommand(this.externalService, "0")).thenReturn("I failed");
        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository, actuatorFactory, actuatorRepository);
        //Act
        boolean result = actuatorService.closeRollerBlind(actuatorIDVO);
        //Assert
        assertFalse(result);
    }

    /**
     * This test verifies that if the ActuatorIDVO is valid but is not of the type RollerBlind then the method returns
     * false. It creates mocks of the ActuatorIDVO, Actuator, DeviceRepository, ActuatorTypeRepository, ActuatorFactory
     * and ActuatorRepository.
     * Then, it sets the behavior of the ActuatorRepository to return true when the isPresent method is called and to return
     * the actuator double when the findById method is called with the ActuatorIDVO as an argument.
     * Next, it sets the behavior of the actuator double to return a new ActuatorTypeIDVO of type "SwitchActuator".
     * After that, it creates an ActuatorServiceImpl instance with the mocked dependencies. It then calls the
     * closeRollerBlind method with the ActuatorIDVO as an argument and assigns the result to a boolean variable.
     * Finally, it asserts that the result is false.
     */
    @Test
    void whenCloseRollerBlindWithInvalidActuatorType_ThenReturnsFalse() {
        //Arrange
        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        Actuator actuator = mock(RollerBlindActuator.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);
        when(actuator.getActuatorTypeID()).thenReturn(new ActuatorTypeIDVO("SwitchActuator"));
        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository, actuatorFactory, actuatorRepository);
        //Act
        boolean result = actuatorService.closeRollerBlind(actuatorIDVO);
        //Assert
        assertFalse(result);
    }

    /**
     * This test verifies that if the Actuator is null  then the method returns false.
     * It creates mocks of the ActuatorIDVO, Actuator, DeviceRepository, ActuatorTypeRepository, ActuatorFactory
     * and ActuatorRepository.
     * Then, it sets the behavior of the ActuatorRepository to return the actuator double when the findById method is
     * called with the ActuatorIDVO as an argument.
     * Next, it sets the behavior ActuatorRepository to return null when the findById method is called with the
     * ActuatorIDVO as an argument.
     * After that, it creates an ActuatorServiceImpl instance with the mocked dependencies. It then calls the
     * closeRollerBlind method with the ActuatorIDVO as an argument and assigns the result to a boolean variable.
     * Finally, it asserts that the result is false.
     */
    @Test
    void whenCloseRollerBlindWithNullActuator_ThenReturnFalse() {
        // Arrange
        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository, actuatorFactory, actuatorRepository);

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(null);

        // Act
        boolean result = actuatorService.closeRollerBlind(actuatorIDVO);

        // Assert
        assertFalse(result);
    }


    /**
     * This test verifies that if the actuator doesn't exist in the system then the method returns false.
     * It creates mocks of the ActuatorIDVO, DeviceRepository, ActuatorTypeRepository, ActuatorFactory and ActuatorRepository.
     * Then, it sets the behavior of the ActuatorRepository to return null when the findById method is called with the
     * ActuatorIDVO as an argument.
     * After that, it creates an ActuatorServiceImpl instance with the mocked dependencies. It then calls the
     * closeRollerBlind method with the ActuatorIDVO as an argument and assigns the result to a boolean variable.
     * Finally, it asserts that the result is false.
     */
    @Test
    void whenCloseRollerBlindWithNonExistentActuator_ThenThrowsIllegalArgumentException() {
        //Arrange
        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(false);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(null);
        ActuatorService actuatorService = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository, actuatorFactory, actuatorRepository);

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorService.closeRollerBlind(actuatorIDVO));
    }


    /**
     * This test verifies that if the ActuatorIDVO is null, the getListOfActuatorsInADevice method should throw an
     * IllegalArgumentException.
     */
    @Test
    void whenDeviceIDVOIsNull_thenThrowsIllegalArgumentException() {
        // Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        ActuatorService service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.getListOfActuatorsInADevice(null));
    }

    /**
     * This test verifies that if the device is not present in the system, the method GetListOfActuatorsInADevice
     * should throw an IllegalArgumentException.
     */
    @Test
    void whenDeviceIsNotPresent_thenThrowsIllegalArgumentException() {
        // Arrange
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(false);
        ActuatorService service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.getListOfActuatorsInADevice(deviceIDVO));
    }

    /**
     * This test verifies that if the device is present in the system, the method GetListOfActuatorsInADevice should
     * return a list of actuators in that device.
     */
    @Test
    void whenGetListOfActuatorsInADevice_thenReturnListOfActuators() {
        // Arrange
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        List<Actuator> actuators = Collections.singletonList(mock(Actuator.class));
        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(actuatorRepository.findByDeviceID(deviceIDVO)).thenReturn(actuators);
        ActuatorService service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Act
        List<Actuator> result = service.getListOfActuatorsInADevice(deviceIDVO);

        // Assert
        assertEquals(actuators, result);
    }

    /**
     * Test method for {@link ActuatorServiceImpl#executeCommand(ActuatorIDVO, String)}.
     * <p>
     * This test verifies that when an actuator is not found by ID, the {@code executeCommand} method
     * throws an {@link IllegalArgumentException} with the expected message.
     * </p>
     * <p>
     * The test follows these steps:
     * <ul>
     *   <li>Mocks the necessary dependencies: {@link DeviceRepository}, {@link ActuatorTypeRepository},
     *   {@link ActuatorFactory}, and {@link ActuatorRepository}.</li>
     *   <li>Initializes an instance of {@link ActuatorServiceImpl} with the mocked dependencies.</li>
     *   <li>Mocks an {@link ActuatorIDVO} and a command string.</li>
     *   <li>Defines the expected exception message.</li>
     *   <li>Invokes the {@code executeCommand} method of the service with the mocked actuator ID and command.</li>
     *   <li>Asserts that the thrown exception is an {@link IllegalArgumentException} with the expected message.</li>
     * </ul>
     * </p>
     */
    @Test
    void whenActuatorIsNotFoundByID_executeCommandThrowsIllegalArgument(){
        // Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository,actuatorTypeRepository,
                actuatorFactory,actuatorRepository);

        ActuatorIDVO actuatorIDVO = mock(ActuatorIDVO.class);
        String command = "Will not get here";

        String expected = "Actuator not found";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.executeCommand(actuatorIDVO,command));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * Test method for {@link ActuatorServiceImpl#executeCommand(ActuatorIDVO, String)}.
     * <p>
     * This test verifies that when the simulated hardware method fails and throws an
     * {@link IllegalArgumentException}, the {@code executeCommand} method propagates this exception
     * with the expected message.
     * </p>
     * <p>
     * The test follows these steps:
     * <ul>
     *   <li>Mocks the necessary dependencies: {@link DeviceRepository}, {@link ActuatorTypeRepository},
     *   {@link ActuatorFactory}, and {@link ActuatorRepository}.</li>
     *   <li>Initializes the actuator ID and mocks the {@link SwitchActuator} instance.</li>
     *   <li>Sets up the necessary conditions for the {@link ActuatorRepository} and the actuator
     *   execution.</li>
     *   <li>Throws an {@link IllegalArgumentException} when the actuator's simulated hardware method
     *   fails.</li>
     *   <li>Initializes the {@link ActuatorServiceImpl} with the mocked dependencies.</li>
     *   <li>Calls the {@code executeCommand} method of the service with the specified actuator ID and command
     *   and expects an {@link IllegalArgumentException} to be thrown.</li>
     *   <li>Asserts that the exception message matches the expected message.</li>
     * </ul>
     * </p>
     */
    @Test
    void whenSimHardwareMethodFails_executeCommandPropagatesIllegalArgument(){
        // Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        SwitchActuator actuator = mock(SwitchActuator.class);

        String command = "1";

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);

        IllegalArgumentException simException = new IllegalArgumentException("Hardware error: Value was not set");

        when(actuator.executeCommand(this.externalService,command)).thenThrow(simException);

        String expected = "Hardware error: Value was not set";

        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.executeCommand(actuatorIDVO,command));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Test method for {@link ActuatorServiceImpl#executeCommand(ActuatorIDVO, String)}.
     * <p>
     * This test verifies that when an actuator fails to save after executing a command,
     * the service throws an {@link IllegalArgumentException} with the expected message.
     * </p>
     * <p>
     * The test follows these steps:
     * <ul>
     *   <li>Mocks the necessary dependencies: {@link DeviceRepository}, {@link ActuatorTypeRepository},
     *   {@link ActuatorFactory}, and {@link ActuatorRepository}.</li>
     *   <li>Initializes the actuator ID and mocks the {@link SwitchActuator} instance.</li>
     *   <li>Sets up the necessary conditions for the {@link ActuatorRepository} and the actuator
     *   execution.</li>
     *   <li>Throws an {@link IllegalArgumentException} when the actuator fails to save.</li>
     *   <li>Initializes the {@link ActuatorServiceImpl} with the mocked dependencies.</li>
     *   <li>Calls the {@code executeCommand} method of the service with the specified actuator ID and command
     *   and expects an {@link IllegalArgumentException} to be thrown.</li>
     *   <li>Asserts that the exception message matches the expected message.</li>
     * </ul>
     * </p>
     */
    @Test
    void whenUpdatedActuatorFailsToSave_executeCommandThrowsIllegalArgument(){
        // Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        SwitchActuator actuator = mock(SwitchActuator.class);

        String command = "1";

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);
        when(actuatorRepository.save(actuator)).thenReturn(false);
        when(actuator.executeCommand(this.externalService,command)).thenReturn("1");

        String expected = "Unable to save";

        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.executeCommand(actuatorIDVO,command));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }

    /**
     * Test method for {@link ActuatorServiceImpl#executeCommand(ActuatorIDVO, String)}.
     * <p>
     * This test verifies that when a command is executed successfully and the actuator is saved,
     * the service returns the correct actuator instance.
     * </p>
     * <p>
     * The test follows these steps:
     * <ul>
     *   <li>Mocks the necessary dependencies: {@link DeviceRepository}, {@link ActuatorTypeRepository},
     *   {@link ActuatorFactory}, and {@link ActuatorRepository}.</li>
     *   <li>Initializes the actuator ID and mocks the {@link SwitchActuator} instance.</li>
     *   <li>Sets up the necessary conditions for the {@link ActuatorRepository} and the actuator
     *   execution.</li>
     *   <li>Initializes the {@link ActuatorServiceImpl} with the mocked dependencies.</li>
     *   <li>Calls the {@code executeCommand} method of the service with the specified actuator ID and command.</li>
     *   <li>Asserts that the result of the command execution is the expected actuator instance.</li>
     * </ul>
     * </p>
     */
    @Test
    void whenExecuteCommandProceedsSuccessfullyAndActuatorIsSaved_thenReturnsActuatorInstance(){
        // Arrange
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);
        ActuatorFactory actuatorFactory = mock(ActuatorFactory.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        String actuatorId = "f642fa85-4562-b3fc-5717-6afa62c963f6";
        ActuatorIDVO actuatorIDVO = new ActuatorIDVO(UUID.fromString(actuatorId));
        SwitchActuator actuator = mock(SwitchActuator.class);

        String command = "1";

        when(actuatorRepository.isPresent(actuatorIDVO)).thenReturn(true);
        when(actuatorRepository.findById(actuatorIDVO)).thenReturn(actuator);
        when(actuatorRepository.save(actuator)).thenReturn(true);
        when(actuator.executeCommand(this.externalService,command)).thenReturn("1");

        ActuatorServiceImpl service = new ActuatorServiceImpl(deviceRepository, actuatorTypeRepository,
                actuatorFactory, actuatorRepository);

        // Act
        Actuator result = service.executeCommand(actuatorIDVO,command);

        // Assert
        assertEquals(actuator, result);
    }
}
