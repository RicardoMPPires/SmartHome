package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.mapper.assembler.SensorTypeAssembler;
import smarthome.persistence.SensorTypeRepository;
import smarthome.persistence.jpa.datamodel.SensorTypeDataModel;

import java.util.Optional;


/**
 * Implementation of SensorTypeRepository using Spring Data JPA.
 * This class provides concrete implementations of methods to interact
 * with the data stored for SensorType entities.
 * Depends on ISensorTypeRepositorySpringData for data access
 * and SensorTypeFactory for creating domain objects.
 */

@Repository
public class SensorTypeRepositorySpringData implements SensorTypeRepository {

    private final ISensorTypeRepositorySpringData repositorySpringData;
    private final SensorTypeFactory factory;


    /**
     * Constructs a new SensorTypeRepositorySpringData.
     *
     * @param repositorySpringData the Spring Data repository interface
     * @param factory the factory to create SensorType instances
     */
    public SensorTypeRepositorySpringData(ISensorTypeRepositorySpringData repositorySpringData, SensorTypeFactory factory){
        this.repositorySpringData = repositorySpringData;
        this.factory = factory;
    }


    /**
     * Saves a SensorType entity to the database.
     *
     * @param sensorType the SensorType to save
     * @return true if the operation was successful, false otherwise
     * @throws IllegalArgumentException if the sensorType is null
     * @throws DataAccessException if there is any problem occurred during the database access
     */
    @Override
    public boolean save(SensorType sensorType) {
        if(sensorType == null) {
            throw new IllegalArgumentException("Sensor Type is null");
        }
        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);

        try{
            this.repositorySpringData.save(sensorTypeDataModel);
            return true;
        }catch (DataAccessException e){
            return false;
        }
    }


    /**
     * Retrieves all SensorType entities from the database.
     *
     * @return an Iterable collection of SensorType, or null if an error occurs
     * @throws DataAccessException if there is any problem occurred during the database access
     */
    @Override
    public Iterable<SensorType> findAll() {
        try{
            Iterable<SensorTypeDataModel> sensorTypeDataModelIterable = this.repositorySpringData.findAll();
            return SensorTypeAssembler.toDomain(this.factory, sensorTypeDataModelIterable);
        }catch (DataAccessException e){
            return null;
        }
    }


    /**
     * Finds a single SensorType by its ID.
     *
     * @param id the ID value object of the SensorType to find
     * @return the found SensorType, or null if not found or an error occurs
     * @throws DataAccessException if there is any problem occurred during the database access
     */
    @Override
    public SensorType findById(SensorTypeIDVO id) {
        if(id == null){
            return null;
        }
        String sensorTypeID = id.getID();
        try{
            Optional<SensorTypeDataModel> sensorTypeDataModelOptional = this.repositorySpringData.findById(sensorTypeID);

            if(sensorTypeDataModelOptional.isPresent()){
                SensorTypeDataModel sensorTypeDataModel = sensorTypeDataModelOptional.get();
                return SensorTypeAssembler.toDomain(this.factory, sensorTypeDataModel);
            }
            return null;

        }catch (DataAccessException e){
            return null;
        }
    }


    /**
     * Checks if a SensorType with the specified ID exists in the database.
     *
     * @param id the ID value object of the SensorType to check
     * @return true if an entity with the specified ID exists, false otherwise
     * @throws DataAccessException if there is any problem occurred during the database access
     */
    @Override
    public boolean isPresent(SensorTypeIDVO id) {
        if(id == null){
            return false;
        }
        return findById(id) != null;
    }
}
