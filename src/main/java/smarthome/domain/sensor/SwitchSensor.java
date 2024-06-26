package smarthome.domain.sensor;

import smarthome.domain.sensor.externalservices.SensorExternalServices;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.sensor.sensorvalues.SwitchValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.util.UUID;

public class SwitchSensor implements Sensor {

    private SensorNameVO nameVO;
    private final DeviceIDVO deviceIDVO;
    private final SensorTypeIDVO sensorTypeIDVO;
    private final SensorIDVO sensorIDVO;

    /**
     * Constructor for SwitchSensor, it validates the receiving VOs and creates a new SensorIDVO utilizing the UUID
     * library. Received VOs and SensorID are encapsulated within the object.
     *
     * @param nameVO         Sensor name
     * @param deviceIDVO     Device ID
     * @param sensorTypeIDVO SensorType ID
     */
    public SwitchSensor(SensorNameVO nameVO, DeviceIDVO deviceIDVO, SensorTypeIDVO sensorTypeIDVO) {
        if (nameVO == null || deviceIDVO == null || sensorTypeIDVO == null)
            throw new IllegalArgumentException("Invalid parameters.");

        this.sensorIDVO = new SensorIDVO(UUID.randomUUID());
        this.nameVO = nameVO;
        this.sensorTypeIDVO = sensorTypeIDVO;
        this.deviceIDVO = deviceIDVO;
    }


    /**
     * Constructor for SwitchSensor.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * It receives a SensorIDVO, SensorNameVO, DeviceIDVO and SensorTypeIDVO retrieved from the database.
     *
     * @param sensorIDVO     Sensor ID
     * @param nameVO         Sensor name
     * @param deviceIDVO     Device ID
     * @param sensorTypeIDVO SensorType ID
     */
    public SwitchSensor(SensorIDVO sensorIDVO, SensorNameVO nameVO, DeviceIDVO deviceIDVO, SensorTypeIDVO sensorTypeIDVO) {
        this.sensorIDVO = sensorIDVO;
        this.nameVO = nameVO;
        this.sensorTypeIDVO = sensorTypeIDVO;
        this.deviceIDVO = deviceIDVO;
    }

    /**
     * Simple getter method, implemented from EntityDomain interface
     *
     * @return The encapsulated VO for SensorID;
     */
    @Override
    public SensorIDVO getId() {
        return sensorIDVO;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for Name;
     */
    @Override
    public SensorNameVO getSensorName() {
        return nameVO;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for SensorTypeID;
     */
    @Override
    public SensorTypeIDVO getSensorTypeID() {
        return sensorTypeIDVO;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for DeviceID;
     */
    @Override
    public DeviceIDVO getDeviceID() {
        return deviceIDVO;
    }

    /**
     * Retrieves the reading from the specified sensor hardware and returns a SwitchValue object.
     * This method retrieves the reading from the provided sensor hardware using the SensorExternalServices interface
     * and returns a SwitchValue object representing the obtained reading. It delegates the creation of the SwitchValue
     * object to the provided SensorValueFactory.
     *
     * @param simHardware The sensor hardware from which to retrieve the reading.
     * @param valueFactory The factory responsible for creating SwitchValue objects based on the obtained readings.
     * @return A SwitchValue object representing the obtained reading.
     * @throws IllegalArgumentException If either the simHardware or valueFactory parameter is null.
     */

    public SwitchValue getReading(SensorExternalServices simHardware, SensorValueFactory valueFactory) {
        if (areParamsNull(simHardware,valueFactory)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        String simValue = simHardware.getValue();
        return (SwitchValue) valueFactory.createSensorValue(simValue,this.sensorTypeIDVO);
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
