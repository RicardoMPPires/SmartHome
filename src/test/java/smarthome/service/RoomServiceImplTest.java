package smarthome.service;

import org.junit.jupiter.api.Test;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.*;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.mem.HouseRepositoryMem;
import smarthome.persistence.mem.RoomRepositoryMem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomServiceImplTest {

    /**
     * This test ensures that, when given a null RoomRepository, the constructor throws and IllegalArgumentException.
     */
    @Test
    void whenGivenAnInvalidRoomRepository_ConstructorThrowsIllegalArgument(){
        // Arrange
        HouseRepositoryMem houseRepository = mock(HouseRepositoryMem.class);
        RoomFactoryImpl factory = mock(RoomFactoryImpl.class);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new RoomServiceImpl(houseRepository,null,factory));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test ensures that, when given a null factory, the constructor throws and IllegalArgumentException.
     */
    @Test
    void whenGivenAnInvalidFactory_ConstructorThrowsIllegalArgument(){
        // Arrange
        HouseRepositoryMem houseRepository = mock(HouseRepositoryMem.class);
        RoomRepositoryMem repository = mock(RoomRepositoryMem.class);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new RoomServiceImpl(houseRepository,repository,null));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test ensures that, when given a null houseRepository, the constructor throws and IllegalArgumentException.
     */
    @Test
    void whenGivenAnInvalidHouseRepository_ConstructorThrowsIllegalArgument(){
        // Arrange
        RoomRepositoryMem repository = mock(RoomRepositoryMem.class);
        RoomFactoryImpl factory = mock(RoomFactoryImpl.class);
        String expected = "Invalid parameters";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new RoomServiceImpl(null,repository,factory));
        String result = exception.getMessage();

        // Assert
        assertEquals(expected,result);
    }

    /**
     * This test ensures that, when given valid parameters, the constructor creates a new Room object.
     */
    @Test
    void givenValidParameters_whenAddRoom_thenReturnRoom() {
        // Arrange
        HouseRepository houseRepository = mock(HouseRepositoryMem.class);
        RoomRepository repository = mock(RoomRepositoryMem.class);
        RoomFactory factory = mock(RoomFactoryImpl.class);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        when(houseRepository.getFirstHouseIDVO()).thenReturn(houseID);
        RoomNameVO roomNameVO = new RoomNameVO("Room");
        RoomFloorVO roomFloorVO = new RoomFloorVO(1);
        RoomLengthVO roomLengthVO = new RoomLengthVO(1);
        RoomWidthVO roomWidthVO = new RoomWidthVO(1);
        RoomHeightVO roomHeightVO = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensionsVO = new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        Room room = new Room(roomNameVO, roomFloorVO, roomDimensionsVO, houseID);
        when(factory.createRoom(roomNameVO, roomFloorVO, roomDimensionsVO, houseID)).thenReturn(room);
        when(repository.save(room)).thenReturn(true);
        RoomServiceImpl service = new RoomServiceImpl(houseRepository, repository, factory);


        // Act
        Optional<Room> result = service.addRoom(roomNameVO, roomFloorVO, roomDimensionsVO);

        // Assert
        assertEquals(room.getRoomName(), result.get().getRoomName());
        assertEquals(room.getFloor(), result.get().getFloor());
        assertEquals(room.getRoomDimensions(), result.get().getRoomDimensions());
        assertEquals(room.getHouseID(), result.get().getHouseID());
    }

    /**
     * This test ensures that, when rooms exist, the findAll method returns a list of rooms.
     */
    @Test
    void givenValidRooms_whenFindAll_thenReturnRoomList() {
        //Arrange
        HouseRepository houseRepository = mock(HouseRepositoryMem.class);
        RoomRepository repository = mock(RoomRepositoryMem.class);
        RoomFactory factory = mock(RoomFactoryImpl.class);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        when(houseRepository.getFirstHouseIDVO()).thenReturn(houseID);
        RoomNameVO roomNameVO = new RoomNameVO("Room");
        RoomFloorVO roomFloorVO = new RoomFloorVO(1);
        RoomLengthVO roomLengthVO = new RoomLengthVO(1);
        RoomWidthVO roomWidthVO = new RoomWidthVO(1);
        RoomHeightVO roomHeightVO = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensionsVO = new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        Room room = new Room(roomNameVO, roomFloorVO, roomDimensionsVO, houseID);
        RoomNameVO room2NameVO = new RoomNameVO("Room2");
        RoomFloorVO room2FloorVO = new RoomFloorVO(2);
        RoomLengthVO room2LengthVO = new RoomLengthVO(2);
        RoomWidthVO room2WidthVO = new RoomWidthVO(2);
        RoomHeightVO room2HeightVO = new RoomHeightVO(2);
        RoomDimensionsVO room2DimensionsVO = new RoomDimensionsVO(room2LengthVO, room2WidthVO, room2HeightVO);
        Room room2 = new Room(room2NameVO, room2FloorVO, room2DimensionsVO, houseID);
        RoomServiceImpl service = new RoomServiceImpl(houseRepository, repository, factory);
        service.addRoom(roomNameVO, roomFloorVO, roomDimensionsVO);
        service.addRoom(room2NameVO, room2FloorVO, room2DimensionsVO);
        when(factory.createRoom(roomNameVO, roomFloorVO, roomDimensionsVO, houseID)).thenReturn(room);
        when(factory.createRoom(room2NameVO, room2FloorVO, room2DimensionsVO, houseID)).thenReturn(room2);
        when(repository.save(room)).thenReturn(true);
        when(repository.save(room2)).thenReturn(true);
        when(repository.findAll()).thenReturn(List.of(room, room2));


        //Act
        List<Room> roomList = service.findAll();

        //Assert
        assertEquals(2, roomList.size());
        assertEquals(roomList.get(0).getRoomName(), room.getRoomName());
        assertEquals(roomList.get(0).getFloor(), room.getFloor());
        assertEquals(roomList.get(0).getRoomDimensions(), room.getRoomDimensions());
        assertEquals(roomList.get(0).getHouseID(), room.getHouseID());
        assertEquals(roomList.get(1).getRoomName(), room2.getRoomName());
        assertEquals(roomList.get(1).getFloor(), room2.getFloor());
        assertEquals(roomList.get(1).getRoomDimensions(), room2.getRoomDimensions());
        assertEquals(roomList.get(1).getHouseID(), room2.getHouseID());
    }

    /**
     * This test ensures that, when rooms are not saved, the addRoom method returns an empty optional.
     */
    @Test
    void givenValidParameters_whenSaveRoomIsFalse_thenReturnEmptyOptional() {
        // Arrange
        HouseRepository houseRepository = mock(HouseRepositoryMem.class);
        RoomRepository repository = mock(RoomRepositoryMem.class);
        RoomFactory factory = mock(RoomFactoryImpl.class);
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());
        when(houseRepository.getFirstHouseIDVO()).thenReturn(houseID);
        RoomNameVO roomNameVO = new RoomNameVO("Room");
        RoomFloorVO roomFloorVO = new RoomFloorVO(1);
        RoomLengthVO roomLengthVO = new RoomLengthVO(1);
        RoomWidthVO roomWidthVO = new RoomWidthVO(1);
        RoomHeightVO roomHeightVO = new RoomHeightVO(1);
        RoomDimensionsVO roomDimensionsVO = new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        Room room = new Room(roomNameVO, roomFloorVO, roomDimensionsVO, houseID);
        when(factory.createRoom(roomNameVO, roomFloorVO, roomDimensionsVO, houseID)).thenReturn(room);
        when(repository.save(room)).thenReturn(false);
        RoomServiceImpl service = new RoomServiceImpl(houseRepository, repository, factory);

        //Act
        Optional<Room> result = service.addRoom(roomNameVO, roomFloorVO, roomDimensionsVO);

        //Assert
        assertTrue(result.isEmpty());
    }

}