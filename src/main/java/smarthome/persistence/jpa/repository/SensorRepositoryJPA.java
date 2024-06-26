package smarthome.persistence.jpa.repository;

import jakarta.persistence.*;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.mapper.assembler.ActuatorAssembler;
import smarthome.mapper.assembler.SensorAssembler;
import smarthome.persistence.SensorRepository;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SensorRepositoryJPA implements SensorRepository {

    private final SensorFactory sensorFactory;
    private final EntityManagerFactory entityManagerFactory;
    private static final String QUERY = "SELECT e FROM SensorDataModel e";

    /**
     * Constructor for SensorRepositoryJPA. It takes a SensorFactory and an EntityManagerFactory as parameters.
     *
     * @param sensorFactory        The sensor factory.
     * @param entityManagerFactory The entity manager factory.
     */
    public SensorRepositoryJPA(SensorFactory sensorFactory, EntityManagerFactory entityManagerFactory) {
        this.sensorFactory = sensorFactory;
        this.entityManagerFactory = entityManagerFactory;
    }


    /**
     * Method to save a Sensor object to the database. It returns true if the Sensor is saved successfully, and false otherwise.
     * This method is an implementation of the save() method in the Repository interface.
     * First, it checks if the Sensor object is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * After that it creates an EntityTransaction by calling getTransaction() on the EntityManager. Then it begins the
     * transaction by calling begin() on the EntityTransaction.
     * Then it creates a SensorDataModel object from the Sensor object by calling the SensorDataModel constructor with
     * the Sensor object as a parameter.
     * Then it persists the SensorDataModel object by calling persist() on the EntityManager.
     * Finally, it commits the transaction by calling commit() on the EntityTransaction.
     * If a RuntimeException is thrown, it returns false.
     *
     * @param entity The Sensor object to save.
     * @return A boolean value indicating whether the Sensor was saved successfully.
     * @throws IllegalArgumentException if the Sensor object is null.
     */
    @Override
    public boolean save(Sensor entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Sensor is null");
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            SensorDataModel sensorDataModel = new SensorDataModel(entity);
            entityManager.persist(sensorDataModel);
            tx.commit();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }


    /**
     * Method to find all Sensor objects in the database. It returns an Iterable of Sensor objects.
     * This method is an implementation of the findAll() method in the Repository interface.
     * First, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * Then it creates a Query object by calling createQuery() on the EntityManager with the QUERY string as a parameter.
     * Then it gets the result list by calling getResultList() on the Query object.
     * Then it converts the list of SensorDataModel objects to a list of Sensor objects by calling the toDomain() method
     * in the SensorAssembler class.
     * Finally, it returns the list of Sensor objects.
     * If a RuntimeException is thrown, it returns an empty list.
     *
     * @return An Iterable of Sensor objects.
     */
    @Override
    public Iterable<Sensor> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Query query = entityManager.createQuery(QUERY);
            List<SensorDataModel> sensorDataModelList = query.getResultList();
            return SensorAssembler.toDomain(sensorFactory, sensorDataModelList);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }


    /**
     * Method to find a Sensor object by its ID. It returns the Sensor object if it is found, and null otherwise.
     * This method is an implementation of the findById() method in the Repository interface.
     * First, it checks if the Sensor ID is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * Then it gets the SensorDataModel object from the database by calling the getSensorDataModelFromSensorId() method.
     * Then it converts the SensorDataModel object to a Sensor object by calling the toDomain() method in the SensorAssembler class.
     * Finally, it returns the Sensor object if it is found, and null otherwise.
     * If a RuntimeException is thrown, it returns null.
     *
     * @param id The Sensor ID.
     * @return The Sensor object.
     * @throws IllegalArgumentException if the ID is null.
     */
    @Override
    public Sensor findById(SensorIDVO id) {
        if (id == null) {
            throw new IllegalArgumentException("Sensor ID is null");
        }
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Optional<SensorDataModel> sensorDataModel = getSensorDataModelFromSensorId(entityManager, id);
            return sensorDataModel.map(dataModel -> SensorAssembler.toDomain(sensorFactory, dataModel)).orElse(null);
        } catch (RuntimeException e) {
            return null;
        }
    }


    /**
     * Method to check if a Sensor object is present in the database by its ID. It returns a boolean value.
     * This method is an implementation of the isPresent() method in the Repository interface.
     * First, it checks if the Sensor ID is null, and throws an IllegalArgumentException if it is.
     * Then, it gets an EntityManager by calling createEntityManager() on the EntityManagerFactory.
     * Then it gets the SensorDataModel object from the database by calling the getSensorDataModelFromSensorId() method.
     * Finally, it returns true if the SensorDataModel object is present, and false otherwise, by calling isPresent() on the Optional.
     * If a RuntimeException is thrown, it returns false.
     *
     * @param id The Sensor ID.
     * @return A boolean value indicating whether the Sensor is present in the database.
     * @throws IllegalArgumentException if the Sensor ID is null.
     */
    @Override
    public boolean isPresent(SensorIDVO id) {
        if (id == null) {
            throw new IllegalArgumentException("Sensor ID is null");
        }
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Optional<SensorDataModel> sensorDataModel = getSensorDataModelFromSensorId(entityManager, id);
            return sensorDataModel.isPresent();
        } catch (RuntimeException e) {
            return false;
        }
    }


    /**
     * Method to get a SensorDataModel object from the database by its ID. It returns an Optional of SensorDataModel.
     * First, it gets a SensorDataModel object by calling find() on the EntityManager with the String Sensor ID as a parameter.
     * Then it returns an Optional of the SensorDataModel object.
     *
     * @param entityManager The EntityManager object.
     * @param sensorIDVO    The Sensor ID.
     * @return An Optional of SensorDataModel.
     */
    private Optional<SensorDataModel> getSensorDataModelFromSensorId(EntityManager entityManager, SensorIDVO sensorIDVO) {
        String sensorID = sensorIDVO.getID();
        return Optional.ofNullable(entityManager.find(SensorDataModel.class, sensorID));
    }

    @Override
    public Iterable<Sensor> findByDeviceID(DeviceIDVO deviceID) {
        try (EntityManager em = this.entityManagerFactory.createEntityManager()) {
            TypedQuery<SensorDataModel> query = em.createQuery("SELECT a FROM SensorDataModel a WHERE a.deviceID = :deviceID", SensorDataModel.class);
            query.setParameter("deviceID", deviceID.getID());
            List<SensorDataModel> list = query.getResultList();
            return SensorAssembler.toDomain(sensorFactory, list);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Finds all Sensor objects in the database that match the provided sensor type ID.
     * This method creates a query to select all SensorDataModel entries that have the specified sensor type ID.
     * The results are then converted to domain Sensor objects using the SensorAssembler.
     * If an exception occurs, an empty list is returned.
     *
     * @param sensorTypeID The SensorTypeIDVO representing the type of sensor to search for.
     * @return An Iterable of Sensor objects that match the specified sensor type ID.
     *         If no matching sensors are found or if an exception occurs, an empty list is returned.
     */
    @Override
    public Iterable<Sensor> findBySensorTypeId(SensorTypeIDVO sensorTypeID) {
        try (EntityManager em = this.entityManagerFactory.createEntityManager()) {
            TypedQuery<SensorDataModel> query = em.createQuery("SELECT a FROM SensorDataModel a WHERE a.sensorTypeID = :sensorTypeID", SensorDataModel.class);
            query.setParameter("sensorTypeID", sensorTypeID.getID());
            List<SensorDataModel> list = query.getResultList();
            return SensorAssembler.toDomain(sensorFactory, list);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
}
