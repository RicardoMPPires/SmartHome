package smarthome.controller;

import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.SensorRepository;
import smarthome.service.DeviceService;
import smarthome.service.DeviceServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeactivateDeviceCTRLTest {

    /**
     * Test case to verify that when a null DeviceService is provided to the constructor,
     * an IllegalArgumentException with "Invalid service" message is thrown.
     */

    @Test
    void whenNullDeviceService_ConstructorShouldThrowIllegalArgumentException() {

        //Arrange
        String expected = "Invalid service";
        //Act + Assert

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new DeactivateDeviceCTRL(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that when a null DeviceDTO is provided to deactivateDevice method,
     * it returns false.
     * For this test case DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are being doubled.
     */

    @Test
    void deactivateDevice_WhenNullDeviceDTO_ShouldReturnFalse() {

        //Arrange
        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);

        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        //Act
        boolean result = deactivateDeviceCTRL.deactivateDevice(null);

        //Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that when a null device ID is provided in the DeviceDTO to deactivateDevice method,
     * it returns false.
     * For this test case DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are being doubled.
     */
    @Test
    void deactivateDevice_WhenNullDeviceDTOID_ShouldReturnFalse() {
        // Arrange
        String deviceName = "Smart Oven";
        String deviceModel = "Bosch electronics 77-kk";
        String deviceStatus = "true";
        String roomID = "JL-II-99";

        // Creating a DeviceDTO with a null device ID
        DeviceDTO deviceDTO = new DeviceDTO(null, deviceName, deviceModel, deviceStatus, roomID);

        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        // Creating DeactivateDeviceCTRL instance
        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        // Act: Invoking deactivateDevice method with a DeviceDTO having a null device ID
        boolean result = deactivateDeviceCTRL.deactivateDevice(deviceDTO);

        // Assert: Verifying that the result is false (represents the success of the operation), as expected
        assertFalse(result);
    }

    /**
     * Test case to verify that when an invalid device ID is provided in the DeviceDTO to deactivateDevice method,
     * it returns false. By invalid (empty, empty with blank spaces and null)
     * For this test case DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are being doubled.
     */
    @Test
    void deactivateDevice_WhenInvalidDeviceID_ShouldReturnFalse() {
        // Arrange
        String deviceID1 = ""; // Empty device ID
        String deviceID2 = "    "; // Device ID with blank spaces
        String deviceName = "Smart Oven";
        String deviceModel = "Bosch electronics 77-kk";
        String deviceStatus = "true";
        String roomID = "JL-II-99";

        // Creating a DeviceDTO with an empty device ID
        DeviceDTO deviceDTO1 = new DeviceDTO(deviceID1, deviceName, deviceModel, deviceStatus, roomID);
        DeviceDTO deviceDTO2 = new DeviceDTO(deviceID2, deviceName, deviceModel, deviceStatus, roomID);
        DeviceDTO deviceDTO3 = new DeviceDTO(null, deviceName, deviceModel, deviceStatus, roomID);

        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        // Creating DeactivateDeviceCTRL instance
        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        // Act: Invoking deactivateDevice method with a DeviceDTO having an empty device ID
        boolean result1 = deactivateDeviceCTRL.deactivateDevice(deviceDTO1);
        boolean result2 = deactivateDeviceCTRL.deactivateDevice(deviceDTO2);
        boolean result3 = deactivateDeviceCTRL.deactivateDevice(deviceDTO3);

        // Assert: Verifying that the result is false (represents the success of the operation), as expected
        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
    }


    /**
     * Test case to verify that when a non-convertible UUID string is provided in the DeviceDTO to deactivateDevice method,
     * it returns false.
     * For this test case DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are being doubled.
     */
    @Test
    void deactivateDevice_WhenNonConvertibleUUIDtoString_ShouldReturnFalse() {
        // Arrange
        String deviceID = "ak-ll-99-hhhh-k"; // Non-convertible UUID string
        String deviceName = "Smart Oven";
        String deviceModel = "Bosch electronics 77-kk";
        String deviceStatus = "true";
        String roomID = "JL-II-99";

        // Creating a DeviceDTO with a non-convertible UUID string as device ID
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceName, deviceModel, deviceStatus, roomID);

        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        // Creating DeactivateDeviceCTRL instance
        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        // Act: Invoking deactivateDevice method with a DeviceDTO having a non-convertible UUID string as device ID
        boolean result = deactivateDeviceCTRL.deactivateDevice(deviceDTO);

        // Assert: Verifying that the result is false (represents the success of the operation), as expected
        assertFalse(result);
    }

    /**
     * This test case verifies that when a non-existing device is provided in the DeviceDTO to the `deactivateDevice` method,
     * it returns false.
     * Doubles for DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are utilized in this test case.
     * The behavior of DeviceRepository is conditioned to return null when the `findByID()` method is invoked.
     * This behavior (returning null), although it's already the default behavior of Mockito if not explicitly set, is induced
     * here for clarity.
     */

    @Test
    void deactivateDevice_WhenNonExistingDevice_ShouldReturnFalse() {
        // Arrange
        DeviceIDVO deviceIDVO = new DeviceIDVO(UUID.randomUUID());
        String deviceID = deviceIDVO.getID(); // Generating a random UUID as the device ID
        String deviceName = "Smart Oven";
        String deviceModel = "Bosch electronics 77-kk";
        String deviceStatus = "true";
        String roomID = "JL-II-99";

        // Creating a DeviceDTO with the generated random UUID as device ID
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceName, deviceModel, deviceStatus, roomID);

        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor

        //Conditioning device repository to return null is invoked findById(DeviceIDVO) method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO)).thenReturn(null);

        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        // Creating DeactivateDeviceCTRL instance
        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        // Act: Invoking deactivateDevice method with a DeviceDTO representing a non-existing device
        boolean result = deactivateDeviceCTRL.deactivateDevice(deviceDTO);

        // Assert: Verifying that the result is false (represents the success of the operation), as expected
        assertFalse(result);
    }

    /**
     * Test case to verify that when a device is already deactivated and provided in the DeviceDTO to deactivateDevice method,
     * it returns false.
     * Doubles for DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are utilized in this test case.
     * The behavior of DeviceRepository is conditioned to return the correct Device when the `findByID(DeviceIDVI)` method is invoked.
     */

    @Test
    void deactivateDevice_WhenDeviceIsAlreadyDeactivated_ShouldReturnFalse() {
        // Arrange
        String deviceName = "Smart Oven";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);

        String deviceModel = "Bosch electronics 77-kk";
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);

        String deviceStatus = "false";

        UUID idRoom = UUID.randomUUID();
        String idRoomString = idRoom.toString();
        RoomIDVO roomIDVO = new RoomIDVO(idRoom);

        // Creating a new device
        Device device = new Device(deviceNameVO, deviceModelVO, roomIDVO);
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();

        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor

        //Conditioning device repository to return correct device when is invoked findById(DeviceIDVO) method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO)).thenReturn(device);

        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        // Creating DeactivateDeviceCTRL instance
        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        // Deactivating the device
        device.deactivateDevice();

        // Creating a DeviceDTO representing the deactivated device
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceName, deviceModel, deviceStatus, idRoomString);


        // Act: Invoking deactivateDevice method with a DeviceDTO representing an already deactivated device
        boolean result = deactivateDeviceCTRL.deactivateDevice(deviceDTO);

        //Query the device for its status assuring that the created device is deactivated (false)
        boolean state = device.getDeviceStatus().getValue();

        // Assert: Verifying that the result is false (represents the failure of the operation), as expected
        assertFalse(result);
        // Assert: Verifying that the result is false (represents device's status), as expected
        assertFalse(state);
    }

    /**
     * Test case to verify that when an activated device exists and is provided in the DeviceDTO to deactivateDevice method,
     * it returns False after successful deactivation.
     * Doubles for DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are utilized in this test case.
     * The behavior of DeviceRepository is conditioned to return the correct Device when the `findByID(DeviceIDVI)` method is invoked.
     * The behavior of DeviceRepository is conditioned to return false when update(device) method is invoked.
     */
    @Test
    void deactivateDevice_WhenDeviceUpdateOperationFails_ShouldReturnFalse() {
        // Arrange
        String deviceName = "Smart Oven";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);

        String deviceModel = "Bosch electronics 77-kk";
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceModel);

        String deviceStatus = "true";

        UUID idRoom = UUID.randomUUID();
        String idRoomString = idRoom.toString();
        RoomIDVO roomIDVO = new RoomIDVO(idRoom);

        // Creating a new device
        Device device = new Device(deviceNameVO, deviceModelVO, roomIDVO);
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();

        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor

        //Conditioning device repository to return correct device when is invoked findById(DeviceIDVO) method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO)).thenReturn(device);
        //Conditioning device repository to return false when device update operation is called.
        when(deviceRepositoryDouble.update(device)).thenReturn(false);

        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        // Creating DeactivateDeviceCTRL instance
        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        // Creating a DeviceDTO representing the deactivated device
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceName, deviceModel, deviceStatus, idRoomString);
        when(deviceRepositoryDouble.isPresent(deviceIDVO)).thenReturn(true);

        // Act: Invoking deactivateDevice method with a DeviceDTO representing an already deactivated device
        boolean result = deactivateDeviceCTRL.deactivateDevice(deviceDTO);

        //Query the device for its status assuring that the created device is deactivated (false)
        boolean state = device.getDeviceStatus().getValue();

        // Assert: Verifying that the result is false (represents the success of the operation), as expected
        assertFalse(result);
        // Assert: Verifying that the result is false (represents device's status), as expected
        assertFalse(state);
    }

    /**
     * Test case to verify that when an activated device exists and is provided in the DeviceDTO to deactivateDevice method,
     * it returns False after successful deactivation.
     * Doubles for DeviceRepository, RoomRepository, SensorRepository and ActuatorRepository are utilized in this test case.
     * The behavior of DeviceRepository is conditioned to return the correct Device when the `findByID(DeviceIDVI)` method is invoked.
     * The behavior of DeviceRepository is conditioned to return true when update(device) method is invoked.
     */
    @Test
    void deactivateDevice_WhenDeviceExistsAndActivated_ShouldReturnTrue() {
        // Arrange
        String deviceName = "Smart Oven";
        DeviceNameVO deviceNameVO = new DeviceNameVO(deviceName);

        String deviceModel = "Bosch electronics 77-kk";
        DeviceModelVO deviceModelVO = new DeviceModelVO(deviceName);

        String deviceStatus = "true";

        UUID idRoom = UUID.randomUUID();
        String idRoomString = idRoom.toString();
        RoomIDVO roomIDVO = new RoomIDVO(idRoom);

        // Creating a new device
        Device device = new Device(deviceNameVO, deviceModelVO, roomIDVO);
        DeviceIDVO deviceIDVO = device.getId();
        String deviceID = deviceIDVO.getID();

        //Doubling the device service dependencies (room repository, device repository) and injecting them in device service constructor

        //Conditioning device repository to return correct device when is invoked findById(DeviceIDVO) method.
        DeviceRepository deviceRepositoryDouble = mock(DeviceRepository.class);
        when(deviceRepositoryDouble.findById(deviceIDVO)).thenReturn(device);
        //Conditioning device repository to return true when device update operation is called.
        when(deviceRepositoryDouble.update(device)).thenReturn(true);

        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository sensorRepositoryDouble = mock(SensorRepository.class);
        ActuatorRepository actuatorRepositoryDouble = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(roomRepositoryDouble, deviceFactory, deviceRepositoryDouble, sensorRepositoryDouble, actuatorRepositoryDouble);

        // Creating DeactivateDeviceCTRL instance
        DeactivateDeviceCTRL deactivateDeviceCTRL = new DeactivateDeviceCTRL(deviceService);

        // Creating a DeviceDTO representing the deactivated device
        DeviceDTO deviceDTO = new DeviceDTO(deviceID, deviceName, deviceModel, deviceStatus, idRoomString);
        when(deviceRepositoryDouble.isPresent(deviceIDVO)).thenReturn(true);


        // Act: Invoking deactivateDevice method with a DeviceDTO representing an already deactivated device
        boolean result = deactivateDeviceCTRL.deactivateDevice(deviceDTO);

        //Query the device for its status assuring that the created device is deactivated (false)
        boolean state = device.getDeviceStatus().getValue();

        // Assert: Verifying that the result is true (represents the success of the operation), as expected
        assertTrue(result);
        // Assert: Verifying that the result is false (represents device's status), as expected
        assertFalse(state);
    }
}