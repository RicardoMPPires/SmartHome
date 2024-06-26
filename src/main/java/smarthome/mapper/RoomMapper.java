package smarthome.mapper;

import smarthome.domain.room.Room;
import smarthome.domain.vo.roomvo.*;
import smarthome.mapper.dto.RoomDTO;
import smarthome.domain.vo.housevo.HouseIDVO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Mapper class that converts Room objects to RoomDTO objects and vice versa.
 * It provides utility functions for mapping between Room domain objects and RoomDTO data transfer objects.
 * This class facilitates the conversion of complex domain objects into simpler, flattened
 * data structures suitable for transmission over the network or for use in interfaces
 * where complex domain logic is not required.
 * Each public static method in this class corresponds to a specific component of a Room, allowing for
 * targeted conversion of individual attributes between the Room and RoomDTO representations.
 */


public class RoomMapper {

    private static final String ERRORMESSAGE = "RoomDTO is invalid";

    /**
     * Retrieves the room name from the RoomDTO object and creates a RoomNameVO object.
     * Validates the RoomDTO object to ensure it is not null nor its room name is empty.
     * @param roomDTO  RoomDTO object that contains the room name.
     * @return  RoomNameVO value-object.
     * @throws IllegalArgumentException In case RoomDTO is not valid to create a RoomNameVO.
     */
    public static RoomNameVO createRoomNameVO(RoomDTO roomDTO)  {
        if(roomDTO==null || roomDTO.getRoomName()==null || roomDTO.getRoomName().trim().isEmpty()){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        else {
            return new RoomNameVO(roomDTO.getRoomName());
        }
    }


    /**
     * Retrieves the room dimensions from the RoomDTO object and creates a RoomDimensionsVO object.
     * Validates the RoomDTO object to ensure it is not null and that the room dimensions are valid.
     * @param roomDTO  RoomDTO object that contains the room dimensions.
     * @return  RoomDimensionsVO value-object.
     * @throws IllegalArgumentException In case RoomDTO is not valid to create a RoomDimensionsVO.
     */
    public static RoomDimensionsVO createRoomDimensionsVO(RoomDTO roomDTO) {
        if(roomDTO==null || roomDTO.getRoomHeight()<0 || roomDTO.getRoomLength()<=0 || roomDTO.getRoomWidth()<=0){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        else {
            RoomHeightVO roomHeightVO = new RoomHeightVO(roomDTO.getRoomHeight());
            RoomLengthVO roomLengthVO = new RoomLengthVO(roomDTO.getRoomLength());
            RoomWidthVO roomWidthVO = new RoomWidthVO(roomDTO.getRoomWidth());
            return new RoomDimensionsVO(roomLengthVO, roomWidthVO, roomHeightVO);
        }
    }


    /**
     * Retrieves the room floor from the RoomDTO object and creates a RoomFloorVO object.
     * Validates the RoomDTO object to ensure it is not null.
     * @param roomDTO  RoomDTO object that contains the room floor.
     * @return  RoomFloorVO value-object.
     * @throws IllegalArgumentException In case RoomDTO is not valid to create a RoomFloorVO.
     */
    public static RoomFloorVO createRoomFloorVO(RoomDTO roomDTO) {
        if(roomDTO==null){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        else {
            return new RoomFloorVO(roomDTO.getFloor());
        }
    }


    /**
     * Retrieves the room ID from the RoomDTO object and creates a RoomIDVO object.
     * Validates the RoomDTO object to ensure it is not null nor its ID is empty.
     * @param roomDTO  RoomDTO object that contains the room ID.
     * @return  RoomIDVO value-object.
     * @throws IllegalArgumentException In case RoomDTO is not valid to create a RoomIDVO.
     */
    public static RoomIDVO createRoomIDVO(RoomDTO roomDTO) {
        if(roomDTO==null || roomDTO.getId()==null || roomDTO.getId().trim().isEmpty()){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        else {
            // Converts String from the DTO into a UUID
            UUID roomID = UUID.fromString(roomDTO.getId());
            return new RoomIDVO(roomID);
        }
    }


    /**
     * Retrieves the house ID from the RoomDTO object and creates a HouseIDVO object.
     * Validates the RoomDTO object to ensure it is not null nor its house ID is empty.
     * @param roomDTO  RoomDTO object that contains the house ID.
     * @return  HouseIDVO value-object.
     * @throws IllegalArgumentException In case RoomDTO is not valid to create a HouseIDVO.
     */
    public static HouseIDVO createHouseIDVO(RoomDTO roomDTO) {
        if(roomDTO==null || roomDTO.getHouseID()==null || roomDTO.getHouseID().trim().isEmpty()){
            throw new IllegalArgumentException(ERRORMESSAGE);
        }
        else {
            // Converts String from the DTO into a UUID
            UUID houseID = UUID.fromString(roomDTO.getHouseID());
            return new HouseIDVO(houseID);
        }
    }

    /**
     * Converts any number of Room objects into a list of RoomDTO objects
     * by using the private method convertRoomToDTO in order to convert each Room object.
     * @param listOfRooms<Room>  Room objects to be converted.
     * @return  List of RoomDTO objects.
     */
    public static List<RoomDTO> domainToDTO (List<Room> listOfRooms){
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (Room room : listOfRooms){
            RoomDTO roomDTO = convertRoomToDTO(room);
            roomDTOList.add(roomDTO);
        }
        return roomDTOList;
    }


    /**
     * Private method that converts a Room object into a RoomDTO object.
     * @param room  Room object to be converted.
     * @return  RoomDTO object.
     */
    public static RoomDTO convertRoomToDTO (Room room){
        int roomFloor = room.getFloor().getValue();
        double roomHeight = room.getRoomDimensions().getRoomHeight();
        double roomLength = room.getRoomDimensions().getRoomLength();
        double roomWidth = room.getRoomDimensions().getRoomWidth();
        String roomName = room.getRoomName().getValue();
        String roomID = room.getId().getID();
        String houseID = room.getHouseID().getID();
        return new RoomDTO(roomID, roomName, roomFloor, roomHeight, roomLength, roomWidth, houseID);
    }
}
