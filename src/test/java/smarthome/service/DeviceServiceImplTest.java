package smarthome.service;


import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.SensorRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceServiceImplTest {


    //DEVICE SERVICE IMPL TESTS

    /**
     * Test to verify that the DeviceServiceImpl constructor throws an IllegalArgumentException
     * when the RoomRepository parameter is null. The test arranges a mock DeviceFactory, DeviceRepository, SensorRepository, and ActuatorRepository,
     * and then asserts that an IllegalArgumentException is thrown when the DeviceServiceImpl constructor
     * is called with a null RoomRepository. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenNullRoomRepository_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = null;
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the DeviceServiceImpl constructor throws an IllegalArgumentException
     * when the DeviceFactory parameter is null. The test arranges a mock RoomRepository, DeviceRepository, SensorRepository, and ActuatorRepository,
     * and then asserts that an IllegalArgumentException is thrown when the DeviceServiceImpl constructor
     * is called with a null DeviceFactory. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenNullDeviceFactory_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = null;
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the DeviceServiceImpl constructor throws an IllegalArgumentException
     * when the DeviceRepository parameter is null. The test arranges a mock RoomRepository, DeviceFactory, SensorRepository, and ActuatorRepository,
     * and then asserts that an IllegalArgumentException is thrown when the DeviceServiceImpl constructor
     * is called with a null DeviceRepository. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenNullDeviceRepository_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = null;
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the DeviceServiceImpl constructor throws an IllegalArgumentException
     * when the SensorRepository parameter is null. The test arranges a mock RoomRepository, DeviceFactory, DeviceRepository, and ActuatorRepository,
     * and then asserts that an IllegalArgumentException is thrown when the DeviceServiceImpl constructor
     * is called with a null SensorRepository. The test also verifies that the exception message matches the expected message.
     */

    @Test
    void whenNullSensorRepository_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = null;
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the DeviceServiceImpl constructor throws an IllegalArgumentException
     * when the ActuatorRepository parameter is null. The test arranges a mock RoomRepository, DeviceFactory, DeviceRepository, and SensorRepository,
     * and then asserts that an IllegalArgumentException is thrown when the DeviceServiceImpl constructor
     * is called with a null ActuatorRepository. The test also verifies that the exception message matches the expected message.
     */

    @Test
    void whenNullActuatorRepository_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = null;
        String expected = "Invalid parameters";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the DeviceServiceImpl constructor correctly instantiates the DeviceService
     * when all parameters are valid. The test arranges mock RoomRepository, DeviceFactory, DeviceRepository, SensorRepository, and ActuatorRepository,
     * and then asserts that a DeviceServiceImpl object is successfully created when the constructor is called with these valid parameters.
     */
    @Test
    void whenValidParameters_thenDeviceServiceInstantiated() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);

        //Act
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);

        //Assert
        assertNotNull(deviceService);
    }

    // ADD DEVICE METHOD TESTS

    /**
     * Test to verify that the addDevice method throws an IllegalArgumentException
     * when the DeviceNameVO parameter is null. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository, SensorRepository, ActuatorRepository,
     * DeviceModelVO, and RoomIDVO. It then asserts that an IllegalArgumentException is thrown when the addDevice method
     * is called with a null DeviceNameVO. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenDeviceNameIsInvalid_ThenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceModelVO deviceModelIDVO = mock(DeviceModelVO.class);
        RoomIDVO roomIDVO = mock(RoomIDVO.class);
        String expected = "DeviceNameVO, DeviceModelVO and RoomIDVO cannot be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.addDevice(null, deviceModelIDVO, roomIDVO));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the addDevice method throws an IllegalArgumentException
     * when the DeviceModelVO parameter is null. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceNameVO, and RoomIDVO. It then asserts that an IllegalArgumentException is thrown when the addDevice method
     * is called with a null DeviceModelVO. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenDeviceModelIsInvalid_ThenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceNameVO deviceName = mock(DeviceNameVO.class);
        RoomIDVO roomIDVO = mock(RoomIDVO.class);
        String expected = "DeviceNameVO, DeviceModelVO and RoomIDVO cannot be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.addDevice(deviceName, null, roomIDVO));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the addDevice method throws an IllegalArgumentException
     * when the RoomIDVO parameter is null. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceNameVO, and DeviceModelVO. It then asserts that an IllegalArgumentException is thrown when the addDevice method
     * is called with a null RoomIDVO. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenRoomIDIsInvalid_ThenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceModelVO deviceModelIDVO = mock(DeviceModelVO.class);
        DeviceNameVO deviceNameVO = mock(DeviceNameVO.class);
        String expected = "DeviceNameVO, DeviceModelVO and RoomIDVO cannot be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.addDevice(deviceNameVO, deviceModelIDVO, null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the addDevice method throws an IllegalArgumentException
     * when the RoomIDVO does not exist in the RoomRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceNameVO, DeviceModelVO, and RoomIDVO. It then sets the behavior of the RoomRepository to return false when isPresent is called.
     * It then asserts that an IllegalArgumentException is thrown when the addDevice method is called. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenRoomIsNotPresent_ThenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceNameVO deviceNameVO = mock(DeviceNameVO.class);
        DeviceModelVO deviceModelIDVO = mock(DeviceModelVO.class);
        RoomIDVO roomIDVO = mock(RoomIDVO.class);
        String expected = "Room with ID: " + roomIDVO + " is not present.";

        when(roomRepository.isPresent(roomIDVO)).thenReturn(false);

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.addDevice(deviceNameVO, deviceModelIDVO, roomIDVO));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the addDevice method returns an empty Optional
     * when the Device is not saved in the DeviceRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceNameVO, DeviceModelVO, and RoomIDVO. It then sets the behavior of the RoomRepository to return true when isPresent is called,
     * and the behavior of the DeviceRepository to return false when save is called. It then asserts that the result of the addDevice method is an empty Optional.
     */
    @Test
    void whenDeviceIsNotSaved_thenReturnsEmptyOptional() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceNameVO deviceNameVO = mock(DeviceNameVO.class);
        DeviceModelVO deviceModelIDVO = mock(DeviceModelVO.class);
        RoomIDVO roomIDVO = mock(RoomIDVO.class);

        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.save(deviceFactory.createDevice(deviceNameVO, deviceModelIDVO, roomIDVO))).thenReturn(false);

        //Act
        Optional<Device> result = deviceService.addDevice(deviceNameVO, deviceModelIDVO, roomIDVO);

        //Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test to verify that the addDevice method returns an Optional of Device
     * when the Device is saved in the DeviceRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceNameVO, DeviceModelVO, RoomIDVO, and Device. It then sets the behavior of the RoomRepository to return true when isPresent is called,
     * the behavior of the DeviceFactoryImpl to return the mock Device when createDevice is called, and the behavior of the DeviceRepository to return true when save is called.
     * It then asserts that the result of the addDevice method is an Optional containing the mock Device.
     */
    @Test
    void whenDeviceIsSaved_thenReturnsOptionalOfDevice() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceNameVO deviceNameVO = mock(DeviceNameVO.class);
        DeviceModelVO deviceModelIDVO = mock(DeviceModelVO.class);
        RoomIDVO roomIDVO = mock(RoomIDVO.class);
        Device device = mock(Device.class);

        when(deviceFactory.createDevice(deviceNameVO, deviceModelIDVO, roomIDVO)).thenReturn(device);
        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.save(device)).thenReturn(true);

        //Act
        Optional<Device> result = deviceService.addDevice(deviceNameVO, deviceModelIDVO, roomIDVO);

        //Assert
        assertTrue(result.isPresent());
    }

    // DEACTIVATE DEVICE METHOD TESTS

    /**
     * Test to verify that the deactivateDevice method throws an IllegalArgumentException
     * when the DeviceIDVO parameter is null. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository, SensorRepository, and ActuatorRepository.
     * It then asserts that an IllegalArgumentException is thrown when the deactivateDevice method
     * is called with a null DeviceIDVO. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenDeviceIDVOIsNull_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        String expected = "DeviceIDVO cannot be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> deviceService.deactivateDevice(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the deactivateDevice method
     * when the DeviceIDVO does not exist in the DeviceRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository and DeviceIDVO. It then sets the behavior of the DeviceRepository to return false when isPresent is called.
     * It then asserts that an empty optional is returned
     */
    @Test
    void whenDeviceIsNotPresent_thenShouldReturnEmptyOptional() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(false);

        //Act
        Optional<Device> deviceOptional = deviceService.deactivateDevice(deviceIDVO);

        //Assert
        assertTrue(deviceOptional.isEmpty());
    }

    /**
     * Test to verify that the deactivateDevice method throws an IllegalArgumentException
     * when the Device is not active. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository, SensorRepository,
     * ActuatorRepository, DeviceIDVO, and Device. It then sets the behavior of the DeviceRepository to return true when isPresent is called,
     * the behavior of the DeviceRepository to return the mock Device when findById is called, and the behavior of the Device to return false when isActive is called.
     * It then asserts that an IllegalArgumentException is thrown when the deactivateDevice method is called. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenDeviceIsNotActive_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Device device = mock(Device.class);
        String expected = "Device with ID: " + deviceIDVO + " is already deactivated.";

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(false);


        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.deactivateDevice(deviceIDVO));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the deactivateDevice method returns an empty Optional
     * when the Device is not deactivated. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceIDVO, and Device. It then sets the behavior of the DeviceRepository to return true when isPresent is called,
     * the behavior of the DeviceRepository to return the mock Device when findById is called, the behavior of the Device to return true when isActive is called,
     * the behavior of the Device to return false when deactivateDevice is called, and the behavior of the DeviceRepository to return true when update is called.
     * It then asserts that deactivateDevice method throws and Illegal Argument Exception.
     */

    @Test
    void whenDeviceIsNotDeactivated_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Device device = mock(Device.class);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(true);
        when(device.deactivateDevice()).thenReturn(false);
        String result = "Device could not be updated";

        //Act + //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.deactivateDevice(deviceIDVO));
        String expected = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the deactivateDevice method returns an empty Optional
     * when the Device is not updated in the DeviceRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceIDVO, and Device. It then sets the behavior of the DeviceRepository to return true when isPresent is called,
     * the behavior of the DeviceRepository to return the mock Device when findById is called, the behavior of the Device to return true when isActive is called,
     * the behavior of the Device to return true when deactivateDevice is called, and the behavior of the DeviceRepository to return false when update is called.
     * It then asserts that the result of deactivateDevice method throws and Illegal Argument Exception.
     */

    @Test
    void whenDeviceIsNotUpdated_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Device device = mock(Device.class);
        String result = "Device could not be updated";

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(true);
        when(device.deactivateDevice()).thenReturn(true);
        when(deviceRepository.update(device)).thenReturn(false);

        //Act + //Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.deactivateDevice(deviceIDVO));
        String expected = exception.getMessage();

        //Assert
        assertEquals(expected, result);

    }

    /**
     * Test to verify that the deactivateDevice method returns an Optional of Device
     * when the Device is correctly deactivated and updated in the DeviceRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, DeviceIDVO, and Device. It then sets the behavior of the DeviceRepository to return true when isPresent is called,
     * the behavior of the DeviceRepository to return the mock Device when findById is called, the behavior of the Device to return true when isActive is called,
     * the behavior of the Device to return true when deactivateDevice is called, and the behavior of the DeviceRepository to return true when update is called.
     * It then asserts that the result of the deactivateDevice method is an Optional containing the mock Device.
     */

    @Test
    void whenDeviceIsCorrectlyDeactivated_thenReturnsOptionalOfDevice() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        DeviceIDVO deviceIDVO = mock(DeviceIDVO.class);
        Device device = mock(Device.class);

        when(deviceRepository.isPresent(deviceIDVO)).thenReturn(true);
        when(deviceRepository.findById(deviceIDVO)).thenReturn(device);
        when(device.isActive()).thenReturn(true);
        when(device.deactivateDevice()).thenReturn(true);
        when(deviceRepository.update(device)).thenReturn(true);

        //Act
        Optional<Device> result = deviceService.deactivateDevice(deviceIDVO);

        //Assert
        assertTrue(result.isPresent());
    }
    //GETLISTOFDEVICESINAROOM METHOD TESTS

    /**
     * Test to verify that the getListOfDevicesInARoom method throws an IllegalArgumentException
     * when the RoomIDVO parameter is null. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository, SensorRepository, and ActuatorRepository.
     * It then asserts that an IllegalArgumentException is thrown when the getListOfDevicesInARoom method
     * is called with a null RoomIDVO. The test also verifies that the exception message matches the expected message.
     */

    @Test
    void whenRoomIDVOIsNull_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        String expected = "RoomIDVO cannot be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> deviceService.getListOfDevicesInARoom(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the getListOfDevicesInARoom method throws an IllegalArgumentException
     * when the RoomIDVO does not exist in the DeviceRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository and RoomIDVO. It then sets the behavior of the DeviceRepository to return an empty list when findByRoomID is called.
     * It then asserts that an IllegalArgumentException is thrown when the getListOfDevicesInARoom method is called. The test also verifies that the exception message matches the expected message.
     */
    @Test
    void whenRoomIsNotPresent_thenThrowsIllegalArgumentException() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        RoomIDVO roomIDVO = mock(RoomIDVO.class);
        String expected = "Room with ID: " + roomIDVO + " is not present.";

        when(deviceRepository.findByRoomID(roomIDVO)).thenReturn(Collections.emptyList());

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, ()
                -> deviceService.getListOfDevicesInARoom(roomIDVO));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test to verify that the getListOfDevicesInARoom method returns a list of Devices
     * when the RoomIDVO exists in the RoomRepository. The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository,
     * SensorRepository, ActuatorRepository, RoomIDVO, and a list of Devices. It then sets the behavior of the RoomRepository to return true when isPresent is called,
     * and the behavior of the DeviceRepository to return the list of Devices when findByRoomID is called.
     * It then asserts that the result of the getListOfDevicesInARoom method is the list of Devices.
     */
    @Test
    void whenGetListOfDevices_thenReturnListOfDevices() {
        //Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);
        RoomIDVO roomIDVO = mock(RoomIDVO.class);
        List<Device> devices = Collections.singletonList(mock(Device.class));

        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(deviceRepository.findByRoomID(roomIDVO)).thenReturn(devices);

        //Act
        List<Device> result = deviceService.getListOfDevicesInARoom(roomIDVO);

        //Assert
        assertEquals(devices, result);
    }

    //GETLISTOFDEVICEBYFUNCTIONALITY METHOD TESTS

    /**
     * This test verifies the functionality of the getListOfDevicesByFunctionality method in the DeviceService class.
     * It ensures that the method correctly associates devices with their respective functionalities and returns a map
     * where each key represents a functionality and each value is a list of devices associated with that functionality.
     * The test sets up various doubled dependencies, conditions their behavior, and then invokes the
     * getListOfDevicesByFunctionality method to obtain the result. It then compares the result with the expected
     * functionalities and associated device lists.
     */

    @Test
    void whenNoSensorsOrActuators_thenReturnsEmptyMap() {
        // Arrange
        RoomRepository roomRepository = mock(RoomRepository.class);
        DeviceFactoryImpl deviceFactory = mock(DeviceFactoryImpl.class);
        DeviceRepository deviceRepository = mock(DeviceRepository.class);
        SensorRepository sensorRepository = mock(SensorRepository.class);
        ActuatorRepository actuatorRepository = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorRepository, actuatorRepository);

        when(sensorRepository.findAll()).thenReturn(Collections.emptyList());
        when(actuatorRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        Map<String, List<Device>> result = deviceService.getListOfDeviceByFunctionality();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * This test verifies the functionality of the getListOfDevicesByFunctionality method in the DeviceService class.
     * It ensures that the method correctly associates devices with their respective functionalities and returns a map
     * where each key represents a functionality and each value is a list of devices associated with that functionality.

     * The test sets up various doubled dependencies, conditions their behavior, and then invokes the
     * getListOfDevicesByFunctionality method to obtain the result. It then compares the result with the expected
     * functionalities and associated device lists.

     * Regarding behavior conditioning, every step of the test is commented to a better analysis.
     */

    @Test
    void getListOfDevicesByFunctionality_ShouldReturnListOfDevicesByFunctionality() {
        //Arrange

        //Defining all functionalities for being reused through the test (sensor types and actuator types)
        String humidity = "HumiditySensor";
        String position = "PositionSensor";
        String temperature = "TemperatureSensor";
        String sunset = "SunsetSensor";
        String switchActuator = "SwitchSensor";
        String rollerBlind = "RollerBlindSensor";

        //Adding all expected functionalities to a list (for future comparison with the resulting map key set)
        List<String> expectedFunctionalities = new ArrayList<>();

        expectedFunctionalities.add(humidity);
        expectedFunctionalities.add(position);
        expectedFunctionalities.add(temperature);
        expectedFunctionalities.add(sunset);
        expectedFunctionalities.add(switchActuator);
        expectedFunctionalities.add(rollerBlind);

        //Sorting alphabetically list content
        Collections.sort(expectedFunctionalities);

        //Doubling all device service dependencies
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);

        //Initialize device service with previously doubled dependencies
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactoryDouble, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        //Doubling devices
        Device deviceOneDouble = mock(Device.class);
        Device deviceTwoDouble = mock(Device.class);
        Device deviceThreeDouble = mock(Device.class);
        Device deviceFourDouble = mock(Device.class);

        //Conditioning the doubled device's behavior to return an id when queried for it's ID

        DeviceIDVO deviceIDOneDouble = mock(DeviceIDVO.class);
        when(deviceOneDouble.getId()).thenReturn(deviceIDOneDouble);

        DeviceIDVO deviceIDTwoDouble = mock(DeviceIDVO.class);
        when(deviceTwoDouble.getId()).thenReturn(deviceIDTwoDouble);

        DeviceIDVO deviceIDThreeDouble = mock(DeviceIDVO.class);
        when(deviceThreeDouble.getId()).thenReturn(deviceIDThreeDouble);

        DeviceIDVO deviceIDFourDouble = mock(DeviceIDVO.class);
        when(deviceFourDouble.getId()).thenReturn(deviceIDFourDouble);

        /*
        Creating various lists with the device doubles.
        Each list will be used to compare with the resulting map value of a given key (functionality)
         */

        List<Device> listOne = new ArrayList<>();
        listOne.add(deviceOneDouble);

        List<Device> listTwo = new ArrayList<>();
        listTwo.add(deviceTwoDouble);

        List<Device> listThree = new ArrayList<>();
        listThree.add(deviceThreeDouble);

        List<Device> listFour = new ArrayList<>();
        listFour.add(deviceFourDouble);

        //Conditioning device repository to return a device, when invoked to find a device by id

        when(deviceRepositoryDouble.findById(deviceIDOneDouble)).thenReturn(deviceOneDouble);
        when(deviceRepositoryDouble.findById(deviceIDTwoDouble)).thenReturn(deviceTwoDouble);
        when(deviceRepositoryDouble.findById(deviceIDThreeDouble)).thenReturn(deviceThreeDouble);
        when(deviceRepositoryDouble.findById(deviceIDFourDouble)).thenReturn(deviceFourDouble);

        /*
        Doubling sensors and adding them to a list (this list will be returned by sensor repository double
        when queried for all sensors.
         */

        List<Sensor> sensorList = new ArrayList<>();

        Sensor sensorOneDouble = mock(Sensor.class);
        sensorList.add(sensorOneDouble);

        Sensor sensorTwoDouble = mock(Sensor.class);
        sensorList.add(sensorTwoDouble);

        Sensor sensorThreeDouble = mock(Sensor.class);
        sensorList.add(sensorThreeDouble);

        Sensor sensorFourDouble = mock(Sensor.class);
        sensorList.add(sensorFourDouble);

        //Conditioning sensor repository double to return a previously created sensor doubles list
        when(sensorRepositoryDouble.findAll()).thenReturn(sensorList);

        /*
        Doubling sensor type id value objects and conditioning each behavior to return their
        id as string (functionality)
         */

        SensorTypeIDVO sensorTypeIdOneDouble = mock(SensorTypeIDVO.class);
        when(sensorTypeIdOneDouble.getID()).thenReturn(humidity);

        SensorTypeIDVO sensorTypeIdTwoDouble = mock(SensorTypeIDVO.class);
        when(sensorTypeIdTwoDouble.getID()).thenReturn(position);

        SensorTypeIDVO sensorTypeIdThreeDouble = mock(SensorTypeIDVO.class);
        when(sensorTypeIdThreeDouble.getID()).thenReturn(temperature);

        SensorTypeIDVO sensorTypeIdFourDouble = mock(SensorTypeIDVO.class);
        when(sensorTypeIdFourDouble.getID()).thenReturn(sunset);

        //Conditioning each sensor to return a doubled sensor type id value object

        when(sensorOneDouble.getSensorTypeID()).thenReturn(sensorTypeIdOneDouble);
        when(sensorTwoDouble.getSensorTypeID()).thenReturn(sensorTypeIdTwoDouble);
        when(sensorThreeDouble.getSensorTypeID()).thenReturn(sensorTypeIdThreeDouble);
        when(sensorFourDouble.getSensorTypeID()).thenReturn(sensorTypeIdFourDouble);

        //Conditioning each sensor to return a doubled device id value object

        when(sensorOneDouble.getDeviceID()).thenReturn(deviceIDOneDouble);
        when(sensorTwoDouble.getDeviceID()).thenReturn(deviceIDTwoDouble);
        when(sensorThreeDouble.getDeviceID()).thenReturn(deviceIDThreeDouble);
        when(sensorFourDouble.getDeviceID()).thenReturn(deviceIDFourDouble);

        //Actuators

         /*
        Doubling actuators and adding them to a list (this list will be returned by actuator repository double
        when queried for all actuators.
         */
        List<Actuator> actuatorList = new ArrayList<>();

        Actuator actuatorOne = mock(Actuator.class);
        actuatorList.add(actuatorOne);

        Actuator actuatorTwo = mock(Actuator.class);
        actuatorList.add(actuatorTwo);

        //Conditioning actuator repository to return an actuator list when queried for all actuators
        when(actuatorRepositoryDouble.findAll()).thenReturn(actuatorList);

        /*
        Doubling actuator type id value objects and conditioning each behavior to return their
        id as string (functionality)
        */
        ActuatorTypeIDVO actuatorTypeIDOneDouble = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeIDOneDouble.getID()).thenReturn(switchActuator);

        ActuatorTypeIDVO actuatorTypeIDTwoDouble = mock(ActuatorTypeIDVO.class);
        when(actuatorTypeIDTwoDouble.getID()).thenReturn(rollerBlind);

        //Conditioning each actuator to return a doubled actuator type value object

        when(actuatorOne.getActuatorTypeID()).thenReturn(actuatorTypeIDOneDouble);
        when(actuatorTwo.getActuatorTypeID()).thenReturn(actuatorTypeIDTwoDouble);

        //Conditioning each actuator to return a device id double

        when(actuatorOne.getDeviceID()).thenReturn(deviceIDOneDouble);
        when(actuatorTwo.getDeviceID()).thenReturn(deviceIDTwoDouble);

        //Act

        Map<String,List<Device>> result = deviceService.getListOfDeviceByFunctionality();

        //Assert

        /*
        Getting the key set as a list, this key set has all resulting functionalities.
        It will be used for comparison with a list of expected functionalities
        */

        List<String> resultFunctionalities = new ArrayList<>(result.keySet());

        //Sorting alphabetically list content
        Collections.sort(resultFunctionalities);

        /*
        Extracting the list associated with each functionality.
        Each list will be asserted against another list, that has the expected results per functionality.
         */
        List<Device> firstLineList = result.get(humidity);
        List<Device> secondLineList = result.get(position);
        List<Device> thirdLineList = result.get(temperature);
        List<Device> fourthLineList = result.get(sunset);
        List<Device> fifthLineList = result.get(switchActuator);
        List<Device> sixthLineList = result.get(rollerBlind);

        //Assert

        //Assert the resulting list of functionalities match the expected
        assertEquals(expectedFunctionalities,resultFunctionalities);

        //Asserting that each list associated with a functionality has the expected devices
        assertEquals(listOne,firstLineList);
        assertEquals(listTwo,secondLineList);
        assertEquals(listThree,thirdLineList);
        assertEquals(listFour,fourthLineList);
        assertEquals(listOne,fifthLineList);
        assertEquals(listTwo,sixthLineList);
    }

    /**
     *
     * This test verifies the functionality of the getListOfDevicesByFunctionality method in the DeviceService class.
     * It ensures that the method throws an IllegalArgumentException when the SensorRepository returns null.

     * The test sets up various doubled dependencies, conditions their behavior, and then invokes the
     * getListOfDevicesByFunctionality method to trigger the exception. It then compares the exception message with
     * the expected message.

     * Regarding behavior conditioning, every step of the test is commented for better analysis.
     */

    @Test
    void getListOfDevicesByFunctionality_WhenSensorRepositoryReturnsNull_ShouldThrowIllegalArgumentException() {
        //Arrange

        //Doubling all device service dependencies
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);

        //Initialize device service with previously doubled dependencies
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactoryDouble, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        //Conditioning sensor repository double to return null
        when(sensorRepositoryDouble.findAll()).thenReturn(null);
        String expected = "Cannot access all sensors";

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, deviceService::getListOfDeviceByFunctionality);
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);

    }

    /**
     *
     * This test verifies the functionality of the getListOfDevicesByFunctionality method in the DeviceService class.
     * It ensures that the method throws an IllegalArgumentException when the ActuatorRepository returns null.

     * The test sets up various doubled dependencies, conditions their behavior, and then invokes the
     * getListOfDevicesByFunctionality method to trigger the exception. It then compares the exception message with
     * the expected message.

     * Regarding behavior conditioning, every step of the test is commented for better analysis.
     */

    @Test
    void getListOfDevicesByFunctionality_WhenActuatorRepositoryReturnsNull_ShouldThrowIllegalArgumentException() {
        //Arrange
        List<Sensor> sensorList = new ArrayList<>();

        //Doubling all device service dependencies
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);

        //Initialize device service with previously doubled dependencies
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactoryDouble, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        //Conditioning actuator repository double to return a previously created sensor doubles list
        when(sensorRepositoryDouble.findAll()).thenReturn(sensorList);

        //Conditioning sensor repository double to return null
        when(actuatorRepositoryDouble.findAll()).thenReturn(null);
        String expected = "Cannot access all actuators";

        //Act + Assert
        Exception exception = assertThrows(IllegalArgumentException.class, deviceService::getListOfDeviceByFunctionality);
        String result = exception.getMessage();

        //Assert
        assertEquals(expected,result);

    }

    /**
     * Test to verify that the getDeviceById method throws an IllegalArgumentException when the DeviceIDVO parameter is null.
     * The test arranges mock RoomRepository, DeviceFactoryImpl, DeviceRepository, SensorRepository, and ActuatorRepository.
     * It then asserts that an IllegalArgumentException is thrown when the getDeviceById method is called with a null DeviceIDVO.
     * The test also verifies that the exception message matches the expected message.
     */
    @Test
    void givenNullDeviceID_whenDeactivateDevice_thenThrowIllegalArgumentException() {
//         Arrange
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactoryDouble = mock(DeviceFactory.class);
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactoryDouble, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);
        String expected = "DeviceIDVO cannot be null.";
//        Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> deviceService.getDeviceById(null));
//        Assert
        String result = exception.getMessage();
        assertEquals(expected, result);
    }
}