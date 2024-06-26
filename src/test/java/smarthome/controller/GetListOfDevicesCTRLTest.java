package smarthome.controller;


import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.vo.roomvo.*;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.RoomDTO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.SensorRepository;
import smarthome.service.DeviceService;
import smarthome.service.DeviceServiceImpl;
import smarthome.domain.vo.devicevo.DeviceModelVO;
import smarthome.domain.vo.devicevo.DeviceNameVO;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetListOfDevicesCTRLTest {

    /**
     * Test case to verify that when the RoomService parameter is null,
     * creating a GetListOfDevicesCTRL controller throws an IllegalArgumentException
     * with the expected error message.
     * The test arranges for a null RoomService, then tries to create a
     * GetListOfDevicesCTRL controller with this null parameter. It expects
     * an IllegalArgumentException to be thrown with the message "Invalid parameters."
     * @throws IllegalArgumentException if RoomService parameter is null
     */
    @Test
    void whenDeviceServiceIsNull_thenThrowsIllegalArgumentException(){
        //Arrange
        String expected = "Invalid parameters.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new GetListOfDevicesCTRL(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that when valid parameters are provided,
     * creating a GetListOfDevicesCTRL controller with the specified
     * DeviceService returns a non-null object.
     * The test sets up a memory repository, device repository, factory,
     * and service for devices. It then creates a GetListOfDevicesCTRL
     * controller with the device service. The test asserts that the
     * controller object is not null, indicating successful instantiation.
     */
    @Test
    void whenParametersValid_thenReturnsObject(){
        //Arrange
        RoomRepository doubleRoomRepositoryMem = mock(RoomRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepositoryMem, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        //Act
        GetListOfDevicesCTRL getListOfDevicesCTRL = new GetListOfDevicesCTRL(deviceService);

        //Assert
        assertNotNull(getListOfDevicesCTRL);
    }

    /**
     * Test case to verify that when a room does not exist (not found), calling getListOfDevices() on the
     * GetListOfDevicesCTRL controller with a RoomDTO representing that room returns an empty list of DeviceDTO.
     * The test sets up a RoomDTO for a room that does not exist, then creates a new GetListOfDevicesCTRL controller
     * with a DeviceService.
     * When calling getListOfDevices() with the non-existent room, it asserts that the returned list of DeviceDTO
     * objects is empty.
     */
    @Test
    void whenRoomDoesNotExist_thenReturnsAnEmptyList() {
        //Arrange
        RoomDTO roomDTO = new RoomDTO(UUID.randomUUID().toString(),"room1",2,3,3,2,UUID.randomUUID().toString());

        RoomRepository doubleRoomRepository = mock(RoomRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        GetListOfDevicesCTRL getListOfDevicesCTRL = new GetListOfDevicesCTRL(deviceService);

        List<DeviceDTO> expected = new ArrayList<>();
        //Act
        List<DeviceDTO> result = getListOfDevicesCTRL.getListOfDevices(roomDTO);

        //Assert
        assertTrue(result.isEmpty());
        assertEquals(expected,result);
    }

    /**
     * Test case to verify that when a room has no devices, calling getListOfDevices() on the GetListOfDevicesCTRL
     * controller with the RoomDTO representing that room returns an empty list of DeviceDTO.
     * The test sets up a room with specific dimensions and properties but no devices, then creates a
     * GetListOfDevicesCTRL controller with a DeviceService.
     * The mock DeviceRepository is set up to return an empty list of devices in that room.
     * When calling getListOfDevices() with this room, it asserts that the returned list of DeviceDTO objects is empty.
     */
    @Test
    void ifRoomHasNoDevices_whenGetListOfDevicesIsCalled_thenReturnsEmptyList() {
        //Arrange

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());

        String roomID = roomIDVO.getID();
        String roomName = "room1";
        int roomFloor = 1;
        double roomWidth = 10.0;
        double roomLength = 10.0;
        double roomHeight = 10.0;
        String houseIDVO = "124e4567-e89b-12d3-a456-426614174000";
        RoomDTO roomDTO = new RoomDTO(roomID,roomName,roomFloor,roomHeight,roomLength,roomWidth,houseIDVO);

        RoomRepository doubleRoomRepository = mock(RoomRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        when(doubleDeviceRepository.findByRoomID(roomIDVO)).thenReturn(Collections.emptyList());

        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        GetListOfDevicesCTRL getListOfDevicesCTRL = new GetListOfDevicesCTRL(deviceService);

        //Act
        List<DeviceDTO> deviceDTOList = getListOfDevicesCTRL.getListOfDevices(roomDTO);

        //Assert
        assertTrue(deviceDTOList.isEmpty());
    }

    /**
     * Test case to verify that when a room has one device, calling getListOfDevices() on the GetListOfDevicesCTRL
     * controller with the RoomDTO representing that room returns a list containing the DeviceDTO for that device.
     * The test sets up a room with specific dimensions and properties and instantiates a device to add to that room.
     * The mock DeviceRepository is set up to return a list containing that single device in that room.
     * It creates a GetListOfDevicesCTRL controller with a DeviceService. When calling getListOfDevices() with
     * the room, it asserts that the returned list of DeviceDTO objects contains the details of the device added to
     * the room.
     */
    @Test
    void ifRoomHasOneDevice_whenGetListOfDevicesIsCalled_thenReturnsListOfDevicesDTO() {
        //Arrange
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());

        String roomID = roomIDVO.getID();
        String roomName = "room1";
        int roomFloor = 1;
        double roomWidth = 10.0;
        double roomLength = 10.0;
        double roomHeight = 10.0;
        String houseIDVO = "124e4567-e89b-12d3-a456-426614174000";
        RoomDTO roomDTO = new RoomDTO(roomID, roomName, roomFloor, roomHeight, roomLength, roomWidth, houseIDVO);

        String expectedDeviceName = "Fridge";
        DeviceNameVO deviceNameVO = new DeviceNameVO(expectedDeviceName);
        String expectedDeviceModel = "123QWE";
        DeviceModelVO deviceModelVO = new DeviceModelVO(expectedDeviceModel);

        Device device = new Device(deviceNameVO, deviceModelVO, roomIDVO);

        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        when(doubleDeviceRepository.findByRoomID(any(RoomIDVO.class))).thenReturn(Collections.singletonList(device));

        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        GetListOfDevicesCTRL getListOfDevicesCTRL = new GetListOfDevicesCTRL(deviceService);

        int expectedSize = 1;

        when(doubleRoomRepository.isPresent(roomIDVO)).thenReturn(true);

        //Act
        List<DeviceDTO> deviceDTOList = getListOfDevicesCTRL.getListOfDevices(roomDTO);

        //Assert
        assertFalse(deviceDTOList.isEmpty());
        assertEquals(expectedSize, deviceDTOList.size());
        assertEquals(expectedDeviceName, deviceDTOList.get(0).getDeviceName());
        assertEquals(expectedDeviceModel, deviceDTOList.get(0).getDeviceModel());
        assertEquals(roomID, deviceDTOList.get(0).getRoomID());
        assertEquals("true", deviceDTOList.get(0).getDeviceStatus());
    }

    /**
     * Test case to verify that when a room has two devices, calling getListOfDevices() on the GetListOfDevicesCTRL
     * controller with the RoomDTO representing that room returns a list containing the DeviceDTOs for both devices.
     * The test sets up a room with specific dimensions and properties, and instantiates two devices to add to that room.
     * The mock DeviceRepository is set up to return a list containing both devices in that room.
     * It creates a GetListOfDevicesCTRL controller with a DeviceService. When calling getListOfDevices() with
     * the room, it asserts that the returned list of DeviceDTO objects contains the details of both devices added to
     * the room, including device names, models, statuses, and IDs.
     */
    @Test
    void ifRoomHasTwoDevices_whenGetListOfDevicesIsCalled_thenReturnsListOfDevicesDTO() {
        //Arrange
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomIDVO roomIDVO = new RoomIDVO(UUID.randomUUID());

        String roomID = roomIDVO.getID();
        String roomName = "room1";
        int roomFloor = 1;
        double roomWidth = 10.0;
        double roomLength = 10.0;
        double roomHeight = 10.0;
        String houseIDVO = "124e4567-e89b-12d3-a456-426614174000";
        RoomDTO roomDTO = new RoomDTO(roomID,roomName,roomFloor,roomHeight,roomLength,roomWidth,houseIDVO);

        String expectedDeviceName = "Fridge";
        DeviceNameVO deviceNameVO = new DeviceNameVO(expectedDeviceName);
        String expectedDeviceModel = "123QWE";
        DeviceModelVO deviceModelVO = new DeviceModelVO(expectedDeviceModel);

        Device device1 = new Device(deviceNameVO, deviceModelVO, roomIDVO);

        String expectedName2 = "AirConditioner";
        DeviceNameVO deviceNameVO2 = new DeviceNameVO(expectedName2);
        String expectedModel2 = "456QWE";
        DeviceModelVO deviceModelVO2 = new DeviceModelVO(expectedModel2);

        Device device2 = new Device(deviceNameVO2,deviceModelVO2,roomIDVO);

        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        when(doubleDeviceRepository.findByRoomID(any(RoomIDVO.class))).thenReturn(Arrays.asList(device1, device2));


        String expectedID1 = device1.getId().getID();
        String expectedID2 = device2.getId().getID();

        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        GetListOfDevicesCTRL getListOfDevicesCTRL = new GetListOfDevicesCTRL(deviceService);

        int expectedSize = 2;
        when(doubleRoomRepository.isPresent(roomIDVO)).thenReturn(true);

        //Act
        List<DeviceDTO> deviceDTOList = getListOfDevicesCTRL.getListOfDevices(roomDTO);
        String resultName = deviceDTOList.get(0).getDeviceName();
        String resultModel = deviceDTOList.get(0).getDeviceModel();
        String resultStatus = deviceDTOList.get(0).getDeviceStatus();
        String resultDeviceID = deviceDTOList.get(0).getDeviceID();

        String resultName2 = deviceDTOList.get(1).getDeviceName();
        String resultModel2 = deviceDTOList.get(1).getDeviceModel();
        String resultStatus2 = deviceDTOList.get(1).getDeviceStatus();
        String resultDeviceID2 = deviceDTOList.get(1).getDeviceID();

        //Assert
        assertFalse(deviceDTOList.isEmpty());
        assertEquals(expectedDeviceName, resultName);
        assertEquals(expectedDeviceModel, resultModel);
        assertEquals("true", resultStatus);
        assertEquals(expectedID1, resultDeviceID);

        assertEquals(expectedName2, resultName2);
        assertEquals(expectedModel2, resultModel2);
        assertEquals("true", resultStatus2);
        assertEquals(expectedID2, resultDeviceID2);

        assertEquals(expectedSize, deviceDTOList.size());
    }


    /**
     * Test case to verify that when a RoomDTO is null, calling getListOfDevices() on the GetListOfDevicesCTRL controller
     * returns an empty list of DeviceDTO.
     * The test sets up a null RoomDTO and creates a GetListOfDevicesCTRL controller with a DeviceService.
     * When calling getListOfDevices() with the null RoomDTO, it asserts that the returned list of DeviceDTO objects
     * is empty.
     */
    @Test
    void whenRoomDTOIsNull_thenReturnsEmptyList() {
        //Arrange
        RoomDTO roomDTO = null;

        RoomRepository doubleRoomRepository = mock(RoomRepository.class);
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        GetListOfDevicesCTRL getListOfDevicesCTRL = new GetListOfDevicesCTRL(deviceService);

        //Act
        List<DeviceDTO> result = getListOfDevicesCTRL.getListOfDevices(roomDTO);

        //Assert
        assertTrue(result.isEmpty());
    }
}