package smarthome.controller;

import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.vo.roomvo.*;
import smarthome.mapper.dto.RoomDTO;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.RoomRepository;
import smarthome.service.RoomServiceImpl;
import smarthome.domain.vo.housevo.HouseIDVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetListOfRoomsCTRLTest {
    RoomFactoryImpl roomFactory;
    RoomServiceImpl roomService;
    GetListOfRoomsCTRL controller;
    RoomRepository roomRepositoryDouble;

    /**
     * Set up method for initializing the necessary objects before each test.
     * Initializes a Memory House Repository, a V1 House Factory, and a V1 House Service
     * for testing purposes. Also initializes a Memory Room Repository, a V1 Room Factory,
     * and a V1 Room Service with the previously created House Service. Finally, creates
     * a GetListOfRoomsCTRL controller for testing the Get List of Rooms functionality.
     */
    @BeforeEach
    void setUp() {
        HouseRepository houseRepositoryDouble = mock(HouseRepository.class);
        this.roomRepositoryDouble = mock(RoomRepository.class);
        this.roomFactory = new RoomFactoryImpl();
        this.roomService = new RoomServiceImpl(houseRepositoryDouble, roomRepositoryDouble,roomFactory);
        this.controller = new GetListOfRoomsCTRL(roomService);
    }

    /**
     * Test case to verify that creating a GetListOfRoomsCTRL with a null RoomService
     * throws an IllegalArgumentException with the expected error message.
     */
    @Test
    void whenRoomServiceIsNull_thenThrowException() {
        //Arrange
        String expected = "RoomService cannot be null.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GetListOfRoomsCTRL(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that when a house has no rooms calling getListOfRooms() on the GetListOfRoomsCTRL controller
     * returns an empty list of RoomDTO objects.
     */
    @Test
    void whenHouseHasNoRooms_thenReturnEmptyList() {
        // Arrange
        int expected = 0;

        //Act
        List<RoomDTO> list = controller.getListOfRooms();
        int result = list.size();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case to verify that when a house has one room, the controller method returns a list containing that room.
     *
     * The test performs the following steps:
     * 1. Initializes RoomDimensionsVO with expected dimensions.
     * 2. Initializes RoomNameVO, RoomFloorVO, and HouseIDVO for the room and adds it to a list.
     * 3. Mocks RoomRepository with predefined behaviors for save and findAll methods.
     * 4. Calls the getListOfRooms method from the controller.
     * 5. Retrieves the first room from the returned list.
     * 6. Compares the retrieved room details with the expected values.
     * 7. Asserts that the retrieved room details match the expected values.
     */

    @Test
    void whenHouseHasOneRoom_thenReturnListWithOneRoom() {
        // Arrange
        //Initializing roomDimensionsVO
        double expectedWidth = 3.5;
        RoomWidthVO roomWidth = new RoomWidthVO(expectedWidth);
        double expectedLength = 3;
        RoomLengthVO roomLength = new RoomLengthVO(expectedLength);
        double expectedHeight = 2;
        RoomHeightVO roomHeight = new RoomHeightVO(expectedHeight);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(roomLength, roomWidth, roomHeight);

        //Initializing room1 and add to List
        List<Room> roomList = new ArrayList<>();
        Iterable<Room> roomIterable = roomList;
        String expectedName = "Office";
        RoomNameVO roomName = new RoomNameVO(expectedName);
        int expectedFloor = 2;
        RoomFloorVO roomFloor = new RoomFloorVO(expectedFloor);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        String expectedHouseID = houseID.getID();
        Room room1 = roomFactory.createRoom(roomName, roomFloor, roomDimensions, houseID);
        roomList.add(room1);

        //Adding behaviour to save and findall methods of roomRepositoryDouble
        when(roomRepositoryDouble.save(any(Room.class))).thenReturn(true);
        when(roomRepositoryDouble.findAll()).thenReturn(roomIterable);

        //GetListOfRooms method called from controller
        List<RoomDTO> list = controller.getListOfRooms();

        // Act
        String resultName = list.get(0).getRoomName();
        String resultHouseID = list.get(0).getHouseID();
        int resultFloor = list.get(0).getFloor();
        double resultWidth = list.get(0).getRoomWidth();
        double resultLength = list.get(0).getRoomLength();
        double resultHeight = list.get(0).getRoomHeight();

        // Assert
        assertEquals(expectedName, resultName);
        assertEquals(expectedFloor, resultFloor);
        assertEquals(expectedHouseID, resultHouseID);
        assertEquals(expectedWidth, resultWidth);
        assertEquals(expectedLength, resultLength);
        assertEquals(expectedHeight, resultHeight);
    }

    /**
     * Test case to verify that when a house has multiple rooms, the controller method returns the name of the second room.
     *
     * The test performs the following steps:
     * 1. Initializes Room1 and Room2 with expected details and adds them to a list.
     * 2. Mocks RoomRepository with predefined behaviors for save and findAll methods.
     * 3. Calls the getListOfRooms method from the controller.
     * 4. Retrieves the details of the second room from the returned list.
     * 5. Compares the retrieved room details with the expected values.
     * 6. Asserts that the retrieved room details match the expected values.
     */

    @Test
    void whenHouseHasMultipleRooms_thenReturnNameOfSecondRoom() {
        // Arrange
        //Initializing Room1 and Room2 and add to List
        List<Room> roomList = new ArrayList<>();
        Iterable<Room> roomIterable = roomList;
        RoomNameVO roomName1 = new RoomNameVO("Living Room");
        RoomFloorVO roomFloor1 = new RoomFloorVO(1);
        double expectedWidth2 = 3.5;
        RoomWidthVO roomWidth2 = new RoomWidthVO(expectedWidth2);
        double expectedLength2 = 3;
        RoomLengthVO roomLength2 = new RoomLengthVO(expectedLength2);
        double expectedHeight2 = 2;
        RoomHeightVO roomHeight2 = new RoomHeightVO(expectedHeight2);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(roomLength2, roomWidth2, roomHeight2);
        String expectedName2 = "Kitchen";
        RoomNameVO roomName2 = new RoomNameVO(expectedName2);
        int expectedFloor2 = 2;
        RoomFloorVO roomFloor2 = new RoomFloorVO(expectedFloor2);
        HouseIDVO houseID2 = new HouseIDVO(UUID.randomUUID());
        String expectedHouseID2 = houseID2.getID();
        Room room1 = roomFactory.createRoom(roomName1, roomFloor1, roomDimensions, houseID2);
        Room room2 = roomFactory.createRoom(roomName2, roomFloor2, roomDimensions, houseID2);
        roomList.add(room1);
        roomList.add(room2);

        //Adding behaviour to save and findall methods of roomRepositoryDouble
        when(roomRepositoryDouble.save(any(Room.class))).thenReturn(true);
        when(roomRepositoryDouble.findAll()).thenReturn(roomIterable);

        //GetListOfRooms method called from controller
        List<RoomDTO> list = controller.getListOfRooms();

        // Act
        String resultName = list.get(1).getRoomName();
        String resultHouseID = list.get(1).getHouseID();
        int resultFloor = list.get(1).getFloor();
        double resultWidth = list.get(1).getRoomWidth();
        double resultLength = list.get(1).getRoomLength();
        double resultHeight = list.get(1).getRoomHeight();

        // Assert
        assertEquals(expectedName2, resultName);
        assertEquals(expectedFloor2, resultFloor);
        assertEquals(expectedHouseID2, resultHouseID);
        assertEquals(expectedWidth2, resultWidth);
        assertEquals(expectedLength2, resultLength);
        assertEquals(expectedHeight2, resultHeight);
    }
}