package smarthome.mapper.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.*;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * RoomAssemblerTest is a test class for the RoomAssembler class.
 */

class RoomAssemblerTest {

    private String roomName;
    private int floor;
    private double roomHeight, roomLength, roomWidth;
    private String houseID;

    /**
     * This method sets up the necessary variables for the tests.
     */

    @BeforeEach
    void setUp() {
        roomName = "BedRoom";
        floor = 2;
        roomHeight = 2.5;
        roomLength = 5.6;
        roomWidth = 3.2;
        houseID = "00000000-0000-0000-0000-000000000000";
    }

    /**
     * This method tests the creation of a RoomDataModel object.
     * It uses correct value objects to create a Room object.
     * It checks if the RoomDataModel object created has the same attributes as the Room object.
     */

    @Test
    void givenCorrectValueObjects_whenCreatingDataModel_thenReturnRoomDataModel() {
//        Arrange
        RoomNameVO roomNameVO = new RoomNameVO(roomName);
        RoomFloorVO roomFloorVO = new RoomFloorVO(floor);
        RoomHeightVO roomHeightVO = new RoomHeightVO(roomHeight);
        RoomLengthVO roomLengthVO = new RoomLengthVO(roomLength);
        RoomWidthVO roomWidthVO = new RoomWidthVO(roomWidth);
        RoomDimensionsVO roomDimensionsVO = new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        Room room = new Room(roomNameVO, roomFloorVO, roomDimensionsVO, houseIDVO);
        String expectedRoomID = room.getId().getID();
        String expectedRoomName = room.getRoomName().getValue();
        int expectedFloor = room.getFloor().getValue();
        double expectedRoomHeight = room.getRoomDimensions().getRoomHeight();
        double expectedRoomLength = room.getRoomDimensions().getRoomLength();
        double expectedRoomWidth = room.getRoomDimensions().getRoomWidth();
        String expectedHouseID = room.getHouseID().getID();
//        Act
        RoomDataModel roomDataModel = new RoomDataModel(room);
//        Assert
        assertEquals(roomDataModel.getRoomID(), expectedRoomID);
        assertEquals(roomDataModel.getRoomName(), expectedRoomName);
        assertEquals(roomDataModel.getRoomFloor(), expectedFloor);
        assertEquals(roomDataModel.getRoomHeight(), expectedRoomHeight);
        assertEquals(roomDataModel.getRoomLength(), expectedRoomLength);
        assertEquals(roomDataModel.getRoomWidth(), expectedRoomWidth);
        assertEquals(roomDataModel.getHouseID(), expectedHouseID);
    }

    /**
     * This method tests the toDomain() method of the RoomAssembler class.
     * It tests the conversion of a RoomDataModel object to a Room object.
     * It uses a RoomDataModel object to test the method.
     * It checks if the Room object created has the same attributes as the RoomDataModel object.
     */

    @Test
    void givenRoomDataModel_whenConvertingToDomain_thenReturnDomainRoom() {
//        Arrange
        RoomNameVO roomNameVO = new RoomNameVO(roomName);
        RoomFloorVO roomFloorVO = new RoomFloorVO(floor);
        RoomHeightVO roomHeightVO = new RoomHeightVO(roomHeight);
        RoomLengthVO roomLengthVO = new RoomLengthVO(roomLength);
        RoomWidthVO roomWidthVO = new RoomWidthVO(roomWidth);
        RoomDimensionsVO roomDimensionsVO = new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        Room room = new Room(roomNameVO, roomFloorVO, roomDimensionsVO, houseIDVO);

        RoomDataModel roomDataModel = new RoomDataModel(room);
        String roomIDVO = roomDataModel.getRoomID();

        RoomFactory roomFactory = new RoomFactoryImpl();
//        Act
        Room newRoom = RoomAssembler.toDomain(roomFactory, roomDataModel);
//        Arrange
        assertEquals(newRoom.getId().getID(), roomIDVO);
        assertEquals(newRoom.getRoomName().getValue(), roomName);
        assertEquals(newRoom.getFloor().getValue(), floor);
        assertEquals(newRoom.getRoomDimensions().getRoomHeight(), roomHeight);
        assertEquals(newRoom.getRoomDimensions().getRoomLength(), roomLength);
        assertEquals(newRoom.getRoomDimensions().getRoomWidth(), roomWidth);
        assertEquals(newRoom.getHouseID().getID(), houseID);
    }

    /**
     * This method tests the toDomainList() method of the RoomAssembler class.
     * It tests the conversion of a list of RoomDataModel objects to a list of Room objects.
     * It uses a list with two RoomDataModel objects to test the method.
     * It checks if the Room objects created have the same attributes as the RoomDataModel objects.
     */

    @Test
    void givenListWithTwoRoomDataModelObjects_whenToDomainListIsCalled_thenReturnIterableWithDomainObjects() {
//         Arrange
        RoomNameVO roomName = new RoomNameVO(this.roomName);
        RoomFloorVO roomFloor = new RoomFloorVO(floor);
        RoomHeightVO roomHeight = new RoomHeightVO(this.roomHeight);
        RoomLengthVO roomLength = new RoomLengthVO(this.roomLength);
        RoomWidthVO roomWidth = new RoomWidthVO(this.roomWidth);
        RoomDimensionsVO roomDimensions = new RoomDimensionsVO(roomLength, roomWidth, roomHeight);
        HouseIDVO houseID = new HouseIDVO(UUID.fromString(this.houseID));

        Room room = new Room(roomName, roomFloor, roomDimensions, houseID);

        RoomNameVO secondRoomName = new RoomNameVO("LivingRoom");
        RoomFloorVO secondRoomFloor = new RoomFloorVO(1);
        RoomHeightVO secondRoomHeight = new RoomHeightVO(3.0);
        RoomLengthVO secondRoomLength = new RoomLengthVO(6.0);
        RoomWidthVO secondRoomWidth = new RoomWidthVO(4.0);
        RoomDimensionsVO secondRoomDimensions = new RoomDimensionsVO(secondRoomLength, secondRoomWidth, secondRoomHeight);
        HouseIDVO secondHouseID = new HouseIDVO(UUID.fromString("11111111-1111-1111-1111-111111111111"));

        Room secondRoom = new Room(secondRoomName, secondRoomFloor, secondRoomDimensions, secondHouseID);

        RoomDataModel roomDataModel = new RoomDataModel(room);
        RoomDataModel secondRoomDataModel = new RoomDataModel(secondRoom);

        List<RoomDataModel> roomDataModelList = List.of(roomDataModel, secondRoomDataModel);

        RoomFactory roomFactory = new RoomFactoryImpl();
//        Act
        Iterable<Room> roomIterableList = RoomAssembler.toDomainList(roomFactory, roomDataModelList);
//        Assert
        List<Room> roomList = new ArrayList<>();
        roomIterableList.forEach(roomList::add);

        Room firstRoom = roomList.get(0);

        assertEquals(roomDataModel.getRoomID(), firstRoom.getId().getID());
        assertEquals(roomDataModel.getRoomName(), firstRoom.getRoomName().getValue());
        assertEquals(roomDataModel.getRoomFloor(), firstRoom.getFloor().getValue());
        assertEquals(roomDataModel.getRoomHeight(), firstRoom.getRoomDimensions().getRoomHeight());
        assertEquals(roomDataModel.getRoomLength(), firstRoom.getRoomDimensions().getRoomLength());
        assertEquals(roomDataModel.getRoomWidth(), firstRoom.getRoomDimensions().getRoomWidth());
        assertEquals(roomDataModel.getHouseID(), firstRoom.getHouseID().getID());

        Room secondRoomFromList = roomList.get(1);

        assertEquals(secondRoomDataModel.getRoomID(), secondRoomFromList.getId().getID());
        assertEquals(secondRoomDataModel.getRoomName(), secondRoomFromList.getRoomName().getValue());
        assertEquals(secondRoomDataModel.getRoomFloor(), secondRoomFromList.getFloor().getValue());
        assertEquals(secondRoomDataModel.getRoomHeight(), secondRoomFromList.getRoomDimensions().getRoomHeight());
        assertEquals(secondRoomDataModel.getRoomLength(), secondRoomFromList.getRoomDimensions().getRoomLength());
        assertEquals(secondRoomDataModel.getRoomWidth(), secondRoomFromList.getRoomDimensions().getRoomWidth());
        assertEquals(secondRoomDataModel.getHouseID(), secondRoomFromList.getHouseID().getID());
    }
}
