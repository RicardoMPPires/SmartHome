package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.room.Room;
import smarthome.domain.vo.roomvo.*;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.RoomDTO;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.DeviceRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.SensorRepository;
import smarthome.service.DeviceService;
import smarthome.service.DeviceServiceImpl;
import smarthome.domain.vo.housevo.HouseIDVO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * NOTE: RoomDTO and DeviceDTO attributes were not accessed in the controller tests, since all Mappers ensure they cannot
 * be invalid and used as so. Exceptions would be thrown and caught when appropriate.
 */
class AddDeviceToRoomCTRLTest {

    /**
     * Test method to verify that when valid data is provided to the AddDeviceToRoomCTRL's addDeviceToRoom method,
     * it returns true, indicating successful addition of the device to the room.
     * This test ensures that the controller correctly adds a new device to a room when valid data is provided.
     * The test uses a mock RoomRepository and DeviceRepository to simulate the Room and Device repositories. Both are
     * set up to return true when the respective methods (isPresent() and save()) are called.
     */
    @Test
    void givenValidData_WhenAddDeviceToRoom_ThenShouldReturnTrue(){
        // Arrange
        // arranges Room repository, creates Room
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomNameVO name = new RoomNameVO("Office");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomWidthVO width = new RoomWidthVO(3.5);
        RoomLengthVO length = new RoomLengthVO(3);
        RoomHeightVO height = new RoomHeightVO(2);
        RoomDimensionsVO dimensions = new RoomDimensionsVO(length, width, height);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        Room room = new Room (name,floor,dimensions,houseID);

        when(doubleRoomRepository.isPresent(room.getId())).thenReturn(true);

        // arranges Device Service
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        // arranges RoomDTO. String IDs must be obtained from the previously instantiated Room and HouseIDVO objects.
        String roomId = room.getId().getID();
        String houseId = room.getHouseID().getID();
        RoomDTO roomDTO = new RoomDTO(roomId,"Office",2,3.5,3,2,houseId);

        // arranges DeviceDTO
        String deviceName = "Top Load Washing Machine";
        String deviceModel = "Samsung WA50R5400AW";
        DeviceDTO deviceDTO = DeviceDTO.builder().deviceName(deviceName).deviceModel(deviceModel).build();

        when(doubleDeviceRepository.save(any(Device.class))).thenReturn(true);

        AddDeviceToRoomCTRL ctrl = new AddDeviceToRoomCTRL(deviceService);


        // Act
        // Main operation:
        boolean operationResult = ctrl.addDeviceToRoom(roomDTO, deviceDTO);


        // Assert
        assertTrue(operationResult);
    }


    /**
     * Test method to verify that when valid data is provided to the AddDeviceToRoomCTRL's addDeviceToRoom method,
     * it returns false, indicating that the addition of the device to the room was unsuccessful.
     * This test ensures that the controller behaves as expected when the save operation in the DeviceRepository is not
     * successful.
     * The test uses a mock RoomRepository and DeviceRepository to simulate the Room and Device repositories.
     * The RoomRepository is set up to return true when the isPresent() method is called, while the DeviceRepository
     * is set up to return false when the save() method is called.
     */
    @Test
    void givenValidData_WhenAddDeviceToRoomSaveIsNotSuccessful_ThenShouldReturnFalse() {
        // Arrange
        // arranges Room repository, creates Room
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomNameVO name = new RoomNameVO("Office");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomWidthVO width = new RoomWidthVO(3.5);
        RoomLengthVO length = new RoomLengthVO(3);
        RoomHeightVO height = new RoomHeightVO(2);
        RoomDimensionsVO dimensions = new RoomDimensionsVO(length, width, height);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        Room room = new Room(name, floor, dimensions, houseID);

        when(doubleRoomRepository.isPresent(room.getId())).thenReturn(true);

        // arranges Device Service
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        // arranges RoomDTO. String IDs must be obtained from the previously instantiated Room and HouseIDVO objects.
        String roomId = room.getId().getID();
        String houseId = room.getHouseID().getID();
        RoomDTO roomDTO = new RoomDTO(roomId, "Office", 2, 3.5, 3, 2, houseId);

        // arranges DeviceDTO
        String deviceName = "Top Load Washing Machine";
        String deviceModel = "Samsung WA50R5400AW";
        DeviceDTO deviceDTO = DeviceDTO.builder().deviceName(deviceName).deviceModel(deviceModel).build();

        when(doubleDeviceRepository.save(any(Device.class))).thenReturn(false);

        AddDeviceToRoomCTRL ctrl = new AddDeviceToRoomCTRL(deviceService);

        // Act
        // Main operation:
        boolean operationResult = ctrl.addDeviceToRoom(roomDTO, deviceDTO);

        // Assert
        assertFalse(operationResult);
    }

    /**
     * Test method to verify that when a null device service is provided to the constructor of AddDeviceToRoomCTRL,
     * it throws an IllegalArgumentException.
     * This test ensures that the controller handles the case of a null device service being passed correctly.
     */
    @Test
    void givenNullDeviceService_constructorThrowsIllegalArgumentException(){
        // Arrange
        String expected = "Invalid services provided";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AddDeviceToRoomCTRL(null));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected, result);
    }


    /**
     * Test method to verify that when a null RoomDTO is passed to the addDeviceToRoom method in AddDeviceToRoomCTRL,
     * it returns false, indicating that the addition of the device to the room was unsuccessful.
     * This ensures that the controller behaves as expected when a null RoomDTO is provided.
     */
    @Test
    void givenNullRoomDTO_WhenAddDeviceToRoom_ThenShouldReturnFalse(){
        // Arrange
        // arranges Room repository
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        // arranges Device Service
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        // arranges DeviceDTO
        String deviceName = "Top Load Washing Machine";
        String deviceModel = "Samsung WA50R5400AW";
        DeviceDTO deviceDTO = DeviceDTO.builder().deviceName(deviceName).deviceModel(deviceModel).build();


        AddDeviceToRoomCTRL ctrl = new AddDeviceToRoomCTRL(deviceService);

        // Act
        boolean result = ctrl.addDeviceToRoom(null, deviceDTO);

        // Assert
        assertFalse(result);
    }

    /**
     * Test method to verify that when a null DeviceDTO is provided to the AddDeviceToRoomCTRL's addDeviceToRoom method,
     * it returns false, indicating a failure to add the device to the room.
     * This test ensures that the controller correctly handles a null DeviceDTO input.
     */
    @Test
    void givenNullDeviceDTO_WhenAddDeviceToRoom_ThenShouldReturnFalse(){
        // Arrange
        // arranges Room repository, creates Room
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomNameVO name = new RoomNameVO("Office");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomWidthVO width = new RoomWidthVO(3.5);
        RoomLengthVO length = new RoomLengthVO(3);
        RoomHeightVO height = new RoomHeightVO(2);
        RoomDimensionsVO dimensions = new RoomDimensionsVO(length, width, height);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        Room room = new Room (name,floor,dimensions,houseID);


        // arranges Device service
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        // arranges RoomDTO. String IDs must be obtained from the previously instantiated Room and HouseIDVO objects.
        String roomId = room.getId().getID();
        String houseId = room.getHouseID().getID();
        RoomDTO roomDTO = new RoomDTO(roomId,"Office",2,3.5,3,2,houseId);

        AddDeviceToRoomCTRL ctrl = new AddDeviceToRoomCTRL(deviceService);

        // Act
        boolean result = ctrl.addDeviceToRoom(roomDTO, null);

        // Assert
        assertFalse(result);
    }

    /**
     * This test ensures that the addDeviceToRoom() method returns false if the information within the RoomDTO does not result
     * in a RoomIDVO object. When giving a wrong UUID format, an IllegalArgumentException is thrown.
     */
    @Test
    void givenRoomDTOWithInvalidIdFormat_WhenAddDeviceToRoom_ThenShouldReturnFalse(){
        // Arrange
        // arranges Room repository, creates Room
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomNameVO name = new RoomNameVO("Office");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomWidthVO width = new RoomWidthVO(3.5);
        RoomLengthVO length = new RoomLengthVO(3);
        RoomHeightVO height = new RoomHeightVO(2);
        RoomDimensionsVO dimensions = new RoomDimensionsVO(length, width, height);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        Room room = new Room (name,floor,dimensions,houseID);


        // arranges Device service
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        // arranges RoomDTO. House ID must be obtained from the previously instantiated HouseIDVO object.
        String houseId = room.getHouseID().getID();
        RoomDTO roomDTO = new RoomDTO("I will fail","Office",2,3.5,3,2,houseId);

        // arranges DeviceDTO
        String deviceName = "Top Load Washing Machine";
        String deviceModel = "Samsung WA50R5400AW";
        DeviceDTO deviceDTO = DeviceDTO.builder().deviceName(deviceName).deviceModel(deviceModel).build();

        AddDeviceToRoomCTRL ctrl = new AddDeviceToRoomCTRL(deviceService);

        // Act
        boolean result = ctrl.addDeviceToRoom(roomDTO, deviceDTO);

        // Assert
        assertFalse(result);
    }

    /**
     * This test ensures that the addDeviceToRoom() method returns false if the information within the RoomDTO does not result
     * in a RoomIDVO object that corresponds to a key in the repository (does not exist).
     * The mock RoomRepository is set up to return true when the isPresent() method is called with the RoomIDVO object
     * of the instantiated Room object.
     * However the RoomDTO object is created with a different UUID, and therefore the RoomIDVO object created from it
     * does not correspond to any key in the repository.
     * The method should return false, as the RoomIDVO object from the RoomDTO is not present in the repository.
     */
    @Test
    void givenRoomDTOWithAnIdNotPresentInRoomRepository_WhenAddDeviceToRoom_ThenShouldReturnFalse(){
        // Arrange
        // arranges Room repository, creates Room
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomNameVO name = new RoomNameVO("Office");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomWidthVO width = new RoomWidthVO(3.5);
        RoomLengthVO length = new RoomLengthVO(3);
        RoomHeightVO height = new RoomHeightVO(2);
        RoomDimensionsVO dimensions = new RoomDimensionsVO(length, width, height);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        Room room = new Room (name,floor,dimensions,houseID);

        when(doubleRoomRepository.isPresent(room.getId())).thenReturn(true);

        // arranges Device Service
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        // arranges RoomDTO. House ID must be obtained from the previously instantiated HouseIDVO object.
        String houseId = room.getHouseID().getID();
        RoomDTO roomDTO = new RoomDTO("d1c6bfb3-8d1e-4e5c-a4fd-6b1dfc7dc8f9","Office",2,3.5,3,2,houseId);

        // arranges DeviceDTO
        String deviceName = "Top Load Washing Machine";
        String deviceModel = "Samsung WA50R5400AW";
        DeviceDTO deviceDTO = DeviceDTO.builder().deviceName(deviceName).deviceModel(deviceModel).build();

        AddDeviceToRoomCTRL ctrl = new AddDeviceToRoomCTRL(deviceService);

        // Act
        boolean result = ctrl.addDeviceToRoom(roomDTO, deviceDTO);

        // Assert
        assertFalse(result);
    }


    /**
     * This test ensures the method addDeviceToRoom() returns true even if the Device to be added has a similar name and model.
     * This indicates that a brand new DeviceIDVO is being created on Device instantiation. The same DeviceDTO is introduced twice
     * in the system and added to the same Room.
     * The mock RoomRepository is set up to return true when the isPresent() method is called with the RoomIDVO object
     * of the instantiated Room object. The mock DeviceRepository is set up to return true when the save() method is called
     * on any Device object.
     */
    @Test
    void whenAddingDevicesWithSameNameAndModel_ThenShouldReturnTrue(){
        // Arrange
        // arranges Room repository, creates Room
        RoomRepository doubleRoomRepository = mock(RoomRepository.class);

        RoomNameVO name = new RoomNameVO("Office");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomWidthVO width = new RoomWidthVO(3.5);
        RoomLengthVO length = new RoomLengthVO(3);
        RoomHeightVO height = new RoomHeightVO(2);
        RoomDimensionsVO dimensions = new RoomDimensionsVO(length, width, height);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        Room room = new Room (name,floor,dimensions,houseID);

        when(doubleRoomRepository.isPresent(room.getId())).thenReturn(true);

        // arranges Device Service
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        DeviceRepository doubleDeviceRepository = mock(DeviceRepository.class);
        SensorRepository doubleSensorRepository = mock(SensorRepository.class);
        ActuatorRepository doubleActuatorRepository = mock(ActuatorRepository.class);
        DeviceService deviceService = new DeviceServiceImpl(doubleRoomRepository, deviceFactory, doubleDeviceRepository, doubleSensorRepository, doubleActuatorRepository);

        // arranges RoomDTO. String IDs must be obtained from the previously instantiated Room and HouseIDVO objects.
        String roomId = room.getId().getID();
        String houseId = room.getHouseID().getID();
        RoomDTO roomDTO = new RoomDTO(roomId,"Office",2,3.5,3,2,houseId);

        // arranges DeviceDTO
        String deviceName = "Top Load Washing Machine";
        String deviceModel = "Samsung WA50R5400AW";
        DeviceDTO deviceDTO = DeviceDTO.builder().deviceName(deviceName).deviceModel(deviceModel).build();

        AddDeviceToRoomCTRL ctrl = new AddDeviceToRoomCTRL(deviceService);

        when(doubleDeviceRepository.save(any(Device.class))).thenReturn(true);

        ctrl.addDeviceToRoom(roomDTO, deviceDTO);

        // Act
        boolean result = ctrl.addDeviceToRoom(roomDTO,deviceDTO);

        // Assert
        assertTrue(result);
    }
}