package smarthome.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.persistence.SensorTypeRepository;
import smarthome.domain.vo.sensortype.UnitVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorTypeServiceImpl implements SensorTypeService{
    private final SensorTypeRepository sensorTypeRepository;
    private final SensorTypeFactory sensorTypeFactory;

    /**
     * Constructs an instance of V1SensorTypeService with the provided dependencies.
     * This constructor initializes the V1SensorTypeService with the necessary repository, factory, and configuration path.
     * It ensures that the provided parameters are valid and throws an IllegalArgumentException if any are null or if
     * there is an issue with the configuration path.
     * @param sensorTypeRepository The repository for managing SensorType objects.
     * @param sensorTypeFactory The factory for creating new SensorType objects.
     * @param path The path to the configuration file for populating the SensorType repository.
     * @throws IllegalArgumentException if the sensorTypeFactory, sensorTypeRepository, or path parameters are null, or if
     * there is an issue with the configuration path. This exception is thrown to indicate invalid or missing parameters.
     */
    public SensorTypeServiceImpl(SensorTypeRepository sensorTypeRepository, SensorTypeFactory sensorTypeFactory, @Value("${filepath}") String path) {
        if (areParamsNull(sensorTypeFactory, sensorTypeRepository,path)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        try{
            this.sensorTypeRepository = sensorTypeRepository;
            this.sensorTypeFactory = sensorTypeFactory;
            populateRepository(path);
        } catch (NullPointerException | ConfigurationException e){
            throw new IllegalArgumentException("Invalid path");
        }
    }

    /**
     * Retrieves a list of all SensorTypes available in the system.
     * This method fetches all SensorTypes from the SensorTypeRepository.
     * @return A list of SensorType objects representing all available SensorTypes in the system. The list may be empty
     * if no SensorTypes are found.
     */
    public List<SensorType> getListOfSensorTypes(){
        Iterable<SensorType> sensorTypes = this.sensorTypeRepository.findAll();
        List<SensorType> finalList = new ArrayList<>();

        for (SensorType type : sensorTypes){
            finalList.add(type);
        }
        return finalList;
    }

    /**
     * Verifies if a specific sensor type id is contaned within the repository
     * @param sensorTypeID SensorTypeIDVO object
     * @return True or false
     */
    public boolean sensorTypeExists (SensorTypeIDVO sensorTypeID){
        return this.sensorTypeRepository.isPresent(sensorTypeID);
    }

    /**
     * Validates if parameters are null
     * @param params Any object parameter
     * @return True or false
     */
    private boolean areParamsNull(Object... params){
        for (Object param : params){
            if (param == null){
                return true;
            }
        }
        return false;
    }

    /**
     * Populates the SensorTypeRepository with SensorType objects based on the configuration file at the specified path.
     * This method reads the configuration file, extracts SensorType information, and creates SensorType objects
     * using the SensorTypeFactory. The created SensorType objects are then saved to the SensorTypeRepository.
     * @param path The path to the configuration file containing SensorType information.
     * @throws ConfigurationException if there is an issue with reading or parsing the configuration file. This exception
     * is thrown to indicate an error with the configuration file.
     */
    private void populateRepository(String path) throws ConfigurationException {
        List<String> listOfTypes = getTypesFromConfig(path);

        if (listOfTypes.isEmpty()) {
            throw new ConfigurationException("No types found in configuration file");
        }
        for (String string : listOfTypes){
            String[] splitString = splitStrings(string);
            SensorTypeIDVO type = new SensorTypeIDVO(splitString[0]);
            UnitVO unit = new UnitVO(splitString[1]);
            SensorType sensorType = this.sensorTypeFactory.createSensorType(type,unit);
            this.sensorTypeRepository.save(sensorType);
        }
    }

    /**
     * Retrieves a list of SensorType strings from the configuration file at the specified path.
     * This method reads the configuration file using Apache Commons Configuration, extracts the "unit" property,
     * and returns it as a List of strings.
     * @param path The path to the configuration file containing the "unit" property.
     * @return A List of strings representing SensorType information extracted from the configuration file.
     * @throws ConfigurationException if there is an issue with reading or parsing the configuration file, or if the "unit"
     * property is not found in the configuration. This exception is thrown to indicate an error with the configuration
     * file or property.
     */

    private List<String> getTypesFromConfig (String path) throws ConfigurationException {
        Configurations config = new Configurations();
        Configuration configuration = config.properties(path);
        String[] arrayOfTypes = configuration.getStringArray("unit");
        return List.of(arrayOfTypes);
    }

    /**
     * Splits a string using a delimiter.
     * @param stringToSplit String to split
     * @return Array of strings
     */
    private String[] splitStrings (String stringToSplit){
        return stringToSplit.split("\\|");
    }
}
