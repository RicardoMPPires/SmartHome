package smarthome.controller;

import smarthome.domain.room.Room;
import smarthome.mapper.dto.RoomDTO;
import smarthome.mapper.RoomMapper;
import smarthome.service.RoomService;

import java.util.List;

public class GetListOfRoomsCTRL {
    private final RoomService roomService;

    /**
     * Constructs an instance of GetListOfRoomsCTRL with the provided RoomService dependency.
     * This constructor initializes the GetListOfRoomsCTRL with the RoomService used to retrieve a list of rooms.
     * It ensures that the provided RoomService is not null and throws an IllegalArgumentException if it is.
     * @param roomService The RoomService used to retrieve a list of rooms.
     * @throws IllegalArgumentException if the roomService parameter is null. This exception is thrown to indicate an
     * invalid or missing service.
     */
    public GetListOfRoomsCTRL(RoomService roomService) {
        if (roomService == null) {
            throw new IllegalArgumentException("RoomService cannot be null.");
        }
        this.roomService = roomService;
    }

    /**
     * Retrieves a list of all rooms in the system.
     * This method calls the findAll method of the RoomService to retrieve all rooms in the system.
     * It then maps the retrieved Room objects to RoomDTOs using the RoomMapper.
     * @return A list of RoomDTOs representing all rooms in the system. It returns an empty list if no rooms are found.
     */
    public List<RoomDTO> getListOfRooms() {
        List<Room> rooms = roomService.findAll();
        return RoomMapper.domainToDTO(rooms);
    }
}