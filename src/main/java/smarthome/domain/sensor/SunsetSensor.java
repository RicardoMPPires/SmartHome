package smarthome.domain.sensor;

import smarthome.domain.DomainID;
import smarthome.domain.sensor.externalservices.SunTimeServices;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.sensor.sensorvalues.SunTimeValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;

import java.time.ZonedDateTime;
import java.util.UUID;

public class SunsetSensor implements SunSensor {

    private final SensorIDVO sensorID;
    private SensorNameVO sensorName;
    private final SensorTypeIDVO sensorTypeID;
    private final DeviceIDVO deviceID;

    /**
     * Constructor for SunsetSensor. It validates the received VOs and creates a new SensorIDVO utilizing the UUID
     * library. Received VOs and SensorID are encapsulated within the object.
     *
     * @param sensorName   Sensor name
     * @param deviceID     Device ID
     * @param sensorTypeID SensorType ID
     */

    public SunsetSensor(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        if (areParamsNull(sensorName, deviceID, sensorTypeID)) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
        this.sensorID = new SensorIDVO(UUID.randomUUID());
    }

    /**
     * Constructor for SunsetSensor.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * It receives a SensorIDVO, SensorNameVO, DeviceIDVO and SensorTypeIDVO retrieved from the database.
     *
     * @param sensorID     Sensor ID
     * @param sensorName   Sensor name
     * @param deviceID     Device ID
     * @param sensorTypeID SensorType ID
     */
    public SunsetSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
    }

    /**
     * Method to validate if the received parameters are null. It receives a SensorNameVO, SensorTypeIDVO and DeviceIDVO
     * and returns a boolean.
     *
     * @param sensorName   Sensor name
     * @param sensorTypeID SensorType ID
     * @param deviceID     Device ID
     * @return boolean
     */

    private boolean areParamsNull(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        return sensorName == null || deviceID == null || sensorTypeID == null;
    }

    /**
     * Retrieves the sunset time using the provided date, GPS location, and sun time calculator.
     * This method calculates the sunset time using the provided date, GPS location, and sun time calculator
     * implementation. It then creates a SunTimeValue object using the specified value factory based on the
     * calculated sunset time.
     * The System Under Test (SUT) is the SunriseSensor class. The method takes four parameters: date, GPS location,
     * sun time calculator, and value factory. It checks if any of these parameters are null and throws an
     * IllegalArgumentException if any of them are null.
     * If all parameters are valid, the method computes the sunset time using the provided sun time calculator
     * implementation and creates a SunTimeValue object using the value factory.
     *
     * @param date The date for which the sunset time is to be calculated.
     * @param gpsLocation The GPS location (latitude and longitude) used to calculate the sunset time.
     * @param sunTimeCalculator The sun time calculator implementation used to compute the sunset time.
     * @param valueFactory The factory responsible for creating the SunTimeValue object.
     * @return The sunset time obtained using the provided parameters.
     * @throws IllegalArgumentException If any of the parameters are null.
     */

    public SunTimeValue getReading(String date, String gpsLocation, SunTimeServices sunTimeCalculator, SensorValueFactory valueFactory) {
        if (areParamsNull(date,gpsLocation,sunTimeCalculator,valueFactory)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        ZonedDateTime simValue = sunTimeCalculator.computeSunset(date,gpsLocation);
        return (SunTimeValue) valueFactory.createSensorValue(simValue,this.sensorTypeID);
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for SensorTypeID;
     */
    @Override
    public SensorTypeIDVO getSensorTypeID() {
        return sensorTypeID;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for Name;
     */
    @Override
    public SensorNameVO getSensorName() {
        return sensorName;
    }

    /**
     * Simple getter method
     * It's an override from DomainEntity interface
     *
     * @return The encapsulated VO for SensorID;
     */

    @Override
    public DomainID getId() {
        return this.sensorID;
    }

    /**
     * Simple getter method
     *
     * @return The encapsulated VO for DeviceID;
     */
    @Override
    public DeviceIDVO getDeviceID() {
        return deviceID;
    }

    /**
     * Validates any number of object params against null
     * @param params Any object param
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
}
