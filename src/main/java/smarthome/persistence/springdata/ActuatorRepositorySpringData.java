package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.mapper.assembler.ActuatorAssembler;
import smarthome.persistence.ActuatorRepository;
import smarthome.persistence.jpa.datamodel.ActuatorDataModel;

import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Implementation of the ActuatorRepository interface using Spring Data JPA.
 * This class interacts with the database to perform CRUD operations on actuator entities.
 */
@Repository
public class ActuatorRepositorySpringData implements ActuatorRepository {

    private final IActuatorRepositorySpringData iActuatorRepositorySpringData;
    private final ActuatorFactory actuatorFactory;

    /**
     * Constructs a new ActuatorRepositorySpringData instance with the specified dependencies.
     *
     * @param actuatorFactory               The factory for creating actuator entities.
     * @param iActuatorRepositorySpringData The Spring Data JPA repository for actuator entities.
     */
    public ActuatorRepositorySpringData(ActuatorFactory actuatorFactory, IActuatorRepositorySpringData iActuatorRepositorySpringData) {
        this.iActuatorRepositorySpringData = iActuatorRepositorySpringData;
        this.actuatorFactory = actuatorFactory;
    }


    /**
     * Saves an actuator entity to the database.
     *
     * @param actuator The actuator entity to save.
     * @return true if the actuator was successfully saved, false otherwise.
     * @throws IllegalArgumentException if the actuator is null.
     */
    @Override
    public boolean save(Actuator actuator) {
        if (isNull(actuator)) {
            throw new IllegalArgumentException("Actuator is null");
        }
        ActuatorDataModel actuatorDataModel = new ActuatorDataModel(actuator);

        try {
            this.iActuatorRepositorySpringData.save(actuatorDataModel);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * Retrieves all actuator entities from the database.
     *
     * @return An iterable of actuator entities. Null if a DataAccessExceptions occurs.
     */
    @Override
    public Iterable<Actuator> findAll() {
        try {
            Iterable<ActuatorDataModel> actuatorDataModelIterable = this.iActuatorRepositorySpringData.findAll();
            return ActuatorAssembler.toDomainList(this.actuatorFactory, actuatorDataModelIterable);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * Retrieves an actuator entity from the database based on the provided ID.
     *
     * @param id The ID of the actuator entity to retrieve.
     * @return The actuator entity with the provided ID. Null if the actuator is not found or a DataAccessException occurs.
     */
    @Override
    public Actuator findById(ActuatorIDVO id) {
        if (isNull(id)) {
            return null;
        }
        String actuatorId = id.getID();
        try {
            Optional<ActuatorDataModel> actuatorDataModelOpt = this.iActuatorRepositorySpringData.findById(actuatorId);

            if (actuatorDataModelOpt.isPresent()) {
                ActuatorDataModel actuatorDataModel = actuatorDataModelOpt.get();
                return ActuatorAssembler.toDomain(this.actuatorFactory, actuatorDataModel);
            }
            return null;

        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * Checks if an actuator entity with the provided ID is present in the database.
     *
     * @param id The ID of the actuator entity to check.
     * @return true if the actuator entity is present in the database, false otherwise.
     */
    @Override
    public boolean isPresent(ActuatorIDVO id) {
        if (isNull(id)) {
            return false;
        }
        return findById(id) != null;
    }

    /**
     * Finds actuators associated with a specific device ID.
     *
     * @param deviceID The ID of the device to search for actuators.
     * @return A list of actuators associated with the specified device ID. If no devices are found or if an error occurs
     * during the search, an empty list is returned.
     */
    @Override
    public Iterable<Actuator> findByDeviceID(DeviceIDVO deviceID) {
        try {
            Iterable<ActuatorDataModel> actuatorDataModelIterable = this.iActuatorRepositorySpringData.findByDeviceID(deviceID.getID());
            return ActuatorAssembler.toDomainList(this.actuatorFactory, actuatorDataModelIterable);
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
}
