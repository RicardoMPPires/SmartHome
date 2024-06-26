package smarthome.domain.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import smarthome.domain.vo.actuatorvo.ActuatorIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorStatusVO;
import smarthome.domain.vo.actuatorvo.Settings;
import smarthome.domain.vo.actuatortype.ActuatorTypeIDVO;
import smarthome.domain.vo.actuatorvo.ActuatorNameVO;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@Component
public class ActuatorFactoryImpl implements ActuatorFactory {

    private Configuration configuration;

    /**
     * Constructor for FactoryActuator Class.
     * File path is injected in the Class.
     * So this factory is able to read from any file properties passed on.
     *
     * @throws ConfigurationException If a file path is invalid.
     */
    public ActuatorFactoryImpl(@Value("${filepath}") String filePath) throws ConfigurationException {
        initializeConfiguration(filePath);
    }

    /**
     * Creates an Actuator:
     * 1: Checks input parameters. If any of the parameters are null, an IllegalArgumentException is thrown;
     * 2: Verifies whether the actuator type chosen exists in the file and has a correct path to its Class. The value
     * obtained from the configuration file is a path that dynamically identifies the intended Class at runtime;
     * 3: Once the class is identified, its constructor is obtained, and a new instance is created using the entry parameters.;
     * 4: Attempts to instantiate the actuator and returns it.
     *
     * @param actuatorName   Actuator name
     * @param actuatorTypeID Actuator type ID
     * @param deviceID       Device ID to link the actuator to
     * @param settings       Settings for value actuators. May be null in case no range actuators are being added
     * @return The Actuator object in case of correct instantiation, null if operation does not succeed.
     */
    public Actuator createActuator(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID, DeviceIDVO deviceID,
                                   Settings settings) {

        if (!validParameters(actuatorName, actuatorTypeID, deviceID))
            throw new IllegalArgumentException("Invalid actuator parameters");
        Optional<String> actuatorType = getPath(actuatorTypeID);
        if (actuatorType.isPresent()) {
            try {
                Class<?> classObject = Class.forName(actuatorType.get());
                Object[] parameters = toObjectArray(actuatorName, actuatorTypeID, deviceID, settings);
                Constructor<?> constructor = findMatchingConstructor(classObject, parameters);
                return (Actuator) constructor.newInstance(parameters);

            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                     InstantiationException | IllegalAccessException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Creates an Actuator from the Data Model:
     * 1: Verifies whether the actuator type chosen exists in the file and has a correct path to its Class. The value
     * obtained from the configuration file is a path that dynamically identifies the intended Class at runtime;
     * 2: Once the class is identified, its constructor is obtained, and a new instance is created using the entry parameters.;
     * 3: Attempts to instantiate the actuator and returns it.
     * Note: Input parameters are not being validated since they are generated from already persisted data.
     *
     * @param actuatorID     Actuator ID
     * @param actuatorName   Actuator name
     * @param actuatorTypeID Actuator type ID
     * @param deviceID       Device ID to link the actuator to
     * @param settings       Settings for value actuators. May be null in case no range actuators are being added
     * @param statusVO       the status of the actuator.
     * @return The Actuator object in case of correct instantiation, null if operation does not succeed.
     */
    public Actuator createActuator(ActuatorIDVO actuatorID, ActuatorNameVO actuatorName,
                                   ActuatorTypeIDVO actuatorTypeID, DeviceIDVO deviceID,
                                   Settings settings, ActuatorStatusVO statusVO) {

        Optional<String> actuatorType = getPath(actuatorTypeID);
        if (actuatorType.isPresent()) {
            try {
                Class<?> classObject = Class.forName(actuatorType.get());

                Object[] parameters = toObjectArrayFromDataModel(actuatorID, actuatorName, actuatorTypeID, deviceID,
                        settings, statusVO);

                Constructor<?> constructor = findMatchingConstructor(classObject, parameters);

                return (Actuator) constructor.newInstance(parameters);

            } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                     InstantiationException | IllegalAccessException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Converts the provided parameters into an array of Objects, considering the ActuatorNameVO, DeviceIDVO,
     * ActuatorTypeVO and Settings. If the Settings parameter is null, only ActuatorNameVO, ActuatorTypeVO, and
     * DeviceIDVO are included in the array. If settings are not null, the array includes ActuatorNameVO,
     * ActuatorTypeVO, DeviceIDVO, and the Settings objects.
     *
     * @param actuatorName   The ActuatorNameVO parameter
     * @param actuatorTypeID The DeviceIDVO parameter
     * @param deviceID       The ActuatorTypeVO parameter
     * @param settings       The Settings parameter, which can be null.
     * @return An array of Objects containing parameters: ActuatorNameVO, DeviceIDVO, ActuatorTypeVO, and Settings
     * (if provided).
     */
    private Object[] toObjectArray(ActuatorNameVO actuatorName, ActuatorTypeIDVO actuatorTypeID, DeviceIDVO deviceID,
                                   Settings settings) {

        Object[] parameters;
        if (settings != null) {
            parameters = new Object[]{actuatorName, actuatorTypeID, deviceID, settings};
        } else {
            parameters = new Object[]{actuatorName, actuatorTypeID, deviceID};
        }
        return parameters;
    }

    /**
     * Converts the provided parameters into an array of Objects, considering the ActuatorIDVO, ActuatorNameVO,
     * DeviceIDVO, ActuatorTypeVO and Settings.
     * If the Settings parameter is null, only ActuatorIDVO, ActuatorNameVO, ActuatorTypeVO, and DeviceIDVO are
     * included in the array.
     * If settings are not null, the array includes ActuatorIDVO, ActuatorNameVO, ActuatorTypeVO, DeviceIDVO, and
     * the Settings objects.
     *
     * @param actuatorID     The ActuatorIDVO parameter
     * @param actuatorName   The ActuatorNameVO parameter
     * @param actuatorTypeID The DeviceIDVO parameter
     * @param deviceID       The ActuatorTypeVO parameter
     * @param settings       The Settings parameter, which can be null.
     * @param statusVO       The ActuatorStatusVO parameter
     * @return An array of Objects containing parameters: ActuatorIDVO, ActuatorNameVO, DeviceIDVO, ActuatorTypeVO,
     * and Settings (if provided).
     */
    private Object[] toObjectArrayFromDataModel(ActuatorIDVO actuatorID, ActuatorNameVO actuatorName,
                                                ActuatorTypeIDVO actuatorTypeID, DeviceIDVO deviceID,
                                                Settings settings, ActuatorStatusVO statusVO) {

        Object[] parameters;
        if (settings != null) {
            parameters = new Object[]{actuatorID, actuatorName, actuatorTypeID, deviceID, settings, statusVO};
        } else {
            parameters = new Object[]{actuatorID, actuatorName, actuatorTypeID, deviceID, statusVO};
        }
        return parameters;
    }

    /**
     * Finds a constructor of the given class that matches the provided parameter types.
     * The function starts by iterating through all constructors of the given class and for each of the class
     * constructors gets the parameter types and saves them into an array.
     * After that, it compares the number of parameters and their types against the provided parameters.
     * If a constructor with matching parameter types is found, it returns that constructor.
     *
     * @param classObject        The class for which to find the constructor
     * @param receivedParameters The parameters to match against the constructor's parameter types
     * @return The matching constructor, if found
     * @throws NoSuchMethodException If no matching constructor is found.
     */
    private Constructor<?> findMatchingConstructor(Class<?> classObject, Object[] receivedParameters)
            throws NoSuchMethodException {

        for (Constructor<?> constructor : classObject.getConstructors()) {
            Class<?>[] constructorParametersTypes = constructor.getParameterTypes();

            if (constructorParametersTypes.length == receivedParameters.length
                    && matchingParameters(constructorParametersTypes, receivedParameters)) {

                return constructor;
            }
        }

        throw new NoSuchMethodException("No matching constructor found");
    }

    /**
     * Verify if the parameter types of the found actuator constructor match with the received parameter types.
     *
     * @param constructorParameterTypes Array of Class objects that represent the formal parameter types, in
     *                                  declaration order
     * @param receivedParameters        Array of Objects corresponding to the parameters received in the
     *                                  createActuator() method
     * @return True if all received parameters match with the found constructor parameter types, false otherwise.
     */
    private boolean matchingParameters(Class<?>[] constructorParameterTypes, Object[] receivedParameters) {
        for (int i = 0; i < constructorParameterTypes.length; i++) {
            if (!constructorParameterTypes[i].isAssignableFrom(receivedParameters[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates all the parameters to instantiate an actuator. Settings are not checked, since it can be nullable,
     * depending on the type of Actuator to be instantiated.
     *
     * @param parameters ActuatorName, ActuatorTypeID, DeviceID
     * @return True if all parameters are valid (not null), false otherwise.
     */
    private boolean validParameters(Object... parameters) {
        for (Object param : parameters) {
            if (param == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initializes the Configuration Object with the proper file path containing the required actuators' data.
     *
     * @throws ConfigurationException If file path is invalid.
     */
    private void initializeConfiguration(String filePath) throws ConfigurationException {
        Configurations configs = new Configurations();
        this.configuration = configs.properties(filePath);
    }

    /**
     * Extracts from the file the path to the Class of the pretended actuator type.
     *
     * @param actuatorTypeID ActuatorTypeID to get the correct path from the file
     * @return Optional container, which might be empty in case actuator type does not exist in the file.
     */
    private Optional<String> getPath(ActuatorTypeIDVO actuatorTypeID) {
        String strActuatorType = actuatorTypeID.getID();
        if (this.configuration.getString(strActuatorType) == null) return Optional.empty();
        return Optional.of(this.configuration.getString(strActuatorType));
    }

}
