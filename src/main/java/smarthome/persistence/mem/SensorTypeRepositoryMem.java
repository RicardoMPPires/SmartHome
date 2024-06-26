package smarthome.persistence.mem;

import smarthome.domain.sensortype.SensorType;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.persistence.SensorTypeRepository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A repository implementation for managing SensorType entities.
 * This repository provides methods to save, find, and check for the presence of SensorType entities.
 */
public class SensorTypeRepositoryMem implements SensorTypeRepository {

    /** The data store for SensorType entities, mapped by their unique identifiers. */
    private final Map<SensorTypeIDVO, SensorType> data = new LinkedHashMap<>();

    /**
     * Saves a SensorType entity to the repository.
     * @param entity The SensorType entity to be saved.
     * @return True if the entity was successfully saved, false otherwise.
     */
    @Override
    public boolean save(SensorType entity) {
        if (validSaveConditions(entity)) {
            data.put(entity.getId(), entity);
            return true;
        }
        return false;
    }

    /**
     * Checks if the specified SensorType entity satisfies the conditions for saving.
     * @param entity The SensorType entity to be validated.
     * @return True if the entity is not null, has a non-null ID, and is not already present in the repository, false otherwise.
     */
    private boolean validSaveConditions(SensorType entity) {
        return entity != null && entity.getId() != null && !isPresent(entity.getId());
    }

    /**
     * Retrieves all SensorType entities from the repository.
     * @return An Iterable containing all SensorType entities stored in the repository.
     */
    @Override
    public Iterable<SensorType> findAll() {
        return data.values();
    }

    /**
     * Finds a SensorType entity in the repository by its unique identifier.
     * @param id The unique identifier of the SensorType entity to find.
     * @return The SensorType entity with the specified ID, or null if not found.
     */
    @Override
    public SensorType findById(SensorTypeIDVO id) {
        if (isPresent(id)) {
            return data.get(id);
        }
        return null;
    }

    /**
     * Checks if a SensorType entity with the specified ID is present in the repository.
     * @param id The unique identifier of the SensorType entity to check.
     * @return True if a SensorType entity with the specified ID is present, false otherwise.
     */
    @Override
    public boolean isPresent(SensorTypeIDVO id) {
        return data.containsKey(id);
    }
}
