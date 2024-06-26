package smarthome.service;

import org.springframework.stereotype.Service;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.vo.housevo.HouseIDVO;
import smarthome.domain.vo.roomvo.RoomDimensionsVO;
import smarthome.domain.vo.roomvo.RoomFloorVO;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.domain.vo.roomvo.RoomNameVO;
import smarthome.persistence.HouseRepository;
import smarthome.persistence.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final RoomFactory roomFactory;
    private static final String NOT_PRESENT_MESSAGE = " is not present.";

    /**
     * Constructor for RoomService. It takes a RoomRepository and a RoomFactory as parameters and encapsulates them.
     * If any of the parameters are null, it throws an IllegalArgumentException.
     * @param roomRepository Room repository
     * @param roomFactory Factory Repository
     */
    public RoomServiceImpl(HouseRepository houseRepository, RoomRepository roomRepository, RoomFactory roomFactory) {
        if (areParamsNull(houseRepository, roomRepository, roomFactory)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.roomFactory = roomFactory;
    }

    /**
     * Calls createRoom on RoomFactory and saves received Room using the RoomRepository.
     * @param roomNameVO RoomNameVO object
     * @param roomFloorVO FloorVO object
     * @param roomDimensionsVO RoomDimensionVO object
     * @return Optional of Room object
     */
    @Override
    public Optional<Room> addRoom(RoomNameVO roomNameVO, RoomFloorVO roomFloorVO, RoomDimensionsVO roomDimensionsVO) {
        try{
            HouseIDVO houseIDVO = houseRepository.getFirstHouseIDVO();
            Room newRoom = roomFactory.createRoom(roomNameVO,roomFloorVO,roomDimensionsVO,houseIDVO);
            if (roomRepository.save(newRoom)) {
                return Optional.of(newRoom);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unable to save room");

        }
        return Optional.empty();
    }

    /**
     * This method retrieves all rooms from the Room Repository and translates the received iterable into a List for indexed access.
     * @return List of Room objects
     */
    @Override
    public List<Room> findAll(){
        Iterable<Room> rooms = this.roomRepository.findAll();
        List<Room> finalList = new ArrayList<>();

        for (Room room : rooms){
            finalList.add(room);
        }
        return finalList;
    }

    /**
     * This method receives object parameters and verifies they are null.
     * @param params Any object
     * @return True or false
     */
    private boolean areParamsNull (Object... params){
        for (Object param : params){
            if (param == null){
                return true;
            }
        }
        return false;
    }
    /**
     * This method checks if the room with the given roomId exists in the repository. If the room
     * is found, it returns an Optional containing the Room object. If the room is not found,
     * it returns an empty Optional.
     *
     * @param roomId the ID of the room to be found, represented by RoomIDVO.
     * @return an Optional containing the Room if found, otherwise an empty Optional.
     */

    public Optional<Room> findById(RoomIDVO roomId) {
        if(!roomRepository.isPresent(roomId)){
            return Optional.empty();
        }
        return Optional.of(roomRepository.findById(roomId));
    }

}

