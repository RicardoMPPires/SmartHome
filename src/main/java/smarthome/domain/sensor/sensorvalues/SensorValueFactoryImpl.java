package smarthome.domain.sensor.sensorvalues;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;

@Component
public class SensorValueFactoryImpl implements SensorValueFactory{

    private final Configuration configuration;

    /**
     * Constructs a new instance of SensorValueFactoryImpl, which is responsible for
     * creating SensorValueObjects based on configurations loaded from a properties file
     * located at the specified path.
     *
     * @param path The property path is referred in the application.properties file.
     *             The path to the properties file containing configurations for the factory.
     *             The properties file should contain key-value pairs representing sensor types
     *             and their corresponding valueObject's path.
     * @throws IllegalArgumentException If the path is null or if an error occurs while reading
     *                                  or parsing the configurations from the properties file.
     *                                  Possible reasons for failure include a null pointer
     *                                  exception, or if the properties file is invalid or
     *                                  cannot be read.
     */
    public SensorValueFactoryImpl (@Value("${filePathValue}")String path) {
        try {
            Configurations configs = new Configurations();
            this.configuration = configs.properties(new File(path));
        } catch (NullPointerException | ConfigurationException e){
            throw new IllegalArgumentException("Error reading file");
        }
    }

    /**
     * Creates a new SensorValueObject based on the provided reading string and sensor type ID.
     * The sensor type ID is used to retrieve the path to the specific valueObject constructor
     * from the configuration. Reflection is employed to dynamically load the class and invoke
     * its constructor with the reading string.
     * The SensorValueObject utilizes generics, and its implementation's constructors are designed
     * to receive a string reading, parse it into the related primitive value, and instantiate a
     * SensorValueObject with the specific wrapper for that primitive type.
     *
     * @param reading      The reading string to be parsed into the value of the SensorValueObject.
     * @param sensorTypeID The sensor type ID identifying the type of sensor value to create.
     * @return A new SensorValueObject with the parsed value from the reading string, or null if:
     *         - The sensor type ID is null or invalid,
     *         - The sensor type ID is not permitted based on the configuration,
     *         - An error occurs during value creation or parsing.
     * @throws NullPointerException      If the sensorTypeID parameter is null.
     * @throws IllegalArgumentException  If the sensorTypeID is not permitted or if the configuration
     *                                   does not contain the required information.
     * @throws NumberFormatException     If the reading string cannot be parsed into a numeric value.
     * @throws ClassNotFoundException    If the class specified by the value path cannot be found.
     * @throws NoSuchMethodException     If the constructor with a String parameter cannot be found
     *                                   in the specified class.
     * @throws InvocationTargetException If an error occurs while invoking the constructor.
     * @throws InstantiationException    If the class represents an abstract class, an interface,
     *                                   or if the instantiation fails for some other reason.
     * @throws IllegalAccessException    If the constructor is not accessible due to Java language
     *                                   access control.
     */
    @Override
    public SensorValueObject<?> createSensorValue(String reading, SensorTypeIDVO sensorTypeID) {
        if (areParamsValid(sensorTypeID) && isTypePermitted(sensorTypeID.getID())){
            try{
                String valuePath = this.configuration.getString(sensorTypeID.getID());

                if (sensorTypeID.getID().equals("SunsetSensor") || sensorTypeID.getID().equals("SunriseSensor")){
                    ZonedDateTime dateTime = ZonedDateTime.parse(reading);
                    return createSunValues(dateTime,valuePath);
                }

                Class<?> classObj = Class.forName(valuePath);
                Constructor<?> constructor = classObj.getConstructor(String.class);
                return (SensorValueObject<?>) constructor.newInstance(reading);
            } catch (NumberFormatException | NullPointerException | ClassNotFoundException | NoSuchMethodException
                     | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Creates a sensor value object based on the provided reading and sensor type ID.
     *
     * <p>This method creates a sensor value object using the reading obtained at a specific date and time
     * and the sensor type ID. It verifies that the parameters are not null and that the sensor type ID
     * is permitted. If successful, it attempts to create the sensor value object using the configuration
     * path associated with the sensor type ID.</p>
     * @param reading      The date and time at which the reading was taken.
     * @param sensorTypeID The ID of the sensor type for which the value object is created.
     * @return A sensor value object corresponding to the reading, or {@code null} if creation fails.
     */

    public SensorValueObject<?> createSensorValue(ZonedDateTime reading, SensorTypeIDVO sensorTypeID){
        if (areParamsValid(sensorTypeID) && isTypePermitted(sensorTypeID.getID())){

            String valuePath = this.configuration.getString(sensorTypeID.getID());
            return createSunValues(reading,valuePath);
        }
        return null;
    }

    /**
     * Creates a SensorValueObject for sun-related values using the provided reading and valuePath.
     *
     * <p>
     * This method dynamically instantiates a SensorValueObject using reflection,
     * based on the provided reading and valuePath parameters.
     * The reading parameter represents the timestamp of the sun-related event,
     * and the valuePath parameter specifies the class path for the desired SensorValueObject implementation.
     * </p>
     *
     * <p>
     * If successful, the method returns the instantiated SensorValueObject.
     * If any exception occurs during the process, such as parsing errors or class instantiation failures,
     * the method returns null.
     * </p>
     *
     * @param reading    The timestamp of the sun-related event.
     * @param valuePath  The class path for the desired SensorValueObject implementation.
     * @return A SensorValueObject instantiated with the provided reading, or null if an error occurs.
     */
    private SensorValueObject<?> createSunValues(ZonedDateTime reading, String valuePath){
        try{
            Class<?> classObj = Class.forName(valuePath);
            Constructor<?> constructor = classObj.getConstructor(ZonedDateTime.class);
            return (SensorValueObject<?>) constructor.newInstance(reading);
        } catch (NumberFormatException | NullPointerException | ClassNotFoundException | NoSuchMethodException
                 | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Checks if the specified whether the configuration contains the key corresponding to
     * the provided sensor type.
     *
     * @param type The sensor type to check for permission.
     * @return true if the sensor type is permitted according to the configuration,
     * false otherwise.
     */
    private boolean isTypePermitted(String type){
        return this.configuration.containsKey(type);
    }

    /**
     * Checks if any of the provided parameters is null.
     *
     * <p>
     * This method takes a variable number of parameters and iterates through each one.
     * If any parameter is found to be null during iteration, the method immediately returns true,
     * indicating that at least one parameter is null.
     * If all parameters are non-null, the method returns false.
     * </p>
     *
     * @param params The parameters to be checked for null.
     * @return true if any of the parameters is null, false otherwise.
     */
    private boolean areParamsValid(Object... params){
        for (Object param : params){
            if (param == null){
                return false;
            }
        }
        return true;
    }
}
