package smarthome.controller;

import org.apache.commons.collections4.IterableUtils;

import smarthome.domain.house.House;
import smarthome.domain.house.HouseFactoryImpl;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.vo.housevo.*;
import smarthome.domain.vo.roomvo.*;
import smarthome.mapper.dto.RoomDTO;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.mem.HouseRepositoryMem;
import smarthome.service.HouseServiceImpl;
import smarthome.service.RoomServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddRoomCTRLTest {

    /**
     * This test ensures that the constructor throws an IllegalArgumentException when provided a null room service.
     */
    @Test
    void givenInvalidService_constructorThrowsIllegalArgumentException() {
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new AddRoomCTRL(null));
    }

    /**
     * Test case to verify that when the method to add a room is called, it returns true indicating successful addition.
     *
     * The test performs the following steps:
     * 1. Initializes a unique house ID.
     * 2. Initializes a room and adds it to a list of rooms.
     * 3. Mocks RoomRepository and HouseRepository objects with predefined behaviors for their methods.
     * 4. Initializes service and factory objects required for the test.
     * 5. Initializes location information including the address and GPS coordinates.
     * 6. Calls the method to add a house with the provided location information.
     * 7. Initializes a RoomDTO object with room details.
     * 8. Initializes RoomServiceImpl.
     * 9. Initializes the controller for adding a room.
     * 10. Calls the method to add a room.
     * 11. Asserts that the result of adding the room is true.
     * 12. Asserts that the attributes of the added room match the provided details.
     */
    @Test
    void whenAddRoomIsCalled_thenReturnTrue() {

        // Arrange
        // Initializing houseID
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());

        // Initializing room1 and add to a List
        List<Room> roomList = new ArrayList<>();
        Iterable<Room> roomIterable = roomList;
        RoomNameVO roomName = new RoomNameVO("bedRoom");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomLengthVO roomLenght = new RoomLengthVO(2.2);
        RoomWidthVO roomWidth = new RoomWidthVO(5.0);
        RoomHeightVO roomHeight = new RoomHeightVO(4.5);
        RoomDimensionsVO dimensions1 = new RoomDimensionsVO(roomLenght,roomWidth,roomHeight);
        Room room1 = new Room(roomName,floor, dimensions1, houseID);
        roomList.add(room1);

        // Mocks to RoomRepository and HouseRepository with predefined behaviors for its methods
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        when(roomRepositoryDouble.save(any(Room.class))).thenReturn(true);
        when(roomRepositoryDouble.findAll()).thenReturn(roomIterable);
        HouseRepository houseRepositoryDouble = mock(HouseRepository.class);
        when(houseRepositoryDouble.getFirstHouseIDVO()).thenReturn(houseID);
        when(houseRepositoryDouble.save(any(House.class))).thenReturn(true);

        // Initializing RoomFactoryImpl
        RoomFactoryImpl roomFactoryImpl = new RoomFactoryImpl();

        // Initializing HouseServiceImpl
        HouseFactoryImpl houseFactoryImpl = new HouseFactoryImpl();
        HouseServiceImpl houseServiceImpl = new HouseServiceImpl(houseRepositoryDouble, houseFactoryImpl);

        // Initializing LocationVO
        String door = "1";
        DoorVO doorVO = new DoorVO(door);
        String street = "Rua de Santa Catarina";
        StreetVO streetVO = new StreetVO(street);
        String city = "Porto";
        CityVO cityVO = new CityVO(city);
        String country = "Portugal";
        CountryVO countryVO = new CountryVO(country);
        String postalCode = "PT-4000-009";
        PostalCodeVO postalCodeVO = new PostalCodeVO(postalCode);
        double latitude = 41.14961;
        LatitudeVO latitudeVO = new LatitudeVO(latitude);
        double longitude = -8.61099;
        LongitudeVO longitudeVO = new LongitudeVO(longitude);
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        GpsVO gspVO = new GpsVO(latitudeVO, longitudeVO);
        LocationVO locationVO = new LocationVO(addressVO, gspVO);

        //AddHouse called in HouseServiceImpl
        houseServiceImpl.addHouse(locationVO);

        //Initializing RoomDTO
        String roomName1 = "bedRoom";
        int floor1 = 2;
        double roomHeight1 = 4.5;
        double roomLength1 = 2.2;
        double roomWidth1 = 5.0;
        RoomDTO roomDTO = new RoomDTO(null, roomName1,floor1,roomHeight1, roomLength1, roomWidth1, null);

        //Initializing RoomServiceImpl
        RoomServiceImpl roomServiceImpl = new RoomServiceImpl(houseRepositoryDouble, roomRepositoryDouble, roomFactoryImpl);

        //Initializing controller
        AddRoomCTRL addRoomCTRL = new AddRoomCTRL(roomServiceImpl);

        // Act
        boolean result = addRoomCTRL.addRoom(roomDTO);

        // Assert
        Iterable<Room> roomIterable1 = roomRepositoryDouble.findAll();
        Room room = IterableUtils.get(roomIterable1,0);
        String nameResult = room.getRoomName().getValue();
        int floorResult = room.getFloor().getValue();
        double widthResult = room.getRoomDimensions().getRoomWidth();
        double lengthResult = room.getRoomDimensions().getRoomLength();
        double heightResult = room.getRoomDimensions().getRoomHeight();

        assertTrue(result);
        assertEquals(roomName1,nameResult);
        assertEquals(floor1,floorResult);
        assertEquals(roomWidth1,widthResult);
        assertEquals(roomLength1,lengthResult);
        assertEquals(roomHeight1,heightResult);
    }

    /**
     * Test case to verify that when the method to add a room is called with a null RoomDTO, it returns false.
     *
     * The test performs the following steps:
     * 1. Initializes location information including the address and GPS coordinates.
     * 2. Mocks RoomRepository and HouseRepository objects.
     * 3. Initializes service and factory objects required for the test.
     * 4. Calls the method to add a house with the provided location information.
     * 5. Initializes the controller for adding a room.
     * 6. Calls the method to add a room with a null RoomDTO.
     * 7. Asserts that the result of adding the room is false.
     */
    @Test
    void whenAddRoomIsCalled_ReturnsFalseForNullRoomDTO() {
        // Arrange
        //Initializing LocationVO
        String door = "1";
        String street = "Rua de Santa Catarina";
        String city = "Porto";
        String country = "Portugal";
        String postalCode = "PT-4000-009";
        double latitude = 41.14961;
        double longitude = -8.61099;
        DoorVO doorVO = new DoorVO(door);
        StreetVO streetVO = new StreetVO(street);
        CityVO cityVO = new CityVO(city);
        CountryVO countryVO = new CountryVO(country);
        PostalCodeVO postalCodeVO = new PostalCodeVO(postalCode);
        LatitudeVO latitudeVO = new LatitudeVO(latitude);
        LongitudeVO longitudeVO = new LongitudeVO(longitude);
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        GpsVO gspVO = new GpsVO(latitudeVO, longitudeVO);
        LocationVO locationVO = new LocationVO(addressVO, gspVO);

        // Mocks to RoomRepository and HouseRepository
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        HouseRepository houseRepositoryDouble = mock(HouseRepositoryMem.class);

        // Initializing RoomServiceImpl
        RoomFactoryImpl roomFactoryImpl = new RoomFactoryImpl();
        RoomServiceImpl roomServiceImpl = new RoomServiceImpl(houseRepositoryDouble, roomRepositoryDouble, roomFactoryImpl);

        // Initializing HouseServiceImpl
        HouseFactoryImpl houseFactoryImpl = new HouseFactoryImpl();
        HouseServiceImpl houseServiceImpl = new HouseServiceImpl(houseRepositoryDouble, houseFactoryImpl);

        //AddHouse called in HouseServiceImpl
        houseServiceImpl.addHouse(locationVO);

        //Initializing Controller
        AddRoomCTRL addRoomCTRL = new AddRoomCTRL(roomServiceImpl);

        // Act
        boolean result = addRoomCTRL.addRoom(null);

        // Assert
        assertFalse(result);
    }


    /**
     * Test case to verify that when the method to add a room is called with a null room name in the RoomDTO, it returns false.
     *
     * The test performs the following steps:
     * 1. Initializes a RoomDTO with a null room name and other valid parameters.
     * 2. Initializes location information including the address and GPS coordinates.
     * 3. Mocks RoomRepository and HouseRepository objects.
     * 4. Initializes service and factory objects required for the test.
     * 5. Calls the method to add a house with the provided location information.
     * 6. Initializes the controller for adding a room.
     * 7. Calls the method to add a room with the prepared RoomDTO.
     * 8. Asserts that the result of adding the room is false.
     */
    @Test
    void whenAddRoomIsCalled_ReturnsFalseGivenNullRoomName()  {
        // Arrange
        // Initializing roomDTO with null roomName
        int floor = 2;
        double roomHeight = 2.2;
        double roomLength = 4.5;
        double roomWidth = 5.0;
        RoomDTO roomDTO = new RoomDTO(null, null,floor,roomHeight, roomLength, roomWidth, null);

        //Initializing LocationVO
        String door = "1";
        String street = "Rua de Santa Catarina";
        String city = "Porto";
        String country = "Portugal";
        String postalCode = "PT-4000-009";
        double latitude = 41.14961;
        double longitude = -8.61099;
        DoorVO doorVO = new DoorVO(door);
        StreetVO streetVO = new StreetVO(street);
        CityVO cityVO = new CityVO(city);
        CountryVO countryVO = new CountryVO(country);
        PostalCodeVO postalCodeVO = new PostalCodeVO(postalCode);
        LatitudeVO latitudeVO = new LatitudeVO(latitude);
        LongitudeVO longitudeVO = new LongitudeVO(longitude);
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        GpsVO gspVO = new GpsVO(latitudeVO, longitudeVO);
        LocationVO locationVO = new LocationVO(addressVO, gspVO);

        // Mocks to RoomRepository and HouseRepository
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        HouseRepository houseRepositoryDouble = mock(HouseRepositoryMem.class);

        // Initializing RoomServiceImpl
        RoomFactoryImpl roomFactoryImpl = new RoomFactoryImpl();
        RoomServiceImpl roomServiceImpl = new RoomServiceImpl(houseRepositoryDouble, roomRepositoryDouble, roomFactoryImpl);

        // Initializing HouseServiceImpl
        HouseFactoryImpl houseFactoryImpl = new HouseFactoryImpl();
        HouseServiceImpl houseServiceImpl = new HouseServiceImpl(houseRepositoryDouble, houseFactoryImpl);

        //AddHouse called in HouseServiceImpl
        houseServiceImpl.addHouse(locationVO);

        //Initializing Controller
        AddRoomCTRL addRoomCTRL = new AddRoomCTRL(roomServiceImpl);

        // Act
        boolean result = addRoomCTRL.addRoom(roomDTO);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that when the method to add a room is called and no house is present in the system, it returns false.
     *
     * The test performs the following steps:
     * 1. Initializes a RoomDTO with valid room information and a null house ID.
     * 2. Mocks RoomRepository and HouseRepository objects.
     * 3. Initializes service and factory objects required for the test.
     * 4. Initializes the controller for adding a room.
     * 5. Calls the method to add a room with the prepared RoomDTO.
     * 6. Asserts that the result of adding the room is false.
     */
    @Test
    void whenAddRoomIsCalled_ReturnsFalseIfNoHousePresent()  {
        // Arrange
        // Initializing RoomDTO
        String name = "name";
        int floor = 2;
        double roomHeight = 2.2;
        double roomLength = 4.5;
        double roomWidth = 5.0;
        RoomDTO roomDTO = new RoomDTO(null, name,floor,roomHeight, roomLength, roomWidth, null);

        // Mocks to RoomRepository and HouseRepository
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        HouseRepository houseRepositoryDouble = mock(HouseRepositoryMem.class);

        // Initializing RoomServiceImpl
        RoomFactoryImpl roomFactoryImpl = new RoomFactoryImpl();
        RoomServiceImpl roomServiceImpl = new RoomServiceImpl(houseRepositoryDouble, roomRepositoryDouble, roomFactoryImpl);

        //Initializing Controller
        AddRoomCTRL addRoomCTRL = new AddRoomCTRL(roomServiceImpl);

        // Act
        boolean result = addRoomCTRL.addRoom(roomDTO);

        // Assert
        assertFalse(result);
    }

    /**
     * Test case to verify that when the method to add a room is called and the addition of the room fails to save it,
     * the method should return false.
     *
     * The test performs the following steps:
     * 1. Initializes a unique house ID.
     * 2. Initializes a room and adds it to a list of rooms.
     * 3. Mocks RoomRepository and HouseRepository objects with predefined behaviors for their methods.
     * 4. Initializes service and factory objects required for the test.
     * 5. Initializes location information including the address and GPS coordinates.
     * 6. Calls the method to add a house with the provided location information.
     * 7. Initializes a RoomDTO object with room details.
     * 8. Initializes RoomServiceImpl.
     * 9. Initializes the controller for adding a room.
     * 10. Calls the method to add a room.
     * 11. Asserts that the result of adding the room is false.
     */
    @Test
    void whenAddRoomIsCalledAndAddRoomFailsToSaveTheRoom_ThenReturnsFalse() {

        // Arrange
        // Initializing houseID
        HouseIDVO houseID = new HouseIDVO(UUID.randomUUID());

        // Initializing room1 and add to a List
        List<Room> roomList = new ArrayList<>();
        Iterable<Room> roomIterable = roomList;
        RoomNameVO roomName = new RoomNameVO("bedRoom");
        RoomFloorVO floor = new RoomFloorVO(2);
        RoomLengthVO roomLenght = new RoomLengthVO(2.2);
        RoomWidthVO roomWidth = new RoomWidthVO(5.0);
        RoomHeightVO roomHeight = new RoomHeightVO(4.5);
        RoomDimensionsVO dimensions1 = new RoomDimensionsVO(roomLenght,roomWidth,roomHeight);
        Room room1 = new Room(roomName,floor, dimensions1, houseID);
        roomList.add(room1);

        // Mocks to RoomRepository and HouseRepository with predefined behaviors for its methods
        RoomRepository roomRepositoryDouble = mock(RoomRepository.class);
        when(roomRepositoryDouble.save(any(Room.class))).thenReturn(false);
        when(roomRepositoryDouble.findAll()).thenReturn(roomIterable);
        HouseRepository houseRepositoryDouble = mock(HouseRepository.class);
        when(houseRepositoryDouble.getFirstHouseIDVO()).thenReturn(houseID);
        when(houseRepositoryDouble.save(any(House.class))).thenReturn(true);

        // Initializing RoomFactoryImpl
        RoomFactoryImpl roomFactoryImpl = new RoomFactoryImpl();

        // Initializing HouseServiceImpl
        HouseFactoryImpl houseFactoryImpl = new HouseFactoryImpl();
        HouseServiceImpl houseServiceImpl = new HouseServiceImpl(houseRepositoryDouble, houseFactoryImpl);

        // Initializing LocationVO
        String door = "1";
        DoorVO doorVO = new DoorVO(door);
        String street = "Rua de Santa Catarina";
        StreetVO streetVO = new StreetVO(street);
        String city = "Porto";
        CityVO cityVO = new CityVO(city);
        String country = "Portugal";
        CountryVO countryVO = new CountryVO(country);
        String postalCode = "PT-4000-009";
        PostalCodeVO postalCodeVO = new PostalCodeVO(postalCode);
        double latitude = 41.14961;
        LatitudeVO latitudeVO = new LatitudeVO(latitude);
        double longitude = -8.61099;
        LongitudeVO longitudeVO = new LongitudeVO(longitude);
        AddressVO addressVO = new AddressVO(doorVO, streetVO, cityVO, countryVO, postalCodeVO);
        GpsVO gspVO = new GpsVO(latitudeVO, longitudeVO);
        LocationVO locationVO = new LocationVO(addressVO, gspVO);

        //AddHouse called in HouseServiceImpl
        houseServiceImpl.addHouse(locationVO);

        //Initializing RoomDTO
        String roomName1 = "bedRoom";
        int floor1 = 2;
        double roomHeight1 = 4.5;
        double roomLength1 = 2.2;
        double roomWidth1 = 5.0;
        RoomDTO roomDTO = new RoomDTO(null, roomName1,floor1,roomHeight1, roomLength1, roomWidth1, null);

        //Initializing RoomServiceImpl
        RoomServiceImpl roomServiceImpl = new RoomServiceImpl(houseRepositoryDouble, roomRepositoryDouble, roomFactoryImpl);

        //Initializing controller
        AddRoomCTRL addRoomCTRL = new AddRoomCTRL(roomServiceImpl);

        // Act
        boolean result = addRoomCTRL.addRoom(roomDTO);

        // Assert


        assertFalse(result);

    }
}
