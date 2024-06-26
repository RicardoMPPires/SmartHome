package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.mapper.assembler.ActuatorTypeAssembler;
import smarthome.persistence.ActuatorTypeRepository;
import smarthome.persistence.jpa.datamodel.ActuatorTypeDataModel;


import java.util.Collections;
import java.util.Optional;

/**
 * This class implements the ActuatorTypeRepository interface, interacting with the database using Spring Data JPA.
 */
@Repository
public class ActuatorTypeRepositorySpringData implements ActuatorTypeRepository {

    ActuatorTypeFactory actuatorTypeFactory;
    IActuatorTypeRepositorySpringData iActuatorTypeRepositorySpringData;

    /**
     * Constructor to create a new ActuatorTypeRepositorySpringData object.
     *
     * @param iActuatorRepositorySpringData IActuatorTypeRepositorySpringData object
     * @param actuatorTypeFactory           ActuatorTypeFactory object
     */
    public ActuatorTypeRepositorySpringData(IActuatorTypeRepositorySpringData iActuatorRepositorySpringData, ActuatorTypeFactory actuatorTypeFactory) {
        this.iActuatorTypeRepositorySpringData = iActuatorRepositorySpringData;
        this.actuatorTypeFactory = actuatorTypeFactory;
    }

    /**
     * This method saves an ActuatorType object to the database after converting it to a data model. It returns true if the ActuatorType is saved successfully,
     * and false otherwise.
     * This method is an implementation of the save() method in the Repository interface.
     * This method is used to save an ActuatorType object to the database.
     * First, it checks if the ActuatorType object is null, and throws an IllegalArgumentException if it is.
     * Then, it creates an ActuatorTypeDataModel object from the ActuatorType object by calling the ActuatorTypeDataModel constructor with the
     * ActuatorType object as a parameter.
     * Then, it saves the ActuatorTypeDataModel object by calling save() on the IActuatorTypeRepositorySpringData object.
     * If a DataAccessException is thrown, it returns false.
     *
     * @param actuatorType The ActuatorType object to save.
     * @return A boolean value indicating whether the ActuatorType was saved successfully.
     */
    @Override
    public boolean save(ActuatorType actuatorType) {
        if (actuatorType == null) {
            throw new IllegalArgumentException("Actuator type is null.");
        }
        ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(actuatorType);
        try {
            this.iActuatorTypeRepositorySpringData.save(actuatorTypeDataModel);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * This method searches for all ActuatorType objects in the database and returns an Iterable of ActuatorType objects.
     * This method is an implementation of the findAll() method called upon the interface.
     * After retrieving the ActuatorTypeDataModel objects from the database, it converts them to ActuatorType objects using the ActuatorTypeAssembler class,
     * while using the ActuatorTypeFactory to create the ActuatorType objects. It then returns the Iterable of ActuatorType objects.
     * If a DataAccessException is thrown, it returns an empty list.
     * @return An Iterable of ActuatorType objects.
     */
    @Override
    public Iterable<ActuatorType> findAll() {
        try {
            Iterable<ActuatorTypeDataModel> actuatorTypeDataModelIterable = this.iActuatorTypeRepositorySpringData.findAll();
            return ActuatorTypeAssembler.actuatorTypeListToDomain(this.actuatorTypeFactory, actuatorTypeDataModelIterable);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    /** This method searches for an ActuatorType object in the database by its ID and returns it.
     * This method is an implementation of the findById() method called upon the interface.
     * It first checks if the ActuatorTypeIDVO object is null, and throws an IllegalArgumentException if so.
     * Afterword, creates an optional list with an ActuatorTypeDataModel object retrieved from the database by its ID using
     * an auxiliary method getDataModelFromId().
     * If the ActuatorTypeDataModel object is present, it assigns the optional list's object into an ActuatorTypeDataModel,
     * and then converts it to an ActuatorType object using the ActuatorTypeAssembler class and returns it.
     * If the ActuatorTypeDataModel object is not present, it returns null.
     * If a DataAccessException is thrown, it returns null.
     * @param actuatorTypeIDVO The ActuatorTypeIDVO object containing the ID of the ActuatorType to search for.
     * @return The ActuatorType object found in the database.
     */
    @Override
    public ActuatorType findById(ActuatorTypeIDVO actuatorTypeIDVO) {
        if (actuatorTypeIDVO == null) {
            throw new IllegalArgumentException("Actuator type ID is null.");
        }

        try{
            Optional<ActuatorTypeDataModel> actuatorTypeDataModelOptional = getDataModelFromId(actuatorTypeIDVO);

            if(actuatorTypeDataModelOptional.isPresent()){
                ActuatorTypeDataModel actuatorTypeDataModel = actuatorTypeDataModelOptional.get();
                return ActuatorTypeAssembler.actuatorTypeToDomain(this.actuatorTypeFactory, actuatorTypeDataModel);
            }
            return null;
        }catch (DataAccessException e){
            return null;
        }
    }


    /**
     * This method verifies if an ActuatorType object is present in the database.
     * It first checks if the ActuatorTypeIDVO object is null, and throws an IllegalArgumentException if so.
     * Otherwise, it retrieves the ActuatorTypeDataModel object from the database by its ID using an auxiliary method getDataModelFromId().
     * If the ActuatorTypeDataModel object is present, it returns true.
     * If a DataAccessException is thrown, it returns false.
     * @param actuatorTypeIDVO IDVO
     * @return A boolean value indicating whether the ActuatorType object is present in the database.
     */
    @Override
    public boolean isPresent(ActuatorTypeIDVO actuatorTypeIDVO) {
        if (actuatorTypeIDVO == null) {
            throw new IllegalArgumentException("Actuator type ID is null.");
        }
        try {
            Optional<ActuatorTypeDataModel> actuatorTypeDataModelOptional = getDataModelFromId(actuatorTypeIDVO);
            return actuatorTypeDataModelOptional.isPresent();
        } catch (DataAccessException e) {
            return false;
        }
    }

    /**
     * This method retrieves an ActuatorTypeDataModel object from the database by its ID.
     * First, it retrieves the ID from the ActuatorTypeIDVO object and assigns it to a string variable.
     * Then, it returns the Optional of ActuatorTypeDataModel object, retrieving it from the database by its ID using the findById() method of the IActuatorTypeRepositorySpringData object.
     * @param actuatorTypeIDVO The ActuatorTypeIDVO object containing the ID of the ActuatorType to search for.
     * @return An Optional of ActuatorTypeDataModel object.
     */
    private Optional<ActuatorTypeDataModel> getDataModelFromId(ActuatorTypeIDVO actuatorTypeIDVO) {
        String actuatorTypeId = actuatorTypeIDVO.getID();
        return this.iActuatorTypeRepositorySpringData.findById(actuatorTypeId);
    }

}
