package smarthome.controller;

import smarthome.domain.room.Room;
import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomFloorVO;
import smarthome.domain.vo.roomvo.RoomNameVO;
import smarthome.mapper.RoomMapper;
import smarthome.mapper.dto.RoomDTO;
import smarthome.service.RoomService;

import java.util.Optional;


/**
 * The AddRoomCTRL class is responsible for controlling the process of adding a new room
 * to a house in the SmartHome Domain-Driven Design application. It coordinates between the service
 * layer and the client requests, handling the conversion of data transfer objects (DTOs) to
 * value objects (VOs) and orchestrating the addition of rooms to the house.
 */
public class AddRoomCTRL {
   private final RoomService roomService;

    /**
     * Constructs a new AddRoomCTRL instance with specified service
     * @param roomService the service for managing rooms
     */
   public AddRoomCTRL(RoomService roomService) {
       if (roomService == null){
           throw new IllegalArgumentException("Invalid Service");
       }
       this.roomService = roomService;
   }

    /**
     * Adds a new room to the first (and only ath this point) house by using the provided RoomDTO.
     * This method performs the conversion of a RoomDTO to RoomNameVO,
     * RoomFloorVO, and RoomDimensionsVO using RoomMapper. Then,
     * it uses the roomService to add the room.
     * @param roomDTO the data transfer object containing room details
     * @return true if the room was successfully added; false otherwise.
     * catches InstantiationException if the RoomMapper fails to create the VOs;
     */
    public boolean addRoom(RoomDTO roomDTO) {
        try {
            RoomNameVO roomName = RoomMapper.createRoomNameVO(roomDTO);
            RoomFloorVO roomFloor = RoomMapper.createRoomFloorVO(roomDTO);
            RoomDimensionsVO roomDimensions = RoomMapper.createRoomDimensionsVO(roomDTO);
            Optional<Room> opt = roomService.addRoom(roomName, roomFloor, roomDimensions);
            return opt.isPresent();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
