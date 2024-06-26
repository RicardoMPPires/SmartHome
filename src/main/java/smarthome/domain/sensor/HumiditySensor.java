package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SensorExternalServices;
import smarthome.domain.sensor.sensorvalues.HumidityValue;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.util.UUID;

public class HumiditySensor implements Sensor {

    private SensorNameVO sensorName;
    private final DeviceIDVO deviceID;
    private final SensorTypeIDVO sensorTypeID;
    private final SensorIDVO sensorID;

    /**
     * Constructor for humidity sensor. It can only be instantiated if parameters are not null.
     * It then generates a random UUID for the sensor ID.
     * @param sensorName Sensor name
     * @param deviceID DeviceID
     * @param sensorTypeID SensorTypeID
     * @throws IllegalArgumentException If parameters out of bounds.
     */
    public HumiditySensor(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        if (areParamsNull(sensorName,deviceID,sensorTypeID)){
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
        this.sensorID = new SensorIDVO(UUID.randomUUID());
    }

    /**
     * Constructor for humidity sensor.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * It receives a SensorIDVO, SensorNameVO, DeviceIDVO and SensorTypeIDVO retrieved from the database.
     *
     * @param sensorID     SensorID
     * @param sensorName   SensorName
     * @param deviceID     DeviceID
     * @param sensorTypeID SensorTypeID
     */
    public HumiditySensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
    }

    /**
     * Retrieves the humidity reading using the provided SimHardware and SensorValueFactory.
     *
     * <p>
     * This method retrieves the humidity reading by querying the SimHardware for the current value and using the provided SensorValueFactory to create a HumidityValue object. It throws an IllegalArgumentException if either the SimHardware or the SensorValueFactory is null.
     * </p>
     *
     * <p>
     * The method takes two parameters: simHardware, representing the SimHardware interface for accessing sensor data, and valueFactory, representing the factory for creating sensor values. It ensures that both parameters are not null before proceeding.
     * </p>
     *
     * <p>
     * Upon validation of parameters, the method retrieves the current value from the SimHardware and delegates the creation of a HumidityValue object to the valueFactory. It returns the created HumidityValue object.
     * </p>
     *
     * <p>
     * If either the simHardware or the valueFactory parameter is null, the method throws an IllegalArgumentException with the message "Invalid parameters".
     * </p>
     *
     * @param simHardware The SimHardware object representing the hardware interface for accessing sensor data.
     * @param valueFactory The SensorValueFactory object for creating sensor value objects.
     * @return The HumidityValue object representing the current humidity reading.
     * @throws IllegalArgumentException if either simHardware or valueFactory is null.
     */
    public HumidityValue getReading(SensorExternalServices simHardware, SensorValueFactory valueFactory) {
        if (simHardware == null || valueFactory == null){
            throw new IllegalArgumentException("Invalid parameters");
        }
        String simValue = simHardware.getValue();
        return (HumidityValue) valueFactory.createSensorValue(simValue,this.sensorTypeID);
    }

    /**
     * This method returns the sensor name.
     * @return SensorNameVO with the sensor name.
     */
    @Override
    public SensorNameVO getSensorName() {
        return sensorName;
    }

    /**
     * This method returns the device ID.
     * @return DeviceIDVO with the device ID.
     */
    @Override
    public DeviceIDVO getDeviceID() {
        return deviceID;
    }

    /**
     * This method returns the sensor type ID.
     * @return SensorTypeIDVO with the sensor type ID.
     */
    @Override
    public SensorTypeIDVO getSensorTypeID() {
        return sensorTypeID;
    }

    /**
     * This method returns the sensor ID.
     * @return SensorIDVO with the sensor ID.
     */
    @Override
    public SensorIDVO getId() {
        return this.sensorID;
    }

    /**
     * This method checks if the parameters are null, being used both in the constructor and in the getReading method.
     * If any parameters are null, it returns true. Otherwise, it returns false.
     * @param params Object parameters
     * @return boolean
     */
    private boolean areParamsNull(Object... params){
        for (Object param : params){
            if (param == null){
                return true;
            }
        }
        return false;
    }
}
