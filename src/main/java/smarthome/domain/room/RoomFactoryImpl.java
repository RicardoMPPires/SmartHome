package smarthome.domain.room;

import org.springframework.stereotype.Component;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomFloorVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.roomvo.RoomNameVO;

@Component
public class RoomFactoryImpl implements RoomFactory{

    /**
     * Creates a Room with the specified parameters, that are all Value-Objects.
     * @param roomName The name of the room.
     * @param floor The floor on which the room is located.
     * @param roomDimensions The dimensions of the room.
     * @param houseID The ID of the house to which the room belongs.
     * @return  The Room created.
     */

    public Room createRoom(RoomNameVO roomName, RoomFloorVO floor, RoomDimensionsVO roomDimensions, HouseIDVO houseID){
        return new Room(roomName, floor, roomDimensions, houseID);
    }

    /**
     * Creates a Room with the specified parameters, that are all Value-Objects.
     * This implementation is used when the RoomID is already known and persisted in a database, and it is necessary to
     * create a Room object with it.
     *
     * @param roomID         The ID of the room.
     * @param roomName       The name of the room.
     * @param floor          The floor on which the room is located.
     * @param roomDimensions The dimensions of the room.
     * @param houseID        The ID of the house to which the room belongs.
     * @return The Room created.
     */

    public Room createRoom(RoomIDVO roomID, RoomNameVO roomName, RoomFloorVO floor, RoomDimensionsVO roomDimensions, HouseIDVO houseID) {
        return new Room(roomID, roomName, floor, roomDimensions, houseID);
    }
}
