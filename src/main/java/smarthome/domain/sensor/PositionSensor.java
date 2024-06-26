package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SensorExternalServices;
import smarthome.domain.sensor.sensorvalues.PositionValue;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.util.UUID;

/**
 * PositionSensor is a domain entity that represents a sensor that measures the position/value in a scale.
 */
public class PositionSensor implements Sensor {

    private SensorNameVO sensorName;
    private final DeviceIDVO deviceID;
    private final SensorTypeIDVO sensorType;
    private final SensorIDVO sensorID;


    /**
     * Constructor for PositionSensor, it validates the receiving VOs and generates a new SensorIDVO utilizing the UUID.
     * @param sensorName The name of the sensor.
     * @param deviceID The ID of the device to which the sensor is attached.
     * @param sensorType The type of the sensor.
     */
    public PositionSensor (SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorType){
        if(!areParamsValid(sensorName, deviceID, sensorType)){
            throw new IllegalArgumentException("Invalid Parameters");
        } else {
            this.sensorName = sensorName;
            this.deviceID = deviceID;
            this.sensorType = sensorType;
            this.sensorID = new SensorIDVO(UUID.randomUUID());
        }
    }


    /**
     * Constructor for PositionSensor.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * It receives a SensorIDVO, SensorNameVO, DeviceIDVO and SensorTypeIDVO retrieved from the database.
     *
     * @param sensorID   The ID of the sensor.
     * @param sensorName The name of the sensor.
     * @param deviceID   The ID of the device to which the sensor is attached.
     * @param sensorType The type of the sensor.
     */
    public PositionSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorType) {
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorType = sensorType;
        this.sensorID = sensorID;
    }

    /**
     * Retrieves the position reading using the provided SimHardware and SensorValueFactory.
     *
     * <p>
     * This method retrieves the position reading by querying the SimHardware for the current value and using the
     * provided SensorValueFactory to create a PositionValue object. It throws an IllegalArgumentException if either the
     * SimHardware or the SensorValueFactory is null.
     * </p>
     *
     * <p>
     * The method takes two parameters: simHardware, representing the SimHardware interface for accessing sensor data,
     * and valueFactory, representing the factory for creating sensor values. It ensures that both parameters are not
     * null before proceeding.
     * </p>
     *
     * <p>
     * Upon validation of parameters, the method retrieves the current value from the SimHardware and delegates the
     * creation of a PositionValue object to the valueFactory. It returns the created PositionValue object.
     * </p>
     *
     * <p>
     * If either the simHardware or the valueFactory parameter is null, the method throws an IllegalArgumentException
     * with the message "Invalid parameters".
     * </p>
     *
     * @param simHardware The SimHardware object representing the hardware interface for accessing sensor data.
     * @param valueFactory The SensorValueFactory object for creating sensor value objects.
     * @return The PositionValue object representing the current position reading.
     * @throws IllegalArgumentException if either simHardware or valueFactory is null.
     */
    public PositionValue getReading(SensorExternalServices simHardware, SensorValueFactory valueFactory) {
        if (simHardware == null || valueFactory == null){
            throw new IllegalArgumentException("Invalid parameters");
        }
        String simValue = simHardware.getValue();
        return (PositionValue) valueFactory.createSensorValue(simValue,this.sensorType);
    }

    /**
     * Getter method for the sensor ID.
     * @return  The sensor ID.
     */
    public SensorIDVO getId(){
        return this.sensorID;
    }

    /**
     * Getter method for the sensor name.
     * @return  The sensor name.
     */
    @Override
    public SensorNameVO getSensorName(){
        return this.sensorName;
    }

    /**
     * Getter method for the device ID.
     * @return  The device ID.
     */
    @Override
    public DeviceIDVO getDeviceID(){
        return this.deviceID;
    }

    /**
     * Getter method for the sensor type.
     * @return  The sensor type.
     */
    @Override
    public SensorTypeIDVO getSensorTypeID(){
        return this.sensorType;
    }


    /**
     * Method to validate the parameters of the constructor.
     * @param sensorName The name of the sensor.
     * @param deviceID The ID of the device to which the sensor is attached.
     * @param sensorType The type of the sensor.
     * @return True if the parameters are valid, false otherwise.
     */
    private boolean areParamsValid (SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorType){
        return sensorName != null && deviceID != null && sensorType != null;
    }

}
