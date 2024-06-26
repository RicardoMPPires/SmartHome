package smarthome.persistence.mem;

import smarthome.domain.room.Room;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomRepositoryMemTest {

    /**
     * /**
     *  This test verifies that the save method returns false when a null Room is provided for saving.
     *  First, a roomRepository is created. Then, a null Room is created.
     *  Then, the repository rejects attempts to save invalid data. The null Room represents
     *  invalid data as it wouldn't contain any information to be persisted.
     */
    @Test
    void whenRoomIsNull_thenRoomNotSaved() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();

        //Act
        boolean result = roomRepositoryMem.save(null);

        //Assert
        assertFalse(result);
    }

    /**
     * This test verifies that the save method returns false when a Room with a null ID is provided for saving.
     * First, a roomRepository is created. Then, a Room is created with a null ID.
     * The repository rejects attempts to save invalid data. The null ID represents
     * invalid data as it wouldn't contain any information to be persisted.
     */
    @Test
    void whenRoomIDIsNull_thenRoomNotSaved() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        Room room = mock(Room.class);
        when(room.getId()).thenReturn(null);

        //Act
        boolean result = roomRepositoryMem.save(room);

        //Assert
        assertFalse(result);
    }

    /**
     * This test verifies that the save method returns false when a Room with a present ID is provided for saving.
     * First, a roomRepository is created. Then, a Room is mocked, as well as the roomIDVO.
     * The repository rejects attempts to save invalid data. The RoomIDVO represents
     * invalid data as it would already be present in the repository.
     */
    @Test
    void whenRoomIDIsPresent_thenRoomNotSaved() {
        //Arrange
        Room roomDouble = mock(Room.class);
        RoomIDVO roomID = mock(RoomIDVO.class);
        when(roomDouble.getId()).thenReturn(roomID);

        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        roomRepositoryMem.save(roomDouble);

        //Act
        boolean result = roomRepositoryMem.save(roomDouble);

        //Assert
        assertFalse(result);
    }

    /**
     * This test verifies that the save method returns true when a valid Room is provided for saving.
     * First, a roomRepository is created. Then, a Room is mocked, as well as the roomIDVO.
     * The repository accepts attempts to save valid data. Both the mocked Room and RoomIDVO represent
     * valid data as they contain information to be persisted.
     */
    @Test
    void whenRoomIsValid_thenRoomIsSaved() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        Room room = mock(Room.class);
        RoomIDVO roomID = mock(RoomIDVO.class);
        when(room.getId()).thenReturn(roomID);

        //Act
        boolean result = roomRepositoryMem.save(room);

        //Assert
        assertTrue(result);
    }

    /**
     * This test verifies that the findById method returns the Room with the given ID when it is present in the repository.
     * First, a roomRepository is created. Then, a Room is mocked, as well as the roomIDVO.
     * Then, the roomID is inserted when the room behavior is mimicked.
     * The room is saved in the repository.
     * Afterward, the room is found by its ID.
     * Finally, the expected and actual results are compared.
     */
    @Test
    void whenRoomIsSaved_thenIsFoundByRoomId() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        Room room = mock(Room.class);
        RoomIDVO roomID = mock(RoomIDVO.class);
        when(room.getId()).thenReturn(roomID);
        roomRepositoryMem.save(room);

        //Act

        Room result = roomRepositoryMem.findById(roomID);

        //Assert
        assertEquals(room,result);
    }

    /**
     * This test verifies that the findAll method returns all the rooms saved in the repository.
     * First, a roomRepository is created. Then, three Rooms are mocked, as well as the respective roomIDVOs.
     * Then, the roomIDs are inserted when the rooms behavior is mimicked.
     * Afterward, the rooms are saved in the repository.
     * Then, an Iterable is created to store all the rooms in the repository, followed by booleans that are true if there are
     * any matches between the rooms saved and the rooms in the Iterable list.
     * Finally, an assertTrue is used to verify if all the rooms saved were present in the Iterable list.
     */
    @Test
    void whenRoomsAreSaved_thenFoundInFindAll() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);
        RoomIDVO room1ID = mock(RoomIDVO.class);
        when(room1.getId()).thenReturn(room1ID);
        RoomIDVO room2ID = mock(RoomIDVO.class);
        when(room2.getId()).thenReturn(room2ID);
        RoomIDVO room3ID = mock(RoomIDVO.class);
        when(room3.getId()).thenReturn(room3ID);
        roomRepositoryMem.save(room1);
        roomRepositoryMem.save(room2);
        roomRepositoryMem.save(room3);

        //Act
        Iterable<Room> iterable = roomRepositoryMem.findAll();
        boolean isRoom1Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(room -> room.equals(room1));
        boolean isRoom2Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(room -> room.equals(room2));
        boolean isRoom3Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(room -> room.equals(room3));

        //Assert
        assertTrue(isRoom1Present);
        assertTrue(isRoom2Present);
        assertTrue(isRoom3Present);
    }

    /**
     * This test verifies that the findAll method does not return a room that was not saved in the repository.
     * First, a roomRepository is created. Then, three Rooms are mocked, as well as a roomIDVO.
     * Then, the roomID of the third room is inserted when the room behavior is mimicked.
     * Afterward, the first two rooms are saved in the repository.
     * Then, an Iterable is created to store these rooms in the repository, followed by a boolean that is false if room3
     * was not saved unto the repository.
     * Finally, an assertFalse is used to verify if room3 was not present in the Iterable list.
     */
    @Test
    void whenRoomIsNotSaved_thenNotFoundWithFindAll() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);
        RoomIDVO roomID = mock(RoomIDVO.class);
        when(room3.getId()).thenReturn(roomID);
        roomRepositoryMem.save(room1);
        roomRepositoryMem.save(room2);

        //Act
        Iterable<Room> iterable = roomRepositoryMem.findAll();
        boolean isRoom3Present = StreamSupport.stream(iterable.spliterator(), false)
                .anyMatch(room -> room.equals(room3));

        //Assert
        assertFalse(isRoom3Present);
    }

    /**
     * This test verifies that the findById method returns null when the room is not present in the repository.
     * First, a roomRepository is created. Then, three Rooms are mocked, as well as a roomIDVO.
     * Then, the roomID of the third room is inserted when the room behavior is mimicked.
     * Afterward, the first two rooms are saved in the repository.
     *Then, the third room is search recurring to its ID, which should return a false result.
     * Finally, an assertNull is used to verify if the third room was not found in the repository.
     */
    @Test
    void whenRoomIsNotPresent_thenNotFoundById() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);
        RoomIDVO room3ID = mock(RoomIDVO.class);
        when(room3.getId()).thenReturn(room3ID);
        roomRepositoryMem.save(room1);
        roomRepositoryMem.save(room2);

        //Act
        Room result = roomRepositoryMem.findById(room3ID);

        //Assert
        assertNull(result);
    }

    /**
     * This test verifies that the findHouseByID method returns an empty list when the houseID is not present in the repository.
     * First, a roomRepository is created. Then, two Rooms are mocked.
     * Then, the rooms are saved in the repository without attributing houseIDs to be comparable.
     * Afterward, a houseID is mocked and an expected integer is set to 0.
     * Then, the houseID is used to search for rooms in the repository, with a result that should be an empty list, as the size of the list should be 0.
     * Finally, an assertEquals is used to verify if the expected result is equal to the actual result.
     */
    @Test
    void whenHouseIDIsNotPresent_thenReturnEmptyList() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        roomRepositoryMem.save(room1);
        roomRepositoryMem.save(room2);
        HouseIDVO houseID = mock(HouseIDVO.class);
        int expected = 0;

        //Act
        int result = roomRepositoryMem.findByHouseID(houseID).size();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * This test verifies that the findHouseByID method returns a list of rooms when the houseID is present in the repository.
     * First, a roomRepository is created. Then, a houseId is mocked, as well as two Rooms and two RoomIDVOs.
     * Then, the roomIDs are inserted when the rooms behavior is mimicked.
     * The houseID is also inserted when the rooms behavior is mimicked.
     * The rooms are saved in the repository with the same houseID attributed.
     * Afterward, the houseID is used to search for rooms in the repository, with a result that should be a list of rooms.
     * Finally, an assertTrue is used to verify if the list of rooms contains the rooms saved.
     */
    @Test
    void whenHouseIDIsPresent_thenReturnListOfRoomsInHouse() {
        //Arrange
        RoomRepositoryMem roomRepositoryMem = new RoomRepositoryMem();
        HouseIDVO houseID = mock(HouseIDVO.class);
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        RoomIDVO roomID1 = mock(RoomIDVO.class);
        RoomIDVO roomID2 = mock(RoomIDVO.class);
        when(room1.getId()).thenReturn(roomID1);
        when(room2.getId()).thenReturn(roomID2);
        when(room1.getHouseID()).thenReturn(houseID);
        when(room2.getHouseID()).thenReturn(houseID);
        roomRepositoryMem.save(room1);
        roomRepositoryMem.save(room2);

        //Act
        List<Room> listOfRoomsInAHouse = roomRepositoryMem.findByHouseID(houseID);

        //Assert
        assertTrue(listOfRoomsInAHouse.contains(room1));
        assertTrue(listOfRoomsInAHouse.contains(room2));
    }

}
