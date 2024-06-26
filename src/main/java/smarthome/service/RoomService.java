package smarthome.service;

import smarthome.domain.room.Room;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomFloorVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.roomvo.RoomNameVO;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<Room> findAll();

    Optional<Room> addRoom(RoomNameVO roomNameVO, RoomFloorVO roomFloorVO, RoomDimensionsVO roomDimensionsVO);

    Optional<Room> findById(RoomIDVO id);


}

