package smarthome.domain.sensor;

import smarthome.domain.DomainID;
import smarthome.domain.sensor.externalservices.SunTimeServices;
import smarthome.domain.sensor.sensorvalues.SensorValueFactory;
import smarthome.domain.sensor.sensorvalues.SunTimeValue;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;

import java.time.ZonedDateTime;
import java.util.UUID;

public class SunriseSensor implements SunSensor {

    private final SensorIDVO sensorID;
    private SensorNameVO sensorName;
    private final SensorTypeIDVO sensorTypeID;
    private final DeviceIDVO deviceID;

    /**
     * Constructs an instance of SunriseSensor with the provided sensor information.
     * This constructor initializes a SunriseSensor with the provided SensorName, DeviceID, and SensorTypeID.
     * It generates a random SensorID for the SunriseSensor.
     * @param sensorName   The name of the SunriseSensor.
     * @param deviceID     The ID of the device to which the SunriseSensor is attached.
     * @param sensorTypeID The type ID of the SunriseSensor.
     * @throws IllegalArgumentException if any of the provided parameters (sensorName, deviceID, sensorTypeID) are null.
     * This exception is thrown to indicate invalid or missing parameters.
     */
    public SunriseSensor(SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        if (areParamsNull(sensorName,deviceID,sensorTypeID)){
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
        this.sensorID = new SensorIDVO(UUID.randomUUID());
    }

    /**
     * Constructs an instance of SunriseSensor with the provided sensor information.
     * Context: This constructor is used when we want to create a sensor object from a sensorDataModel.
     * This constructor initializes a SunriseSensor with the provided SensorID, SensorName, DeviceID, and SensorTypeID,
     * retrieved from the database.
     *
     * @param sensorID     The ID of the SunriseSensor.
     * @param sensorName   The name of the SunriseSensor.
     * @param deviceID     The ID of the device to which the SunriseSensor is attached.
     * @param sensorTypeID The type ID of the SunriseSensor.
     */
    public SunriseSensor(SensorIDVO sensorID, SensorNameVO sensorName, DeviceIDVO deviceID, SensorTypeIDVO sensorTypeID) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.deviceID = deviceID;
        this.sensorTypeID = sensorTypeID;
    }

    /**
     * Retrieves the sunrise time for the specified date and GPS location.
     * This method calls the computeSunrise method of the provided SunTimeServices to calculate the sunrise time
     * based on the given date and GPS location.
     * @param date The date for which to calculate the sunrise time.
     * @param gpsLocation The GPS location (latitude and longitude) in string format.
     * @param sunTimeCalculator The SunTimeServices instance used to calculate the sunrise time.
     * @return A ValueObject containing the calculated sunrise time as a ZonedDateTime.
     * @throws IllegalArgumentException if the sunTimeCalculator parameter is null. This exception is thrown to indicate
     * an invalid or missing external service.
     */
    public SunTimeValue getReading(String date, String gpsLocation, SunTimeServices sunTimeCalculator, SensorValueFactory valueFactory) {
        if (areParamsNull(date,gpsLocation,sunTimeCalculator,valueFactory)){
            throw new IllegalArgumentException("Invalid parameters");
        }
        ZonedDateTime simValue = sunTimeCalculator.computeSunrise(date,gpsLocation);
        return (SunTimeValue) valueFactory.createSensorValue(simValue,this.sensorTypeID);
    }

    /**
     * Simple getter method
     * @return The encapsulated VO for Name;
     */
    @Override
    public SensorNameVO getSensorName(){
        return this.sensorName;
    }

    /**
     * Simple getter method
     * @return The encapsulated VO for SensorTypeID;
     */
    @Override
    public SensorTypeIDVO getSensorTypeID(){
        return this.sensorTypeID;
    }

    /**
     * Simple getter method
     * @return The encapsulated VO for DeviceID;
     */
    @Override
    public DeviceIDVO getDeviceID(){
        return this.deviceID;
    }

    /**
     * Simple getter method, implemented from EntityDomain interface
     * @return The encapsulated VO for SensorID;
     */
    @Override
    public DomainID getId() {
        return this.sensorID;
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