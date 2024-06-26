package smarthome.controller;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.BeforeAll;
import smarthome.domain.actuator.*;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.device.Device;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.*;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.dto.ActuatorDTO;
import smarthome.mapper.dto.ActuatorTypeDTO;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.ActuatorTypeRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.service.ActuatorServiceImpl;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.devicevo.DeviceStatusVO;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This test class includes ActuatorTypeService instantiations that may appear unused.
 * The reason for this is that the service initialization automatically populates the `actuatorTypeRepository`.
 * Without this step, when attempting to verify that the actuator type exists, the result will always fail
 * as the repository would be empty.
 */

class AddActuatorToDeviceCTRLTest {

    private static String filePath;

    @BeforeAll
    public static void setUp() {
        filePath = "config.properties";
    }

    /**
     * Test method to verify that when a null ActuatorService is provided to the AddActuatorToDeviceCTRL constructor,
     * it throws an IllegalArgumentException with the expected error message.
     * This test ensures that the controller correctly handles a null ActuatorService input.
     */
    @Test
    void whenNullActuatorService_constructorThrowsIllegalArgumentException() {
        //Arrange
        String expectedMessage = "Invalid service";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AddActuatorToDeviceCTRL(null));

        //Assert
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    /**
     * Test case to verify the behavior of adding a switch actuator to a device.
     * The test performs the following steps:
     * 1. Initializes a device (Device1) with a name, model, and room.
     * 2. Initializes a switch actuator (Actuator1) and adds it to a list of actuators.
     * 3. Initializes an actuator type (SwitchActuator) and sets up mock repositories for devices, actuators, and
     * actuator types, and simulate the behavior of repositories and objects in a controlled environment.
     * 4. Initializes service and factory objects required for the test.
     * 5. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 6. Constructs DTOs for the device, actuator type, and actuator.
     * 7. Calls the method under test (addActuatorToDevice) and captures the result.
     * 8. Asserts that the operation succeeded (a result is true).
     * 9. Verifies that the added switch actuator is present in the actuator repository and its attributes match the
     * expected values.
     */
    @Test
    void  whenAddSwitchActuatorToDevice_thenReturnTrue() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //Actuator1 initialized and added to a List
        List<Actuator> actuatorList = new ArrayList<>();
        ActuatorNameVO actuatorName1 = new ActuatorNameVO("Actuator1");
        ActuatorTypeIDVO actType1 = new ActuatorTypeIDVO("SwitchActuator");
        SwitchActuator actuator1 = new SwitchActuator(actuatorName1,actType1,device1.getId());
        actuatorList.add(actuator1);

        //ActuatorType initialized
        ActuatorType actuatorType = new ActuatorType(actType1);

        //A mock DeviceRepository object with predefined behaviors for its findById method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO1)).thenReturn(device1);

        //A mock ActuatorRepository object with predefined behaviors for its findById, save, and findAll methods.
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        when(actuatorRepositoryDouble.findById(any(ActuatorIDVO.class))).thenReturn(actuator1);
        when(actuatorRepositoryDouble.save(any(Actuator.class))).thenReturn(true);
        when(actuatorRepositoryDouble.findAll()).thenReturn(actuatorList);

        //A mock ActuatorTypeRepository object with predefined behaviors for its findById, isPresent, and save methods.
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeRepositoryDouble.findById(any(ActuatorTypeIDVO.class))).thenReturn(actuatorType);
        when(actuatorTypeRepositoryDouble.isPresent(any(ActuatorTypeIDVO.class))).thenReturn(true);
        when(actuatorTypeRepositoryDouble.save(any(ActuatorType.class))).thenReturn(true);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="SwitchActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);

        //This assertion asserts that the added switch actuator is present in the actuator repository

        //Fetch the added switch actuator from repository
        Iterable<Actuator> actuatorIterable1 = actuatorRepositoryDouble.findAll();
        SwitchActuator addedActuator = (SwitchActuator) IterableUtils.get(actuatorIterable1,0);

        //Query the fetched switch actuator for it's attributes
        String resultName = addedActuator.getActuatorName().getValue();
        String resultTypeID = addedActuator.getActuatorTypeID().getID();
        DeviceIDVO resultDeviceID = addedActuator.getDeviceID();

        //Compare switch actuator attributes with the ones given in its creation
        assertEquals(actuatorNameVO,resultName);
        assertEquals(actuatorTypeID,resultTypeID);
        assertEquals(deviceIDVO1,resultDeviceID);
    }

    /**
     * Test case to verify the behavior of adding an Integer Value Actuator to a device with valid integer settings.
     * The test performs the following steps:
     * 1. Initializes a device (Device1) with a name, model, and room.
     * 2. Initializes an Integer Value Actuator (Actuator1) with integer settings and adds it to a list of actuators.
     * 3. Initializes an actuator type (IntegerValueActuator) and sets up mock repositories for devices, actuators, and actuator types,
     *    and simulate the behavior of repositories and objects in a controlled environment.
     * 4. Initializes service and factory objects required for the test.
     * 5. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 6. Constructs DTOs for the device, actuator type, and actuator, including the upper and lower integer limits.
     * 7. Calls the method under test (addActuatorToDevice) and captures the result.
     * 8. Asserts that the operation succeeded (result is true).
     * 9. Verifies that the added Integer Value Actuator is present in the actuator repository and its attributes match
     *    the expected values, including the integer settings.
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void addIntegerValueActuatorToDevice_WhenValidIntegerSettings_thenReturnTrue() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //Actuator1 initialized and added to a List
        List<Actuator> actuatorList = new ArrayList<>();
        Settings settings = new IntegerSettingsVO("1","2");
        ActuatorNameVO actuatorName1 = new ActuatorNameVO("Actuator1");
        ActuatorTypeIDVO actType1 = new ActuatorTypeIDVO("IntegerValueActuator");
        IntegerValueActuator actuator1 = new IntegerValueActuator(actuatorName1,actType1,device1.getId(),settings);
        actuatorList.add(actuator1);

        //ActuatorType initialized
        ActuatorType actuatorType = new ActuatorType(actType1);

        //A mock DeviceRepository object with predefined behaviors for its findById method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO1)).thenReturn(device1);

        //A mock ActuatorRepository object with predefined behaviors for its findById, save, and findAll methods.
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        when(actuatorRepositoryDouble.findById(any(ActuatorIDVO.class))).thenReturn(actuator1);
        when(actuatorRepositoryDouble.save(any(Actuator.class))).thenReturn(true);
        when(actuatorRepositoryDouble.findAll()).thenReturn(actuatorList);

        //A mock ActuatorTypeRepository object with predefined behaviors for its findById, isPresent, and save methods.
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeRepositoryDouble.findById(any(ActuatorTypeIDVO.class))).thenReturn(actuatorType);
        when(actuatorTypeRepositoryDouble.isPresent(any(ActuatorTypeIDVO.class))).thenReturn(true);
        when(actuatorTypeRepositoryDouble.save(any(ActuatorType.class))).thenReturn(true);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="IntegerValueActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        String upperLimit = "2";
        String downLimit = "1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .lowerLimit(downLimit)
                                            .upperLimit(upperLimit)
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);

        //This assertion asserts that the added Integer Value Actuator is present in actuator repository

        //Fetch the added Integer Value Actuator from repository
        Iterable<Actuator> actuatorIterable1 = actuatorRepositoryDouble.findAll();
        IntegerValueActuator addedActuator = (IntegerValueActuator) IterableUtils.get(actuatorIterable1,0);

        //Query the fetched Integer Value Actuator for it's attributes
        String resultName = addedActuator.getActuatorName().getValue();
        String resultTypeID = addedActuator.getActuatorTypeID().getID();
        DeviceIDVO resultDeviceID = addedActuator.getDeviceID();
        Integer[] resultIntegerSettings = addedActuator.getIntegerSettings().getValue();


        //Compare Integer Value Actuator attributes with the ones given in its creation
        assertEquals(actuatorNameVO,resultName);
        assertEquals(actuatorTypeID,resultTypeID);
        assertEquals(deviceIDVO1,resultDeviceID);

        int upperLimitInt = Integer.parseInt(upperLimit);
        int downLimitInt = Integer.parseInt(downLimit);
        Integer[] expectedIntegerSettings = {downLimitInt,upperLimitInt,};

        assertArrayEquals(expectedIntegerSettings,resultIntegerSettings);
    }

    /**
     * Test case to verify the behavior of adding a Decimal Value Actuator to a device with valid decimal settings.
     * The test performs the following steps:
     * 1. Initializes a device (Device1) with a name, model, and room.
     * 2. Initializes a Decimal Value Actuator (Actuator1) with decimal settings and adds it to a list of actuators.
     * 3. Initializes an actuator type (DecimalValueActuator) and sets up mock repositories for devices, actuators, and actuator types,
     *    and simulate the behavior of repositories and objects in a controlled environment.
     * 4. Initializes service and factory objects required for the test.
     * 5. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 6. Constructs DTOs for the device, actuator type, and actuator, including the upper and lower decimal limits and precision.
     * 7. Calls the method under test (addActuatorToDevice) and captures the result.
     * 8. Asserts that the operation succeeded (result is true).
     * 9. Verifies that the added Decimal Value Actuator is present in the actuator repository and its attributes match the expected values, including the decimal settings.
     *
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void addDecimalValueActuatorToDevice_WhenValidDecimalSettings_thenReturnTrue() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //Actuator1 initialized and added to a List
        List<Actuator> actuatorList = new ArrayList<>();
        Settings settings = new DecimalSettingsVO("1.1","2.1","0.1");
        ActuatorNameVO actuatorName1 = new ActuatorNameVO("Actuator1");
        ActuatorTypeIDVO actType1 = new ActuatorTypeIDVO("DecimalValueActuator");
        DecimalValueActuator actuator1 = new DecimalValueActuator(actuatorName1,actType1,device1.getId(),settings);
        actuatorList.add(actuator1);

        //ActuatorType initialized
        ActuatorType actuatorType = new ActuatorType(actType1);

        //A mock DeviceRepository object with predefined behaviors for its findById method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO1)).thenReturn(device1);

        //A mock ActuatorRepository object with predefined behaviors for its findById, save, and findAll methods.
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        when(actuatorRepositoryDouble.findById(any(ActuatorIDVO.class))).thenReturn(actuator1);
        when(actuatorRepositoryDouble.save(any(Actuator.class))).thenReturn(true);
        when(actuatorRepositoryDouble.findAll()).thenReturn(actuatorList);

        //A mock ActuatorTypeRepository object with predefined behaviors for its findById, isPresent, and save methods.
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeRepositoryDouble.findById(any(ActuatorTypeIDVO.class))).thenReturn(actuatorType);
        when(actuatorTypeRepositoryDouble.isPresent(any(ActuatorTypeIDVO.class))).thenReturn(true);
        when(actuatorTypeRepositoryDouble.save(any(ActuatorType.class))).thenReturn(true);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="DecimalValueActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        String upperLimit = "2.1";
        String downLimit = "1.1";
        String precision = "0.1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .lowerLimit(downLimit)
                                            .upperLimit(upperLimit)
                                            .precision(precision)
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation succeeded
        assertTrue(result);

        //This assertion asserts that the added Integer Value Actuator is present in actuator repository

        //Fetch the added Integer Value Actuator from repository
        Iterable<Actuator> actuatorIterable1 = actuatorRepositoryDouble.findAll();
        DecimalValueActuator addedActuator = (DecimalValueActuator) IterableUtils.get(actuatorIterable1,0);

        //Query the fetched Integer Value Actuator for it's attributes
        String resultName = addedActuator.getActuatorName().getValue();
        String resultTypeID = addedActuator.getActuatorTypeID().getID();
        DeviceIDVO resultDeviceID = addedActuator.getDeviceID();
        Double[] resultDecimalSettings = addedActuator.getDecimalSettings().getValue();


        //Compare Integer Value Actuator attributes with the ones given in its creation
        assertEquals(actuatorNameVO,resultName);
        assertEquals(actuatorTypeID,resultTypeID);
        assertEquals(deviceIDVO1,resultDeviceID);

        double upperLimitDouble = Double.parseDouble(upperLimit);
        double downLimitDouble = Double.parseDouble(downLimit);
        double precisionDouble = Double.parseDouble(precision);
        Double[] expectedDoubleSettings = {downLimitDouble,upperLimitDouble,precisionDouble};

        assertArrayEquals(expectedDoubleSettings,resultDecimalSettings);
    }

    /**
     * Test case to verify the behavior of adding a Decimal Value Actuator to a device when the lower limit is bigger than the upper limit.
     * The test performs the following steps:
     * 1. Initializes a device (Device1) with a name, model, and room.
     * 2. Initializes mock objects for repositories.
     * 3. Initializes service and factory objects required for the test.
     * 4. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 5. Constructs DTOs for the device, actuator type, and actuator, including the upper and lower decimal limits and precision.
     * 6. Calls the method under test (addActuatorToDevice) and captures the result.
     * 7. Asserts that the operation did not succeed (result is false), indicating that the lower limit is bigger than the upper limit.
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void addDecimalValueActuatorToDevice_WhenLowerLimitBiggerThanUpperLimit_thenReturnFalse() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //Mock's for DeviceRepository, ActuatorRepository and ActuatorTypeRepository objects
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="DecimalValueActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization with lower limit bigger than upper limit
        String actuatorNameVO = "Actuator1";
        String upperLimit = "0.5";
        String downLimit = "0.6";
        String precision = "0.1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .lowerLimit(downLimit)
                                            .upperLimit(upperLimit)
                                            .precision(precision)
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation did not succeed
        assertFalse(result);
    }

    /**
     * Test case to verify the behavior of adding a Decimal Value Actuator to a device when the precision is negative.
     * The test performs the following steps:
     * 1. Initializes a device (Device1) with a name, model, and room.
     * 2. Initializes mock objects for repositories.
     * 3. Initializes service and factory objects required for the test.
     * 4. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 5. Constructs DTOs for the device, actuator type, and actuator, including the upper and lower decimal limits and precision.
     * 6. Calls the method under test (addActuatorToDevice) and captures the result.
     * 7. Asserts that the operation did not succeed (result is false), indicating that the precision is negative.
     *
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void addDecimalValueActuatorToDevice_WhenNegativePrecision_thenReturnFalse() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //Mocks for DeviceRepository, ActuatorRepository and ActuatorTypeRepository objects
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="DecimalValueActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization with negative precision
        String actuatorNameVO = "Actuator1";
        String upperLimit = "0.5";
        String downLimit = "0.3";
        String precision = "-0.1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .lowerLimit(downLimit)
                                            .upperLimit(upperLimit)
                                            .precision(precision)
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation did not succeed
        assertFalse(result);

    }

    /**
     * Test case to verify that adding a Decimal Value Actuator with integer value settings returns false.
     * The test performs the following steps:
     * 1. Initializes a device (Device1) with a name, model, and room.
     * 2. Initializes mock objects for repositories.
     * 3. Initializes service and factory objects required for the test.
     * 4. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 5. Constructs DTOs for the device, actuator type, and actuator with integer value settings.
     * 6. Calls the method under test (addActuatorToDevice) and captures the result.
     * 7. Asserts that the operation returns false, indicating that adding a Decimal Value Actuator with integer value settings is not allowed.
     *
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void addDecimalValueActuator_givenIntegerValueSettings_ShouldReturnFalse() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //Mocks for DeviceRepository, ActuatorRepository and ActuatorTypeRepository objects
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="DecimalValueActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        String upperLimit = "1";
        String downLimit = "3";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .lowerLimit(downLimit)
                                            .upperLimit(upperLimit)
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that adding an actuator to a device with inactive status returns false.
     * The test performs the following steps:
     * 1. Initializes a device (Device1) with a name, model, and room, and deactivates it.
     * 2. Initializes an actuator (Actuator1) and adds it to a list.
     * 3. Initializes mock objects for repositories and simulate the behavior of repositories and objects in a controlled environment.
     * 4. Initializes service and factory objects required for the test.
     * 5. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 6. Constructs DTOs for the device, actuator type, and actuator.
     * 7. Calls the method under test (addActuatorToDevice) and captures the result.
     * 8. Asserts that the operation returns false, indicating that adding the actuator to the device with inactive status is not allowed.
     *
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void whenAddActuatorToDeviceWithInactiveStatus_thenReturnFalse() throws ConfigurationException {

        //Arrange
        //Device1 initialized and deactivated
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();
        device1.deactivateDevice();

        //A mock DeviceRepository object with predefined behaviors for its findById method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO1)).thenReturn(device1);

        //A mock ActuatorRepository object with predefined behaviors for its findById, save, and findAll methods.
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);

        //A mock ActuatorTypeRepository object with predefined behaviors for its findById, isPresent, and save methods.
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="SwitchActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation did not succeed
        assertFalse(result);
    }

    /**
     * Test case to verify that adding an actuator to a non-existent device returns false.
     * The test performs the following steps:
     * 1. Initializes mock objects for DeviceRepository, ActuatorRepository, and ActuatorTypeRepository.
     * 2. Configures predefined behaviors for the ActuatorTypeRepository methods and simulate the behavior of the
     *    ActuatorTypeRepository in a controlled environment.
     * 3. Initializes service and factory objects required for the test.
     * 4. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 5. Constructs DTOs for the non-existent device, actuator type, and actuator.
     * 6. Calls the method under test (addActuatorToDevice) and captures the result.
     * 7. Asserts that the operation returns false, indicating that adding the actuator to the non-existent device is not allowed.
     *
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void whenAddActuatorToDeviceNonExistent_thenReturnFalse() throws ConfigurationException {

        //Arrange
        //Mocks for DeviceRepository, ActuatorRepository and ActuatorTypeRepository objects
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        when(deviceRepositoryDouble.findById(deviceIDVO)).thenReturn(null);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);

        //A mock ActuatorTypeRepository object with predefined behaviors for its isPresent, and save methods.
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        String deviceID = UUID.randomUUID().toString();
        String deviceName = "DeviceName";
        String deviceModel = "DeviceModel";
        String deviceStatus = "true";
        String roomId = "RoomId";
        DeviceDTO deviceDTO = new DeviceDTO(deviceID,deviceName,deviceModel,deviceStatus,roomId);

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="SwitchActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation did not succeed
        assertFalse(result);

    }

    /**
     * Test case to verify that adding an actuator with a non-existent actuator type returns false.
     * The test performs the following steps:
     * 1. Initializes a device and mock objects for DeviceRepository, ActuatorRepository, and ActuatorTypeRepository.
     * 2. Initializes service and factory objects required for the test.
     * 3. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 4. Constructs DTOs for the device, non-existent actuator type, and actuator.
     * 5. Calls the method under test (addActuatorToDevice) and captures the result.
     * 6. Asserts that the operation returns false, indicating that adding the actuator with a non-existent actuator type is not allowed.
     * 7. Verifies that the actuator repository is empty after the operation.
     *
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void addActuatorToDevice_WhenActuatorTypeDoesNotExist_thenReturnFalse() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1,model1,room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //A mock for DeviceRepository object
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO1)).thenReturn(device1);

        //A mock for ActuatorRepository object
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);

        //A mock for ActuatorTypeRepository object
        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO("SwithActuator");
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);
        when(actuatorTypeRepositoryDouble.isPresent(actuatorTypeIDVO)).thenReturn(false);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID ="HydraulicActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert

        //This assertion asserts that the operation did not succeed
        assertFalse(result);
    }

    /**
     * Test case to verify that adding an integer value actuator with decimal value settings returns false.
     * The test performs the following steps:
     * 1. Initializes a device and mock objects for DeviceRepository, ActuatorRepository, and ActuatorTypeRepository.
     * 2. Initializes service and factory objects required for the test.
     * 3. Initializes a controller (AddActuatorToDeviceCTRL) to add the actuator to the device.
     * 4. Constructs DTOs for the device, actuator type, and actuator with decimal value settings.
     * 5. Calls the method under test (addActuatorToDevice) and captures the result.
     * 6. Asserts that the operation returns false, indicating that adding the actuator with decimal value settings is not allowed.
     * 7. Verifies that the actuator repository is empty after the operation.
     *
     * @throws ConfigurationException if an error occurs during configuration loading.
     */
    @Test
    void addIntegerValueActuator_givenDecimalValueSettings_ShouldReturnFalse() throws ConfigurationException {

        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1, model1, room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //Mocks for DeviceRepository, ActuatorRepository and ActuatorTypeRepository
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        ActuatorTypeRepository actuatorTypeRepositoryDouble = mock(ActuatorTypeRepository.class);

        //ActuatorFactoryImpl initialized
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialized
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble, actuatorTypeRepositoryDouble, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(), deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID = "IntegerValueActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        String upperLimit = "0.9";
        String downLimit = "0.1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .lowerLimit(downLimit)
                                            .upperLimit(upperLimit)
                                            .build();

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        // Assert
        assertFalse(result);
        Iterable<Actuator> actuatorIterable = actuatorRepositoryDouble.findAll();

        //Act

        boolean isRepoEmpty = IterableUtils.isEmpty(actuatorIterable);

        // Assert
        assertTrue(isRepoEmpty); //This assertion verifies that actuator repository is empty
    }

    /**
     * Test case to verify that when the ActuatorRepository fails to save an Actuator,
     * the addActuatorToDevice method in the AddActuatorToDeviceCTRL class returns false.
     *
     * @throws ConfigurationException If there is an error in configuration.
     */
    @Test
    void whenActuatorRepositorySaveFails_thenAddActuatorReturnsFalse() throws ConfigurationException {
        //Arrange
        //Device1 initialized
        DeviceNameVO name1 = new DeviceNameVO("name1");
        DeviceModelVO model1 = new DeviceModelVO("model1");
        RoomIDVO room1 = new RoomIDVO(UUID.randomUUID());
        Device device1 = new Device(name1, model1, room1);
        DeviceIDVO deviceIDVO1 = device1.getId();

        //A mock for DeviceRepository object
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO1)).thenReturn(device1);

        //A mock for ActuatorTypeRepository object
        ActuatorTypeRepository actuatorTypeRepository = mock(ActuatorTypeRepository.class);

        //A mock for ActuatorRepository object
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        Actuator actuator = mock(Actuator.class);
        when(actuatorRepositoryDouble.save(actuator)).thenReturn(false);

        //ActuatorFactory initialization
        ActuatorFactoryImpl actuatorFactoryImpl = new ActuatorFactoryImpl(filePath);

        //ActuatorServiceImpl initialization
        ActuatorServiceImpl actuatorServiceImpl = new ActuatorServiceImpl(deviceRepositoryDouble,
                actuatorTypeRepository, actuatorFactoryImpl, actuatorRepositoryDouble);

        //Controller initialization
        AddActuatorToDeviceCTRL addActuatorToDeviceCTRL = new AddActuatorToDeviceCTRL(actuatorServiceImpl);

        //DeviceDTO initialization
        DeviceStatusVO deviceStatusVO = device1.getDeviceStatus();
        DeviceDTO deviceDTO = new DeviceDTO(deviceIDVO1.getID(), name1.getValue(), model1.getValue(),
                deviceStatusVO.getValue().toString(), room1.getID());

        //ActuatorTypeDTO initialization
        String actuatorTypeID = "HydraulicActuator";
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeID);

        //ActuatorDTO initialization
        String actuatorNameVO = "Actuator1";
        ActuatorDTO actuatorDTO = ActuatorDTO.builder()
                                            .actuatorName(actuatorNameVO)
                                            .actuatorTypeID(actuatorTypeID)
                                            .deviceID(deviceIDVO1.getID())
                                            .build();

        ActuatorTypeIDVO actuatorTypeIDVO = new ActuatorTypeIDVO(actuatorTypeID);

        when(actuatorTypeRepository.isPresent(actuatorTypeIDVO)).thenReturn(true);

        //Act
        boolean result = addActuatorToDeviceCTRL.addActuatorToDevice(actuatorDTO, actuatorTypeDTO, deviceDTO);

        //Assert
        assertFalse(result);
    }
}