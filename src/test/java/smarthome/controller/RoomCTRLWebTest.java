package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import smarthome.domain.room.Room;
import smarthome.domain.vo.housevo.*;
import smarthome.domain.vo.roomvo.*;
import smarthome.mapper.dto.RoomDTO;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.RoomRepository;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The RoomCTRLWebTest class is a test class that tests the RoomCTRLWeb class.
 * It tests the endpoints in the RoomCTRLWeb class by mocking the repository layer.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class RoomCTRLWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private HouseRepository houseRepository;

    /**
     * This test method tests the findById endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * a valid room ID and checking if the response contains the correct room information. The test mocks the
     * repository layer to return a room object when the findByID method is called with the provided room ID.
     * The test expects the response to have an HTTP status of 200 (OK) and the correct room information in the
     * response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenRoomID_whenGetRoomById_thenReturnRoomDTO() throws Exception {
        // Arrange
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        String roomName = "Test Room";
        int roomFloor = 1;
        double roomHeight = 1;
        double roomWidth = 2;
        double roomLength = 3;
        String houseID = "3fa86f64-5717-4572-b3fc-2c963f66afa6";

        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));
        RoomNameVO roomNameVO = new RoomNameVO(roomName);
        RoomFloorVO roomFloorVO = new RoomFloorVO(roomFloor);
        RoomLengthVO roomLengthVO = new RoomLengthVO(roomLength);
        RoomWidthVO roomWidthVO = new RoomWidthVO(roomWidth);
        RoomHeightVO roomHeightVO = new RoomHeightVO(roomHeight);
        RoomDimensionsVO roomDimensionsVO = new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        Room room = new Room(roomIDVO,roomNameVO, roomFloorVO, roomDimensionsVO, houseIDVO);

        Link expectedLink = linkTo(RoomCTRLWeb.class).slash(roomID).withSelfRel();

        Link expLinklistDevicesByRoomID = linkTo(methodOn(DeviceCTRLWeb.class)
                .getDevicesByRoomId(roomID)).withRel("listDevicesByRoomID");

        Link expLinkAddDeviceToRoom = linkTo(methodOn(DeviceCTRLWeb.class).addDeviceToRoom(null)).withRel("addDevice");

        when(roomRepository.isPresent(roomIDVO)).thenReturn(true);
        when(roomRepository.findById(roomIDVO)).thenReturn(room);

        // Act + Assert
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rooms/" + roomID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomID))
                .andExpect(jsonPath("$.roomName").value(roomName))
                .andExpect(jsonPath("$.floor").value(roomFloor))
                .andExpect(jsonPath("$.roomHeight").value(roomHeight))
                .andExpect(jsonPath("$.roomWidth").value(roomWidth))
                .andExpect(jsonPath("$.roomLength").value(roomLength))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").value(expectedLink.getHref()))
                .andExpect(jsonPath("$._links.listDevicesByRoomID.href")
                        .value(expLinklistDevicesByRoomID.getHref()))
                .andExpect(jsonPath("$._links.addDevice.href")
                        .value(expLinkAddDeviceToRoom.getHref()))
                .andReturn();

                String resultvalue = result.getResponse().getContentAsString();
        System.out.println(resultvalue);
    }

    /**
     * This test method tests the findByID endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * a non-existent room ID and checking if the response contains an HTTP status of 404 (Not Found). The test mocks
     * the repository layer to return false when the isPresent method is called with the provided room ID. The test
     * expects the response to have an HTTP status of 404 (Not Found) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */

    @Test
    void givenNonExistentRoomID_whenGetRoomById_thenReturnNotFound() throws Exception {
        //        Arrange
        String roomID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));

        when(roomRepository.isPresent(roomIDVO)).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/" + roomID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }
    /**
     * This test method tests the findByID endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * an invalid room ID and checking if the response contains an HTTP status of 400 (Bad Request). The test expects
     * the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */

    @Test
    void givenInvalidRoomID_whenGetRoomById_thenReturnBadRequest() throws Exception {
//        Arrange
        RoomIDVO roomIDVO = null;
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms/" + roomIDVO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the addRoom endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * a valid room DTO and checking if the response contains the correct room information. The test mocks the
     * repository layer to return true when the save method is called with the provided room object. The test expects
     * the response to have an HTTP status of 201 (Created) and the correct room information in the response body.
     *
     * @throws Exception if there is an error in the test execution
     */

    @Test
    void givenRoomDTO_whenCreateRoom_thenReturnRoomDTO() throws Exception {
//        Arrange
        String roomName = "Room Name";
        int roomFloor = 2;
        double roomHeight = 2;
        double roomWidth = 3;
        double roomLength = 3;
        String houseID = "3fa85f64-5737-4562-b3fc-2c963f66afa6";
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        RoomDTO roomDTO = RoomDTO.builder()
                .roomName(roomName)
                .floor(roomFloor)
                .roomWidth(roomWidth)
                .roomHeight(roomHeight)
                .roomLength(roomLength)
                .houseID(houseID)
                .build();

        String roomJSON = objectMapper.writeValueAsString(roomDTO);

        when(houseRepository.getFirstHouseIDVO()).thenReturn(houseIDVO);
        when(roomRepository.save(any(Room.class))).thenReturn(true);

//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(roomJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomName").value(roomName))
                .andExpect(jsonPath("$.floor").value(roomFloor))
                .andExpect(jsonPath("$.roomHeight").value(roomHeight))
                .andExpect(jsonPath("$.roomWidth").value(roomWidth))
                .andExpect(jsonPath("$.roomLength").value(roomLength))
                .andExpect(jsonPath("$.houseID").value(houseID))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andReturn();
    }

    /**
     * This test method tests the addRoom endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * a non-existent house ID and checking if the response contains an HTTP status of 404 (Not Found). The test mocks
     * the repository layer to return false when the isPresent method is called with the provided house ID. The test
     * expects the response to have an HTTP status of 404 (Not Found) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */

    @Test
    void givenNonExistentHouse_whenAddRoomToRoomToHouse_thenReturnNotFound() throws Exception {
//        Arrange
        String roomName = "Room Name";
        int roomFloor = 2;
        double roomHeight = 2;
        double roomWidth = 3;
        double roomLength = 3;
        String houseID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        RoomDTO roomDTO = RoomDTO.builder()
                .roomName(roomName)
                .floor(roomFloor)
                .roomWidth(roomWidth)
                .roomHeight(roomHeight)
                .roomLength(roomLength)
                .build();

        String roomJSON = objectMapper.writeValueAsString(roomDTO);

        when(houseRepository.isPresent(houseIDVO)).thenReturn(false);
        // when(roomRepository.save(any(Room.class))).thenReturn(true);

//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(roomJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }
    /**
     * This test method tests the addRoom endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * an existing device and checking if the response contains an HTTP status of 422 (Unprocessable Entity). The test
     * mocks the repository layer to return false when the save method is called with the provided room object. The
     * test expects the response to have an HTTP status of 422 (Unprocessable Entity) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenExistingRoom_whenAddRoomToHouse_thenReturnConflict() throws Exception {
//        Arrange
        String roomName = "Room Name";
        int roomFloor = 2;
        double roomHeight = 2;
        double roomWidth = 3;
        double roomLength = 3;
        String houseID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        RoomDTO roomDTO = RoomDTO.builder()
                .roomName(roomName)
                .floor(roomFloor)
                .roomWidth(roomWidth)
                .roomHeight(roomHeight)
                .roomLength(roomLength)
                .houseID(houseID)
                .build();

        String roomJSON = objectMapper.writeValueAsString(roomDTO);

        when(houseRepository.getFirstHouseIDVO()).thenReturn(houseIDVO);
        when(roomRepository.save(any(Room.class))).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(roomJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }
    /**
     * This test method tests the addRoom endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * a room DTO with null parameters and checking if the response contains an HTTP status of 400 (Bad Request). The
     * test expects the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenRoomDTO_whenCreateRoomFailsToSaveToRepository_thenReturnNull() throws Exception {
//        Arrange
        String roomName = "Room Name";
        int roomFloor = 2;
        double roomHeight = 2;
        double roomWidth = 3;
        double roomLength = 3;
        String houseID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";

        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        RoomDTO roomDTO = RoomDTO.builder()
                .roomName(roomName)
                .floor(roomFloor)
                .roomWidth(roomWidth)
                .roomHeight(roomHeight)
                .roomLength(roomLength)
                .build();

        String roomJSON = objectMapper.writeValueAsString(roomDTO);

        when(houseRepository.getFirstHouseIDVO()).thenReturn(houseIDVO);
        when(roomRepository.save(any(Room.class))).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(roomJSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }
    /**
     * This test method tests the addRoom endpoint in the RoomCTRLWeb class. It tests the endpoint by providing
     * a non-existent house ID and checking if the response contains an HTTP status of 404 (Not Found). The test mocks
     * the repository layer to return false when the isPresent method is called with the provided house ID. The test
     * expects the response to have an HTTP status of 404 (Not Found) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenNonExistentHouse_whenAddRoomToHouse_thenReturnNotFound() throws Exception {
//        Arrange
        String houseID = "3fa86f64-5717-4572-b3fc-2c963f66afa6";

        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        when(houseRepository.isPresent(houseIDVO)).thenReturn(false);
//        Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                        .param("houseID", houseID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }

    /**
     * This test method tests the getListOfRooms endpoint in the RoomCTRLWeb class. It tests the endpoint by
     * providing a valid house ID and checking if the response contains the correct room information. The test mocks
     * the repository layer to return a list of rooms when the findAll method is called with the provided house ID.
     * The test expects the response to have an HTTP status of 200 (OK) and the correct room information in the
     * response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenHouseID_whenGetRooms_thenReturnRoomDTOCollection() throws Exception {
//         Arrange
        String houseID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        String roomName1 = "bedroom";
        int roomFloor1 = 2;
        double roomHeight1 = 2;
        double roomWidth1 = 3;
        double roomLength1 = 3;
        String roomID1 = "3fa95f64-5727-4562-b3fc-2c963f66afa6";


        RoomNameVO firstRoomNameVO = new RoomNameVO(roomName1);
        RoomFloorVO firstRoomFloorVO = new RoomFloorVO(roomFloor1);
        RoomWidthVO firstRoomWidthVO = new RoomWidthVO(roomWidth1);
        RoomLengthVO firstRoomLengthVO = new RoomLengthVO(roomLength1);
        RoomHeightVO firstRoomHeightVO = new RoomHeightVO(roomHeight1);
        RoomDimensionsVO firstRoomDimensionsVO = new RoomDimensionsVO(firstRoomLengthVO,firstRoomWidthVO,firstRoomHeightVO);
        RoomIDVO firstRoomIDVO = new RoomIDVO(UUID.fromString(roomID1));

        Room firstRoom= new Room(firstRoomIDVO,firstRoomNameVO,firstRoomFloorVO,firstRoomDimensionsVO,houseIDVO);

        Link expLinkAddRoom = linkTo(RoomCTRLWeb.class).withRel("addRoom");

        String roomName2 = "kitchen";
        int roomFloor2 = 1;
        double roomHeight2 = 1;
        double roomWidth2 = 2;
        double roomLength2 = 4;
        String roomID2 = "3fa95f64-5727-4563-b3fc-2c973f66afa6";

        RoomNameVO secondRoomNameVO = new RoomNameVO(roomName2);
        RoomFloorVO secondRoomFloorVO = new RoomFloorVO(roomFloor2);
        RoomWidthVO secondRoomWidthVO = new RoomWidthVO(roomWidth2);
        RoomLengthVO secondRoomLengthVO = new RoomLengthVO(roomLength2);
        RoomHeightVO secondRoomHeightVO = new RoomHeightVO(roomHeight2);
        RoomDimensionsVO secondRoomDimensionsVO = new RoomDimensionsVO(secondRoomLengthVO,secondRoomWidthVO,secondRoomHeightVO);
        RoomIDVO secondRoomIDVO = new RoomIDVO(UUID.fromString(roomID2));

        Room secondRoom= new Room(secondRoomIDVO,secondRoomNameVO,secondRoomFloorVO,secondRoomDimensionsVO,houseIDVO);

        List<Room> roomList = new ArrayList<>();
        roomList.add(firstRoom);
        roomList.add(secondRoom);

        when(houseRepository.isPresent(houseIDVO)).thenReturn(true);
        when(roomRepository.findAll()).thenReturn(roomList);
//         Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms")
                        .param("houseID", houseID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomDTOList").exists())
                .andExpect(jsonPath("$._embedded.roomDTOList[0].roomName").value(roomName1))
                .andExpect(jsonPath("$._embedded.roomDTOList[0].floor").value(roomFloor1))
                .andExpect(jsonPath("$._embedded.roomDTOList[0].roomHeight").value(roomHeight1))
                .andExpect(jsonPath("$._embedded.roomDTOList[0].roomLength").value(roomLength1))
                .andExpect(jsonPath("$._embedded.roomDTOList[0].roomWidth").value(roomWidth1))
                .andExpect(jsonPath("$._embedded.roomDTOList[0].id").value(roomID1))
                .andExpect(jsonPath("$._embedded.roomDTOList[0].houseID").value(houseID))
                .andExpect(jsonPath("$._embedded.roomDTOList[0]._links.self").exists())
                .andExpect(jsonPath("$._embedded.roomDTOList[1].roomName").value(roomName2))
                .andExpect(jsonPath("$._embedded.roomDTOList[1].floor").value(roomFloor2))
                .andExpect(jsonPath("$._embedded.roomDTOList[1].roomHeight").value(roomHeight2))
                .andExpect(jsonPath("$._embedded.roomDTOList[1].roomLength").value(roomLength2))
                .andExpect(jsonPath("$._embedded.roomDTOList[1].roomWidth").value(roomWidth2))
                .andExpect(jsonPath("$._embedded.roomDTOList[1].id").value(roomID2))
                .andExpect(jsonPath("$._embedded.roomDTOList[1].houseID").value(houseID))
                .andExpect(jsonPath("$._embedded.roomDTOList[1]._links.self").exists())
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.addRoom.href").value(expLinkAddRoom.getHref()))

                .andReturn();
    }

    /**
     * This test method tests the getListOfRooms endpoint in the RoomCTRLWeb class. It tests the endpoint by
     * providing a valid House ID that does not have any rooms associated with it. The test mocks the repository layer
     * to return an empty list of rooms when the findAll method is called with the provided house ID. The test
     * expects the response to have an HTTP status of 200 (OK) and no response body.
     *
     * @throws Exception if there is an error in the test execution
     */
    @Test
    void givenHouseIDWithoutRooms_whenGetRooms_thenReturnEmptyList() throws Exception {
//         Arrange
        String houseID = "3fa85f64-5717-4562-b3fc-2c963f66afa6";
        HouseIDVO houseIDVO = new HouseIDVO(UUID.fromString(houseID));

        Iterable<Room> roomList = Collections.emptyList();

        when(houseRepository.isPresent(houseIDVO)).thenReturn(true);
        when(roomRepository.findAll()).thenReturn(roomList);
//         Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms")
                        .param("houseID", houseID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    /**
     * This test method tests the getListOfRooms endpoint in the RoomCTRLWeb class. It tests the endpoint by
     * providing a house ID with null parameters and checking if the response contains an HTTP status of 400 (Bad Request).
     * The test expects the response to have an HTTP status of 400 (Bad Request) and no response body.
     *
     * @throws Exception if there is an error in the test execution
    @Test
    void givenNullHouseIDVO_whenGetRooms_thenReturnNotFound() throws Exception {
//         Arrange
        String houseID = null;
//         Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/rooms")
                        .param("houseID", houseID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist())
                .andReturn();
    }*/

}
