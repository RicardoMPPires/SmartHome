package smarthome.domain.sensor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
public class SensorFactoryImpl implements SensorFactory{
    private final Configuration configuration;


    /**
     * Constructs an instance of V1SensorFactory with the provided configuration file path.
     * This constructor initializes a V1SensorFactory by loading configurations from the specified file path.
     * It uses the Apache Commons Configuration library to load the properties from the file.
     * @param path The path to the configuration file.
     * @throws IllegalArgumentException if there is an error reading the configuration file. This exception is thrown to
     * indicate an error while loading properties from the file.
     */
    public SensorFactoryImpl(@Value("${filepath}") String path) {
        try {
            Configurations configs = new Configurations();
            this.configuration = configs.properties(new File(path));
        } catch (NullPointerException | ConfigurationException e){
            throw new IllegalArgumentException("Error reading file");
        }
    }

    /**
     * Creates a Sensor object based on the provided sensor information.
     * This method constructs a Sensor object using reflection based on the provided SensorName, DeviceID, and SensorTypeID.
     * It first checks if the provided parameters are not null and if the sensor type is permitted.
     * It then retrieves the sensor type's class path from the loaded configuration file, dynamically creates an instance
     * of the specified class using reflection, and returns the created Sensor object.
     * @param sensorName   The name of the sensor.
     * @param deviceID     The ID of the device to which the sensor is attached.
     * @param sensorTypeID The type ID of the sensor.
     * @return A Sensor object created based on the provided information. It returns null if the parameters are null, the
     * sensor type is not permitted, or if there is an error during object creation.
     */
    public Sensor createSensor (SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID){
        if (!areParamsNull(sensorName,deviceID,sensorTypeID) && isTypePermitted(sensorTypeID.getID())){
            try{
                String sensorTypePath = this.configuration.getString(sensorTypeID.getID());
                Class<?> classObj = Class.forName(sensorTypePath);
                Constructor<?> constructor = classObj.getConstructor(SensorNameVO.class, DeviceIDVO.class, SensorTypeIDVO.class);
                return (Sensor) constructor.newInstance(sensorName, deviceID, sensorTypeID);
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }


    /**
     * Creates a Sensor object based on the provided sensor information, including the SensorID.
     * Context: This method is used when we want to create a sensor object from a sensorDataModel.
     * This method constructs a Sensor object using reflection based on the provided SensorID, SensorName, DeviceID, and SensorTypeID.
     * It retrieves the sensor type's class path from the loaded configuration file, dynamically creates an instance
     * of the specified class using reflection, and returns the created Sensor object.
     *
     * @param sensorID     The ID of the sensor.
     * @param sensorName   The name of the sensor.
     * @param deviceID     The ID of the device to which the sensor is attached.
     * @param sensorTypeID The type ID of the sensor.
     * @return A Sensor object created based on the provided information.
     */
    @Override
    public Sensor createSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        try {
            String sensorTypePath = this.configuration.getString(sensorTypeID.getID());
            Class<?> classObj = Class.forName(sensorTypePath);
            Constructor<?> constructor = classObj.getConstructor(SensorIDVO.class, SensorNameVO.class, DeviceIDVO.class, SensorTypeIDVO.class);
            return (Sensor) constructor.newInstance(sensorID, sensorName, deviceID, sensorTypeID);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Verifies parameters are not null;
     * @param params Object to validate
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
     * Queries the configuration object by calling the method containsKey, which matches the input string with a key;
     * @param type Type of the sensor.
     * @return True or false
     */
    private boolean isTypePermitted(String type){
        return this.configuration.containsKey(type);
    }
}
