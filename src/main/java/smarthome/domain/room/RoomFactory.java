package smarthome.domain.room;

import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomFloorVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.roomvo.RoomNameVO;

public interface RoomFactory {
    Room createRoom(RoomNameVO roomName, RoomFloorVO floor, RoomDimensionsVO roomDimensions, HouseIDVO houseID);
    Room createRoom(RoomIDVO roomID, RoomNameVO roomName, RoomFloorVO floor, RoomDimensionsVO roomDimensions, HouseIDVO houseID);
}
