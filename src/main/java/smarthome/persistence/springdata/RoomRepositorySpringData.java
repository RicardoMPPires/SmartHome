package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.assembler.RoomAssembler;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

import java.util.Collections;
import java.util.Optional;

/**
 * RoomRepositorySpringData class is a concrete implementation of RoomRepository interface.
 * It is used to interact with the database and perform CRUD operations on the Room entity.
 * It uses Spring Data JPA to interact with the database.
 */

@Repository
public class RoomRepositorySpringData implements RoomRepository {

    IRoomRepositorySpringData iRoomRepositorySpringData;
    RoomFactory roomFactory;

    /**
     * Constructor to create a new RoomRepositorySpringData object.
     *
     * @param iRoomRepositorySpringData IRoomRepositorySpringData object
     * @param roomFactory               RoomFactory object
     */

    public RoomRepositorySpringData(IRoomRepositorySpringData iRoomRepositorySpringData, RoomFactory roomFactory) {
        this.iRoomRepositorySpringData = iRoomRepositorySpringData;
        this.roomFactory = roomFactory;
    }

    /**
     * Method to save a Room object to the database. It returns true if the Room is saved successfully,
     * and false otherwise.
     * This method is an implementation of the save() method in the Repository interface.
     * This method is used to save a Room object to the database.
     * First, it checks if the Room object is null, and throws an IllegalArgumentException if it is.
     * Then, it creates a RoomDataModel object from the Room object by calling the RoomDataModel constructor with the
     * Room object as a parameter.
     * Then, it saves the RoomDataModel object by calling save() on the IRoomRepositorySpringData object.
     * If a DataAccessException is thrown, it returns false.
     *
     * @param room The Room object to save.
     * @return A boolean value indicating whether the Room was saved successfully.
     */

    @Override
    public boolean save(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room is null");
        }
        RoomDataModel roomDataModel = new RoomDataModel(room);
        try {
            this.iRoomRepositorySpringData.save(roomDataModel);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Method to find all Room objects in the database. It returns an Iterable of Room objects.
     * This method is an implementation of the findAll() method in the Repository interface.
     * This method is used to find all Room objects in the database.
     * It gets an Iterable of RoomDataModel objects by calling findAll() on the IRoomRepositorySpringData object.
     * Then it calls the toDomainList() method in the RoomAssembler class to convert the Iterable of RoomDataModel objects
     * to an Iterable of Room objects.
     * If a DataAccessException is thrown, it returns an empty Iterable.
     *
     * @return An Iterable of Room objects.
     */

    @Override
    public Iterable<Room> findAll() {
        try {
            Iterable<RoomDataModel> roomDataModelIterable = this.iRoomRepositorySpringData.findAll();
            return RoomAssembler.toDomainList(this.roomFactory, roomDataModelIterable);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Method to find a Room object by its ID. It returns the Room object if it is found, and null otherwise.
     * This method is an implementation of the findById() method in the Repository interface.
     * First, it checks if the RoomIDVO object is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an Optional of RoomDataModel object by calling getDataModelFromId() method with the RoomIDVO object as a parameter.
     * Then, it checks if the RoomDataModel object is present, and returns the Room object by calling the toDomain() method in the
     * RoomAssembler class with the RoomFactory and the RoomDataModel object as parameters.
     * If a DataAccessException is thrown, it returns null.
     *
     * @param roomIDVO IDVO object of the Room
     * @return The Room object if it is found, and null otherwise.
     */

    @Override
    public Room findById(RoomIDVO roomIDVO) {
        if (roomIDVO == null) {
            throw new IllegalArgumentException("ID is null");
        }
        try {
            Optional<RoomDataModel> roomDataModelOptional = getDataModelFromId(roomIDVO);
            if (roomDataModelOptional.isPresent()) {
                RoomDataModel roomDataModel = roomDataModelOptional.get();
                return RoomAssembler.toDomain(this.roomFactory, roomDataModel);
            }
            return null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * Method to check if a Room object is present in the database. It returns true if the Room is present, and false otherwise.
     * This method is an implementation of the isPresent() method in the Repository interface.
     * First, it checks if the RoomIDVO object is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an Optional of RoomDataModel object by calling getDataModelFromId() method with the RoomIDVO object as a parameter.
     * Then, it returns a boolean value indicating whether the RoomDataModel object is present by calling isPresent() on the Optional object.
     * If a DataAccessException is thrown, it returns false.
     *
     * @param roomIDVO IDVO object of the Room
     * @return A boolean value indicating whether the Room is present.
     */

    @Override
    public boolean isPresent(RoomIDVO roomIDVO) {
        if (roomIDVO == null) {
            throw new IllegalArgumentException("ID is null");
        }
        try {
            Optional<RoomDataModel> roomDataModelOptional = getDataModelFromId(roomIDVO);
            return roomDataModelOptional.isPresent();
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Private method to get a RoomDataModel object by its ID. It returns an Optional of RoomDataModel object.
     * This method is used to get a RoomDataModel object by its ID.
     * First, it gets the ID of the RoomIDVO object.
     * Then, it gets an Optional of RoomDataModel object by calling findById() on the IRoomRepositorySpringData object with the ID as a parameter.
     * Then, it returns the Optional of RoomDataModel object.
     *
     * @param roomIDVO IDVO object of the Room
     * @return An Optional of RoomDataModel object.
     */

    private Optional<RoomDataModel> getDataModelFromId(RoomIDVO roomIDVO) {
        String roomId = roomIDVO.getID();
        return this.iRoomRepositorySpringData.findById(roomId);
    }
}
