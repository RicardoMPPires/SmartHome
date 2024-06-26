package smarthome.persistence.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.vo.roomvo.RoomIDVO;
import smarthome.mapper.assembler.RoomAssembler;
import smarthome.persistence.RoomRepository;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * RoomRepositoryJPA is a class that implements the RoomRepository interface.
 * It is used to interact with the database and perform CRUD operations on the Room entity although in this specific
 * repository no Update or Delete operations are made.
 * It uses JPA (Java Persistence API) to interact with the database.
 */

public class RoomRepositoryJPA implements RoomRepository {

    private final RoomFactory roomFactory;
    private final EntityManagerFactory entityManagerFactory;

    /**
     * Constructor for RoomRepositoryJPA. It takes a RoomFactory and an EntityManagerFactory as parameters.
     *
     * @param roomFactory          The RoomFactory used to create Room objects.
     * @param entityManagerFactory The EntityManagerFactory used to create EntityManagers.
     */

    public RoomRepositoryJPA(RoomFactory roomFactory, EntityManagerFactory entityManagerFactory) {
        this.roomFactory = roomFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Method to save a Room object to the database. It returns true if the Room is saved successfully,
     * and false otherwise.
     * This method is an implementation of the save() method in the Repository interface.
     * This method is used to save a Room object to the database. It is called by other methods in this class.
     * First, it checks if the Room object is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * After that it creates an EntityTransaction by calling getTransaction() on the EntityManager.
     * Then it begins the transaction by calling begin() on the EntityTransaction.
     * Then it creates a RoomDataModel object from the Room object by calling the RoomDataModel constructor with the
     * Room object as a parameter.
     * Then it persists the RoomDataModel object by calling persist() on the EntityManager.
     * Finally, it commits the transaction by calling commit() on the EntityTransaction.
     * If a RuntimeException is thrown, it returns false and rolls back the transaction.
     *
     * @param room The Room object to save.
     * @return A boolean value indicating whether the Room was saved successfully.
     */

    @Override
    public boolean save(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Entity is null");
        }

        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            RoomDataModel roomDataModel = new RoomDataModel(room);
            em.persist(roomDataModel);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Method to retrieve all Room objects from the database. It returns an Iterable of Room objects.
     * This method is an implementation of the findAll() method in the Repository interface.
     * First, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * Then it creates a Query object by calling createQuery() on the EntityManager with a JPQL query as a parameter.
     * The JPQL query is "SELECT r FROM RoomDataModel r", which selects all RoomDataModel objects from the database.
     * Then it gets a List of RoomDataModel objects by calling getResultList() on the Query object.
     * Then it converts the List of RoomDataModel objects to an Iterable of Room objects by calling RoomAssembler.toDomainList()
     * method with the RoomFactory and the List of RoomDataModel objects as parameters.
     * Finally, it returns the Iterable of Room objects.
     * If a RuntimeException is thrown, it returns an empty Iterable.
     *
     * @return An Iterable of Room objects.
     */

    @Override
    public Iterable<Room> findAll() {

        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            Query query = em.createQuery("SELECT r FROM RoomDataModel r");
            List<RoomDataModel> rooms = query.getResultList();
            return RoomAssembler.toDomainList(roomFactory, rooms);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Method to retrieve a Room object from the database by its ID. It returns a Room object.
     * This method is an implementation of the findById() method in the Repository interface.
     * First it checks if the RoomIDVO object is null, and throws an IllegalArgumentException if it is.
     * Then it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * Then it gets an Optional of RoomDataModel object by calling getDataModelFromId() method with the EntityManager and
     * the RoomIDVO object as parameters.
     * Then it returns a Room object by calling RoomAssembler.toDomain() method with the RoomFactory and the RoomDataModel object
     * as parameters.
     * If a RuntimeException is thrown, it returns null.
     * If the RoomDataModel object is present, it returns the Room object, and null otherwise.
     *
     * @param roomIDVO The RoomIDVO object of the Room to retrieve.
     * @return A Room object.
     */

    @Override
    public Room findById(RoomIDVO roomIDVO) {
        if (roomIDVO == null) {
            throw new IllegalArgumentException("ID is null");
        }
        try(EntityManager em = entityManagerFactory.createEntityManager()) {
            Optional<RoomDataModel> roomDataModel = getDataModelFromId(em, roomIDVO);
            return roomDataModel.map(dataModel -> RoomAssembler.toDomain(roomFactory, dataModel)).orElse(null);
        } catch (RuntimeException e) {
            return null;
        }
    }

    /**
     * Method to check if a Room object is present in the database by its ID. It returns a boolean value.
     * This method is an implementation of the isPresent() method in the Repository interface.
     * First it checks if the RoomIDVO object is null, and throws an IllegalArgumentException if it is.
     * Then it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * Then it gets an Optional of RoomDataModel object by calling getDataModelFromId() method with the EntityManager and
     * the RoomIDVO object as parameters.
     * Then it returns a boolean value indicating whether the RoomDataModel object is present by calling isPresent() on the
     * Optional object.
     * If a RuntimeException is thrown, it returns false.
     *
     * @param roomIDVO The RoomIDVO object of the Room to check.
     * @return A boolean value indicating whether the Room is present.
     */

    @Override
    public boolean isPresent(RoomIDVO roomIDVO) {
        if (roomIDVO == null) {
            throw new IllegalArgumentException("ID is null");
        }
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            Optional<RoomDataModel> roomDataModel = getDataModelFromId(em, roomIDVO);
            return roomDataModel.isPresent();
        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     * Private method to get a RoomDataModel object from the database by its ID. It returns an Optional of RoomDataModel object.
     * This method is used by the findById() method to retrieve a RoomDataModel object from the database.
     * First it gets an ID string by calling the getID() method on the RoomIDVO object.
     * Then t returns an Optional of RoomDataModel object by calling the find() method on the EntityManager with the
     * RoomDataModel class and the ID string as parameters.
     * If the RoomDataModel object is present, it returns the RoomDataModel object, and null otherwise.
     *
     * @param entityManager
     * @param roomIDVO
     * @return
     */

    private Optional<RoomDataModel> getDataModelFromId(EntityManager entityManager, RoomIDVO roomIDVO) {
        String roomId = roomIDVO.getID();
        return Optional.ofNullable(entityManager.find(RoomDataModel.class, roomId));
    }
}
