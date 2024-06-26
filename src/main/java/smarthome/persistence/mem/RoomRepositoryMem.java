package smarthome.persistence.mem;
import smarthome.domain.room.Room;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.persistence.RoomRepository;


import java.util.*;
public class RoomRepositoryMem implements RoomRepository {

    private final LinkedHashMap<RoomIDVO, Room> data = new LinkedHashMap<>();

    /**
     * Saves a room to the repository.
     * @param room Room object
     * @return true if the room was saved successfully, false otherwise.
     */
    @Override
    public boolean save(Room room) {
        if(room == null || room.getId() == null || isPresent(room.getId())) {
            return false;
        }
        data.put(room.getId(), room);
        return true;
    }

    /**
     * Finds all rooms in the repository.
     * @return an iterable of all rooms in the repository.
     */
    @Override
    public Iterable<Room> findAll() {
        return data.values();
    }

    /**
     * Finds a room by its ID.
     * @param roomID RoomIDVO object
     * @return the room with the given ID, or null if it does not exist.
     */
    @Override
    public Room findById(RoomIDVO roomID) {
        if(!isPresent(roomID)) {
            return null;
        } else {
            return data.get(roomID);
        }
    }

    /**
     * Verifies if a room is present in the repository, searching by its ID.
     * @param roomID RoomIDVO object
     * @return true if the room is present, false otherwise.
     */
    @Override
    public boolean isPresent(RoomIDVO roomID) {
        return data.containsKey(roomID);
    }

    /**
     * Finds all rooms in a house given a house ID.
     * @param houseID HouseIDVO object
     * @return list of rooms in the house.
     */
    public List<Room> findByHouseID(HouseIDVO houseID) {
        return data.values().stream().filter(room -> houseID.equals(room.getHouseID())).toList();
    }
}

