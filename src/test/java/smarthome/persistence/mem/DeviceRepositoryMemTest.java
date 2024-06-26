package smarthome.persistence.mem;

import smarthome.domain.device.Device;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeviceRepositoryMemTest {
    /**
     * Tests if a device is saved when it is null.
     * This test case verifies that a device cannot be saved when it is null.
     */
    @Test
    void whenDeviceIsNull_thenItCannotBeSaved() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();

        //Act
        boolean result = deviceRepositoryMem.save(null);

        //Assert
        assertFalse(result);
    }

    /**
     * Tests if a device is saved when its ID is null.
     * This test case verifies that a device cannot be saved when its ID is null.
     */
    @Test
    void whenDeviceIDIsNull_thenItCannotBeSaved() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();
        Device device = mock(Device.class);
        when(device.getId()).thenReturn(null);

        //Act
        boolean result = deviceRepositoryMem.save(device);

        //Assert
        assertFalse(result);
    }

    /**
     * Tests if a device is saved when its ID is already saved.
     * This test case verifies that a device cannot be saved when its ID is present1.
     */
    @Test
    void whenDeviceIsPresent_thenItCannotBeSaved() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();
        Device device = mock(Device.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(device.getId()).thenReturn(deviceID);
        deviceRepositoryMem.save(device);

        //Act
        boolean result = deviceRepositoryMem.save(device);

        //Assert
        assertFalse(result);
    }

    /**
     * Tests if a device is saved when it is valid.
     * This test case verifies that a device can be saved when it is valid.
     */
    @Test
    void whenDeviceIsValid_thenItCanBeSaved() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();
        Device device = mock(Device.class);
        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(device.getId()).thenReturn(deviceID);

        //Act
        boolean result = deviceRepositoryMem.save(device);

        //Assert
        assertTrue(result);
    }

    /**
     * Tests if a device is saved and can be found by its ID.
     * The test case verifies that a device can be successfully saved to the repository
     * and subsequently retrieved using its ID.
     */
    @Test
    void whenDeviceIsSaved_thenItCanBeFoundById() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();
        Device device = mock(Device.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(device.getId()).thenReturn(deviceID);

        //Act
        deviceRepositoryMem.save(device);
        Device result = deviceRepositoryMem.findById(deviceID);

        //Assert
        assertEquals(device, result);

    }

    /**
     * This test case ensures that when a device  saved in the repository,
     * it does appear in the list of devices returned by the findAll method.
     */
    @Test
    void whenDeviceIsSaved_thenItShouldAppearInFindAll() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();

        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        Device device3 = mock(Device.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(device2.getId()).thenReturn(deviceID);

        deviceRepositoryMem.save(device1);
        deviceRepositoryMem.save(device2);
        deviceRepositoryMem.save(device3);

        //Act
        Iterable<Device> iterable = deviceRepositoryMem.findAll();
        boolean isDevice2Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(device -> device.equals(device2));

        //Assert

        assertTrue(isDevice2Present);
    }

    /**
     * Tests if a device is not saved and does not appear in the findAll method.
     * This test case ensures that when a device is not saved in the repository,
     * it does not appear in the list of devices returned by the findAll method.
     */
    @Test
    void whenDeviceIsNotSaved_thenItShouldNotAppearInFindAll() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();

        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        Device device3 = mock(Device.class);

        DeviceIDVO deviceID = mock(DeviceIDVO.class);
        when(device2.getId()).thenReturn(deviceID);

        deviceRepositoryMem.save(device1);
        deviceRepositoryMem.save(device3);

        //Act
        Iterable<Device> iterable = deviceRepositoryMem.findAll();
        boolean isDevice2Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(device -> device.equals(device2));

        //Assert
        assertFalse(isDevice2Present);
    }

    /**
     * Tests if a device that is not present in the repository cannot be found by its ID.
     * This test case ensures that when a device is not saved in the repository,
     * attempting to find it by its ID returns null.
     */
    @Test
    void whenDeviceIsNotPresent_thenItCannotBeFoundById() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();

        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        Device device3 = mock(Device.class);

        DeviceIDVO device2ID = mock(DeviceIDVO.class);

        when(device2.getId()).thenReturn(device2ID);

        deviceRepositoryMem.save(device1);
        deviceRepositoryMem.save(device3);

        //Act
        Device result = deviceRepositoryMem.findById(device2ID);

        //Assert
        assertNull(result);
    }

    /**
     * Tests if when RoomID is not found, an empty list is returned.
     */
    @Test
    void whenRoomIDIsNotPresent_thenReturnEmptyList() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();

        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);

        deviceRepositoryMem.save(device1);
        deviceRepositoryMem.save(device2);

        RoomIDVO roomID = mock(RoomIDVO.class);
        int expected = 0;

        //Act
        int result = deviceRepositoryMem.findByRoomID(roomID).size();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test case verifies that a list of devices associated with a given room ID
     * is returned when the room ID is present in the repository.
     */
    @Test
    void whenRoomIDIsPresent_ShouldReturnListOfDevicesInRoom() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();
        RoomIDVO roomID = mock(RoomIDVO.class);

        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);

        DeviceIDVO deviceID1 = mock(DeviceIDVO.class);
        DeviceIDVO deviceID2 = mock(DeviceIDVO.class);

        when(device1.getId()).thenReturn(deviceID1);
        when(device2.getId()).thenReturn(deviceID2);

        deviceRepositoryMem.save(device1);
        deviceRepositoryMem.save(device2);

        when(device1.getRoomID()).thenReturn(roomID);
        when(device2.getRoomID()).thenReturn(roomID);

        //Act
        List<Device> listOfDevicesInARoom = deviceRepositoryMem.findByRoomID(roomID);

        //Assert
        assertTrue(listOfDevicesInARoom.contains(device1));
        assertTrue(listOfDevicesInARoom.contains(device2));
    }

    /**
     * This test ensures no devices can be saved if the deviceID is already present as key.
     */
    @Test
    void givenDuplicateEntity_RepositoryDoesNotSaveSecondOne() {
        // Arrange
        DeviceIDVO deviceID = mock(DeviceIDVO.class);

        Device device1 = mock(Device.class);
        when(device1.getId()).thenReturn(deviceID);

        Device device2 = mock(Device.class);
        when(device2.getId()).thenReturn(deviceID);

        DeviceRepositoryMem repository = new DeviceRepositoryMem();
        repository.save(device1);

        // Act
        boolean result = repository.save(device2);

        // Assert
        assertFalse(result);
    }

    /**
     * This test ensures that the repository can find a device by its specific RoomID.
     */
    @Test
    void givenHouseWithTwoRooms_ThenReturnListOfDevicesInSpecificRoom() {
        //Arrange
        DeviceRepositoryMem deviceRepositoryMem = new DeviceRepositoryMem();
        RoomIDVO roomID = mock(RoomIDVO.class);
        RoomIDVO roomID2 = mock(RoomIDVO.class);

        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        Device device3 = mock(Device.class);

        DeviceIDVO deviceID1 = mock(DeviceIDVO.class);
        DeviceIDVO deviceID2 = mock(DeviceIDVO.class);
        DeviceIDVO deviceID3 = mock(DeviceIDVO.class);

        when(device1.getId()).thenReturn(deviceID1);
        when(device2.getId()).thenReturn(deviceID2);
        when(device3.getId()).thenReturn(deviceID3);

        deviceRepositoryMem.save(device1);
        deviceRepositoryMem.save(device2);
        deviceRepositoryMem.save(device3);

        when(device1.getRoomID()).thenReturn(roomID);
        when(device2.getRoomID()).thenReturn(roomID2);
        when(device3.getRoomID()).thenReturn(roomID);

        when(device1.getRoomID()).thenReturn(roomID);
        when(device2.getRoomID()).thenReturn(roomID);
        when(device3.getRoomID()).thenReturn(roomID2);

        //Act
        List<Device> listOfDevicesInARoom = deviceRepositoryMem.findByRoomID(roomID);

        //Assert
        assertTrue(listOfDevicesInARoom.contains(device1));
        assertTrue(listOfDevicesInARoom.contains(device2));
        assertFalse(listOfDevicesInARoom.contains(device3));
    }
}