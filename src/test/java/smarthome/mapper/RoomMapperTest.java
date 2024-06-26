package smarthome.mapper;

import smarthome.domain.room.Room;
import smarthome.mapper.dto.DeviceDTO;
import smarthome.mapper.dto.RoomDTO;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomFloorVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.roomvo.RoomNameVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.mapper.dto.SensorDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoomMapperTest {

    private RoomDTO roomDTO;
    private String roomID;
    private String roomName;
    private int floor;
    private double roomHeight, roomLength, roomWidth;
    private String houseID;
    private static final String ERRORMESSAGE = "RoomDTO is invalid";


    /**
     * Common setup for the following tests.
     * Creates a RoomDTO object with the same values for each test.
     */
    @BeforeEach
    void setUp() {
        // Common setup for multiple tests
        roomID = "123e4567-e89b-12d3-a456-426655440000";
        roomName = "BedRoom";
        floor = 2;
        roomHeight = 2.5;
        roomLength = 5.6;
        roomWidth = 3.2;
        houseID = "00000000-0000-0000-0000-000000000000";
        roomDTO = RoomDTO.builder()
                .id(roomID)
                .roomName(roomName)
                .floor(floor)
                .roomHeight(roomHeight)
                .roomLength(roomLength)
                .roomWidth(roomWidth)
                .houseID(houseID)
                .build();
    }


    /**
     * Test to verify that the RoomNameVO object is created correctly.
     */
    @Test
    void whenCreateRoomNameVOisCalled_RoomNameVOisRetrieved() {
        // Arrange
        String expected = "BedRoom";

        // Act
        RoomNameVO result = RoomMapper.createRoomNameVO(roomDTO);

        // Assert
        assertEquals(expected, result.getValue());
    }


    /**
     * Test to verify that when the RoomDTO object is null, an IllegalArgumentException is
     * thrown when creating a RoomNameVO object.
     */
    @Test
    void whenCreateRoomNameVOisCalledWithNullEntry_ThrowsIllegalArgumentException() {
        // Arrange
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception e = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomNameVO(null));

        // Assert
        assertEquals(expectedMessage, e.getMessage());
    }

    /**
     * Test to verify that the RoomFloorVO object is created correctly.
     */
    @Test
    void whenCreateRoomFloorVOisCalled_RoomFloorVOisRetrieved() {
        // Arrange
        int expected = 2;

        // Act
        RoomFloorVO result = RoomMapper.createRoomFloorVO(roomDTO);

        // Assert
        assertEquals(expected, result.getValue());
    }

    /**
     * Test to verify that when the RoomDTO object is null, an IllegalArgumentException is thrown
     * when creating a RoomFloorVO object.
     */
    @Test
    void whenCreateRoomFloorVOisCalledWithNullEntry_ThrowsIllegalArgumentException() {
        // Arrange
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception e = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomFloorVO(null));

        // Assert
        assertEquals(expectedMessage, e.getMessage());
    }


    /**
     * Test to verify that the RoomDimensionsVO object is created correctly.
     */
    @Test
    void whenCreateRoomDimensionsVOisCalled_RoomDimensionsVOisRetrieved() {
        // Arrange
        double expectedLength = 5.6;
        double expectedWidth = 3.2;
        double expectedHeight = 2.5;

        // Act
        RoomDimensionsVO result = RoomMapper.createRoomDimensionsVO(roomDTO);

        // Assert
        assertEquals(expectedLength, result.getRoomLength());
        assertEquals(expectedWidth, result.getRoomWidth());
        assertEquals(expectedHeight, result.getRoomHeight());
    }

    /**
     * Test to verify that when the RoomDTO object is null, an IllegalArgumentException is thrown
     * when creating a RoomDimensionsVO object.
     */
    @Test
    void whenCreateRoomDimensionsVOisCalledWithNullEntry_ThrowsIllegalArgumentException() {
        // Arrange
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception e = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomDimensionsVO(null));

        // Assert
        assertEquals(expectedMessage, e.getMessage());
    }


    /**
     * Test to verify that when the RoomDTO object has invalid values, an IllegalArgumentException is
     * thrown when creating a RoomDimensionsVO object.
     */
    @Test
    void whenCreateRoomDimensionsVOisCalledWithInvalidDTOValues_ThrowsIllegalArgumentException() {
        // Arrange
        String roomID1 = "123e4567-e89b-12d3-a456-426655440000";
        String roomName1 = "BedRoom";
        int floor1 = 2;
        double roomHeight1 = -1;
        double roomLength1 = 0;
        double roomWidth1 = 0;
        String houseID1 = "00000000-0000-0000-0000-000000000000";
        RoomDTO room1DTO = RoomDTO.builder()
                .id(roomID1)
                .roomName(roomName1)
                .floor(floor1)
                .roomHeight(roomHeight1)
                .roomLength(roomLength1)
                .roomWidth(roomWidth1)
                .houseID(houseID1)
                .build();
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception e = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomDimensionsVO(room1DTO));

        // Assert
        assertEquals(expectedMessage, e.getMessage());
    }

    /**
     * Test to verify that when the RoomDTO object has invalid values, an IllegalArgumentException is
     * thrown when creating a RoomDimensionsVO object.
     */
    @Test
    void whenCreateRoomDimensionsVOisCalledWithInvalidBorderDTOValues_ThrowsIllegalArgumentException() {
        // Arrange
        String roomID1 = "123e4567-e89b-12d3-a456-426655440000";
        String roomName1 = "BedRoom";
        int floor1 = 2;
        double roomHeight1 = 0;
        double roomLength1 = -1;
        double roomWidth1 = -1;
        String houseID1 = "00000000-0000-0000-0000-000000000000";
        RoomDTO room1DTO = RoomDTO.builder()
                .id(roomID1)
                .roomName(roomName1)
                .floor(floor1)
                .roomHeight(roomHeight1)
                .roomLength(roomLength1)
                .roomWidth(roomWidth1)
                .houseID(houseID1)
                .build();
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception e = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomDimensionsVO(room1DTO));

        // Assert
        assertEquals(expectedMessage, e.getMessage());
    }

    /**
     * Test to verify that when the RoomDTO object has border values, The object is created
     */
    @Test
    void whenCreateRoomDimensionsVOisCalledWithBorderDTOValues_ThenDimensionsVOIsCreated() {
        // Arrange
        String roomID1 = "123e4567-e89b-12d3-a456-426655440000";
        String roomName1 = "BedRoom";
        int floor1 = 2;
        double roomHeight1 = 0;
        double roomLength1 = 1;
        double roomWidth1 = 1;
        String houseID1 = "00000000-0000-0000-0000-000000000000";
        RoomDTO room1DTO = RoomDTO.builder()
                .id(roomID1)
                .roomName(roomName1)
                .floor(floor1)
                .roomHeight(roomHeight1)
                .roomLength(roomLength1)
                .roomWidth(roomWidth1)
                .houseID(houseID1)
                .build();

        // Act
        RoomDimensionsVO dimensionsVO = RoomMapper.createRoomDimensionsVO(room1DTO);

        // Assert
        assertNotNull(dimensionsVO);
    }


    /**
     * Test to verify that the RoomIDVO object is created correctly.
     */
    @Test
    void whenCreateRoomIDVOisCalled_RoomIDVOisRetrieved() {
        // Arrange
        String expected = "123e4567-e89b-12d3-a456-426655440000";

        // Act
        RoomIDVO result = RoomMapper.createRoomIDVO(roomDTO);

        // Assert
        assertEquals(expected, result.getID());
    }


    /**
     * Test to verify that when the RoomDTO object is null, an IllegalArgumentException is thrown
     * when creating a RoomIDVO object.
     */
    @Test
    void whenCreateRoomIDVOisCalledWithNullEntry_ThrowsIllegalArgumentException() {
        // Arrange
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception e = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomIDVO(null));

        // Assert
        assertEquals(expectedMessage, e.getMessage());
    }


    /**
     * Test to verify that the HouseIDVO object is created correctly.
     */
    @Test
    void whenCreateHouseIDVOisCalled_HouseIDVOisRetrieved() {
        // Arrange
        String expected = "00000000-0000-0000-0000-000000000000";

        // Act
        HouseIDVO result = RoomMapper.createHouseIDVO(roomDTO);

        // Assert
        assertEquals(expected, result.getID());
    }


    /**
     * Test to verify that when the RoomDTO object is null, an IllegalArgumentException is thrown when
     * creating a HouseIDVO object.
     */
    @Test
    void whenCreateHouseIDVOisCalledWithNullEntry_ThrowsIllegalArgumentException() {
        // Arrange
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception e = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createHouseIDVO(null));

        // Assert
        assertEquals(expectedMessage, e.getMessage());
    }


    /**
     * Test to verify that a list of Room objects of size 1 is correctly converted
     * to a list of RoomDTO objects with the same size
     */
    @Test
    void whenDomainToDTOisCalled_ThenExpectedListOfRoomsDTOExpectedSizeIsOf1() {
        // Arrange
        RoomNameVO nameDouble = mock(RoomNameVO.class);
        RoomFloorVO floorDouble = mock(RoomFloorVO.class);
        RoomDimensionsVO roomDimensionsDouble = mock(RoomDimensionsVO.class);
        HouseIDVO houseIDDouble = mock(HouseIDVO.class);
        RoomIDVO roomIDDouble = mock(RoomIDVO.class);

        Room roomDouble = mock(Room.class);
        when(roomDouble.getFloor()).thenReturn(floorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getRoomName()).thenReturn(nameDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);
        when(roomDouble.getId()).thenReturn(roomIDDouble);

        List<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(roomDouble);
        int expectedSizeOfList = 1;

        //Act
        List<RoomDTO> listOfRoomsDTO = RoomMapper.domainToDTO(listOfRooms);

        //Assert
        assertEquals(expectedSizeOfList, listOfRoomsDTO.size());
    }


    /**
     * Test to verify that a list of Room objects of size 2 is correctly converted
     * to a list of RoomDTO objects with the same size
     */
    @Test
    void whenDomainToDTOisCalled_ThenExpectedListOfRoomsDTOExpectedSizeIsOf2() {
        // Arrange

        // Mock dependencies inside Room.
        RoomNameVO nameDouble = mock(RoomNameVO.class);
        RoomFloorVO floorDouble = mock(RoomFloorVO.class);
        RoomDimensionsVO roomDimensionsDouble = mock(RoomDimensionsVO.class);
        HouseIDVO houseIDDouble = mock(HouseIDVO.class);
        RoomIDVO roomIDDouble = mock(RoomIDVO.class);
        RoomNameVO nameDouble2 = mock(RoomNameVO.class);
        RoomFloorVO floorDouble2 = mock(RoomFloorVO.class);
        RoomDimensionsVO roomDimensionsDouble2 = mock(RoomDimensionsVO.class);
        HouseIDVO houseIDDouble2 = mock(HouseIDVO.class);
        RoomIDVO roomIDDouble2 = mock(RoomIDVO.class);

        // Mock One Room and stub to return mocked VOs.
        Room roomDouble = mock(Room.class);
        when(roomDouble.getFloor()).thenReturn(floorDouble);
        when(roomDouble.getRoomDimensions()).thenReturn(roomDimensionsDouble);
        when(roomDouble.getRoomName()).thenReturn(nameDouble);
        when(roomDouble.getHouseID()).thenReturn(houseIDDouble);
        when(roomDouble.getId()).thenReturn(roomIDDouble);

        // Mock Another Room and stub to return mocked VOs.
        Room roomDouble2 = mock(Room.class);
        when(roomDouble2.getFloor()).thenReturn(floorDouble2);
        when(roomDouble2.getRoomDimensions()).thenReturn(roomDimensionsDouble2);
        when(roomDouble2.getRoomName()).thenReturn(nameDouble2);
        when(roomDouble2.getHouseID()).thenReturn(houseIDDouble2);
        when(roomDouble2.getId()).thenReturn(roomIDDouble2);

        // Create a list of Rooms and add the mocked Rooms.
        List<Room> listOfRooms = new ArrayList<>();
        listOfRooms.add(roomDouble);
        listOfRooms.add(roomDouble2);
        int expectedSizeOfList = 2;

        //Act
        List<RoomDTO> listOfRoomsDTO = RoomMapper.domainToDTO(listOfRooms);

        //Assert
        assertEquals(expectedSizeOfList, listOfRoomsDTO.size());
    }


    /**
     * Test to verify that the values from the RoomDTO object match the values from the Room object.
     * First, mock it mocks the dependencies inside Room and stub the methods to return specific values.
     * Then, mocks the Room object and stubs to return the mocked VO's.
     * After that, calls the domainToDTO method and checks if the values match between the
     * first RoomDTO object of the output's list and the only Room object given.
     */
    @Test
    void whenDomainToDTOisCalled_thenValuesFromRoomDTOObjectMatchValuesFromRoomObject() {

        // Arrange

        // Mock dependencies inside Room
        RoomFloorVO mockRoomFloorVO = mock(RoomFloorVO.class);
        RoomDimensionsVO mockRoomDimensionsVO = mock(RoomDimensionsVO.class);
        RoomNameVO mockRoomNameVO = mock(RoomNameVO.class);
        RoomIDVO mockRoomIDVO = mock(RoomIDVO.class);
        HouseIDVO mockHouseIDVO = mock(HouseIDVO.class);

        // Arrange stubbing for the methods to return specific values
        when(mockRoomFloorVO.getValue()).thenReturn(2);
        when(mockRoomDimensionsVO.getRoomLength()).thenReturn(5.6);
        when(mockRoomDimensionsVO.getRoomWidth()).thenReturn(3.2);
        when(mockRoomDimensionsVO.getRoomHeight()).thenReturn(2.5);
        when(mockRoomNameVO.getValue()).thenReturn("BedRoom");
        when(mockRoomIDVO.getID()).thenReturn("123e4567-e89b-12d3-a456-426655440000");
        when(mockHouseIDVO.getID()).thenReturn("00000000-0000-0000-0000-000000000000");

        //  Mock Room object and stub to return mocked VO's.
        Room roomDouble = mock(Room.class);
        when(roomDouble.getFloor()).thenReturn(mockRoomFloorVO);
        when(roomDouble.getRoomDimensions()).thenReturn(mockRoomDimensionsVO);
        when(roomDouble.getRoomName()).thenReturn(mockRoomNameVO);
        when(roomDouble.getId()).thenReturn(mockRoomIDVO);
        when(roomDouble.getHouseID()).thenReturn(mockHouseIDVO);

        // Expected values are in BeforeEach Setup

        // Act
        List<RoomDTO> result = RoomMapper.domainToDTO(List.of(roomDouble));

        // Assert
        assertEquals(roomName, result.get(0).getRoomName());
        assertEquals(floor, result.get(0).getFloor());
        assertEquals(roomLength, result.get(0).getRoomLength());
        assertEquals(roomWidth, result.get(0).getRoomWidth());
        assertEquals(roomHeight, result.get(0).getRoomHeight());
        assertEquals(roomID, result.get(0).getId());
        assertEquals(houseID, result.get(0).getHouseID());
    }


    /**
     * Verifies that attempting to create RoomDimensionsVO with all zero dimensions throws an IllegalArgumentException.
     * This test checks the validation logic to ensure zero values for length, and width are considered invalid.
     */
    @Test
    void whenCreateRoomDimensionsVOisCalledWithZeroDimensions_ThrowsIllegalArgumentException() {
        // Arrange
        String roomID = "123e4567-e89b-12d3-a456-426655440000";
        String roomName = "BedRoom";
        int floor = 2;
        double roomHeight = -1;
        double roomLength = 0;
        double roomWidth = 0;
        String houseID = "00000000-0000-0000-0000-000000000000";
        RoomDTO roomDTOWithZeroDimensions = RoomDTO.builder()
                .id(roomID)
                .roomName(roomName)
                .floor(floor)
                .roomHeight(roomHeight)
                .roomLength(roomLength)
                .roomWidth(roomWidth)
                .houseID(houseID)
                .build();
        String expectedMessage = "RoomDTO is invalid";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomDimensionsVO(roomDTOWithZeroDimensions));

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }


    /**
     * Tests that an IllegalArgumentException is thrown when the room height is negative, indicating invalid dimensions.
     */
    @Test
    void whenHeightIsNegative_ThrowsIllegalArgumentException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(-1.0, 1.0, 1.0, "Room height is negative but was expected to throw IllegalArgumentException.");
    }


    /**
     * Tests that an IllegalArgumentException is thrown when the room length is zero, ensuring that zero length is treated as invalid.
     */
    @Test
    void whenLengthIsZero_ThrowsIllegalException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(1.0, 0.0, 1.0, "Room length is zero but was expected to throw IllegalArgumentException.");
    }


    /**
     * Tests that an IllegalArgumentException is thrown when the room width is negative, confirming negative width is handled as invalid.
     */
    @Test
    void whenWidthIsNegative_ThrowsIllegalException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(1.0, 1.0, -1.0, "Room width is negative but was expected to throw IllegalArgumentException.");
    }


    /**
     * Verifies that no exception is thrown when the room length is just above zero, confirming the minimum valid boundary for length.
     */
    @Test
    void whenLengthIsJustAboveZero_DoesNotThrowException() {
        assertDoesNotThrow(() -> RoomMapper.createRoomDimensionsVO(new RoomDTO("id", "name", 2, 1.0, 0.1, 1.0, "houseID")));
    }


    /**
     * Verifies that no exception is thrown when the room width is just above zero, confirming the minimum valid boundary for width.
     */
    @Test
    void whenWidthIsJustAboveZero_DoesNotThrowException() {
        assertDoesNotThrow(() -> RoomMapper.createRoomDimensionsVO(new RoomDTO("id", "name", 2, 1.0, 1.0, 0.1, "houseID")));
    }


    /**
     * Tests that an IllegalArgumentException is thrown when all dimensions are negative, ensuring the method handles multiple invalid inputs correctly.
     */
    @Test
    void whenAllDimensionsAreNegative_ThrowsIllegalArgumentException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(-1.0, -1.0, -1.0, "All dimensions are negative but was expected to throw IllegalArgumentException.");
    }


    /**
     * Tests that an IllegalArgumentException is thrown when the height is zero and other dimensions are negative, ensuring complex invalid inputs are handled.
     */
    @Test
    void whenHeightIsZeroAndOthersAreNegative_ThrowsIllegalArgumentException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(0.0, -1.0, -1.0, "Height is zero and other dimensions negative but was expected to throw IllegalArgumentException.");
    }


    /**
     * Tests that an IllegalArgumentException is thrown when the height is below zero, to confirm that this boundary condition is correctly identified as invalid.
     */
    @Test
    void whenHeightIsExactlyZero_ThrowsIllegalArgumentException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(-0.1, 1.0, 1.0, "Height is exactly zero but was expected to throw IllegalArgumentException.");
    }


    /**
     * Tests that an IllegalArgumentException is thrown when the room length is exactly zero, confirming that this boundary condition is treated as invalid.
     */
    @Test
    void whenLengthIsJustBelowOne_ThrowsIllegalArgumentException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(1.0, 0, 1.0, "Length is just below one but was expected to throw IllegalArgumentException.");
    }


    /**
     * Tests that an IllegalArgumentException is thrown when the room width is exactly zero, ensuring that this boundary condition is handled as invalid.
     */
    @Test
    void whenWidthIsJustBelowOne_ThrowsIllegalArgumentException() {
        assertThrowsIllegalArgumentExceptionForRoomDimensions(1.0, 1.0, 0, "Width is just below one but was expected to throw IllegalArgumentException.");
    }

    /**
     * Helper method to assert that an IllegalArgumentException is thrown for given room dimensions, aiding in code reuse across multiple tests.
     *
     * @param height  Room height to test.
     * @param length  Room length to test.
     * @param width   Room width to test.
     * @param message Error message expected when an exception is thrown.
     */
    private void assertThrowsIllegalArgumentExceptionForRoomDimensions(double height, double length, double width, String message) {
        RoomDTO roomDTO = RoomDTO.builder()
                .id("id")
                .roomName("name")
                .floor(2)
                .roomHeight(height)
                .roomLength(length)
                .roomWidth(width)
                .houseID("houseID")
                .build();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> RoomMapper.createRoomDimensionsVO(roomDTO));
        assertEquals(ERRORMESSAGE, exception.getMessage(), message);
    }

    @Test
    void testBuilderToString() {
        // Arrange
        RoomDTO.RoomDTOBuilder builder = RoomDTO.builder()
                .id("id")
                .roomName("name")
                .floor(2)
                .roomHeight(0)
                .roomLength(1)
                .roomWidth(2)
                .houseID("houseID");

        // Act
        String result = builder.toString();

        // Assert
        String expected = "RoomDTO.RoomDTOBuilder(id=id, roomName=name, floor=2, roomHeight=0.0, roomLength=1.0, roomWidth=2.0, houseID=houseID)";
        assertEquals(expected, result);
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyObject() {
        //Act
        RoomDTO roomDTO = new RoomDTO();

        //Assert
        assertNull(roomDTO.getId());
        assertNull(roomDTO.getRoomName());
        assertNull(roomDTO.getHouseID());
        assertEquals(roomDTO.getRoomWidth(),0);
        assertEquals(roomDTO.getRoomHeight(),0);
        assertEquals(roomDTO.getRoomLength(),0);
        assertEquals(roomDTO.getFloor(),0);
    }
}
