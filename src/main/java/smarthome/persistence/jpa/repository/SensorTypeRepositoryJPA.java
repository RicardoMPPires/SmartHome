package smarthome.persistence.jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.mapper.assembler.SensorTypeAssembler;
import smarthome.persistence.SensorTypeRepository;
import smarthome.persistence.jpa.datamodel.SensorTypeDataModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Implementation of SensorTypeRepository using JPA for data access.
 * This class provides methods to manage SensorType entities in a database,
 * including operations such as save, find, and check presence.
 */
public class SensorTypeRepositoryJPA implements SensorTypeRepository {

    private final SensorTypeFactory sensorTypeFactory;
    private final EntityManagerFactory entityManagerFactory;


    /**
     * Constructs a new SensorTypeRepositoryJPA with a SensorTypeFactory and EntityManagerFactory.
     *
     * @param sensorTypeFactory The factory to create SensorType domain objects.
     * @param entityManagerFactory The factory to create EntityManager instances for database operations.
     */
    public SensorTypeRepositoryJPA(SensorTypeFactory sensorTypeFactory, EntityManagerFactory entityManagerFactory)
    {
        this.sensorTypeFactory = sensorTypeFactory;
        this.entityManagerFactory = entityManagerFactory;
    }


    /**
     * Saves a SensorType entity to the database.
     * This method handles all aspects of database interaction necessary to persist a new or updated SensorType entity.
     * It begins by checking if the provided SensorType object is null, throwing an IllegalArgumentException if it is.
     * A new EntityManager is created and a transaction is started. The SensorType domain object is converted to a
     * SensorTypeDataModel, which is then persisted in the database. The transaction is committed if the operation is
     * successful. If a RuntimeException occurs during the process (e.g., a database access error), the method will
     * catch this exception and return false, indicating that the save operation failed.
     *
     * @param sensorType The SensorType domain object to be persisted. Must not be null.
     * @return boolean True if the sensor type is successfully saved, false if any exception occurs during the process.
     * @throws IllegalArgumentException if the sensorType argument is null, to prevent saving null entities.
     */
    public boolean save(SensorType sensorType) {
        if (sensorType == null) {
            throw new IllegalArgumentException("SensorType is null");
        }

        try (EntityManager em = entityManagerFactory.createEntityManager()) {

            EntityTransaction tx = em.getTransaction();
            tx.begin();
            SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);
            em.persist(sensorTypeDataModel);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }


    /**
     * Retrieves all SensorType entities from the database and converts them into SensorType domain objects.
     * This method creates a new EntityManager for querying the database and executes a JPQL query to fetch all
     * entries of SensorTypeDataModel. Each data model is then converted to its corresponding domain object via the
     * SensorTypeAssembler. If the query execution is successful, a collection of SensorType domain objects is returned.
     * If a RuntimeException occurs during the database operation, such as a connectivity issue or a query error,
     * an empty list is returned, ensuring that the method does not fail and allows the caller to handle the absence
     * of data.
     *
     * @return Iterable<SensorType> A collection of SensorType domain objects, potentially empty if no data is found or
     * if an exception occurs during the database operation.
     */
    public Iterable<SensorType> findAll() {
        try(EntityManager em = entityManagerFactory.createEntityManager()){
        Query query = em.createQuery(
                "SELECT e FROM SensorTypeDataModel e");

        List<SensorTypeDataModel> listOfSensorTypeDataModel = query.getResultList();

        return SensorTypeAssembler.toDomain(sensorTypeFactory, listOfSensorTypeDataModel);
    } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }


    /**
     * Finds a SensorType by its ID and converts it to a domain object.
     * This method retrieves a SensorTypeDataModel from the database using the provided ID encapsulated within
     * a SensorTypeIDVO object. It uses the private helper method getDataModelFromId(EntityManager, SensorTypeIDVO)
     * to perform the database lookup. If a matching data model is found, it is converted to a SensorType domain
     * object using the SensorTypeAssembler. If no data model is found, or if an exception occurs during database
     * access, the method returns null.
     * The method enforces that the ID must not be null by throwing an IllegalArgumentException if a null ID is
     * provided, ensuring that database operations are not attempted with invalid data.
     *
     * @param id The ID of the SensorType to find, encapsulated in a SensorTypeIDVO object. Must not be null.
     * @return SensorType The SensorType domain object corresponding to the provided ID, or null if the data model
     * is not found or an exception occurs.
     * @throws IllegalArgumentException if the provided ID is null, preventing the method from executing a lookup
     * with a null key.
     */
    @Override
    public SensorType findById(SensorTypeIDVO id) {
        if (id == null) {
            throw new IllegalArgumentException("Sensor Type ID is null");
        }
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            Optional<SensorTypeDataModel> sensorTypeDataModel = getDataModelFromId(em, id);
            return sensorTypeDataModel.map(dataModel -> SensorTypeAssembler.toDomain(sensorTypeFactory, dataModel)).orElse(null);
            } catch (RuntimeException e) {
                return null;
        }
    }



    /**
     * Checks whether a SensorType entity with the specified ID exists in the database.
     * This method uses the provided SensorTypeIDVO, which encapsulates the ID of the sensor type, to check for the
     * presence of a corresponding SensorTypeDataModel in the database. It leverages the private helper method
     * getDataModelFromId(EntityManager, SensorTypeIDVO) to perform the database query. The method returns true
     * if the SensorTypeDataModel exists (i.e., the ID corresponds to an existing entity), and false if no such
     * model is found or if an exception occurs during the database operation.
     * This method throws an IllegalArgumentException if a null ID is provided, as searching for a null key
     * is invalid and potentially harmful in terms of database performance and integrity.
     *
     * @param id The ID of the SensorType to check, encapsulated in a SensorTypeIDVO object. Must not be null.
     * @return boolean True if the entity exists, false otherwise. This includes returning false if any exceptions
     * occur during the process, such as database access issues.
     * @throws IllegalArgumentException if the provided ID is null, to ensure safe and meaningful database operations.
     */
    @Override
    public boolean isPresent(SensorTypeIDVO id) {
        if(id== null){
            throw new IllegalArgumentException("Sensor Type ID is null");
        }
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            Optional<SensorTypeDataModel> sensorTypeDataModel = getDataModelFromId(em, id);
            return sensorTypeDataModel.isPresent();
        } catch (RuntimeException e){
            return false;
        }
    }

    /**
     * Retrieves a SensorTypeDataModel from the database using a given ID.
     * This private method encapsulates the logic for fetching a single SensorTypeDataModel based on the ID provided
     * by a SensorTypeIDVO object. It utilizes the EntityManager to perform a database find operation. The method
     * is designed to return an Optional, which will be empty if no matching SensorTypeDataModel is found (i.e.,
     * if the entity corresponding to the provided ID does not exist in the database).
     * This method is intended for internal use within the repository class to aid other public methods in
     * accessing sensor type data models, ensuring that all database access logic is centralized and consistent.
     *
     * @param entityManager The EntityManager to be used for the database operation.
     * @param sensorTypeIDVO The value object containing the ID of the sensor type to be retrieved.
     * @return Optional<SensorTypeDataModel> An Optional containing the found SensorTypeDataModel if present;
     * otherwise, an Optional.empty().
     *
     */

    private Optional<SensorTypeDataModel> getDataModelFromId(EntityManager entityManager, SensorTypeIDVO sensorTypeIDVO) {
        String sensorTypeId = sensorTypeIDVO.getID();
        return Optional.ofNullable(entityManager.find(SensorTypeDataModel.class, sensorTypeId));
    }
}


