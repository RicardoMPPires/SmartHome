package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SensorExternalServices;
import smarthome.domain.DomainID;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.sensor.sensorvalues.SolarIrradianceValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.util.UUID;

public class SolarIrradianceSensor implements Sensor {

    private final SensorIDVO sensorID;
    private SensorNameVO sensorName;
    private final SensorTypeIDVO sensorTypeID;
    private final DeviceIDVO deviceID;

    /**
     * Constructor for SolarIrradianceSensor Class.
     * Starts by validating input parameter and proceeds with the creation of a unique sensor ID, using the Java's
     * built-in class named java.util.UUID.
     * @param sensorName Sensor name Value Object
     * @param deviceID Device ID Value Object where the sensor is going to be connected to
     * @param sensorTypeID ID Value Object of the sensor type chosen
     */
    public SolarIrradianceSensor(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID){
        if(invalidParameters(sensorName, deviceID, sensorTypeID))
            throw new IllegalArgumentException("Sensor parameters cannot be null");
        this.sensorID = new SensorIDVO(UUID.randomUUID());
        this.sensorName = sensorName;
        this.sensorTypeID = sensorTypeID;
        this.deviceID = deviceID;
    }


    /**
     * Constructor for SolarIrradianceSensor Class.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * It receives a SensorIDVO, SensorNameVO, DeviceIDVO and SensorTypeIDVO retrieved from the database.
     *
     * @param sensorID     the ID of the sensor
     * @param sensorName   the name of the sensor
     * @param deviceID     the ID of the device
     * @param sensorTypeID the type of the sensor
     */
    public SolarIrradianceSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.sensorTypeID = sensorTypeID;
        this.deviceID = deviceID;
    }

    /**
     * Retrieves the solar irradiance value from the provided sensor hardware using the given value factory.
     *
     * <p>
     * This method retrieves the solar irradiance value from the provided sensor hardware, which simulates
     * the sensor readings. It uses the specified value factory to create the appropriate sensor value object
     * based on the simulated value obtained from the hardware.
     * </p>
     *
     * @param simHardware The sensor hardware providing the simulated solar irradiance value.
     * @param valueFactory The factory responsible for creating the appropriate sensor value object.
     * @return The solar irradiance value obtained from the sensor hardware.
     * @throws IllegalArgumentException If either the simHardware or valueFactory parameter is null.
     */
    public SolarIrradianceValue getReading(SensorExternalServices simHardware, SensorValueFactory valueFactory) {
        if (invalidParameters(simHardware,valueFactory)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        String simValue = simHardware.getValue();
        return (SolarIrradianceValue) valueFactory.createSensorValue(simValue,this.sensorTypeID);
    }

    /**
     * Mandatory getter method to retrieve the SensorID, implemented from EntityDomain interface
     * @return The encapsulated VO for SensorID
     */
    @Override
    public DomainID getId() {
        return this.sensorID;
    }

    /**
     * Simple getter method
     * @return The encapsulated VO for Name
     */
    @Override
    public SensorNameVO getSensorName(){
        return this.sensorName;
    }

    /**
     * Simple getter method
     * @return The encapsulated VO for SensorTypeID
     */
    @Override
    public SensorTypeIDVO getSensorTypeID(){
        return this.sensorTypeID;
    }

    /**
     * Simple getter method
     * @return The encapsulated VO for DeviceID
     */
    @Override
    public DeviceIDVO getDeviceID(){
        return this.deviceID;
    }

    /**
     * Checks if the provided parameters are invalid, i.e., if any of them is null.
     *
     * @param parameters The parameters to be checked for nullity.
     * @return {@code true} if any of the parameters is null, {@code false} otherwise.
     */
    private boolean invalidParameters(Object... parameters){
        for(Object param : parameters){
            if(param == null){
                return true;
            }
        }
        return false;
    }
}
