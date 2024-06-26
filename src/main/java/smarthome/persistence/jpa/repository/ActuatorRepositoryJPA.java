package smarthome.persistence.jpa.repository;

import jakarta.persistence.*;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.mapper.assembler.ActuatorAssembler;
import smarthome.mapper.assembler.DeviceAssembler;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;
import smarthome.persistence.jpa.datamodel.DeviceDataModel;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ActuatorRepositoryJPA implements ActuatorRepository {

    private final ActuatorFactory actuatorFactory;
    private final EntityManagerFactory entityManagerFactory;

    /**
     * Constructor for JPA ActuatorRepository implementation.
     * @param actuatorFactory Actuator Factory to enable the conversion of relational data to domain data by the assembler
     * @param entityManagerFactory Factory used to provide an entity manager
     */
    public ActuatorRepositoryJPA(ActuatorFactory actuatorFactory, EntityManagerFactory entityManagerFactory){
        this.actuatorFactory =actuatorFactory;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Saves a Domain Actuator to the database.
     * @param actuator Actuator to be saved.
     * @return True if operation succeeds, false otherwise.
     * @throws IllegalArgumentException If the Actuator provided is invalid (null).
     */
    @Override
    public boolean save(Actuator actuator) {
        if(isNull(actuator)){
            throw new IllegalArgumentException("Actuator is null");
        }
        try(EntityManager manager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            ActuatorDataModel dataModel = new ActuatorDataModel(actuator);
            manager.persist(dataModel);
            transaction.commit();
            return true;

        } catch (RuntimeException exception){
            return false;
        }
    }

    /**
     * Fetches all Actuator data models from the database and converts them into domain objects.
     * @return An Iterable containing all domain Actuators, or an empty list in case there are no
     * persisted Actuator data models.
     */
    @Override
    public Iterable<Actuator> findAll() {
        try(EntityManager manager = entityManagerFactory.createEntityManager()){

            Query query = manager.createQuery("SELECT actuator FROM ActuatorDataModel actuator");
            List<ActuatorDataModel> actuatorList = query.getResultList();
            return ActuatorAssembler.toDomainList(actuatorFactory, actuatorList);

        } catch (RuntimeException exception) {
            return Collections.emptyList();
        }
    }

    /**
     * Fetches from the database the Actuator data model which ID matches the requested one and converts it to a domain Actuator.
     * @param actuatorID Actuator identifier
     * @return The Actuator domain object which ID corresponds to the requested, or null in case operation fails.
     * @throws IllegalArgumentException If the ID provided is invalid (null).
     */
    @Override
    public Actuator findById(ActuatorIDVO actuatorID) {
        if(isNull(actuatorID)){
            throw new IllegalArgumentException("Actuator ID is null");
        }
        try(EntityManager manager = entityManagerFactory.createEntityManager()){

            Optional<ActuatorDataModel> dataModel = getActuatorDataModelFromID(manager, actuatorID);
            return dataModel.map(actuatorDataModel ->
                    ActuatorAssembler.toDomain(actuatorFactory, actuatorDataModel))
                    .orElse(null);

        } catch (RuntimeException exception){
            return null;
        }
    }

    /**
     * Verifies if there is any Actuator data model with a given ID in the database.
     * @param actuatorID Actuator identifier
     * @return True if an Actuator data model with the requested ID is found.
     * @throws IllegalArgumentException If the ID provided is invalid (null).
     */
    @Override
    public boolean isPresent(ActuatorIDVO actuatorID) {
        if(isNull(actuatorID)){
            throw new IllegalArgumentException("Actuator ID is null");
        }
        try(EntityManager manager = entityManagerFactory.createEntityManager()){

            Optional<ActuatorDataModel> dataModel = getActuatorDataModelFromID(manager, actuatorID);
            return dataModel.isPresent();

        } catch (RuntimeException e) {
            return false;
        }
    }

    /**
     *
     * @param manager Entity manager used to read data from the database.
     * @param actuatorID Actuator identification to search for
     * @return An optional containing the ActuatorDataModel if found, an empty Optional otherwise.
     */
    private Optional<ActuatorDataModel> getActuatorDataModelFromID(EntityManager manager, ActuatorIDVO actuatorID){
        String id = actuatorID.getID();
        return Optional.ofNullable(manager.find(ActuatorDataModel.class, id));
    }


    /**
     * Simple function to verify if a given Object is null.
     * @param object Object to verify
     * @return True if Object is null, false otherwise
     */
    private boolean isNull(Object object){
        return object == null;
    }


    /**
     * Retrieves all Actuator entities from a device in the database.
     *
     * @param deviceID The ID of the device to retrieve Actuators from.
     * @return A List containing all actuator entities from a given device.
     */
    @Override
    public Iterable<Actuator> findByDeviceID(DeviceIDVO deviceID) {
        try (EntityManager em = this.entityManagerFactory.createEntityManager()) {
            TypedQuery<ActuatorDataModel> query = em.createQuery("SELECT a FROM ActuatorDataModel a WHERE a.deviceID = :deviceID", ActuatorDataModel.class);
            query.setParameter("deviceID", deviceID.getID());
            List<ActuatorDataModel> list = query.getResultList();
            return ActuatorAssembler.toDomainList(actuatorFactory, list);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
}
