package smarthome.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.mapper.ActuatorTypeMapper;
import smarthome.persistence.ActuatorTypeRepository;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import org.apache.commons.configuration2.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer class for managing actuator types within the system. This class provides methods
 * for interacting with the ActuatorTypeRepository to perform operations such as retrieving
 * all actuator types, and checking the existence of an actuator type.
 * It encapsulates the business logic related to actuator types, ensuring that consumers of this
 * service can perform necessary operations without direct access to the underlying data access layer.
 * This abstraction also facilitates easier testing and maintenance.
 * An instance of this class must be initialized with a non-null ActuatorTypeRepository,
 * which it then uses to interact with actuator type data. The service validates the presence of the
 * repository upon construction to prevent null-pointer exceptions during runtime.
 */

@Service
public class ActuatorTypeServiceImpl implements ActuatorTypeService {
    private final ActuatorTypeRepository actuatorTypeRepository;
    private final ActuatorTypeFactory actuatorTypeFactory;
    private final String filepath;

    /**
     * Constructor for ActuatorTypeService. Checks if the repository, factory and filepath are null
     * and if the filepath is empty, null or non-existent. If any of these conditions
     * are met, an IllegalArgumentException is thrown.
     * Additionally, it calls the populateRepository() method to populate the repository with actuator types.
     * @param actuatorTypeRepository ActuatorTypeRepository implementation
     * @param actuatorTypeFactory ActuatorTypeFactory implementation
     * @param filepath Filepath configured in application.properties file
     */
    public ActuatorTypeServiceImpl(ActuatorTypeRepository actuatorTypeRepository, ActuatorTypeFactory actuatorTypeFactory, @Value("${filepath}")String filepath)  {
        if (actuatorTypeRepository == null || actuatorTypeFactory == null || filepath == null || filepath.trim().isEmpty()){
            throw new IllegalArgumentException("Invalid repository");
        }
        else {
            try {
                this.actuatorTypeRepository = actuatorTypeRepository;
                this.actuatorTypeFactory = actuatorTypeFactory;
                this.filepath = filepath;
                populateRepository();
            } catch (NullPointerException | ConfigurationException e) {
                throw new IllegalArgumentException("Filepath non existent");
            }
        }
    }


    /**
     * Populates the repository with all actuator types from the configuration file.
     * Each actuator type is used to initialize ActuatorTypeIDVO (Actuator type mapper is used to initialize ActuatorTypeIDVO) ;
     * Initializes an actuatorType with this value object;
     * Saves the newly created actuatorType in the actuator type repository;
     * @throws ConfigurationException If a configuration object unable to be instantiated.
     */
    private void populateRepository() throws ConfigurationException {
        List<String> actuatorTypes;
        actuatorTypes = actuatorTypeReadingAndConversion();
        for(String type : actuatorTypes){
            ActuatorTypeIDVO actuatorTypeIDVO = ActuatorTypeMapper.createActuatorTypeIDVOFromString(type);
            ActuatorType actuatorType = actuatorTypeFactory.createActuatorType(actuatorTypeIDVO);
            this.actuatorTypeRepository.save(actuatorType);
        }
    }



    /**
     * Reads all actuator types from the configuration file and converts them to a list of strings.
     * @return List of actuator types.
     * @throws ConfigurationException If unable to instantiate configuration object
     */
    private List<String> actuatorTypeReadingAndConversion() throws ConfigurationException {
        List<String> actuatorTypes;

        // Read all actuator types from configuration file
        Configurations configs = new Configurations();
        Configuration configuration = configs.properties(filepath);

        // Get all values (Actuator Types) from actuator.properties where the key is actuatorRepo and places them on
        // an array of String, which is then converted to a List
        String [] types = configuration.getStringArray("actuatorRepo");
        actuatorTypes = List.of(types);

        return actuatorTypes;
    }

    /**
     * Returns a list of all ActuatorTypes in the system.
     * @return List of ActuatorTypes.
     */
    public List<ActuatorType> getListOfActuatorTypes(){
        Iterable<ActuatorType> actuatorTypes = this.actuatorTypeRepository.findAll();
        List<ActuatorType> listOfActuatorTypes = new ArrayList<>();
        actuatorTypes.forEach(listOfActuatorTypes::add);
        return listOfActuatorTypes;
    }


    /**
     * Checks if an ActuatorType exists through the ActuatorTypeIDVO.
     * @param actuatorTypeIDVO ActuatorTypeIDVO.
     * @return boolean, returns true if the ActuatorType exists.
     */
    public boolean actuatorTypeExists (ActuatorTypeIDVO actuatorTypeIDVO){
        return this.actuatorTypeRepository.isPresent(actuatorTypeIDVO);
    }
}
