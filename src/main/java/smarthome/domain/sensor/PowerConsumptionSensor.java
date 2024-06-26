package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SensorExternalServices;
import smarthome.domain.sensor.sensorvalues.PowerConsumptionValue;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.util.UUID;

public class PowerConsumptionSensor implements Sensor {

    private SensorNameVO sensorName;
    private final SensorIDVO sensorID;
    private final SensorTypeIDVO sensorTypeID;
    private final DeviceIDVO deviceID;

    /**
     * Constructor for PowerConsumptionSensor. It validates the receiving VOs and creates a new SensorIDVO utilizing
     * the UUID library. Received VOs and SensorID are encapsulated within the object.
     * @param sensorName Sensor Name
     * @param deviceID Device ID
     * @param sensorTypeID SensorType ID
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public PowerConsumptionSensor(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        if (areParamsNull(sensorName,deviceID,sensorTypeID)){
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
        this.sensorID = new SensorIDVO(UUID.randomUUID());
    }


    /**
     * Constructor for PowerConsumptionSensor.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * It receives a SensorIDVO, SensorNameVO, DeviceIDVO and SensorTypeIDVO retrieved from the database.
     *
     * @param sensorID     Sensor ID
     * @param sensorName   Sensor Name
     * @param deviceID     Device ID
     * @param sensorTypeID SensorType ID
     */
    public PowerConsumptionSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
    }


    /**
     * Simple getter method, implemented from EntityDomain interface
     * @return The encapsulated VO for SensorID;
     */
    @Override
    public SensorIDVO getId() {
        return this.sensorID;
    }


    /**
     * Simple getter method
     * @return The encapsulated VO for Name;
     */
    @Override
    public SensorNameVO getSensorName(){
        return sensorName;
    }


    /**
     * Simple getter method
     * @return The encapsulated VO for SensorTypeID;
     */
    @Override
    public SensorTypeIDVO getSensorTypeID(){
        return sensorTypeID;
    }


    /**
     * Simple getter method
     * @return The encapsulated VO for DeviceID;
     */
    @Override
    public DeviceIDVO getDeviceID(){
        return deviceID;
    }

    /**
     * Retrieves the power consumption value from the provided sensor hardware using the given value factory.
     *
     * <p>This method retrieves the power consumption value from the provided sensor hardware, which simulates
     * the sensor readings. It uses the specified value factory to create the appropriate sensor value object
     * based on the simulated value obtained from the hardware.</p>
     *
     * @param simHardware The sensor hardware providing the simulated power consumption value.
     * @param valueFactory The factory responsible for creating the appropriate sensor value object.
     * @return The power consumption value obtained from the sensor hardware.
     * @throws IllegalArgumentException If either the simHardware or valueFactory parameter is null.
     */
    public PowerConsumptionValue getReading(SensorExternalServices simHardware, SensorValueFactory valueFactory) {
        if (simHardware == null || valueFactory == null){
            throw new IllegalArgumentException("Invalid parameters");
        }
        String simValue = simHardware.getValue();
        return (PowerConsumptionValue) valueFactory.createSensorValue(simValue,this.sensorTypeID);
    }


    /**
     * Validates any number of object params against null
     * @param params Any number of objects
     * @return True if any of the params are null, False otherwise
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
