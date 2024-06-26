package smarthome.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smarthome.domain.room.Room;
import smarthome.domain.vo.roomvo.*;
import smarthome.mapper.RoomMapper;
import smarthome.mapper.dto.RoomDTO;
import smarthome.service.RoomService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The RoomCTRLWeb class is a Spring Boot REST controller that handles HTTP requests related to rooms.
 * It provides endpoints for retrieving room information, adding rooms to the house, and retrieving the list of rooms from the house.
 * The class uses the RoomService class to interact with the domain layer and perform business logic operations.
 */

@RestController
@RequestMapping(path = "/rooms")
@CrossOrigin(origins = "*")

public class RoomCTRLWeb {

    private final RoomService roomService;

    /**
     * Constructs a new RoomCTRLWeb object with the specified RoomService.
     *
     * @param roomService The RoomService to be used by the controller.
     */
    public RoomCTRLWeb(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * This endpoint adds a room to the House. It converts the room DTO object into RoomNameVO, RoomFloorVO, and
     * RoomDimensionsVO objects and passes them to the room service to add the room to the house. If the room is
     * successfully added, this room is converted to a RoomDTO object and returned with an HTTP status of 201
     * (Created). If the room cannot be added, a 422 (Unprocessable Entity) status is returned. If the room DTO
     * is invalid, a 400 (Bad Request) status is returned. The endpoint is accessible via a POST request to /rooms.
     *
     * @param roomDTO The RoomDTO object representing the room to be added.
     * @return A ResponseEntity containing the created RoomDTO object and an HTTP status code.
     */

    @PostMapping("")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDTO) {
        try{
            RoomNameVO roomName = RoomMapper.createRoomNameVO(roomDTO);
            RoomFloorVO roomFloor = RoomMapper.createRoomFloorVO(roomDTO);
            RoomDimensionsVO roomDimensions = RoomMapper.createRoomDimensionsVO(roomDTO);

            Optional<Room> room = this.roomService.addRoom(roomName,roomFloor,roomDimensions);

            if(room.isPresent()) {
                RoomDTO createdRoom = RoomMapper.convertRoomToDTO(room.get());

                Link selfLink = linkTo(methodOn(RoomCTRLWeb.class).findById(createdRoom.getId())).withSelfRel();
                createdRoom.add(selfLink);

                return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This endpoint retrieves a list of rooms from the house. Uses the device service to retrieve the list of devices.
     * If rooms are found, they are converted to a list of roomDTO objects and returned with an HTTP status of 200 (OK).
     * If no rooms are found, a 200 (OK) status is returned with an empty list. If the roomDTOList is invalid, a 400 (Bad
     * Request) status is returned. The endpoint is accessible via a GET request to /rooms.
     *
     * @return A ResponseEntity containing a CollectionModel of RoomDTO objects and an HTTP status code.
     */

    @GetMapping("")
    public ResponseEntity<CollectionModel<RoomDTO>> getListOfRooms() {
            List<Room> listOfRooms = this.roomService.findAll();
            List<RoomDTO> roomDTOList = RoomMapper.domainToDTO(listOfRooms);

            for (RoomDTO roomDTO : roomDTOList) {
                Link selfLink = linkTo(methodOn(RoomCTRLWeb.class).findById(roomDTO.getId())).withSelfRel();
                roomDTO.add(selfLink);
            }

            CollectionModel<RoomDTO> roomDTOCollectionModel = CollectionModel.of(roomDTOList);
            Link listRoomsLink = linkTo(methodOn(RoomCTRLWeb.class).getListOfRooms()).withSelfRel();
            roomDTOCollectionModel.add(listRoomsLink);

            Link addRoomLink = linkTo(methodOn(RoomCTRLWeb.class).addRoom(null)).withRel("addRoom");
            roomDTOCollectionModel.add(addRoomLink);

            return new ResponseEntity<>(roomDTOCollectionModel, HttpStatus.OK);
    }
    /**
     * This endpoint retrieves a room by its ID. It converts the room ID string into a RoomIDVO object
     * and passes it to the room service to retrieve the room. If the room is found, it is converted to a
     * RoomDTO object and returned with an HTTP status of 200 (OK). If the room is not found, a 404 (Not Found)
     * status is returned. If the room ID is invalid, a 400 (Bad Request) status is returned. The endpoint is
     * accessible via a GET request to /rooms/{roomID}.
     *
     * @param roomID The ID of the room to retrieve.
     * @return A ResponseEntity containing the RoomDTO object and an HTTP status code.
     */

    @GetMapping(path = "/{roomID}")
    public ResponseEntity<RoomDTO> findById(@PathVariable("roomID") String roomID) {
        try{
            RoomIDVO roomIDVO = new RoomIDVO(UUID.fromString(roomID));
            Optional<Room> room = this.roomService.findById(roomIDVO);

            if (room.isPresent()) {
                RoomDTO roomDTO = RoomMapper.convertRoomToDTO(room.get());

                Link selfLink = linkTo(methodOn(RoomCTRLWeb.class).findById(roomDTO.getId())).withSelfRel();
                roomDTO.add(selfLink);

                Link listDevicesByRoomID = linkTo(methodOn(DeviceCTRLWeb.class)
                        .getDevicesByRoomId(roomDTO.getId())).withRel("listDevicesByRoomID");
                roomDTO.add(listDevicesByRoomID);

                Link addDeviceLink = linkTo(methodOn(DeviceCTRLWeb.class).addDeviceToRoom(null)).withRel("addDevice");
                roomDTO.add(addDeviceLink);

                return new ResponseEntity<>(roomDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
